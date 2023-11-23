package it.dohyun.nms.type.config

import org.bukkit.entity.Player

data class ConfigFormatOption (
    val player: Player? = null,
    val trim: Boolean = true,
)