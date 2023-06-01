package com.cashfree.subscription.demo.helper

object Config {
    var environment: Environment = Environment.SANDBOX

    fun getHeaders(): Map<String, String> {
        return mutableMapOf(
            "X-Client-Id" to environment.clientId,
            "X-Client-Secret" to environment.clientSecret
        )
    }
}