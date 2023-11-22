package it.dohyun.plugin.api.type

interface Scheduler {
    var id: Int?
    fun init()
    fun remove()
}