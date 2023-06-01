package com.cashfree.subscription.demo.views

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cashfree.subscription.demo.helper.Config
import com.cashfree.subscription.demo.network.ApiState
import com.cashfree.subscription.demo.network.SubscriptionRequest
import com.cashfree.subscription.demo.network.SubscriptionResponse
import com.cashfree.subscription.demo.network.SubscriptionService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val service: SubscriptionService
) : ViewModel() {

    private val _subscription: MutableLiveData<ApiState<SubscriptionResponse>> by lazy { MutableLiveData() }
    val subscription: LiveData<ApiState<SubscriptionResponse>> by lazy { _subscription }

    fun createSubscription(headers: Map<String, String>, param: SubscriptionRequest) {
        _subscription.value = ApiState.Loading(true)
        val job = viewModelScope.async {
            service.createSubscription(headers, param, Config.environment.url)
        }
        viewModelScope.launch {
            try {
                val result = job.await()
                _subscription.value = ApiState.Loading(false)
                _subscription.value = ApiState.Success(result)
            } catch (e: Exception) {
                _subscription.value = ApiState.Loading(false)
                _subscription.value = ApiState.Failure(Throwable(e.message ?: "Some Error"))
            }
        }
    }

    fun fetchSubscription(headers: Map<String, String>, subRefId: String) {
        _subscription.value = ApiState.Loading(true)
        val job = viewModelScope.async {
            service.fetchSubscription(headers, String.format("%s%s", Config.environment.url, subRefId))
        }
        viewModelScope.launch {
            try {
                val result = job.await()
                _subscription.value = ApiState.Loading(false)
                _subscription.value = ApiState.Success(result)
            } catch (e: Exception) {
                _subscription.value = ApiState.Loading(false)
                _subscription.value = ApiState.Failure(Throwable(e.message ?: "Some Error"))
            }
        }
    }
}