package com.cashfree.subscription.demo.helper

import com.cashfree.subscription.demo.BuildConfig

enum class Environment(
    val url: String,
    val clientId: String,
    val clientSecret: String
) {
    SANDBOX(
        "https://test-k8s.cashfree.com/subscriptionapi/api/v2/subscriptions/",
        BuildConfig.SANDBOX_CLIENT_ID,
        BuildConfig.SANDBOX_CLIENT_SECRET
    ),
    PROD_TEST(
        "https://prod.cashfree.com/subscriptionapi-test/api/v2/subscriptions/",
        BuildConfig.PROD_TEST_CLIENT_ID,
        BuildConfig.PROD_TEST_CLIENT_SECRET
    );
}