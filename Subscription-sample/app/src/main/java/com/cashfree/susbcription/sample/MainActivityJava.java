package com.cashfree.susbcription.sample;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cashfree.subscription.coresdk.channel.CFCheckoutResponseCallback;
import com.cashfree.subscription.coresdk.models.CFErrorResponse;
import com.cashfree.subscription.coresdk.models.CFSubscriptionPayment;
import com.cashfree.subscription.coresdk.models.CFSubscriptionResponse;
import com.cashfree.subscription.coresdk.services.CFSubscriptionPaymentService;
import com.cashfree.susbcription.sample.databinding.ActivityMainBinding;

public class MainActivityJava extends AppCompatActivity {

    private static final String TAG = "MainActivityJava";
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        handlePaymentClick();
        addPaymentCallback();
        binding.tiePaymentUrl.setText("https://cfre.in/gjxrigv");
    }

    private void handlePaymentClick() {
        binding.btnMakePayment.setOnClickListener(v -> {
            String paymentLink = binding.tiePaymentUrl.getText().toString();
            if (!paymentLink.isEmpty()) openWebPaymentFlow(paymentLink);
            else showToast("Payment Link can't be empty");
        });
    }

    private void openWebPaymentFlow(String url) {
        CFSubscriptionPaymentService.INSTANCE.doPayment(this, new CFSubscriptionPayment(url));
    }

    private void addPaymentCallback() {
        CFSubscriptionPaymentService.INSTANCE.setCheckoutCallback(new CFCheckoutResponseCallback() {

            @Override
            public void onPaymentVerify(@NonNull CFSubscriptionResponse cfSubscriptionResponse) {
                Log.d(TAG, "Verify-->>"+ cfSubscriptionResponse);
            }

            @Override
            public void onPaymentCancelled(@NonNull CFErrorResponse cfErrorResponse) {
                Log.d(TAG, "Failure-->>"+ cfErrorResponse);
            }
        });
    }


    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
