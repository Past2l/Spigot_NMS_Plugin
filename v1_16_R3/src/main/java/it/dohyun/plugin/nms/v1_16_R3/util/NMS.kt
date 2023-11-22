package it.dohyun.plugin.nms.v1_16_R3.util

import it.dohyun.plugin.api.type.nms.NMS
import net.minecraft.server.v1_16_R3.Packet
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin

class NMS(val plugin: Plugin) : NMS {
    private val PACKET = Packet(plugin)

    override fun sendPacket(player: Player, packet: Any) {
        val connection = (player as CraftPlayer).handle.playerConnection
        connection.sendPacket(packet as Packet<*>)
    }
}