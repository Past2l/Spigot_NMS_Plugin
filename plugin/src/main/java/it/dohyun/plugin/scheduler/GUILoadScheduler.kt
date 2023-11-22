package it.dohyun.plugin.scheduler

import it.dohyun.plugin.api.API
import it.dohyun.plugin.api.gui.TabList
import it.dohyun.plugin.api.type.Scheduler
import org.bukkit.Bukkit

class GUILoadScheduler {
    companion object : Scheduler {
        override var id: Int? = null

        override fun init() {
            id = Bukkit.getScheduler().scheduleSyncRepeatingTask(
                API.getPlugin(),
                {
                    TabList.setHeaderFooter()
                },
                0,
                200
            )
        }

        override fun remove() {
            if (id != null)
                Bukkit.getScheduler().cancelTask(id!!)
        }
    }
}