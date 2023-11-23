package it.dohyun.nms.nms.v1_16_R3.util

import it.dohyun.nms.api.type.nms.NMS
import net.minecraft.server.v1_16_R3.IChatBaseComponent
import net.minecraft.server.v1_16_R3.Packet
import net.minecraft.server.v1_16_R3.PacketPlayOutPlayerListHeaderFooter
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin

class NMS(plugin: Plugin) : NMS {
    override fun sendPacket(player: Player, packet: Any) {
        val connection = (player as CraftPlayer).handle.playerConnection
        connection.sendPacket(packet as Packet<*>)
    }

    override fun setTabList(player: Player, header: String, footer: String) {
        val packet = PacketPlayOutPlayerListHeaderFooter()
        packet.header = IChatBaseComponent.ChatSerializer.a("""{"text":"${header.replace("\n", "\\n")}"}""")
        packet.footer = IChatBaseComponent.ChatSerializer.a("""{"text":"${footer.replace("\n", "\\n")}"}""")
        sendPacket(player, packet)
    }

    private fun getValue(instance: Any, name: String): Any? {
        var result: Any? = null
        try {
            val field = instance.javaClass.getDeclaredField(name)
            field.isAccessible = true
            result = field.get(instance)
            field.isAccessible = false
        } catch(e: Exception) {
            e.printStackTrace()
        }
        return result
    }

    private fun setValue(obj: Any, key: String, value: Any?) {
        val field = obj.javaClass.getDeclaredField(key)
        field.isAccessible = true
        field.set(obj, value)
    }
}