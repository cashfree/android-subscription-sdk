package com.cashfree.subscription.demo.network

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.Url

interface SubscriptionService {

    @POST
    suspend fun createSubscription(
        @HeaderMap header: Map<String, String>,
        @Body params: SubscriptionRequest,
        @Url url: String
    ): SubscriptionResponse

    @GET
    suspend fun fetchSubscription(
        @HeaderMap header: Map<String, String>,
        @Url url:String
    ): SubscriptionResponse
}