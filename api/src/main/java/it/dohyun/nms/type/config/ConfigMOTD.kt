package it.dohyun.nms.type.config

data class ConfigMOTD(
    val content: ArrayList<String> = arrayListOf("%server.name%", ""),
    val center: Boolean = false,
)