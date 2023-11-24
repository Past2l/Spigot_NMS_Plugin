package it.dohyun.nms.type.config

data class Config(
    val name: String = "Test Server",
    val timezone: String = "Asia/Seoul",
    val resourcePack: String = "",
    val chat: String = "%player.name% > %chat.message%",
    val tabList: ConfigTabList = ConfigTabList(),
    val motd: ConfigMOTD = ConfigMOTD(),
)