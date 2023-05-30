package com.cashfree.subscription.coresdk.ui

import android.annotation.SuppressLint
import android.content.pm.ApplicationInfo
import android.content.pm.ResolveInfo
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.DrawableCompat
import com.cashfree.pg.base.logger.CFLoggerService
import com.cashfree.subscription.coresdk.models.CFErrorResponse
import com.cashfree.subscription.coresdk.models.CFSubscriptionResponse
import com.cashfree.subscription.coresdk.payment.Constants
import com.cashfree.subscription.coresdk.payment.Constants.WB_INTENT_BRIDGE
import com.cashfree.subscription.coresdk.payment.WebHelperInterface
import com.cashfree.subscription.coresdk.payment.WebJSInterfaceImpl
import com.cashfree.subscription.coresdk.utils.CFCallbackUtil
import com.cashfree.subscription.coresdk.utils.CfUtils
import com.cashfree.subscription.coresdk.utils.getUPIIntent
import com.cashfree.subscription.coresdk.utils.queryIntent
import com.cashfree.subscription.coresdk.databinding.SubscriptionPaymentActivityBinding
import org.json.JSONObject


internal class SubscriptionPaymentActivity : AppCompatActivity() {

    private val TAG = "SubscriptionPayment"
    private lateinit var binding: SubscriptionPaymentActivityBinding
    private var exitDialog: AlertDialog? = null
    private val webJsBridge: WebJSInterfaceImpl by lazy {
        WebJSInterfaceImpl(addWebHelperInterfaceImplementation())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SubscriptionPaymentActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar()
        setWebView()
        loadUrl()
        addBackPressDispatcher()
    }

    override fun onStop() {
        super.onStop()
        hideExitDialog()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.let { actionBar ->
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = ""
        }
    }

    private fun setToolBarTheme(themeColor: String) {
        val color = Color.parseColor(themeColor)
        with(binding) {
            toolbar.apply {
                setTitleTextColor(color)
                navigationIcon?.let {
                    DrawableCompat.setTint(it, color)
                }
            }
            toolbarTitle.setTextColor(color)
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setWebView() {
        with(binding.paymentWebView) {
            settings.apply {
                javaScriptEnabled = true
                mixedContentMode = WebSettings.MIXED_CONTENT_NEVER_ALLOW
            }
            addJavascriptInterface(webJsBridge, WB_INTENT_BRIDGE)
        }
        setWebViewClient()
    }

    private fun setWebViewClient() {
        binding.paymentWebView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                CFLoggerService.getInstance().d(TAG, "onPageFinished-->>$url")
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                CFLoggerService.getInstance().d(TAG, "onPageStarted-->>$url")
            }

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                CFLoggerService.getInstance().d(TAG, "shouldOverrideUrlLoading-->>$url")
                return false
            }

        }
    }


    private fun loadUrl() {
        intent.extras?.let { bundle ->
            bundle.getString(Constants.PAYMENT_URL)?.let { paymentLink ->
                binding.paymentWebView.loadUrl(paymentLink)
            }
        }
    }

    private fun addBackPressDispatcher() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                exitDialog = ExitDialog(this@SubscriptionPaymentActivity) {
                    finish()
                    handleCancelled(CfUtils.getCancelledPaymentResponse())
                }
                if (!isFinishing && !isDestroyed) {
                    exitDialog?.show()
                }
            }
        })
    }

    private fun hideExitDialog() {
        exitDialog?.let {
            if (it.isShowing) {
                it.dismiss()
            }
        }
    }

    private fun addWebHelperInterfaceImplementation(): WebHelperInterface {
        return object : WebHelperInterface {
            override fun getUpiAppList(link: String): List<ResolveInfo> {
                CFLoggerService.getInstance().d(TAG, "getUpiAppList-->>$link")
                return queryIntent(getUPIIntent(link))
            }

            override fun onResponseReceived(jsonObject: JSONObject) {
                CFLoggerService.getInstance().d(TAG, "onResponseReceived")
                handlePaymentResponse(CfUtils.getPaymentResponse(jsonObject))
            }

            override fun openUpiApp(appPkg: String, url: String) {
                CFLoggerService.getInstance().d(TAG, "openUpiApp-->>$url")
                val intent = getUPIIntent(url)
                val resInfo = queryIntent(intent)
                val upiClientResolveInfo: ResolveInfo? =
                    resInfo.find { it.activityInfo.packageName == appPkg }

                upiClientResolveInfo?.let {
                    intent.setClassName(it.activityInfo.packageName, it.activityInfo.name)
                    startActivity(intent)
                }
            }

            override fun getAppName(pkg: ApplicationInfo): String =
                packageManager.getApplicationLabel(pkg).toString()

            override fun setTheme(color: String) {
                CFLoggerService.getInstance().d(TAG, "setTheme-->>$color")
                setToolBarTheme("#$color")
            }
        }
    }

    private fun handleCancelled(error: CFErrorResponse) {
        finish()
        CFCallbackUtil.sendOnCancelled(error)
    }

    private fun handlePaymentResponse(response: CFSubscriptionResponse) {
        finish()
        CFCallbackUtil.sendOnVerify(response)
    }
}