package com.cashfree.subscription.coresdk.base

import android.os.Build
import com.cashfree.subscription.coresdk.BuildConfig
import java.util.*

open class CFPayment {

    private var sdkFramework: CFSDKFramework = CFSDKFramework.ANDROID
    private var sdkFlavour: CFSDKFlavour = CFSDKFlavour.WEB_CHECKOUT
    private var sdkVersion: String = BuildConfig.VERSION_NAME
    private var browserVersion: String = "x"
    private var source: String? = null

    fun setBrowserVersion(browserVersion: String) {
        this.browserVersion = browserVersion
        updateSource()
    }

    private fun updateSource() {
        source = String.format(
            "%s-%s-%s-%s-%s-%s-%s-%s",
            sdkFramework.framework,
            sdkFlavour.sdkFlavour,
            sdkVersion,
            "m",
            "w",
            browserVersion,
            "a",
            Build.VERSION.SDK_INT.toString()
        ).lowercase((Locale.getDefault()))
    }

    fun getSource(): String? = source

}

internal enum class CFSDKFramework(val framework: String) {
    ANDROID("andx")
}

internal enum class CFSDKFlavour(val sdkFlavour: String) {
    WEB_CHECKOUT("c")
}