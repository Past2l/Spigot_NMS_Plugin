package it.dohyun.nms.type.config

data class ConfigTabList(
    val playerName: String = "%player.name%",
    val header: ArrayList<String> = arrayListOf("Welcome to %server.name%!"),
    val footer: ArrayList<String> = arrayListOf("players : %server.players%"),
    val interval: Int = 20,
)