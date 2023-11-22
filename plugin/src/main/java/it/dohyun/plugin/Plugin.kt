package it.dohyun.plugin

import it.dohyun.plugin.api.util.NMS
import org.bukkit.plugin.java.JavaPlugin

class Plugin : JavaPlugin() {
    override fun onEnable() {
        // Plugin startup logic
        if (!NMS.init()) return
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}