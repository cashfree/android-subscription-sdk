package com.cashfree.susbcription.coresdk.channel

import com.cashfree.susbcription.coresdk.models.CFErrorResponse
import com.cashfree.susbcription.coresdk.models.CFSubscriptionResponse

sealed class CFCallbackEvents {
    object Verify : CFCallbackEvents()
    object Cancelled : CFCallbackEvents()
}

data class CFPaymentCallbackEvent(
    val eventId: CFCallbackEvents,
    val response: CFSubscriptionResponse? = null,
    val error: CFErrorResponse? = null
)