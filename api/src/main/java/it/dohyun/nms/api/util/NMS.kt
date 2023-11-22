package it.dohyun.nms.api.util

import it.dohyun.nms.api.API
import it.dohyun.nms.api.type.nms.NMS
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin

class NMS {
    companion object {
        private val plugin = API.getPlugin()
        private lateinit var nms: NMS

        fun getVersion(): String = Bukkit.getServer().javaClass.`package`.name.split(".")[3]

        fun init(): Boolean {
            Logger.log("NMS Version : ${getVersion()}")
            return try {
                val version = getVersion()
                val className = "it.dohyun.nms.nms.$version.util.NMS"
                val clazz = Class.forName(className)
                val constructor = clazz.getConstructor(Plugin::class.java)
                val instance = constructor.newInstance(plugin)
                if (instance is NMS) {
                    nms = instance
                    Logger.log("NMS loading completed§r")
                    true
                } else {
                    Logger.error("'$className' Class not found")
                    Bukkit.getPluginManager().disablePlugin(plugin)
                    false
                }
            } catch (_: ClassNotFoundException) {
                Logger.error("This plugin does not currently support Minecraft versions")
                Bukkit.getPluginManager().disablePlugin(plugin)
                false
            }
        }

        fun setTabList(player: Player, header: String, footer: String) {
            nms.setTabList(player, header, footer)
        }
    }
}