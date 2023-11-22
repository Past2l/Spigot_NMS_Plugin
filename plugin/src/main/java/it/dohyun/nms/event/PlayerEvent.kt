package it.dohyun.nms.event

import it.dohyun.nms.api.gui.TabList
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerEvent: Listener {
    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        TabList.setHeaderFooter()
    }
}