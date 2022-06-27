package exceptions

class CouldNotFindDependencyOnArtifacts(dependency: String) : Exception("dependency:[$dependency]")