package it.dohyun.nms.api.scheduler

import it.dohyun.nms.api.API
import it.dohyun.nms.api.config.Config
import it.dohyun.nms.api.gui.TabList
import it.dohyun.nms.type.Scheduler
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
                Config.data.tabList.interval.toLong(),
            )
        }

        override fun remove() {
            if (id != null)
                Bukkit.getScheduler().cancelTask(id!!)
        }
    }
}