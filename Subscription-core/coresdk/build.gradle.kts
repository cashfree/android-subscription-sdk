@file:Suppress("UnstableApiUsage")
import Module.Dependencies

plugins {
    id(Module.Plugin.androidLibrary)
    id(Module.Plugin.kotlinAndroid)
}

apply(from = "${rootProject.projectDir}/scripts/publish-module.gradle")

android {
    namespace = "com.cashfree.subscription.coresdk"
    compileSdk = Versions.compileSdk

    defaultConfig {
        minSdk = Versions.minSdk
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField("Integer", "VERSION_CODE", "${Versions.apiVersionCode}")
        buildConfigField("String", "VERSION_NAME", "\"${Versions.apiVersionName}\"")
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
        }
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    api(Dependencies.base)
    implementation(Dependencies.coreKtx)
    implementation(Dependencies.appcompat)
    implementation(Dependencies.material)
}