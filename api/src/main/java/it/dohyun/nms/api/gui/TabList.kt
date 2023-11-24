package it.dohyun.nms.api.gui

import it.dohyun.nms.api.config.Config
import it.dohyun.nms.api.util.NMS
import it.dohyun.nms.type.config.ConfigFormatOption
import org.bukkit.Bukkit

class TabList {
    companion object {
        fun setHeaderFooter(header: String, footer: String) {
            Bukkit.getOnlinePlayers().forEach {
                NMS.setTabList(
                    it,
                    Config.format(header, ConfigFormatOption(player = it)),
                    Config.format(footer, ConfigFormatOption(player = it)),
                )
            }
        }

        fun setPlayerName() {
            Bukkit.getOnlinePlayers().forEach {
                it.playerListName = Config.format(
                    Config.data.tabList.playerName,
                    ConfigFormatOption(player = it),
                )
            }
        }
    }
}