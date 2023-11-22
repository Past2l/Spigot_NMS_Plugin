package it.dohyun.nms.api.type

interface Scheduler {
    var id: Int?
    fun init()
    fun remove()
}