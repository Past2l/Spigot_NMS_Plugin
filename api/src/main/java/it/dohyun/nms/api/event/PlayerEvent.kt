package it.dohyun.nms.api.event

import it.dohyun.nms.api.config.Config
import it.dohyun.nms.api.gui.TabList
import it.dohyun.nms.type.config.ConfigFormatOption
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.event.player.PlayerJoinEvent

class PlayerEvent: Listener {
    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        TabList.setHeaderFooter()
    }

    @EventHandler
    fun onChat(event: AsyncPlayerChatEvent) {
        event.format = Config.format(
            Config.data.chat,
            ConfigFormatOption(
                player = event.player,
            ),
        )
            .replace("%chat.message%", event.message)
            .replace("%", "%%")
    }
}