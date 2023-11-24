package it.dohyun.nms.api.scheduler

import it.dohyun.nms.api.API
import it.dohyun.nms.api.config.Config
import it.dohyun.nms.api.gui.TabList
import it.dohyun.nms.api.util.Math
import it.dohyun.nms.type.Scheduler
import org.bukkit.Bukkit

class GUILoadScheduler {
    companion object : Scheduler {
        override var id: Int? = null

        override fun init() {
            val tabList = Config.data.tabList
            var tabListCount = 0
            val tabListMax =
                tabList.header.size * tabList.header.size /
                    Math.gcd(tabList.header.size, tabList.footer.size)
            id = Bukkit.getScheduler().scheduleSyncRepeatingTask(
                API.getPlugin(),
                {
                    TabList.setHeaderFooter(
                        tabList.header[tabListCount % tabList.header.size],
                        tabList.footer[tabListCount % tabList.footer.size],
                    )
                    TabList.setPlayerName()
                    tabListCount = if (
                        ++tabListCount < tabListMax
                    ) tabListCount else 0
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