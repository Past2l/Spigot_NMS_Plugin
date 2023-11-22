package it.dohyun.plugin.nms.v1_20_R1.util

import it.dohyun.plugin.api.type.nms.NMS
import net.minecraft.network.protocol.Packet
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftPlayer
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin

class NMS(val plugin: Plugin) : NMS {
    private val PACKET = Packet(plugin)

    override fun sendPacket(player: Player, packet: Any) {
        val connection = (player as CraftPlayer).handle.connection
        connection.send(packet as Packet<*>)
    }

    override fun setTabList(player: Player, header: String, footer: String) {
        sendPacket(player, PACKET.setTabList(header, footer))
    }
}