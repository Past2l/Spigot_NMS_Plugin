package it.dohyun.nms.api.command

import it.dohyun.nms.api.config.Config
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

class ConfigCommand: CommandExecutor, TabExecutor {
    companion object {
        const val name = "config"
    }

    private val help = hashMapOf(
        "default" to arrayOf(
            "&e---------------&r Help: /$name &e---------------&r",
            "&6/$name reload&r: Config를 다시 불러옵니다.",
            "&6/$name save&r: Config를 저장합니다.",
        ).joinToString("\n")
    )

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>,
    ): MutableList<String> {
        return when(args.size) {
            else -> mutableListOf("reload", "save")
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
            else -> sender.sendMessage(Config.format(help["default"]))
        }
        return true
    }
}