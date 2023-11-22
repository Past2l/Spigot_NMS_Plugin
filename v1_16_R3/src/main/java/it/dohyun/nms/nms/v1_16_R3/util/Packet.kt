package it.dohyun.nms.nms.v1_16_R3.util

import it.dohyun.nms.api.type.nms.Packet
import net.minecraft.server.v1_16_R3.IChatBaseComponent
import net.minecraft.server.v1_16_R3.PacketPlayOutPlayerListHeaderFooter
import org.bukkit.plugin.Plugin

class Packet(plugin: Plugin) : Packet {
    override fun setTabList(header: String, footer: String): Any {
        val packet = PacketPlayOutPlayerListHeaderFooter()
        packet.header = IChatBaseComponent.ChatSerializer.a("""{"text":"${header.replace("\n", "\\n")}"}""")
        packet.footer = IChatBaseComponent.ChatSerializer.a("""{"text":"${footer.replace("\n", "\\n")}"}""")
        return packet
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