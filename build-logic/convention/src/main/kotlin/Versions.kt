@SuppressWarnings("unused")
object Versions {
    // Project versions
    private const val version = "0.21.1"
    const val versionCode = 77

    val versionName by lazy {
        if (CI.isCiBuild) {
            "$version-${CI.commitHash}-SNAPSHOT"
        } else version
    }

    // Platform & Tool versions
    const val buildToolsVersion = "33.0.0"
    const val compileSdkVersion = 33
    const val minSdkVersion = 21
    const val minSdkVersionHighApi = 26
    const val targetSdkVersion = 33
}