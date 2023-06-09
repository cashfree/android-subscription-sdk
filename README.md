## Subscription Payment For Mobile SDKs
This repository will have subscription payment flow for Android platform.

### Sample
<img src="media/sample.gif" alt="Subscription Sample" width="250" height="500"/>

### [Android Integration](https://github.com/cashfree/android-subscription-sdk/blob/master/Subscription-sample/app/src/main/java/com/cashfree/susbcription/sample/MainActivityKotlin.kt) 

#### Steps

1. Add Maven url for Cashfree subscription SDK in project level `build.gradle`.

   `maven { url "https://maven.cashfree.com/release"}`

2. Add sdk dependencies in app level `build.gradle`.

   `implementation "com.cashfree.subscription:coresdk:0.0.1"`

3. Register for payment result callback

   `CFSubscriptionPaymentService.setCheckoutCallback(this)`

4. Call `doPayment` with `CFSubscriptionPayment` object.

   `CFSubscriptionPaymentService.doPayment(this, CFSubscriptionPayment(url))`


Click [here](https://docs.cashfree.com/docs/subscription-android-sdk) for more Documentation.

### License

MIT
