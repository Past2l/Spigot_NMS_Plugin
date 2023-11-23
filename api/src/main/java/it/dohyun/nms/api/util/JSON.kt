package it.dohyun.nms.api.util

import org.json.JSONArray
import org.json.JSONObject
import java.util.HashMap

class JSON {
    companion object {
        fun write(path: String, data: HashMap<String, *>) {
            File.write(path, stringify(data))
        }

        fun read(path: String): HashMap<String, *>? {
            val data = File.read(path)
            return data?.let { parse(it) }
        }

        fun parse(data: String): HashMap<String, *> {
            val json = JSONObject(data)
            return json.keys().asSequence().associateWith { it ->
                when (val value = json[it]) {
                    is JSONArray -> {
                        val map = (0 until value.length()).associate {
                            Pair(it.toString(), value[it])
                        }
                        parse(JSONObject(map).toString()).values.toList()
                    }
                    is JSONObject -> parse(value.toString())
                    JSONObject.NULL -> null
                    else -> value
                }
            } as HashMap
        }

        fun stringify(data: HashMap<String, *>): String {
            return JSONObject(data).toString()
        }
    }
}