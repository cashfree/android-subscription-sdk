@file:Suppress("UnstableApiUsage")

import Module.Dependencies

plugins {
    id(Module.Plugin.androidApplication)
    id(Module.Plugin.kotlinAndroid)
    id(Module.Plugin.hiltAndroid)
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.cashfree.subscription.demo"
    compileSdk = Versions.compileSdk

    defaultConfig {
        applicationId = "com.cashfree.subscription.demo"
        minSdk = Versions.minSdk
        targetSdk = Versions.targetSdk
        versionCode = Versions.apiVersionCode
        versionName = Versions.apiVersionName

        buildConfigField("Integer", "VERSION_CODE", "${Versions.apiVersionCode}")
        buildConfigField("String", "VERSION_NAME", "\"${Versions.apiVersionName}\"")

    }

    productFlavors {
        flavorDimensions.add("tier")
        create("demo") {
            versionNameSuffix = "-demo"
            dimension = flavorDimensions[0]
        }
    }

    buildTypes {
        named("debug") {
            isMinifyEnabled = false
            isRenderscriptDebuggable = true
        }
        named("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.findByName("debug")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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

    implementation(Dependencies.coreKtx)
    implementation(Dependencies.activityKtx)
    implementation(Dependencies.appcompat)
    implementation(Dependencies.material)
    implementation(Dependencies.constraintLayout)

    implementation(Dependencies.retrofit)
    implementation(Dependencies.okhttp)
    implementation(Dependencies.constraintLayout)

    implementation(Dependencies.retrofit_gson)
    implementation(Dependencies.okhttp_logger)

    implementation(Dependencies.coroutineCore)
    implementation(Dependencies.coroutineAndroid)
    implementation(Dependencies.livedata)
    implementation(Dependencies.viewmodel)


    //DI
    implementation(Dependencies.hiltAndroid)
    kapt(Dependencies.hiltCompiler)

    releaseApi(Dependencies.subscription)
    debugImplementation(project(":coresdk"))
}