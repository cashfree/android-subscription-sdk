package com.cashfree.susbcription.coresdk.services

import android.app.Application
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.cashfree.pg.base.exception.CFException
import com.cashfree.susbcription.coresdk.channel.CFCallbackEventBus
import com.cashfree.susbcription.coresdk.channel.CFCheckoutResponseCallback
import com.cashfree.susbcription.coresdk.payment.Constants
import com.cashfree.susbcription.coresdk.ui.SubscriptionPaymentActivity
import java.lang.ref.WeakReference
import java.util.concurrent.Executors

interface IPaymentService {
    fun doPayment(context: Context, url: String)
    fun setCheckoutCallback(cfResponseCallback: CFCheckoutResponseCallback)
}

object CFSubscriptionPaymentService : IPaymentService {

    init {
        CFCallbackEventBus.initialize(Executors.newSingleThreadExecutor())
    }

    private var context: WeakReference<Context>? = null

    override fun doPayment(context: Context, url: String) {
        if (context is Service || context is Application) {
            throw CFException("Calling context must be activity or fragment")
        }
        this.context = WeakReference<Context>(context)
        startPayment(url)
    }

    override fun setCheckoutCallback(cfResponseCallback: CFCheckoutResponseCallback) {
        CFCallbackEventBus.getInstance()?.setCfPaymentCallback(cfResponseCallback)
    }

    private fun startPayment(url: String) {
        context?.let {
            it.get()?.let { context ->
                Intent(context, SubscriptionPaymentActivity::class.java).apply {
                    putExtras(getPaymentCheckoutBundle(url))
                    context.startActivity(this)
                }
            }
        }
    }

    private fun getPaymentCheckoutBundle(url: String): Bundle {
        return Bundle().apply {
            putString(Constants.PAYMENT_URL, url)
        }
    }
}