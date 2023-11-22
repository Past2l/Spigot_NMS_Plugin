package it.dohyun.nms

import it.dohyun.nms.api.util.NMS
import it.dohyun.nms.event.PlayerEvent
import it.dohyun.nms.scheduler.GUILoadScheduler
import org.bukkit.plugin.java.JavaPlugin

class NMS : JavaPlugin() {
    private val schedulers = arrayOf(
        GUILoadScheduler
    )
    private val events = arrayOf(
        PlayerEvent()
    )

    override fun onEnable() {
        if (!NMS.init()) return
        initSchedulers()
        initEvents()
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

    private fun initEvents() {
        events.forEach { server.pluginManager.registerEvents(it, this) }
    }
}