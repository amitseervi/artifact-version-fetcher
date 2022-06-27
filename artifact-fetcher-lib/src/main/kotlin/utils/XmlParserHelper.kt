package utils

import com.fasterxml.jackson.dataformat.xml.XmlMapper
import model.MetaData

internal object XmlParserHelper {
    fun getVersionFromMavenMetaData(xmlStringData: String): String {
        val jsonData = XmlMapper().readValue(xmlStringData, MetaData::class.java)
        return jsonData?.versioning?.latest ?: ""
    }
}