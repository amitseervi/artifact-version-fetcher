import exceptions.CouldNotFindDependencyOnArtifacts
import model.ArtifactInfo
import utils.XmlParserHelper

class VersionFetcher(private val repositories: List<ArtifactInfo>) {
    private val artifactRequestHandlers = repositories.map { artifactInfo ->
        ArtifactRequestHandler(artifactInfo)
    }

    fun getLatestVersion(dependency: String): String {
        artifactRequestHandlers.forEach { artifactory ->
            val xmlData = artifactory.fetchDependencyMetaInfo(dependency)
            xmlData?.let {
                return XmlParserHelper.getVersionFromMavenMetaData(it)
            }
        }
        throw CouldNotFindDependencyOnArtifacts(dependency)

    }
}