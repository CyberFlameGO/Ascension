/*
 * This file is part of DiscordSRV, licensed under the GPLv3 License
 * Copyright (c) 2016-2022 Austin "Scarsz" Shapiro, Henri "Vankka" Schubin and DiscordSRV contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.discordsrv.common.discord.api.entity.channel;

import com.discordsrv.api.discord.api.entity.channel.DiscordTextChannel;
import com.discordsrv.api.discord.api.entity.channel.DiscordThreadChannel;
import com.discordsrv.api.discord.api.entity.guild.DiscordGuild;
import com.discordsrv.api.discord.api.entity.message.ReceivedDiscordMessage;
import com.discordsrv.api.discord.api.entity.message.SendableDiscordMessage;
import com.discordsrv.common.DiscordSRV;
import com.discordsrv.common.discord.api.entity.guild.DiscordGuildImpl;
import com.discordsrv.common.discord.api.entity.message.ReceivedDiscordMessageImpl;
import com.discordsrv.common.discord.api.entity.message.util.SendableDiscordMessageUtil;
import net.dv8tion.jda.api.entities.IThreadContainer;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.ThreadChannel;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class DiscordThreadChannelImpl extends DiscordMessageChannelImpl<ThreadChannel> implements DiscordThreadChannel {

    private final DiscordTextChannel textChannel;
    private final DiscordGuild guild;

    public DiscordThreadChannelImpl(DiscordSRV discordSRV, ThreadChannel thread) {
        super(discordSRV, thread);

        IThreadContainer container = thread.getParentChannel();
        this.textChannel = container instanceof TextChannel
                           ? new DiscordTextChannelImpl(discordSRV, (TextChannel) container)
                           : null;
        this.guild = new DiscordGuildImpl(discordSRV, thread.getGuild());
    }

    @Override
    public @NotNull CompletableFuture<ReceivedDiscordMessage> sendMessage(@NotNull SendableDiscordMessage message) {
        if (message.isWebhookMessage()) {
            throw new IllegalArgumentException("Cannot send webhook messages to ThreadChannels");
        }

        return discordSRV.discordAPI().mapExceptions(
                channel.sendMessage(SendableDiscordMessageUtil.toJDA(message))
                        .submit()
                        .thenApply(msg -> ReceivedDiscordMessageImpl.fromJDA(discordSRV, msg))
        );
    }

    @Override
    public CompletableFuture<Void> deleteMessageById(long id, boolean webhookMessage) {
        if (webhookMessage) {
            throw new IllegalArgumentException("ThreadChannels do not contain webhook messages");
        }

        return discordSRV.discordAPI().mapExceptions(
                channel.deleteMessageById(id).submit()
        );
    }

    @Override
    public @NotNull CompletableFuture<ReceivedDiscordMessage> editMessageById(long id, @NotNull SendableDiscordMessage message) {
        if (message.isWebhookMessage()) {
            throw new IllegalArgumentException("Cannot send webhook messages to ThreadChannels");
        }

        return discordSRV.discordAPI().mapExceptions(
                channel.editMessageById(id, SendableDiscordMessageUtil.toJDA(message))
                        .submit()
                        .thenApply(msg -> ReceivedDiscordMessageImpl.fromJDA(discordSRV, msg))
        );
    }

    @Override
    public @NotNull String getName() {
        return channel.getName();
    }

    @Override
    public @NotNull DiscordGuild getGuild() {
        return guild;
    }

    @Override
    public @NotNull DiscordTextChannel getParentChannel() {
        return textChannel;
    }

    @Override
    public ThreadChannel getAsJDAThreadChannel() {
        return channel;
    }

    @Override
    public String toString() {
        return "Thread:" + getName() + "(" + Long.toUnsignedString(getId()) + " in " + textChannel + ")";
    }
}
