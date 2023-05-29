object Versions {

    object APIVersion {
        const val versionMajor = 0
        const val versionMinor = 0
        const val versionPatch = 1
        const val suffix = ""
    }

    const val minSdk = 21
    const val targetSdk = 33
    const val compileSdk = 33

    const val apiVersionName =
        "${APIVersion.versionMajor}.${APIVersion.versionMinor}.${APIVersion.versionPatch}${APIVersion.suffix}"
    const val apiVersionCode =
        (APIVersion.versionMajor * 10000) + (APIVersion.versionMinor * 100) + APIVersion.versionPatch
}