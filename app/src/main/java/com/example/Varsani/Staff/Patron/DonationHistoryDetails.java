package com.example.Varsani.Staff.Patron;

import static com.example.Varsani.utils.Urls.URL_ADD_TO_WALLET;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.Varsani.Artists.MyDonationDetails;
import com.example.Varsani.R;
import com.example.Varsani.Staff.Models.DonationHistory;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DonationHistoryDetails extends AppCompatActivity {
    private TextView tv_donation_id,tv_donor,tv_artist,tv_amount, tv_payment_method,tv_reference_code,tv_status;
    private TextView tv_title;
    private Button btn_view_receipt;
    private String donationID,donorID,artID,artistID,amount,artist,donor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_history_details);

        getSupportActionBar().setSubtitle("Donation History");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_donation_id=findViewById(R.id.tv_donation_id);
        tv_title=findViewById(R.id.tv_title);
        tv_payment_method=findViewById(R.id.tv_payment_method);
        tv_artist=findViewById(R.id.tv_artist);
        tv_status=findViewById(R.id.tv_status);
        tv_reference_code=findViewById(R.id.tv_reference_code);
        tv_donor=findViewById(R.id.tv_donor);
        tv_amount=findViewById(R.id.tv_amount);
        btn_view_receipt=findViewById(R.id.btn_view_receipt);


        Intent intent=getIntent();

        donationID=intent.getStringExtra("donationID");
        donorID=intent.getStringExtra("donorID");
        artist=intent.getStringExtra("artist");
        donor=intent.getStringExtra("donor");
        artID=intent.getStringExtra("artID");
        artistID=intent.getStringExtra("artistID");
        amount=intent.getStringExtra("amount");
        String paymentMethod=intent.getStringExtra("paymentMethod");
        String refCode=intent.getStringExtra("refCode");
        String donationStatus=intent.getStringExtra("donationStatus");
        String title=intent.getStringExtra("title");

        tv_amount.setText("Amount: " + amount);
        tv_payment_method.setText("Payment Method: " + paymentMethod);
        tv_artist.setText("Artist: " + artist);
        tv_status.setText("Status: " + donationStatus);
        tv_reference_code.setText("Reference Code: " + refCode);
        tv_donation_id.setText("Donation ID: " + donationID);
        tv_donor.setText("Donor: " + donor );
        tv_title.setText("Title: " + title );

        btn_view_receipt.setOnClickListener(v -> {
            Intent receiptIntent = new Intent(DonationHistoryDetails.this, ViewReceipt.class);
            receiptIntent.putExtra("artist", artist);
            receiptIntent.putExtra("donor", donor);
            receiptIntent.putExtra("amount", amount);
            receiptIntent.putExtra("paymentMethod", paymentMethod);
            receiptIntent.putExtra("refCode", refCode);
            receiptIntent.putExtra("title", title);
            startActivity(receiptIntent);
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}