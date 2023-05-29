package com.cashfree.subscription.demo.network

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.Path

interface SubscriptionService {

    @POST("subscriptions")
    suspend fun createSubscription(
        @HeaderMap header: Map<String, String>,
        @Body params: SubscriptionRequest
    ): SubscriptionResponse

    @GET("subscriptions/{subRefId}")
    suspend fun fetchSubscription(
        @HeaderMap header: Map<String, String>,
        @Path("subRefId") subRefId: String
    ): SubscriptionResponse
}