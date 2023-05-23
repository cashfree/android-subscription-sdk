package com.cashfree.susbcription.demo.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.cashfree.susbcription.demo.helper.Util
import com.cashfree.susbcription.demo.databinding.ActivityMainBinding
import com.cashfree.susbcription.demo.network.ApiState
import com.cashfree.susbcription.demo.network.SubscriptionRequest
import com.cashfree.susbcription.demo.helper.visibility
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        handleClick()
        observeDataChange()
        initBasicData()
    }

    private fun handleClick() {
        binding.btnCreateSubs.setOnClickListener {
            createSubscription()
        }
    }

    private fun observeDataChange() {
        viewModel.subscription.observe(this) { state ->
            when (state) {
                is ApiState.Success -> showToast(state.data.toString())
                is ApiState.Loading -> binding.progress.visibility(state.isLoading)
                is ApiState.Failure -> showToast(state.failure.message ?: "")
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun initBasicData() {
        with(binding) {
            tieSubsId.setText("test-sdk-2")
            tiePlanId.setText("nice-ondemand-1")
            tieEmail.setText("sidharth.shambu@cashfree.com")
            tiePhone.setText("9445737949")
            tieReturnUrl.setText("www.google.com")
        }
    }

    private fun createSubscription() {
        val subsId = binding.tieSubsId.text.toString()
        val planId = binding.tiePlanId.text.toString()
        val email = binding.tieEmail.text.toString()
        val phone = binding.tiePhone.text.toString()
        val returnUrl = binding.tieReturnUrl.text.toString()

        val request = SubscriptionRequest(subsId, planId, email, phone, returnUrl)
        viewModel.createSubscription(Util.header, request)
    }
}