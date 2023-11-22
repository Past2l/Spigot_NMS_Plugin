package it.dohyun.nms.nms.v1_16_R3.util

import it.dohyun.nms.api.type.nms.NMS
import net.minecraft.server.v1_16_R3.Packet
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin

class NMS(plugin: Plugin) : NMS {
    private val packet = Packet(plugin)

    override fun sendPacket(player: Player, packet: Any) {
        val connection = (player as CraftPlayer).handle.playerConnection
        connection.sendPacket(packet as Packet<*>)
    }

    override fun setTabList(player: Player, header: String, footer: String) {
        sendPacket(player, packet.setTabList(header, footer))
    }
}