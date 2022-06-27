import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.stream.JsonWriter
import exceptions.CacheFileDoesNotExist
import exceptions.CacheValueExpired
import exceptions.CacheValueNotFound
import java.io.File
import java.io.FileWriter
import java.nio.charset.Charset

private const val OUT_DIR = "./cache"
private const val OUT_FILE = "./cache/versions.json"
private const val CACHE_TTL = 86400000
private const val KEY_TTL = "ttl"
private const val VERSION_VALUE = "value"

class VersionFileCacheManager {
    private val gson = GsonBuilder().setPrettyPrinting().create()
    private lateinit var inMemoryCache: JsonObject

    init {
        inMemoryCache = try {
            getJsonObjectFromFile()
        } catch (e: CacheFileDoesNotExist) {
            JsonObject()
        }

    }

    private fun getJsonObjectFromFile(): JsonObject {
        val file = File(OUT_FILE)
        if (!file.exists()) {
            throw CacheFileDoesNotExist()
        }
        if (::inMemoryCache.isInitialized) {
            return inMemoryCache
        }
        return gson.fromJson<JsonObject>(file.bufferedReader(Charset.defaultCharset()), JsonObject::class.java)
    }

    private fun getNewTTL(): Long {
        return System.currentTimeMillis() + CACHE_TTL
    }

    internal fun updateCacheMemory(key: String, value: String) {
        val cacheInfo = JsonObject()
        cacheInfo.addProperty(KEY_TTL, getNewTTL())
        cacheInfo.addProperty(VERSION_VALUE, value)
        inMemoryCache.add(key, cacheInfo)
    }

    internal fun getCacheValue(key: String): String {
        inMemoryCache.get(key)?.asJsonObject?.let {
            val ttl = it.get(KEY_TTL).asLong
            val version = it.get(VERSION_VALUE).asString
            if (ttl < System.currentTimeMillis()) {
                throw CacheValueExpired()
            }
            return version
        }
        throw CacheValueNotFound()
    }

    internal fun saveCacheFile() {
        val file = File(OUT_FILE)
        if (!file.exists()) {
            val cacheDir = File(OUT_DIR)
            cacheDir.mkdirs()
            file.createNewFile()
        }
        val writer = FileWriter(OUT_FILE)
        gson.toJson(inMemoryCache, writer)
        writer.close()
    }

}