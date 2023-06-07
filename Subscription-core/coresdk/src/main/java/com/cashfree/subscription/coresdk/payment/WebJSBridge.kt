package com.cashfree.subscription.coresdk.payment

import android.content.pm.ApplicationInfo
import android.content.pm.ResolveInfo
import android.webkit.JavascriptInterface
import com.cashfree.pg.base.logger.CFLoggerService
import org.json.JSONArray
import org.json.JSONObject

internal interface WebHelperInterface {
    fun getUpiAppList(link: String): List<ResolveInfo>
    fun onResponseReceived(jsonObject: JSONObject)
    fun onSubscriptionStatus(jsonObject: JSONObject)
    fun openUpiApp(appPkg: String, url: String)
    fun getAppName(pkg: ApplicationInfo): String
    fun setTheme(color: String)
}

internal class WebJSInterfaceImpl(private var callback: WebHelperInterface?) {
    private val TAG: String = "SubscriptionPayment"

    @JavascriptInterface
    fun paymentResult(result: String): String {
        CFLoggerService.getInstance().d(TAG, "paymentResult-->>$result")
        callback?.onResponseReceived(JSONObject(result))
        return "true"
    }

    @JavascriptInterface
    fun getSubscriptionStatus(result: String){
        CFLoggerService.getInstance().d(TAG, "subscriptionStatus-->>$result")
        callback?.onSubscriptionStatus(JSONObject(result))
    }


    @JavascriptInterface
    fun getAppList(name: String): String {
        CFLoggerService.getInstance().d(TAG, "getAppList-->>$name")
        val resInfo: List<ResolveInfo>? = callback?.getUpiAppList(name)
        val packageNames = JSONArray()
        resInfo?.forEach {
            val appInfo = JSONObject().apply {
                put("appName", callback?.getAppName(it.activityInfo.applicationInfo))
                put("appPackage", it.activityInfo.packageName)
            }
            packageNames.put(appInfo)
        }
        CFLoggerService.getInstance().d(TAG, "getAppList-->>$packageNames")
        return packageNames.toString()
    }

    @JavascriptInterface
    fun openApp(upiClientPackage: String, upiURL: String): Boolean {
        CFLoggerService.getInstance().d(TAG, "openApp-->>$upiClientPackage")
        callback?.openUpiApp(upiClientPackage, upiURL);
        return true
    }

    @JavascriptInterface
    fun merchantTheme(config: String) {
        CFLoggerService.getInstance().d(TAG, "merchantTheme-->>$config")
        val jsonObject = JSONObject(config)
        callback?.setTheme(jsonObject.getString("theme_color"))
    }

    fun clearCallback() {
        callback = null
    }
}