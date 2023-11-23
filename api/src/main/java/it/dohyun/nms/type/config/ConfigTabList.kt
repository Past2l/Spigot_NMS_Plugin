package it.dohyun.nms.type.config

data class ConfigTabList(
    val player: String = "%player.name%",
    val header: String = "Welcome to %server.name%!",
    val footer: String = "players : %server.players%",
    val interval: Int = 20,
)