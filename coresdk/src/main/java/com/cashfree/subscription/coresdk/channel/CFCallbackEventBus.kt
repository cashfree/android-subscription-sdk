package com.cashfree.subscription.coresdk.channel

import com.cashfree.pg.base.CFEventBus
import com.cashfree.subscription.coresdk.models.CFErrorResponse
import com.cashfree.subscription.coresdk.models.CFSubscriptionResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.ExecutorService

interface CFCheckoutResponseCallback {
    fun onPaymentVerify(response: CFSubscriptionResponse)
    fun onPaymentCancelled(error: CFErrorResponse)
}

internal class CFCallbackEventBus private constructor(
    executorService: ExecutorService
) : CFEventBus<CFPaymentCallbackEvent, CFPaymentCallbackEvent>(executorService) {

    private var cfCheckoutResponseCallback: CFCheckoutResponseCallback? = null
    private var storedCFPaymentCallbackEvent: CFPaymentCallbackEvent? = null

    init {
        subscribe { event: CFPaymentCallbackEvent ->
            storedCFPaymentCallbackEvent = event
            triggerCallback(event)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: CFCallbackEventBus? = null

        fun initialize(executorService: ExecutorService) {
            synchronized(this) {
                INSTANCE = CFCallbackEventBus(executorService)
            }
        }

        fun getInstance() = INSTANCE
    }

    fun setCfPaymentCallback(paymentCallBack: CFCheckoutResponseCallback) {
        synchronized(CFCallbackEventBus::class) {
            cfCheckoutResponseCallback = paymentCallBack
        }
        checkAndFireStoredEvent()
    }

    private fun checkAndFireStoredEvent() {
        storedCFPaymentCallbackEvent?.let { triggerCallback(it) }
    }

    private fun triggerCallback(event: CFPaymentCallbackEvent) {
        when (event.eventId) {
            CFCallbackEvents.Verify -> {
                cfCheckoutResponseCallback?.let {
                    storedCFPaymentCallbackEvent = null
                    it.let { callback ->
                        CoroutineScope(Dispatchers.Main).launch {
                            event.response?.let { response ->
                                callback.onPaymentVerify(response)
                            }
                        }
                    }
                }
            }
            CFCallbackEvents.Cancelled -> {
                cfCheckoutResponseCallback?.let {
                    storedCFPaymentCallbackEvent = null
                    it.let { callback ->
                        CoroutineScope(Dispatchers.Main).launch {
                            event.error?.let { error ->
                                callback.onPaymentCancelled(error)
                            }
                        }
                    }
                }
            }
        }
    }


    override fun transformResponse(inputEvent: CFPaymentCallbackEvent): CFPaymentCallbackEvent =
        inputEvent
}