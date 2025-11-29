package com.example.Varsani.Staff.ExhibitionManager;

import static com.example.Varsani.utils.Urls.URL_ADD_INVENTORY;
import static com.example.Varsani.utils.Urls.URL_APPROVE_ARTWORK;
import static com.example.Varsani.utils.Urls.URL_DONATE;
import static com.example.Varsani.utils.Urls.URL_DONATE_EX_ARTWORKS;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.example.Varsani.Artists.ViewDetails;
import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.R;
import com.example.Varsani.utils.SessionHandler;
import com.example.Varsani.utils.Urls;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ApplicantDetails extends AppCompatActivity {
    private ImageView img_banner;
    private TextView tv_title,tv_description,tv_artist;
    private Button btn_approve,btn_support,btn_inventory;
    private String ID,artistID,exhibitionID,artistName,title,desc,imgName;
    private SessionHandler session;
    private UserModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicant_details);

        img_banner = findViewById(R.id.img_banner);
        tv_title = findViewById(R.id.tv_title);
        tv_artist = findViewById(R.id.tv_artist);
        tv_description = findViewById(R.id.tv_description);
        btn_approve = findViewById(R.id.btn_approve);
        btn_support = findViewById(R.id.btn_support);
        btn_inventory = findViewById(R.id.btn_inventory);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        session=new SessionHandler(getApplicationContext());
        user=session.getUserDetails();

        Intent intent = getIntent();
        ID = intent.getStringExtra("ID");
        exhibitionID = intent.getStringExtra("exhibitionID");
        artistID = intent.getStringExtra("artistID");
        artistName = intent.getStringExtra("artistName");
        title = intent.getStringExtra("title");
        desc = intent.getStringExtra("desc");
        imgName = intent.getStringExtra("imgName");
        String exStatus = intent.getStringExtra("exStatus");

        tv_title.setText(title);
        tv_description.setText("Description: " + desc);
        tv_artist.setText("Artist: " + artistName);

        if (exStatus.equals("Approved")) {
            btn_approve.setVisibility(View.GONE);
        }
        if (exStatus.equals("Approved") && user.getUser_type().equals("Exhibition Manager")) {
            btn_approve.setVisibility(View.GONE);
            btn_inventory.setVisibility(View.VISIBLE);
        }
        if (user.getUser_type().equals("Patron")) {
            btn_support.setVisibility(View.VISIBLE);
        }

        // Load image (with Picasso or Glide)
        String url = Urls.ROOT_URL_EXHIBITION_ARTS;
        Picasso.get().load(url + imgName )
                .fit()
                .centerCrop()
                .into(img_banner );

        btn_approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertApprove(v);
            }
        });
        btn_support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDonate();
            }
        });
        btn_inventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertInventory(v);
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
    public void approve() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_APPROVE_ARTWORK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("RESPONSE", response);
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            String msg = jsonObject.getString("message");
                            Toast toast = Toast.makeText(ApplicantDetails.this, msg, Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP, 0, 250);
                            toast.show();
                            if (status.equals("1")) {
                                finish();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast toast = Toast.makeText(ApplicantDetails.this, e.toString(), Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP, 0, 250);
                            toast.show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast toast = Toast.makeText(ApplicantDetails.this, error.toString(), Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP, 0, 250);
                        toast.show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ID", ID);
                params.put("exhibitionID", exhibitionID);
                params.put("artistID", artistID);
                Log.e("PARAMS", "" + params);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    public void donate(final String amount, final String paymentMethod) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_DONATE_EX_ARTWORKS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("RESPONSE", response);
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            String msg = jsonObject.getString("message");
                            Toast toast = Toast.makeText(ApplicantDetails.this, msg, Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP, 0, 250);
                            toast.show();
                            if (status.equals("1")) {
                                finish();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast toast = Toast.makeText(ApplicantDetails.this, e.toString(), Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP, 0, 250);
                            toast.show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast toast = Toast.makeText(ApplicantDetails.this, error.toString(), Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP, 0, 250);
                        toast.show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("artID", ID);
                params.put("artistID", artistID);
                //params.put("exhibitionID", exhibitionID);
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
    public void addInventory() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ADD_INVENTORY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("RESPONSE", response);
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            String msg = jsonObject.getString("message");
                            Toast toast = Toast.makeText(ApplicantDetails.this, msg, Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP, 0, 250);
                            toast.show();
                            if (status.equals("1")) {
                                finish();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast toast = Toast.makeText(ApplicantDetails.this, e.toString(), Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP, 0, 250);
                            toast.show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast toast = Toast.makeText(ApplicantDetails.this, error.toString(), Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP, 0, 250);
                        toast.show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("exhibitionID", exhibitionID);
                params.put("artID", ID);
                params.put("artistID", artistID);
                params.put("artistName", artistName);
                params.put("title", title);
                params.put("imgName",imgName);
                Log.e("PARAMS", "" + params);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    public void alertApprove(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Approve");

        builder.setMessage("You are about to approve this artwork?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //progressBar1.setVisibility(View.VISIBLE);
                approve();
            }
        });
        builder.setNegativeButton("No", null);
        builder.show();
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
        dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
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
    public void alertInventory(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add to Inventory Archive");

        builder.setMessage("Do you want to add this artwork to inventory archive?");
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //progressBar1.setVisibility(View.VISIBLE);
                addInventory();
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }
}