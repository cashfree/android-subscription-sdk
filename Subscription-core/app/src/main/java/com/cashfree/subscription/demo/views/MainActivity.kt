package com.cashfree.subscription.demo.views

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.cashfree.subscription.coresdk.channel.CFCheckoutResponseCallback
import com.cashfree.subscription.coresdk.models.CFErrorResponse
import com.cashfree.subscription.coresdk.models.CFSubscriptionPayment
import com.cashfree.subscription.coresdk.models.CFSubscriptionResponse
import com.cashfree.subscription.coresdk.services.CFSubscriptionPaymentService
import com.cashfree.subscription.demo.databinding.ActivityMainBinding
import com.cashfree.subscription.demo.helper.Config
import com.cashfree.subscription.demo.helper.Environment
import com.cashfree.subscription.demo.helper.visibility
import com.cashfree.subscription.demo.network.ApiState
import com.cashfree.subscription.demo.network.SubscriptionRequest
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val TAG = "SubscriptionPayment"
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private var alertDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        handleClick()
        observeDataChange()
        initBasicData()
        addPaymentCallback()
    }

    private fun handleClick() {
        binding.btnCreateSubs.setOnClickListener {
            createSubscription()
        }

        binding.btnMakePayment.setOnClickListener {
            val paymentLink = binding.tiePaymentUrl.text.toString()
            if (paymentLink.isEmpty().not()) openWebPaymentFlow(paymentLink)
            else showToast("Payment Link can't be empty")
        }

        binding.btnFetchSubscription.setOnClickListener {
            val subRefId = binding.tieSubRefId.text.toString()
            if (subRefId.isEmpty().not()) fetchExistingSubscription(subRefId)
            else showToast("SubRefId can't be empty")
        }

        binding.smEnv.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.tieAppId.setText(Environment.PROD_TEST.clientId)
                Config.environment = Environment.PROD_TEST
            } else {
                binding.tieAppId.setText(Environment.SANDBOX.clientId)
                Config.environment = Environment.SANDBOX
            }
        }
    }

    private fun observeDataChange() {
        viewModel.subscription.observe(this) { state ->
            when (state) {
                is ApiState.Success -> {
                    state.data.subscription?.let {
                        if (!"ACTIVE".contentEquals(it.status)) {
                            updateUIData(it.subReferenceId, it.authLink)
                            openWebPaymentFlow(it.authLink)
                        } else showToast("Subscription Already Active.")
                    }
                    state.data.authLink?.let {
                        updateUIData(state.data.subReferenceId, it)
                        openWebPaymentFlow(it)
                    }
                }
                is ApiState.Loading -> binding.progress.visibility(state.isLoading)
                is ApiState.Failure -> showToast(state.failure.message ?: "")
            }
        }
    }

    private fun updateUIData(subRefId: String, authLink: String) {
        with(binding) {
            tieSubRefId.setText(subRefId)
            tiePaymentUrl.setText(authLink)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun openWebPaymentFlow(url: String) {
        CFSubscriptionPaymentService.doPayment(this, CFSubscriptionPayment(url))
    }

    private fun initBasicData() {
        with(binding) {
            smEnv.isChecked = false
            tieAppId.setText(Environment.SANDBOX.clientId)
            tieSubsId.setText("test-sdk-2")
            tiePlanId.setText("nice-ondemand-1")
            tieEmail.setText("sidharth.shambu@cashfree.com")
            tiePhone.setText("9445737949")
            tieReturnUrl.setText("www.google.com")
            tieSubRefId.setText("247197")
            tiePaymentUrl.setText("https://cfre.in/nzishz7")
        }
    }

    private fun createSubscription() {
        val subsId = binding.tieSubsId.text.toString()
        val planId = binding.tiePlanId.text.toString()
        val email = binding.tieEmail.text.toString()
        val phone = binding.tiePhone.text.toString()
        val returnUrl = binding.tieReturnUrl.text.toString()

        val request = SubscriptionRequest(subsId, planId, email, phone, returnUrl)
        viewModel.createSubscription(Config.getHeaders(), request)
    }

    private fun fetchExistingSubscription(subRefId: String) {
        viewModel.fetchSubscription(Config.getHeaders(), subRefId)
    }

    private fun addPaymentCallback() {
        CFSubscriptionPaymentService.setCheckoutCallback(object : CFCheckoutResponseCallback {
            override fun onPaymentVerify(response: CFSubscriptionResponse) {
                Log.d(TAG, "Verify-->>$response")
                handleAlertDialog("Response", response.toString())
            }

            override fun onPaymentCancelled(error: CFErrorResponse) {
                Log.d(TAG, "Failure-->>$error")
                handleAlertDialog("Cancelled", error.toString())
            }
        })
    }

    private fun handleAlertDialog(status: String, msg: String) {
        alertDialog?.let {
            if (it.isShowing) it.dismiss()
        }

        alertDialog = AlertDialog.Builder(this)
            .apply {
                setTitle(status)
                setMessage(msg)
                setPositiveButton("OK") { dialog: DialogInterface, _: Int ->
                    dialog.dismiss()
                }
            }.create().apply {
                setCanceledOnTouchOutside(true)
            }
        alertDialog?.show()
    }
}