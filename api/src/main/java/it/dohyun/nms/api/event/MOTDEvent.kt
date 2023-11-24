package it.dohyun.nms.api.event

import it.dohyun.nms.api.config.Config
import it.dohyun.nms.type.config.ConfigFormatOption
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.server.ServerListPingEvent
import kotlin.math.roundToInt

class MOTDEvent: Listener {
    @EventHandler
    fun onServerListPing(event: ServerListPingEvent) {
        val motd = Config.data.motd
        event.motd = Config.format(
            if (!motd.center)
                motd.content.joinToString("\n")
            else
                motd.content.joinToString("\n") {
                    val content = Config.format(
                        Regex("&[0-9a-fk-orA-FK-OR]").replace(
                            it, ""
                        )
                    )
                    val korean = Regex("[ㄱ-ㅎㅏ-ㅣ가-힣]").findAll(it).count()
                    val length = (27 - korean / 2.7 - (content.length - 1) / 1.8).roundToInt()
                    (if (length > 0) " ".repeat(length) else "") + it
                },
            ConfigFormatOption(trim = false)
        )
    }
}