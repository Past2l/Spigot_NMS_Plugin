package it.dohyun.plugin.nms.v1_14_R1.util

import it.dohyun.plugin.api.type.nms.NMS
import org.bukkit.plugin.Plugin

class NMS(val plugin: Plugin) : NMS {
    private val PACKET = Packet(plugin)
}