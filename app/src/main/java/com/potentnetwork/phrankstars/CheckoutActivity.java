package com.potentnetwork.phrankstars;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class CheckoutActivity extends AppCompatActivity {
    TextView payment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        payment = findViewById(R.id.payment);

        String paymentAmount = getIntent().getStringExtra("payment");
        payment.setText(String.valueOf(paymentAmount));
    }
}