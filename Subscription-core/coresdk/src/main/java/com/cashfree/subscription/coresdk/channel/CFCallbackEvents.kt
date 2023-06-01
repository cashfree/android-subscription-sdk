package com.cashfree.subscription.coresdk.channel

import com.cashfree.subscription.coresdk.models.CFErrorResponse
import com.cashfree.subscription.coresdk.models.CFSubscriptionResponse

internal sealed class CFCallbackEvents {
    object Verify : CFCallbackEvents()
    object Cancelled : CFCallbackEvents()
}

internal data class CFPaymentCallbackEvent(
    val eventId: CFCallbackEvents,
    val response: CFSubscriptionResponse? = null,
    val error: CFErrorResponse? = null
)