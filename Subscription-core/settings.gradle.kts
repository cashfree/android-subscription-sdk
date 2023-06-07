@file:Suppress("UnstableApiUsage")

import java.net.URI

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        //maven { url = URI("https://maven.cashfree.com/development")}
        maven { url = URI("https://maven.cashfree.com/release") }
    }
}
rootProject.name = "Subscription Core"
include(":app", ":coresdk")
