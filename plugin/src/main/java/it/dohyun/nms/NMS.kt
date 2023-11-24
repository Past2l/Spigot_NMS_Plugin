package it.dohyun.nms

import it.dohyun.nms.api.event.*
import it.dohyun.nms.api.command.*
import it.dohyun.nms.api.scheduler.*
import it.dohyun.nms.api.util.NMS
import it.dohyun.nms.api.config.Config
import org.bukkit.plugin.java.JavaPlugin

class NMS : JavaPlugin() {
    private val commands = hashMapOf(
        ConfigCommand.name to ConfigCommand()
    )
    private val schedulers = arrayOf(
        GUILoadScheduler
    )
    private val events = arrayOf(
        PlayerEvent(),
        MOTDEvent(),
    )

    override fun onEnable() {
        if (!NMS.init()) return
        Config.init()
        initCommands()
        initSchedulers()
        initEvents()
    }

    override fun onDisable() {
        removeSchedulers()
    }

    private fun initCommands() {
        commands.map {
            getCommand(it.key)?.executor = it.value
            getCommand(it.key)?.tabCompleter = it.value
        }
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