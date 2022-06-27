import exceptions.CouldNotFindDependencyOnArtifacts
import model.ArtifactInfo
import utils.XmlParserHelper

class VersionFetcher(private val repositories: List<ArtifactInfo>) {
    private val cacheManager = VersionFileCacheManager()
    private val artifactRequestHandlers = repositories.map { artifactInfo ->
        ArtifactRequestHandler(artifactInfo)
    }

    init {
        Runtime.getRuntime().addShutdownHook(Thread {
            println("Save Cache File")
            cacheManager.saveCacheFile()
        })
    }

    fun getLatestVersion(dependency: String): String {
        try {
            return cacheManager.getCacheValue(dependency)
        } catch (e: Exception) {
            //Do Nothing
        }
        artifactRequestHandlers.forEach { artifactory ->
            val xmlData = artifactory.fetchDependencyMetaInfo(dependency)
            xmlData?.let {
                val newVersion = XmlParserHelper.getVersionFromMavenMetaData(it)
                cacheManager.updateCacheMemory(dependency, newVersion)
                return newVersion
            }
        }

        throw CouldNotFindDependencyOnArtifacts(dependency)

    }

}