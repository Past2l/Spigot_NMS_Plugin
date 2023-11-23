package it.dohyun.nms.api.gui

import it.dohyun.nms.api.util.NMS
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

        fun setPlayerName() {
            Bukkit.getOnlinePlayers().forEach {
                it.playerListName = it.name
            }
        }
    }
}