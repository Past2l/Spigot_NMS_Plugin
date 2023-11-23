package it.dohyun.nms.type

interface Scheduler {
    var id: Int?
    fun init()
    fun remove()
}