package com.cashfree.susbcription.coresdk.models

/**
"subscription_id": "test_checkout_001ywy",
"sub_reference_id": 73967,
"auth_status": "PENDING",
"sub_status": "BANK_APPROVAL_PENDING",
}
 */
data class CFSubscriptionResponse(
    val subscriptionId: String,
    val subReferenceId: String,
    val authStatus: String,
    val subStatus: String
)

data class CFErrorResponse(
    val status: String,
    val message: String,
    val code: String
)

internal enum class AuthStatus(val status: String) {
    PENDING("PENDING"),
    FAILED("FAILED"),
    INITIALIZED("INITIALIZED"),
    ACTIVE("ACTIVE")
}