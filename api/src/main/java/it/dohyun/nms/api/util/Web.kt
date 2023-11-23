package it.dohyun.nms.api.util

import java.io.*
import java.net.*
import java.security.MessageDigest

class Web {
    companion object {
        fun parse(url: String): String? {
            return try {
                val connection = URL(url).openConnection() as HttpURLConnection
                if (connection.responseCode == 200) {
                    val lines = ArrayList<String>()
                    val reader = BufferedReader(InputStreamReader(connection.inputStream))
                    reader.lines().forEach { e: String -> lines.add(e) }
                    java.lang.String.join(" ", lines)
                } else {
                    Logger.log("$url: ${connection.responseCode}")
                    null
                }
            } catch (e: IOException) {
                e.printStackTrace()
                null
            }
        }

        fun getFileChecksum(url: String): ByteArray? {
            return try {
                val steam = URL(url).openStream()
                val buffer = ByteArray(1024)
                val complete = MessageDigest.getInstance("SHA1")
                var numRead: Int
                do {
                    numRead = steam.read(buffer)
                    if (numRead > 0) complete.update(buffer, 0, numRead)
                } while (numRead != -1)
                steam.close()
                complete.digest()
            } catch (e: FileNotFoundException) {
                null
            }
        }
    }
}