import exceptions.InvalidDependencyFormat
import model.ArtifactInfo
import model.DependencyInfo
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class ArtifactRequestHandler(private val artifactory: ArtifactInfo) {
    fun fetchDependencyMetaInfo(dependency: String): String? {
        val depInfo = getDependencyInfo(dependency)
        val requestUrl = getRequestUrl(artifactory.url, depInfo)
        return getXmlDataFromUrl(requestUrl)
    }

    private fun getRequestUrl(repoUrl: String, depInfo: DependencyInfo): String {
        val groupList = depInfo.group.split(".")
        val sb = StringBuilder()
        sb.append(repoUrl)
        groupList.forEach {
            sb.append(it)
            sb.append("/")
        }
        sb.append(depInfo.artifact)
        sb.append("/")
        sb.append("maven-metadata.xml")
        return sb.toString()
    }

    private fun getXmlDataFromUrl(url: String): String? {
        return try {
            val urlRequest = URL(url)
            val connection = urlRequest.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.doOutput = true
            val status = connection.responseCode
            if (status == 200) {
                val bufferedReader = BufferedReader(InputStreamReader(connection.inputStream))
                bufferedReader.readText()
            } else {
                throw Exception("url request $url failed with status $status")
            }
        } catch (e: Exception) {
            null
        }
    }

    private fun getDependencyInfo(dependency: String): DependencyInfo {
        try {
            return dependency.split(":").let {
                DependencyInfo(it[0], it[1])
            }
        } catch (e: Exception) {
            throw InvalidDependencyFormat()
        }
    }
}