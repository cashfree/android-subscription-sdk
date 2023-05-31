package com.cashfree.subscription.coresdk.services

import android.app.Application
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.cashfree.pg.base.exception.CFException
import com.cashfree.subscription.coresdk.channel.CFCallbackEventBus
import com.cashfree.subscription.coresdk.channel.CFCheckoutResponseCallback
import com.cashfree.subscription.coresdk.models.CFSubscriptionPayment
import com.cashfree.subscription.coresdk.ui.SubscriptionPaymentActivity
import com.cashfree.subscription.coresdk.utils.Constants
import com.cashfree.subscription.coresdk.utils.getWebViewVersion
import java.lang.ref.WeakReference
import java.util.concurrent.Executors

internal interface IPaymentService {
    fun doPayment(context: Context, webPayment: CFSubscriptionPayment)
    fun setCheckoutCallback(cfResponseCallback: CFCheckoutResponseCallback)
}

object CFSubscriptionPaymentService : IPaymentService {

    init {
        CFCallbackEventBus.initialize(Executors.newSingleThreadExecutor())
    }

    private var context: WeakReference<Context>? = null

    override fun doPayment(context: Context, webPayment: CFSubscriptionPayment) {
        if (context is Service || context is Application) {
            throw CFException("Calling context must be activity or fragment")
        }
        with(webPayment) {
            if (paymentUrl.isEmpty()) throw CFException("Payment url can't be null or empty")
            else webPayment.setBrowserVersion(context.getWebViewVersion())
        }
        this.context = WeakReference<Context>(context)
        startPayment(webPayment)
    }

    override fun setCheckoutCallback(cfResponseCallback: CFCheckoutResponseCallback) {
        CFCallbackEventBus.getInstance()?.setCfPaymentCallback(cfResponseCallback)
    }

    private fun startPayment(webPayment: CFSubscriptionPayment) {
        context?.let {
            it.get()?.let { context ->
                Intent(context, SubscriptionPaymentActivity::class.java).apply {
                    putExtras(getPaymentCheckoutBundle(webPayment))
                    context.startActivity(this)
                }
            }
        }
    }

    private fun getPaymentCheckoutBundle(webPayment: CFSubscriptionPayment): Bundle {
        return Bundle().apply {
            putString(Constants.PAYMENT_URL, webPayment.paymentUrl)
            putString(Constants.PAYMENT_SOURCE, webPayment.getSource())
        }
    }
}