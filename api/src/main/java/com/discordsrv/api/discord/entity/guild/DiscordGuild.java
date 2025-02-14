/*
 * This file is part of the DiscordSRV API, licensed under the MIT License
 * Copyright (c) 2016-2022 Austin "Scarsz" Shapiro, Henri "Vankka" Schubin and DiscordSRV contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.discordsrv.api.discord.entity.guild;

import com.discordsrv.api.DiscordSRVApi;
import com.discordsrv.api.discord.entity.Snowflake;
import com.discordsrv.api.placeholder.annotation.Placeholder;
import net.dv8tion.jda.api.entities.Guild;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * A Discord server.
 */
public interface DiscordGuild extends Snowflake {

    /**
     * Gets the name of this Discord guild.
     * @return the guild's name
     */
    @Placeholder("server_name")
    @NotNull
    String getName();

    /**
     * Gets the member count of the guild.
     * @return the guild's member count
     */
    @Placeholder("server_member_count")
    int getMemberCount();

    /**
     * Gets a Discord guild member by id from the cache, the provided entity can be cached and will not update if it changes on Discord.
     * @param id the id for the Discord guild member
     * @return the Discord guild member from the cache
     */
    @NotNull
    Optional<DiscordGuildMember> getMemberById(long id);

    /**
     * Gets the members of this server that are in the cache.
     * @return the Discord server members that are currently cached
     */
    @NotNull
    Set<DiscordGuildMember> getCachedMembers();

    /**
     * Gets a Discord role by id from the cache, the provided entity can be cached and will not update if it changes on Discord.
     * @param id the id for the Discord role
     * @return the Discord role from the cache
     */
    @NotNull
    Optional<DiscordRole> getRoleById(long id);

    /**
     * Gets the roles in this Discord server.
     * @return an ordered list of the roles in this Discord server
     */
    @NotNull
    List<DiscordRole> getRoles();

    /**
     * Returns the JDA representation of this object. This should not be used if it can be avoided.
     * @return the JDA representation of this object
     * @see DiscordSRVApi#jda()
     */
    Guild getAsJDAGuild();
}
