import model.ArtifactInfo

object App {
    private val repos = listOf<String>(
        "https://dl.google.com/dl/android/maven2/",
        "https://repo.maven.apache.org/maven2/"
    )

    @JvmStatic
    fun main(args: Array<String>) {
        val artifactRepos = repos.map { ArtifactInfo(it) }
        val versionFetcher = VersionFetcher(artifactRepos)

        val dependencies = listOf<String>(
            "androidx.core:core-ktx",
            "androidx.appcompat:appcompat",
            "com.google.android.material:material",
            "androidx.constraintlayout:constraintlayout",
            "androidx.navigation:navigation-fragment-ktx",
            "androidx.navigation:navigation-ui-ktx",
            "com.google.firebase:firebase-analytics",
            "androidx.compose.material3:material3",
            "androidx.compose.material3:material3-window-size-class",
            "junit:junit",
            "org.jetbrains.kotlinx:kotlinx-coroutines-android",
            "androidx.test.ext:junit",
            "androidx.test.espresso:espresso-core",
            "androidx.lifecycle:lifecycle-livedata-ktx",
            "androidx.lifecycle:lifecycle-viewmodel-ktx",
            "androidx.compose.ui:ui",
            "androidx.compose.ui:ui-tooling",
            "androidx.compose.foundation:foundation",
            "androidx.compose.material:material-icons-core",
            "androidx.compose.material:material-icons-extended",
            "androidx.activity:activity-compose",
            "androidx.lifecycle:lifecycle-viewmodel-compose",
            "androidx.lifecycle:lifecycle-runtime-ktx",
            "androidx.compose.runtime:runtime-livedata",
            "androidx.navigation:navigation-compose",
            "androidx.hilt:hilt-navigation-compose",
            "androidx.compose.ui:ui-test-junit4",
            "com.google.dagger:hilt-android",
            "com.google.dagger:hilt-android-compiler",
            "org.jetbrains.kotlinx:kotlinx-metadata-jvm",
            "androidx.hilt:hilt-lifecycle-viewmodel",
            "androidx.hilt:hilt-common",
            "androidx.hilt:hilt-navigation",
            "androidx.hilt:hilt-navigation-compose",
            "androidx.hilt:hilt-navigation-fragment",
            "com.github.bumptech.glide:glide",
            "com.facebook.stetho:stetho",
            "com.facebook.stetho:stetho-okhttp3",
            "com.squareup.retrofit2:converter-gson",
            "com.squareup.retrofit2:retrofit",
            "androidx.room:room-runtime",
            "androidx.room:room-ktx",
            "androidx.room:room-compiler",
            "androidx.room:room-testing",
            "com.jakewharton.timber:timber",
        )
        dependencies.forEach {
            val version = versionFetcher.getLatestVersion(it)
            println("dep : $it version : $version")
        }
    }
}