package it.dohyun.plugin

import it.dohyun.plugin.api.util.NMS
import it.dohyun.plugin.scheduler.GUILoadScheduler
import org.bukkit.plugin.java.JavaPlugin

class Plugin : JavaPlugin() {
    private val schedulers = arrayOf(
        GUILoadScheduler
    )

    override fun onEnable() {
        if (!NMS.init()) return
        initSchedulers()
    }

    override fun onDisable() {
        removeSchedulers()
    }

    private fun initSchedulers() {
       schedulers.forEach { it.init() }
    }

    private fun removeSchedulers() {
        schedulers.forEach { it.remove() }
    }
}