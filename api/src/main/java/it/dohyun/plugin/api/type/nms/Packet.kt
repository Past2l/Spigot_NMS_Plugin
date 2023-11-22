package it.dohyun.plugin.api.type.nms

import org.bukkit.entity.Player

interface Packet {
    fun setTabList(header: String, footer: String): Any
}