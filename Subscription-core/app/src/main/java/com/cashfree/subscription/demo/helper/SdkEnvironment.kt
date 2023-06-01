package com.cashfree.subscription.demo.helper

enum class Environment(
    val url: String,
    val clientId: String,
    val clientSecret: String
) {
    SANDBOX(
        "https://test-k8s.cashfree.com/subscriptionapi/api/v2/subscriptions/",
        "144436e71d659a3bb9cd8bac74634441",
        "TEST365fd19c71734164c8cd891474ee09ad39654736"
    ),
    PROD_TEST(
        "https://prod.cashfree.com/subscriptionapi-test/api/v2/subscriptions/",
        "1848d0ce8441fb8ffa258bc98481",
        "f7cbcd7ba238c4f85a4083c39f9386be33de1214"
    );
}