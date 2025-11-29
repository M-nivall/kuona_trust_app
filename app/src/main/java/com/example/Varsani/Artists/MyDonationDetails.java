package com.example.Varsani.Artists;

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
import com.example.Varsani.R;
import com.example.Varsani.Staff.Finance.DonationDetails;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MyDonationDetails extends AppCompatActivity {
    private TextView tv_donation_id,tv_donor,tv_artist,tv_amount, tv_payment_method,tv_reference_code,tv_status;
    private TextView tvArtistShare,tvCompanyShare,tv_total_fund,tv_title;
    private Button btn_add_to_wallet;
    private String donationID,donorID,artID,artistID,amount,artist;
    private double artistShare,totalAmount,companyShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_donation_details);

        getSupportActionBar().setSubtitle("Received Donation");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_donation_id=findViewById(R.id.tv_donation_id);
        tv_title=findViewById(R.id.tv_title);
        tv_payment_method=findViewById(R.id.tv_payment_method);
        tv_artist=findViewById(R.id.tv_artist);
        tv_status=findViewById(R.id.tv_status);
        tv_reference_code=findViewById(R.id.tv_reference_code);
        tv_donor=findViewById(R.id.tv_donor);
        tv_amount=findViewById(R.id.tv_amount);
        tv_total_fund=findViewById(R.id.tv_total_fund);
        btn_add_to_wallet=findViewById(R.id.btn_add_to_wallet);

        tvArtistShare = findViewById(R.id.tv_artist_share);
        tvCompanyShare = findViewById(R.id.tv_company_share);

        btn_add_to_wallet.setVisibility(View.GONE);

        Intent intent=getIntent();

        donationID=intent.getStringExtra("donationID");
        donorID=intent.getStringExtra("donorID");
        artist=intent.getStringExtra("artist");
        String donor=intent.getStringExtra("donor");
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

        totalAmount = Double.parseDouble(amount);
        artistShare = totalAmount * 0.7;
        companyShare = totalAmount * 0.3;

        tv_total_fund.setText("Total Amount: KES " + amount);
        tvArtistShare.setText("Artist Share (70%): KES " + String.format("%.2f", artistShare));
        tvCompanyShare.setText("Company Share (30%): KES " + String.format("%.2f", companyShare));

        if (donationStatus.equals("Dispatched")) {
            btn_add_to_wallet.setVisibility(View.VISIBLE);
        }


        btn_add_to_wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertWallet();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void addToWallet(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_ADD_TO_WALLET,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.e("RESPONSE",response);
                            JSONObject jsonObject=new JSONObject(response);
                            String status=jsonObject.getString("status");
                            String msg=jsonObject.getString("message");
                            if (status.equals("1")){

                                Toast toast= Toast.makeText(MyDonationDetails.this, msg, Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP,0,250);
                                toast.show();
                                finish();
                            }else{

                                Toast toast= Toast.makeText(MyDonationDetails.this, msg, Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP,0,250);
                                toast.show();
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                            Toast toast= Toast.makeText(MyDonationDetails.this, e.toString(), Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP,0,250);
                            toast.show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast toast= Toast.makeText(MyDonationDetails.this, error.toString(), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP,0,250);
                toast.show();
            }
        }){
            @Override
            protected Map<String,String> getParams()throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("donationID",donationID);
                params.put("artID",artID);
                params.put("artistID",artistID);
                params.put("amount", String.valueOf(artistShare));
                Log.e("PARAMS",""+params);
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    private void alertWallet() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MyDonationDetails.this);
        builder.setCancelable(false);

        // Custom title
        TextView title = new TextView(this);
        title.setText("Confirm Transfer");
        title.setPadding(20, 30, 20, 10);
        title.setTextSize(20f);
        title.setTypeface(null, Typeface.BOLD);
        title.setTextColor(Color.parseColor("#1E3A8A")); // Deep blue
        builder.setCustomTitle(title);

        // Message
        builder.setMessage("Are you sure you want to transfer KES " + artistShare + " to your wallet?")
                .setPositiveButton("Yes, Proceed", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        addToWallet();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        // Styling buttons
        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(dialogInterface -> {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#256D1B")); // Green
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#EF4444")); // Red
        });

        dialog.show();
    }

}