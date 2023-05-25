package com.cashfree.susbcription.coresdk.payment

import android.content.pm.ApplicationInfo
import android.content.pm.ResolveInfo
import android.util.Log
import android.webkit.JavascriptInterface
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

interface WebHelperInterface {
    fun getUpiAppList(link: String): List<ResolveInfo>
    fun onResponseReceived(returnedParams: Map<String, String>)
    fun openUpiApp(appPkg: String, url: String)
    fun getAppName(pkg: ApplicationInfo): String
    fun setTheme(color: String)
}

class WebJSInterfaceImpl(private val callback: WebHelperInterface) {
    private val TAG: String = "SubscriptionPayment"

    @JavascriptInterface
    fun paymentResult(result: String): String {
        Log.v(TAG, "Called paymentResult -->> $result")
        val returnedParams: MutableMap<String, String> = mutableMapOf()
        try {
            val response = JSONObject(result)
            val iterator: Iterator<String> = response.keys()
            while (iterator.hasNext()) {
                val key = iterator.next()
                returnedParams[key] = response.get(key).toString()
            }
        } catch (ex: JSONException) {
            ex.printStackTrace()
        }
        callback.onResponseReceived(returnedParams)
        return "true"
    }


    @JavascriptInterface
    fun getAppList(name: String): String {
        Log.v(TAG, "Called getAppList -->> $name")
        val resInfo: List<ResolveInfo> = callback.getUpiAppList(name)
        val packageNames = JSONArray()
        resInfo.forEach {
            val appInfo = JSONObject().apply {
                put("appName", callback.getAppName(it.activityInfo.applicationInfo))
                put("appPackage", it.activityInfo.packageName)
            }
            packageNames.put(appInfo)
        }
        Log.v(TAG, "GetAppList -->> $packageNames")
        return packageNames.toString()
    }

    @JavascriptInterface
    fun openApp(upiClientPackage: String, upiURL: String): Boolean {
        Log.v(TAG, "Called openApp -->> $upiClientPackage")
        callback.openUpiApp(upiClientPackage, upiURL);
        return true
    }

    @JavascriptInterface
    fun merchantTheme(config: String) {
        Log.v(TAG, "Called merchantTheme -->> $config ")
        val jsonObject = JSONObject(config)
        callback.setTheme(jsonObject.getString("theme_color"))
    }
}