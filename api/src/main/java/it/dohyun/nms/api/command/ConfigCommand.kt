package it.dohyun.nms.api.command

import it.dohyun.nms.api.config.Config
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player
import java.util.*

class ConfigCommand: CommandExecutor, TabExecutor {
    companion object {
        const val name = "config"
    }

    private val help = hashMapOf(
        "default" to arrayOf(
            "&e---------------&r Help: /$name &e---------------&r",
            "&6/$name reload&r: Config를 다시 불러옵니다.",
            "&6/$name save&r: Config를 저장합니다.",
            "&6/$name edit <option>&r: Config를 수정합니다.",
        ).joinToString("\n"),
        "edit" to arrayOf(
            "&e---------------&r Help: /$name edit &e---------------&r",
            "&6/$name edit name <string>&r: 서버 이름을 수정합니다.",
            "&6/$name edit tabList <option>&r: 플레이어 목록 리스트에 관한 Config를 수정합니다.",
            "&6/$name edit motd <option>&r: MOTD에 관한 Config를 수정합니다.",
            "&6/$name edit resourcePack <string>&r: Resource Pack 링크를 수정합니다.",
            "&6/$name edit timezone <string>&r: 시간대를 수정합니다.",
            "&6/$name edit chat <string>&r: 채팅 포맷을 수정합니다.",
        ).joinToString("\n"),
        "tabList" to arrayOf(
            "&e---------------&r Help: /$name edit tabList &e---------------&r",
            "&6/$name edit tabList header <option>&r: 플레이어 목록 리스트 Header를 수정합니다.",
            "&6/$name edit tabList footer <option>&r: 플레이어 목록 리스트 Footer를 수정합니다.",
            "&6/$name edit tabList playerName <string>&r: 플레이어 목록 리스트 플레이어 이름을 수정합니다.",
            "&6/$name edit tabList interval <tick>&r: 플레이어 목록 리스트 갱신 주기를 수정합니다.",
        ).joinToString("\n"),
        "motd" to arrayOf(
            "&e---------------&r Help: /$name edit motd &e---------------&r",
            "&6/$name edit motd content <option>&r: MOTD 내용을 수정합니다.",
            "&6/$name edit center footer <boolean>&r: MOTD 가운데 정렬 여부를 수정합니다.",
        ).joinToString("\n"),
        "tabList_header" to arrayOf(
            "&e-----------&r Help: /$name edit tabList header &e-----------&r",
            "&6/$name edit tabList header add <string>&r: 플레이어 목록 리스트 Header의 내용을 추가합니다.",
            "&6/$name edit tabList header edit <index> <string>&r: " +
                "플레이어 목록 리스트 Header의 <index>번째 내용을 수정합니다.",
            "&6/$name edit tabList header remove <index>&r: " +
                "플레이어 목록 리스트 Header의 <index>번째 내용을 제거합니다.",
            "&6/$name edit tabList header list&r: 플레이어 목록 리스트 Header의 모든 내용을 출력합니다.",
        ).joinToString("\n"),
        "tabList_footer" to arrayOf(
            "&e-----------&r Help: /$name edit tabList footer &e-----------&r",
            "&6/$name edit tabList footer add <string>&r: 플레이어 목록 리스트 Footer의 내용을 추가합니다.",
            "&6/$name edit tabList footer edit <index> <string>&r: " +
                "플레이어 목록 리스트 Footer의 <index>번째 내용을 수정합니다.",
            "&6/$name edit tabList footer remove <index>&r: " +
                "플레이어 목록 리스트 Footer의 <index>번째 내용을 제거합니다.",
            "&6/$name edit tabList footer list&r: 플레이어 목록 리스트 Footer의 모든 내용을 출력합니다.",
        ).joinToString("\n"),
        "motd_content" to arrayOf(
            "&e-----------&r Help: /$name edit motd content &e-----------&r",
            "&6/$name edit motd content add <string>&r: MOTD의 내용을 추가합니다.",
            "&6/$name edit motd content edit <index> <string>&r: " +
                "MOTD의 <index>번째 내용을 수정합니다.",
            "&6/$name edit motd content remove <index>&r: " +
                "MOTD의 <index>번째 내용을 제거합니다.",
            "&6/$name edit motd content list&r: MOTD의 모든 내용을 출력합니다.",
        ).joinToString("\n"),
    )

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>,
    ): MutableList<String> {
        return when(args.size) {
            1 -> mutableListOf("reload", "save", "edit")
                .filter { it.contains(args[0]) } as MutableList
            2 -> when (args[0]) {
                "edit" -> mutableListOf("name", "tabList", "motd", "resourcePack", "timezone", "chat")
                    .filter { it.contains(args[1]) } as MutableList
                else -> mutableListOf()
            }
            3 -> when (args[0]) {
                "edit" -> when (args[1]) {
                    "tabList" -> mutableListOf("header", "footer", "interval", "playerName")
                        .filter { it.contains(args[2]) } as MutableList
                    "motd" -> mutableListOf("content", "center")
                        .filter { it.contains(args[2]) } as MutableList
                    else -> mutableListOf()
                }
                else -> mutableListOf()
            }
            4 -> when (args[0]) {
                "edit" -> when (args[1]) {
                    "tabList" -> when (args[2]) {
                        in arrayOf("header", "footer") ->
                            mutableListOf("add", "edit", "remove", "list")
                                .filter { it.contains(args[3]) } as MutableList
                        else -> mutableListOf()
                    }
                    "motd" -> when (args[2]) {
                        "content" -> mutableListOf("add", "edit", "remove", "list")
                            .filter { it.contains(args[3]) } as MutableList
                        "center" -> mutableListOf("true", "false")
                            .filter { it.contains(args[3]) } as MutableList
                        else -> mutableListOf()
                    }
                    else -> mutableListOf()
                }
                else -> mutableListOf()
            }
            else -> mutableListOf()
        }
    }

    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>,
    ): Boolean {
        if (sender !is Player) return false
        if (args.isEmpty())
            sender.sendMessage(Config.format(help["default"]))
        else when (args[0]) {
            "reload" -> {
                Config.init()
                sender.sendMessage(Config.format("&a[&e%server.name%&a]&r Config를 다시 불러왔습니다."))
            }
            "save" -> {
                Config.save()
                sender.sendMessage(Config.format("&a[&e%server.name%&a]&r Config를 저장하였습니다."))
            }
            "edit" -> if (args.size < 2)
                sender.sendMessage(Config.format(help["edit"]))
            else when (args[1]) {
                in arrayOf("name", "resourcePack", "timezone", "chat") ->
                    if (args.size < 3)
                        sender.sendMessage(Config.format(help["edit"]))
                    else {
                        var value = args.joinToString(" ").split("${args[1]} ")[1]
                        value = if (args[1] == "timezone") TimeZone.getTimeZone(value).id else value
                        when (args[1]) {
                            "name" -> Config.data.name = value
                            "resourcePack" -> Config.data.resourcePack = value
                            "timezone" -> Config.data.timezone = value
                            "chat" -> Config.data.chat = value
                        }
                        Config.save()
                        sender.sendMessage(
                            Config.format("&a[&e%server.name%&a]&r ${args[1]}을(를) '${
                                value.replace("%", "\\%")
                            }&r'로 변경하였습니다.").replace("\\%", "%")
                        )
                    }
                "tabList" -> if (args.size < 3)
                    sender.sendMessage(Config.format(help["tabList"]))
                else when (args[2]) {
                    in arrayOf("header", "footer") -> if (args.size < 4)
                        sender.sendMessage(Config.format(help["tabList_${args[2]}"]))
                    else when (args[3]) {
                        else -> sender.sendMessage(Config.format(help["tabList_${args[2]}"]))
                    }
                }
                "motd" -> if (args.size < 3)
                    sender.sendMessage(Config.format(help["motd"]))
                else when (args[2]) {
                    in arrayOf("content") -> if (args.size < 4)
                        sender.sendMessage(Config.format(help["motd_${args[2]}"]))
                    else when (args[3]) {
                        else -> sender.sendMessage(Config.format(help["motd_${args[2]}"]))
                    }
                    else -> sender.sendMessage(Config.format(help["motd"]))
                }
                else -> sender.sendMessage(Config.format(help["edit"]))
            }
            else -> sender.sendMessage(Config.format(help["default"]))
        }
        return true
    }
}