package com.cashfree.subscription.coresdk.utils

import com.cashfree.subscription.coresdk.models.AuthStatus
import com.cashfree.subscription.coresdk.models.CFErrorResponse
import com.cashfree.subscription.coresdk.models.CFSubscriptionResponse
import org.json.JSONObject

internal class CfUtils {

    companion object {

        fun getPaymentResponse(jsonObject: JSONObject): CFSubscriptionResponse {
            return CFSubscriptionResponse(
                jsonObject.getString(Constants.SUBS_ID),
                jsonObject.getString(Constants.SUB_REF_ID),
                jsonObject.getString(Constants.AUTH_STATUS),
                jsonObject.getString(Constants.SUB_STATUS),
            )
        }

        fun getCancelledPaymentResponse(): CFErrorResponse {
            return CFErrorResponse(
                AuthStatus.FAILED.status,
                "Payment has been cancelled",
                "payment_cancelled"
            )
        }
    }
}