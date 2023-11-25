package it.dohyun.nms.type.config

data class Config(
    var name: String = "Test Server",
    var timezone: String = "Asia/Seoul",
    var resourcePack: String = "",
    var chat: String = "%player.name% > %chat.message%",
    var tabList: ConfigTabList = ConfigTabList(),
    var motd: ConfigMOTD = ConfigMOTD(),
)