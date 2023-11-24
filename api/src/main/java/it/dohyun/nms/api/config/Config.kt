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
                    playerName = tabList?.get("playerName")?.toString() ?: default.tabList.playerName,
                    header = tabList?.get("header") as ArrayList<String>? ?: default.tabList.header,
                    footer = tabList?.get("footer") as ArrayList<String>? ?: default.tabList.footer,
                    interval = tabList?.get("interval")?.toString()?.toInt() ?: default.tabList.interval,
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
                        "playerName" to this.data.tabList.playerName,
                        "header" to this.data.tabList.header,
                        "footer" to this.data.tabList.footer,
                        "interval" to this.data.tabList.interval,
                    ),
                )
            )
        }

        private fun formatPart(str: String): String {
            return formatRepeatInsert(formatIf(formatNotEmpty(str)))
        }

        private fun formatIf(str: String) : String {
            return Regex("%if\\(([^|]+)\\|([^)]+)\\)%\\(([^|]*)\\|([^)]*)\\)%").replace(str) {
                var (str1, str2, res1, res2) = it.destructured
                str1 = formatPart(str1)
                str2 = formatPart(str2)
                res1 = formatPart(res1)
                res2 = formatPart(res2)
                if (str1 == str2) res1 else res2
            }
        }

        private fun formatNotEmpty(str: String) : String {
            return Regex("%notempty\\(([^)]+)\\)%\\(([^|]*)\\|([^)]*)\\)%").replace(str) {
                val (str1, res1, res2) = it.destructured
                if (str1.isNotEmpty()) res1 else res2
            }
        }

        private fun formatRepeatInsert(str: String): String {
            return Regex("%ri\\(([^|]+)\\|([^|]+)\\|([^)]+)\\)%").replace(str) {
                val (input, color, idx) = it.destructured
                val colors = color.split(",")
                var count = idx.toInt() - 1
                val result = arrayListOf<String>()
                input.forEach { char ->
                    result.add(
                        if (char.isWhitespace()) " " else colors[count] + char
                    )
                    if (!char.isWhitespace())
                        count = if (++count < colors.size) count else 0
                }
                result.joinToString("")
            }
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
            result = result
                .replace("%server.name%", this.data.name)
                .replace("%server.players%", Bukkit.getOnlinePlayers().size.toString())
                .replace("%date.year%", now.format(DateTimeFormatter.ofPattern("yyyy")))
                .replace("%date.month%", now.format(DateTimeFormatter.ofPattern("MM")))
                .replace("%date.day%", now.format(DateTimeFormatter.ofPattern("dd")))
                .replace("%date.hour%", now.format(DateTimeFormatter.ofPattern("HH")))
                .replace("%date.minute%", now.format(DateTimeFormatter.ofPattern("mm")))
            result = formatPart(result)
            result = Regex("&[0-9a-fk-orA-FK-OR]").replace(result) {
                "§${it.value.replace("&", "")}"
            }
            return if (option.trim) result.trim() else result
        }
    }
}