package it.dohyun.nms.api.gui

import it.dohyun.nms.api.config.Config
import it.dohyun.nms.api.util.NMS
import it.dohyun.nms.type.config.ConfigFormatOption
import org.bukkit.Bukkit

class TabList {
    companion object {
        fun setHeaderFooter() {
            Bukkit.getOnlinePlayers().forEach {
                NMS.setTabList(
                    it,
                    Config.format(
                        Config.data.tabList.header,
                        ConfigFormatOption(player = it),
                    ),
                    Config.format(
                        Config.data.tabList.footer,
                        ConfigFormatOption(player = it),
                    ),
                )
            }
        }

        fun setPlayerName() {
            Bukkit.getOnlinePlayers().forEach {
                it.playerListName = Config.format(
                    Config.data.tabList.player,
                    ConfigFormatOption(player = it),
                )
            }
        }
    }
}