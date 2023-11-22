package it.dohyun.plugin.api.util

import it.dohyun.plugin.api.API
import org.bukkit.Bukkit

class Logger {
    companion object {
        fun log(str: Any) = Bukkit.getConsoleSender()
            .sendMessage("[${API.getPlugin().name}] $str§r")
        fun warn(str: String) = log("§e$str")
        fun error(str: String) = log("§c$str")
    }
}