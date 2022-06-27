plugins {
    kotlin("jvm").version("1.7.0")
}

group = "com.amit"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.3")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.13.3")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}