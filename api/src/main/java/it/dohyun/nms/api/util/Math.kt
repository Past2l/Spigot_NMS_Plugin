package it.dohyun.nms.api.util

class Math {
    companion object {
        fun gcd(a: Int, b:Int): Int = if(b != 0) gcd(b, a % b) else a
    }
}