@file:Suppress("UnstableApiUsage")
import Module.Dependencies

plugins {
    id(Module.Plugin.androidLibrary)
    id(Module.Plugin.kotlinAndroid)
}

android {
    namespace = "com.cashfree.subscription.coresdk"
    compileSdk = Versions.compileSdk

    defaultConfig {
        minSdk = Versions.minSdk
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
        }
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile ("proguard-android-optimize.txt"), "proguard-rules.pro")
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
    //implementation(Dependencies.base)
    implementation ("com.cashfree.pg:base:1.0.1")
    implementation(Dependencies.coreKtx)
    implementation(Dependencies.appcompat)
    implementation(Dependencies.material)
}