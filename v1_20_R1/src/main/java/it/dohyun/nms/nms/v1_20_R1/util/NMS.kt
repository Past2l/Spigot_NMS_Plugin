package it.dohyun.nms.nms.v1_20_R1.util

import it.dohyun.nms.api.type.nms.NMS
import net.minecraft.network.chat.MutableComponent
import net.minecraft.network.chat.contents.LiteralContents
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientboundTabListPacket
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftPlayer
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin

class NMS(plugin: Plugin) : NMS {
    override fun sendPacket(player: Player, packet: Any) {
        val connection = (player as CraftPlayer).handle.connection
        connection.send(packet as Packet<*>)
    }

    override fun setTabList(player: Player, header: String, footer: String) {
        sendPacket(
            player,
            ClientboundTabListPacket(
                MutableComponent.create(LiteralContents(header)),
                MutableComponent.create(LiteralContents(footer))
            )
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