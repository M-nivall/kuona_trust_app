package com.example.Varsani.Artists;

import static com.example.Varsani.utils.Urls.URL_APPROVE_SERV_PAYMENTS;
import static com.example.Varsani.utils.Urls.URL_DONATE;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.Clients.SingleService;
import com.example.Varsani.R;
import com.example.Varsani.Staff.Finance.PaymentDetails;
import com.example.Varsani.utils.SessionHandler;
import com.example.Varsani.utils.Urls;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ViewDetails extends AppCompatActivity {
    private TextView tvTitle,tvDesc,tvFullName;
    private ImageView imgArtwork;
    private Button btnDonate;
    private String title,artID,artistID,desc,imgUrl,fullName,username;
    private SessionHandler session;
    private UserModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        tvTitle = findViewById(R.id.tvTitle);
        tvDesc = findViewById(R.id.tvDesc);
        tvFullName = findViewById(R.id.tvFullName);
        imgArtwork = findViewById(R.id.imgArtwork);
        btnDonate = findViewById(R.id.btnDonate);

        btnDonate.setVisibility(View.GONE);

        session=new SessionHandler(getApplicationContext());
        user=session.getUserDetails();

        if (user.getUser_type().equals("Patron")) {
            btnDonate.setVisibility(View.VISIBLE);
        }

        Intent intent = getIntent();

        artID = intent.getStringExtra("artID");
        artistID = intent.getStringExtra("artistID");
        title = intent.getStringExtra("title");
        desc = intent.getStringExtra("desc");
        imgUrl = intent.getStringExtra("imgUrl");
        fullName = intent.getStringExtra("fullName");
        username = intent.getStringExtra("username");

        tvTitle.setText(title);
        tvDesc.setText("Description: " + desc);
        tvFullName.setText("By: " + fullName);


        // Load image (with Picasso or Glide)
        String url = Urls.ROOT_URL_ART_IMAGES;
        Picasso.get().load(url + imgUrl )
                .fit()
                .centerCrop()
                .into(imgArtwork );

        btnDonate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDonate();
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
    public void donate(final String amount, final String paymentMethod) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_DONATE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("RESPONSE", response);
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            String msg = jsonObject.getString("message");
                            Toast toast = Toast.makeText(ViewDetails.this, msg, Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP, 0, 250);
                            toast.show();
                            if (status.equals("1")) {
                                finish();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast toast = Toast.makeText(ViewDetails.this, e.toString(), Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP, 0, 250);
                            toast.show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast toast = Toast.makeText(ViewDetails.this, error.toString(), Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP, 0, 250);
                        toast.show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("artID", artID);
                params.put("artistID", artistID);
                params.put("donorID", user.getClientID());
                params.put("amount", amount);
                params.put("payment_method", paymentMethod);
                Log.e("PARAMS", "" + params);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    public void alertDonate() {
        View view = getLayoutInflater().inflate(R.layout.dialog_donate, null);

        final EditText edtAmount = view.findViewById(R.id.edtAmount);
        final Spinner spnPaymentMethod = view.findViewById(R.id.spnPaymentMethod);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.payment_methods, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnPaymentMethod.setAdapter(adapter);

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Support this Project")
                .setView(view)
                .setPositiveButton("Donate", null)
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        final androidx.appcompat.app.AlertDialog dialog = builder.create();
        dialog.show();

        // Overriding onClick to prevent auto-dismiss on validation failure
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            String amount = edtAmount.getText().toString().trim();
            String method = spnPaymentMethod.getSelectedItem().toString();

            if (amount.isEmpty()) {
                edtAmount.setError("Amount is required");
                return;
            }

            dialog.dismiss();
            donate(amount, method);
        });
    }

}