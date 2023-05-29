// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id (Module.Plugin.androidApplication) version "7.4.1" apply false
    id (Module.Plugin.androidLibrary) version "7.4.1" apply false
    id (Module.Plugin.kotlinAndroid) version "1.8.21" apply false
    id (Module.Plugin.hiltAndroid) version "2.44" apply false
}