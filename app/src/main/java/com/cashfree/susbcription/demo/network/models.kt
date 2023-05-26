package com.cashfree.susbcription.demo.network

import com.google.gson.annotations.SerializedName


sealed class ApiState<out T> {
    data class Loading(val isLoading: Boolean) : ApiState<Nothing>()
    data class Success<T>(val data: T) : ApiState<T>()
    data class Failure(val failure: Throwable) : ApiState<Nothing>()
}

/**
 * {
"status": "OK",
"message": "Subscription created successfully",
"subReferenceId": 138280,
"authLink": "https://cfre.in/lhz17ul",
"subStatus": "INITIALIZED"
}
 */
data class SubscriptionResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("subReferenceId")
    val subReferenceId: String,
    @SerializedName("authLink")
    val authLink: String?,
    @SerializedName("subStatus")
    val subStatus: String,
    @SerializedName("subscription")
    val subscription: Subscription? = null
)

data class Subscription(
    @SerializedName("authLink")
    val authLink: String,
)

/**
 * {
"subscriptionId": "test-sdk-2",
"planId": "nice-ondemand-1",
"customerEmail": "sidharth.shambu@cashfree.com",
"customerPhone": "9445737949",
"returnUrl": "www.google.com"
}
 */
data class SubscriptionRequest(
    @SerializedName("subscriptionId")
    private val subscriptionId: String,
    @SerializedName("planId")
    private val planId: String,
    @SerializedName("customerEmail")
    private val customerEmail: String,
    @SerializedName("customerPhone")
    private val customerPhone: String,
    @SerializedName("returnUrl")
    private val returnUrl: String? = null
)