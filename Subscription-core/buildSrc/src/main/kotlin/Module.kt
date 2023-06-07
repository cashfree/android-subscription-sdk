object Module {
    object Plugin {
        const val androidLibrary = "com.android.library"
        const val kotlinAndroid = "org.jetbrains.kotlin.android"
        const val androidApplication = "com.android.application"
        const val hiltAndroid = "com.google.dagger.hilt.android"
        const val mavenPublish = "maven-publish"
    }

    object Dependencies {
        internal object Version {
            const val coreKtx = "1.5.0"
            const val activityKtx = "1.7.1"
            const val appCompatVersion = "1.5.0"
            const val materialVersion = "1.5.0"
            const val constraintLayoutVersion = "2.1.4"
            const val gsonVersion = "2.8.8"
            const val retrofitVersion = "2.9.0"
            const val okhttpVersion = "4.10.0"
            const val leakCanary = "2.9.1"
            const val baseVersion = "1.0.1"
            const val analyticsVersion = "1.0.13"


            const val gsonConvertor = "2.9.0"
            const val loggingInterceptor = "5.0.0-alpha.6"
            const val coroutineCore = "1.6.0"
            const val coroutineAndroid = "1.6.4"
            const val livedataKtx = "2.6.1"
            const val viewModelKtx = "2.6.1"
            const val hiltAndroid = "2.44"
            const val hiltCompiler = "2.44"
            const val subscription = "0.0.1"
        }

        const val coreKtx = "androidx.core:core-ktx:${Version.coreKtx}"
        const val activityKtx = "androidx.activity:activity-ktx:${Version.activityKtx}"
        const val appcompat = "androidx.appcompat:appcompat:${Version.appCompatVersion}"
        const val material = "com.google.android.material:material:${Version.materialVersion}"
        const val constraintLayout =
            "androidx.constraintlayout:constraintlayout:${Version.constraintLayoutVersion}"
        const val gson = "com.google.code.gson:gson:${Version.gsonVersion}"
        const val retrofit = "com.squareup.retrofit2:retrofit:${Version.retrofitVersion}"
        const val retrofit_gson = "com.squareup.retrofit2:converter-gson:${Version.retrofitVersion}"
        const val okhttp = "com.squareup.okhttp3:okhttp:${Version.okhttpVersion}"
        const val okhttp_logger =
            "com.squareup.okhttp3:logging-interceptor:${Version.loggingInterceptor}"
        const val leakCanary = "com.squareup.leakcanary:leakcanary-android:${Version.leakCanary}"

        const val coroutineCore =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.coroutineCore}"
        const val coroutineAndroid =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Version.coroutineAndroid}"
        const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:${Version.livedataKtx}"
        const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Version.viewModelKtx}"
        const val hiltAndroid = "com.google.dagger:hilt-android:${Version.hiltAndroid}"
        const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Version.hiltCompiler}"

        const val base = "com.cashfree.pg:base:${Version.baseVersion}"
        const val cf_analytics = "com.cashfree.pg:analytics:${Version.analyticsVersion}"
        const val subscription = "com.cashfree.subscription:coresdk:${Version.subscription}"
    }
}