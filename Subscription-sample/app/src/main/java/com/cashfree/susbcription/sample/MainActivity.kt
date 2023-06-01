package com.cashfree.susbcription.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.cashfree.subscription.coresdk.channel.CFCheckoutResponseCallback
import com.cashfree.subscription.coresdk.models.CFErrorResponse
import com.cashfree.subscription.coresdk.models.CFSubscriptionPayment
import com.cashfree.subscription.coresdk.models.CFSubscriptionResponse
import com.cashfree.subscription.coresdk.services.CFSubscriptionPaymentService
import com.cashfree.susbcription.sample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val TAG = "SubscriptionPayment"
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        handlePaymentClick()
        addPaymentCallback()
    }

    private fun handlePaymentClick() {
        binding.btnMakePayment.setOnClickListener {
            val paymentLink = binding.tiePaymentUrl.text.toString()
            if (paymentLink.isEmpty().not()) openWebPaymentFlow(paymentLink)
            else showToast("Payment Link can't be empty")
        }
    }

    private fun openWebPaymentFlow(url: String) {
        CFSubscriptionPaymentService.doPayment(this, CFSubscriptionPayment(url))
    }

    private fun addPaymentCallback() {
        CFSubscriptionPaymentService.setCheckoutCallback(object : CFCheckoutResponseCallback {
            override fun onPaymentVerify(response: CFSubscriptionResponse) {
                Log.d(TAG, "Verify-->>$response")
            }

            override fun onPaymentCancelled(error: CFErrorResponse) {
                Log.d(TAG, "Failure-->>$error")
            }
        })
    }


    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}