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

package com.discordsrv.bukkit.listener;

import com.discordsrv.api.component.MinecraftComponent;
import com.discordsrv.api.event.events.message.receive.game.DeathMessageReceiveEvent;
import com.discordsrv.api.player.DiscordSRVPlayer;
import com.discordsrv.bukkit.BukkitDiscordSRV;
import com.discordsrv.bukkit.component.util.PaperComponentUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class BukkitDeathListener implements Listener {

    private final BukkitDiscordSRV discordSRV;

    public BukkitDeathListener(BukkitDiscordSRV discordSRV) {
        this.discordSRV = discordSRV;
    }

    @SuppressWarnings("deprecation") // Paper
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerDeath(PlayerDeathEvent event) {
        DiscordSRVPlayer player = discordSRV.playerProvider().player(event.getEntity());
        MinecraftComponent component = PaperComponentUtil.getComponent(discordSRV, event, "deathMessage", PlayerDeathEvent::getDeathMessage);

        discordSRV.scheduler().run(() -> discordSRV.eventBus().publish(
                new DeathMessageReceiveEvent(player, null, component, event.isCancelled())));
    }
}
