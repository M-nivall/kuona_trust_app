package com.example.Varsani.Artists;

import static com.example.Varsani.utils.Urls.URL_MY_DONATIONS;
import static com.example.Varsani.utils.Urls.URL_MY_WALLET_BALANCE;
import static com.example.Varsani.utils.Urls.URL_WITHDRAW;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.Varsani.Artists.Adapters.AdapterMyDonations;
import com.example.Varsani.Artists.Models.MyDonationsModel;
import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.R;
import com.example.Varsani.Staff.Finance.Adapters.AdapterDonations;
import com.example.Varsani.Staff.Finance.Models.DonationsModel;
import com.example.Varsani.utils.SessionHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyDonations extends AppCompatActivity {
    private List<MyDonationsModel> list;
    private AdapterMyDonations adapterDonations;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private SessionHandler session;
    private UserModel user;
    private TextView tv_wallet_balance;
    private Button btn_withdraw;
    private String wallet_balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_donations);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("New Donations");
        recyclerView=findViewById(R.id.recyclerView);
        progressBar=findViewById(R.id.progressBar);

        tv_wallet_balance = findViewById(R.id.tv_wallet_balance);
        btn_withdraw = findViewById(R.id.btn_withdraw);

        session=new SessionHandler(getApplicationContext());
        user=session.getUserDetails();

        list=new ArrayList<>();
        recyclerView.setLayoutManager( new LinearLayoutManager( getApplicationContext() ) );
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);

        btn_withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertWithdraw();
            }
        });

        myDonations();
        walletBalance();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public void myDonations(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_MY_DONATIONS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.e("RESPONSE", response);
                            JSONObject jsonObject=new JSONObject(response);
                            String status=jsonObject.getString("status");
                            String msg=jsonObject.getString("message");
                            if(status.equals("1")){
                                JSONArray jsonArray=jsonObject.getJSONArray("details");
                                for(int i=0; i <jsonArray.length();i++){
                                    JSONObject jsn=jsonArray.getJSONObject(i);
                                    String donationID=jsn.getString("donationID");
                                    String donorID=jsn.getString("donorID");
                                    String artistName=jsn.getString("artistName");
                                    String donorName=jsn.getString("donorName");
                                    String artID=jsn.getString("artID");
                                    String artistID=jsn.getString("artistID");
                                    String amount=jsn.getString("amount");
                                    String paymentMethod=jsn.getString("paymentMethod");
                                    String referenceCode=jsn.getString("referenceCode");
                                    String donationStatus=jsn.getString("donationStatus");
                                    String donationDate=jsn.getString("donationDate");
                                    String title=jsn.getString("title");

                                    MyDonationsModel myDonationsModel=new MyDonationsModel(donationID,donorID,artistName,donorName,
                                            artID,artistID, amount,paymentMethod,referenceCode,donationStatus,donationDate,title);
                                    list.add(myDonationsModel);
                                }
                                adapterDonations=new AdapterMyDonations(getApplicationContext(),list);
                                recyclerView.setAdapter(adapterDonations);
                                progressBar.setVisibility(View.GONE);

                            }else{
                                Toast toast=Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP,0,250);
                                toast.show();
                                progressBar.setVisibility(View.GONE);
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                            Toast toast=Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP,0,250);
                            toast.show();
                            Log.e("ERROR E ", e.toString());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast toast=Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP,0,250);
                toast.show();
                Log.e("ERROR E ", error.toString());
            }
        }){
            @Override
            protected Map<String,String> getParams()throws AuthFailureError {
                Map<String,String>params=new HashMap<>();
                params.put("userID",user.getClientID());
                Log.e("PARAMS","" +params);
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    public void walletBalance(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_MY_WALLET_BALANCE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.e("RESPONSE", response);
                            JSONObject jsonObject=new JSONObject(response);
                            String status=jsonObject.getString("status");
                            String msg=jsonObject.getString("message");
                            if(status.equals("1")){
                                JSONArray jsonArray=jsonObject.getJSONArray("details");
                                for(int i=0; i <jsonArray.length();i++){
                                    JSONObject jsn=jsonArray.getJSONObject(i);
                                    wallet_balance=jsn.getString("wallet");
                                }
                                tv_wallet_balance.setText("KES: " + wallet_balance);

                            }else{
                                Toast toast=Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP,0,250);
                                toast.show();
                                progressBar.setVisibility(View.GONE);
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                            Toast toast=Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP,0,250);
                            toast.show();
                            Log.e("ERROR E ", e.toString());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast toast=Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP,0,250);
                toast.show();
                Log.e("ERROR E ", error.toString());
            }
        }){
            @Override
            protected Map<String,String> getParams()throws AuthFailureError {
                Map<String,String>params=new HashMap<>();
                params.put("userID",user.getClientID());
                Log.e("PARAMS","" +params);
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    public void withdraw(final String amount) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_WITHDRAW,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("RESPONSE", response);
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            String msg = jsonObject.getString("message");
                            Toast toast = Toast.makeText(MyDonations.this, msg, Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP, 0, 250);
                            toast.show();
                            if (status.equals("1")) {
                                finish();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast toast = Toast.makeText(MyDonations.this, e.toString(), Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP, 0, 250);
                            toast.show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast toast = Toast.makeText(MyDonations.this, error.toString(), Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP, 0, 250);
                        toast.show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userID", user.getClientID());
                params.put("amount", amount);
                Log.e("PARAMS", "" + params);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    public void alertWithdraw() {
        View view = getLayoutInflater().inflate(R.layout.dialog_withdraw, null);

        final EditText edtAmount = view.findViewById(R.id.edtAmount);
        //final Spinner spnPaymentMethod = view.findViewById(R.id.spnPaymentMethod);

        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
        //        R.array.payment_methods, android.R.layout.simple_spinner_item);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spnPaymentMethod.setAdapter(adapter);

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Withdraw")
                .setView(view)
                .setPositiveButton("Withdraw", null)
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        final androidx.appcompat.app.AlertDialog dialog = builder.create();
        dialog.show();

        // Overriding onClick to prevent auto-dismiss on validation failure
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            String amount = edtAmount.getText().toString().trim();
            //String method = spnPaymentMethod.getSelectedItem().toString();

            if (amount.isEmpty()) {
                edtAmount.setError("Amount is required");
                return;
            }

            dialog.dismiss();
            withdraw(amount);
        });
    }
    @Override
    public void onRestart()
    {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
}