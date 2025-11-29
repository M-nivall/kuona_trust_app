package com.example.Varsani.Staff.Finance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.Varsani.Staff.Models.ClientItemsModal;
import com.example.Varsani.R;
import com.example.Varsani.Staff.Adapters.AdapterClientItems;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.Varsani.utils.Urls.URL_APPROVE_DONATION;
import static com.example.Varsani.utils.Urls.URL_GET_CLIENT_ITEMS;
import static com.example.Varsani.utils.Urls.URL_RELEASE_DONATION;

public class DonationDetails extends AppCompatActivity {

    private TextView tv_donation_id,tv_donor,tv_artist,tv_amount, tv_payment_method,tv_reference_code,tv_status;
    private TextView tvArtistShare,tvCompanyShare,tv_total_share;
    private Button btn_release_funds,btn_approve;
    private String donationID,donorID,artID,artistID,amount,artist;
    private double artistShare,totalAmount,companyShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_details);

        getSupportActionBar().setSubtitle("Received Donation");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_donation_id=findViewById(R.id.tv_donation_id);
        tv_payment_method=findViewById(R.id.tv_payment_method);
        tv_artist=findViewById(R.id.tv_artist);
        tv_status=findViewById(R.id.tv_status);
        tv_reference_code=findViewById(R.id.tv_reference_code);
        tv_donor=findViewById(R.id.tv_donor);
        tv_amount=findViewById(R.id.tv_amount);
        btn_release_funds=findViewById(R.id.btn_release_funds);
        btn_approve=findViewById(R.id.btn_approve);

        tvArtistShare = findViewById(R.id.tv_artist_share);
        tvCompanyShare = findViewById(R.id.tv_company_share);
        tv_total_share = findViewById(R.id.tv_total_share);

        btn_approve.setVisibility(View.GONE);

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

        if (donationStatus.equals("New Donation")) {
            btn_approve.setVisibility(View.VISIBLE);
        }
        if (donationStatus.equals("Received")) {
            btn_release_funds.setVisibility(View.VISIBLE);
        }

        tv_amount.setText("Amount: " + amount);
        tv_payment_method.setText("Payment Method: " + paymentMethod);
        tv_artist.setText("Artist: " + artist);
        tv_status.setText("Status: " + donationStatus);
        tv_reference_code.setText("Reference Code: " + refCode);
        tv_donation_id.setText("Donation ID: " + donationID);
        tv_donor.setText("Donor: " + donor );

        totalAmount = Double.parseDouble(amount);
        artistShare = totalAmount * 0.7;
        companyShare = totalAmount * 0.3;

        tv_total_share.setText("Total Amount: " + amount);
        tvArtistShare.setText("Artist Share (70%): KES " + String.format("%.2f", artistShare));
        tvCompanyShare.setText("Company Share (30%): KES " + String.format("%.2f", companyShare));




        btn_release_funds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertRelease();
            }
        });
        btn_approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertApprove();
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
    public void approve(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_APPROVE_DONATION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.e("RESPONSE",response);
                            JSONObject jsonObject=new JSONObject(response);
                            String status=jsonObject.getString("status");
                            String msg=jsonObject.getString("message");
                            if (status.equals("1")){

                                Toast toast= Toast.makeText(DonationDetails.this, msg, Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP,0,250);
                                toast.show();
                                finish();
                            }else{

                                Toast toast= Toast.makeText(DonationDetails.this, msg, Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP,0,250);
                                toast.show();
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                            Toast toast= Toast.makeText(DonationDetails.this, e.toString(), Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP,0,250);
                            toast.show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast toast= Toast.makeText(DonationDetails.this, error.toString(), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP,0,250);
                toast.show();
            }
        }){
            @Override
            protected Map<String,String>getParams()throws AuthFailureError{
                Map<String,String> params=new HashMap<>();
                params.put("donationID",donationID);
                params.put("artID",artID);
                params.put("artistID",artistID);
                params.put("artistShare", String.valueOf(artistShare));
                params.put("companyShare", String.valueOf(companyShare));
                Log.e("PARAMS",""+params);
                return params;
            }
        };
        RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void release(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_RELEASE_DONATION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.e("RESPONSE",response);
                            JSONObject jsonObject=new JSONObject(response);
                            String status=jsonObject.getString("status");
                            String msg=jsonObject.getString("message");
                            if (status.equals("1")){

                                Toast toast= Toast.makeText(DonationDetails.this, msg, Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP,0,250);
                                toast.show();
                                finish();
                            }else{

                                Toast toast= Toast.makeText(DonationDetails.this, msg, Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP,0,250);
                                toast.show();
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                            Toast toast= Toast.makeText(DonationDetails.this, e.toString(), Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP,0,250);
                            toast.show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
               Toast toast= Toast.makeText(DonationDetails.this, error.toString(), Toast.LENGTH_SHORT);
               toast.setGravity(Gravity.TOP,0,250);
               toast.show();
            }
        }){
            @Override
            protected Map<String,String>getParams()throws AuthFailureError{
                Map<String,String> params=new HashMap<>();
                params.put("donationID",donationID);
                params.put("artID",artID);
                params.put("artistID",artistID);
                params.put("artistShare", String.valueOf(artistShare));
                params.put("companyShare", String.valueOf(companyShare));
                Log.e("PARAMS",""+params);
                return params;
            }
        };
        RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void alertRelease(){
        android.app.AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setMessage("Release " + artistShare + " to " + artist);
        alertDialog.setCancelable(false);
        alertDialog.setButton2("Close", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

                return;
            }
        });
        alertDialog.setButton("Confirm ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                release();
                return;
            }
        });
        alertDialog.show();
    }
    public void alertApprove(){
        android.app.AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setMessage("Release " + artistShare + " to " + artist);
        alertDialog.setCancelable(false);
        alertDialog.setButton2("Close", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

                return;
            }
        });
        alertDialog.setButton("Confirm ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                approve();
                return;
            }
        });
        alertDialog.show();
    }
}
