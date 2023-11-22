package it.dohyun.plugin.api

import org.bukkit.plugin.java.JavaPlugin

class API {
    companion object {
        fun getPlugin(): JavaPlugin {
            return JavaPlugin.getProvidingPlugin(this::class.java)
        }
    }
}