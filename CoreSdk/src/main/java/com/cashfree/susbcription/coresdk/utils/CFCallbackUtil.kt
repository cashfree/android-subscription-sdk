package com.cashfree.susbcription.coresdk.utils

import com.cashfree.susbcription.coresdk.channel.CFCallbackEventBus
import com.cashfree.susbcription.coresdk.channel.CFCallbackEvents
import com.cashfree.susbcription.coresdk.channel.CFPaymentCallbackEvent
import com.cashfree.susbcription.coresdk.models.CFErrorResponse
import com.cashfree.susbcription.coresdk.models.CFSubscriptionResponse

object CFCallbackUtil {

    fun sendOnVerify(response: CFSubscriptionResponse) {
        CFCallbackEventBus.getInstance()?.publishEvent(
            CFPaymentCallbackEvent(CFCallbackEvents.Verify, response = response)
        )
    }

    fun sendOnCancelled(error: CFErrorResponse) {
        CFCallbackEventBus.getInstance()?.publishEvent(
            CFPaymentCallbackEvent(CFCallbackEvents.Cancelled, error = error)
        )
    }
}