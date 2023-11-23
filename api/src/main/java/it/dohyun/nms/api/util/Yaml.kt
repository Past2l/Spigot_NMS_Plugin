package it.dohyun.nms.api.util

import it.dohyun.nms.api.API
import org.yaml.snakeyaml.DumperOptions
import java.io.*

class Yaml {
    companion object {
        private val plugin = API.getPlugin()

        fun write(path: String, map: HashMap<String, *>) {
            val file = File(plugin.dataFolder, path)
            try {
                if (!file.exists()) {
                    if (!file.parentFile.exists()) file.parentFile.mkdirs()
                    file.createNewFile()
                }
                val options = DumperOptions()
                options.isAllowUnicode = true
                options.defaultFlowStyle = DumperOptions.FlowStyle.BLOCK
                options.isPrettyFlow = true
                val yaml = org.yaml.snakeyaml.Yaml(options)
                val writer = OutputStreamWriter(FileOutputStream(file), Charsets.UTF_8)
                yaml.dump(map, writer)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        fun read(path: String): HashMap<String, *>? {
            val file = File(plugin.dataFolder, path)
            if (!file.exists()) return null
            try {
                return org.yaml.snakeyaml.Yaml().load(InputStreamReader(FileInputStream(file), Charsets.UTF_8))
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
            return null
        }
    }
}