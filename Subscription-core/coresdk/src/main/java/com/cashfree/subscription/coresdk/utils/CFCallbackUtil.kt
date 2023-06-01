package com.cashfree.subscription.coresdk.utils

import com.cashfree.subscription.coresdk.channel.CFCallbackEventBus
import com.cashfree.subscription.coresdk.channel.CFCallbackEvents
import com.cashfree.subscription.coresdk.channel.CFPaymentCallbackEvent
import com.cashfree.subscription.coresdk.models.CFErrorResponse
import com.cashfree.subscription.coresdk.models.CFSubscriptionResponse

internal object CFCallbackUtil {

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