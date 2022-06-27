plugins {
    kotlin("jvm").version("1.7.0")
}

group = "com.amit"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.3")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.13.3")
    implementation("com.google.code.gson:gson:2.9.0")

}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.create<org.gradle.jvm.tasks.Jar>("buildLibrary") {
    archiveVersion.set(version)
    archiveFileName.set("artifact-fetcher-${version}.jar")
    from("./src")
    destinationDirectory.set(File("./Out/Library/"))
}