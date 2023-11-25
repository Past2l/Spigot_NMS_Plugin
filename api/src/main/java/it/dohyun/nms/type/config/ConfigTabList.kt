package it.dohyun.nms.type.config

data class ConfigTabList(
    var playerName: String = "%player.name%",
    val header: ArrayList<String> = arrayListOf("Welcome to %server.name%!"),
    val footer: ArrayList<String> = arrayListOf("players : %server.players%"),
    var interval: Int = 20,
)