package it.dohyun.nms.nms.v1_20_R1.util

import it.dohyun.nms.api.type.nms.NMS
import net.minecraft.network.protocol.Packet
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftPlayer
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin

class NMS(plugin: Plugin) : NMS {
    private val packet = Packet(plugin)

    override fun sendPacket(player: Player, packet: Any) {
        val connection = (player as CraftPlayer).handle.connection
        connection.send(packet as Packet<*>)
    }

    override fun setTabList(player: Player, header: String, footer: String) {
        sendPacket(player, packet.setTabList(header, footer))
    }
}