package it.dohyun.plugin.event

import it.dohyun.plugin.api.gui.TabList
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerEvent: Listener {
    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        TabList.setHeaderFooter()
    }
}