package it.dohyun.nms.scheduler

import it.dohyun.nms.api.API
import it.dohyun.nms.api.gui.TabList
import it.dohyun.nms.api.type.Scheduler
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