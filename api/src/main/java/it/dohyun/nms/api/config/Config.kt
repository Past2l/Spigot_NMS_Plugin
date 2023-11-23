package it.dohyun.nms.api.config

import it.dohyun.nms.api.util.Yaml
import it.dohyun.nms.type.config.Config
import it.dohyun.nms.type.config.ConfigFormatOption
import it.dohyun.nms.type.config.ConfigTabList
import org.bukkit.Bukkit
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class Config {
    companion object {
        lateinit var data: Config

        fun init() {
            val data = Yaml.read("config.yml")
            val tabList = data?.get("tabList") as HashMap<*, *>?
            val default = Config()

            this.data = Config(
                name = data?.get("name")?.toString() ?: default.name,
                timezone = data?.get("timezone")?.toString() ?: default.timezone,
                resourcePack = data?.get("resourcePack")?.toString() ?: default.resourcePack,
                chat = data?.get("chat")?.toString() ?: default.chat,
                tabList = ConfigTabList(
                    player = tabList?.get("player")?.toString() ?: default.tabList.player,
                    header = tabList?.get("header")?.toString() ?: default.tabList.header,
                    footer = tabList?.get("footer")?.toString() ?: default.tabList.footer,
                    interval = tabList?.get("footer")?.toString()?.toInt() ?: default.tabList.interval,
                )
            )
        }

        fun save() {
            Yaml.write(
                "config.yml",
                hashMapOf(
                    "name" to this.data.name,
                    "timezone" to this.data.timezone,
                    "resourcePack" to this.data.resourcePack,
                    "chat" to this.data.chat,
                    "tabList" to hashMapOf(
                        "player" to this.data.tabList.player,
                        "header" to this.data.tabList.header,
                        "footer" to this.data.tabList.footer,
                        "interval" to this.data.tabList.interval,
                    ),
                )
            )
        }

        fun format(
            str: String?,
            option: ConfigFormatOption = ConfigFormatOption()
        ): String {
            var result = str ?: return ""
            val now = ZonedDateTime.now(ZoneId.of(this.data.timezone))
            if (option.player != null) {
                result = result
                    .replace("%player.name%", option.player.name)
                    .replace("%player.op%", option.player.isOp.toString())
                    .replace("%player.uuid%", option.player.uniqueId.toString())
            }
            result = Regex("&[0-9a-fk-orA-FK-OR]").replace(
                result
                    .replace("%server.name%", this.data.name)
                    .replace("%server.players%", Bukkit.getOnlinePlayers().size.toString())
                    .replace("%date.year%", now.format(DateTimeFormatter.ofPattern("yyyy")))
                    .replace("%date.month%", now.format(DateTimeFormatter.ofPattern("MM")))
                    .replace("%date.day%", now.format(DateTimeFormatter.ofPattern("dd")))
                    .replace("%date.hour%", now.format(DateTimeFormatter.ofPattern("HH")))
                    .replace("%date.minute%", now.format(DateTimeFormatter.ofPattern("mm")))
            ) {
                "§${it.value.replace("&", "")}"
            }
            return if (option.trim) result.trim() else result
        }
    }
}