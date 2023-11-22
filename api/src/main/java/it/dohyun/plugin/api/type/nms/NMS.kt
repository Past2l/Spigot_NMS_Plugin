package it.dohyun.plugin.api.type.nms

import org.bukkit.entity.Player

interface NMS {
    fun sendPacket(player: Player, packet: Any)
    fun setTabList(player: Player, header: String, footer: String)
}