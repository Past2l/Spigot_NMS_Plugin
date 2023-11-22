package it.dohyun.plugin.api.gui

import it.dohyun.plugin.api.util.NMS
import org.bukkit.Bukkit

class TabList {
    companion object {
        fun setHeaderFooter() {
            Bukkit.getOnlinePlayers().forEach {
                NMS.setTabList(
                    it,
                    "It's Header\nIt's Header!",
                    "It's Footer\nIt's Footer!"
                )
            }
        }
    }
}