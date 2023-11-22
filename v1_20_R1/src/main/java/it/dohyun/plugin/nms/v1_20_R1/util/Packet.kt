package it.dohyun.plugin.nms.v1_20_R1.util

import it.dohyun.plugin.api.type.nms.Packet
import net.minecraft.network.chat.MutableComponent
import net.minecraft.network.chat.contents.LiteralContents
import net.minecraft.network.protocol.game.ClientboundTabListPacket
import org.bukkit.plugin.Plugin

class Packet(val plugin: Plugin) : Packet {
    override fun setTabList(header: String, footer: String): Any {
        return ClientboundTabListPacket(
            MutableComponent.create(LiteralContents(header)),
            MutableComponent.create(LiteralContents(footer))
        )
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