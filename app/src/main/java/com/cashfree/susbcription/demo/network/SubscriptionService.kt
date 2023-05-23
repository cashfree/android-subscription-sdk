package com.cashfree.susbcription.demo.network

import retrofit2.http.Body
import retrofit2.http.HeaderMap
import retrofit2.http.POST

interface SubscriptionService {

    @POST("subscriptions")
    suspend fun createSubscription(
        @HeaderMap header: Map<String, String>,
        @Body params: SubscriptionRequest
    ): SubscriptionResponse
}