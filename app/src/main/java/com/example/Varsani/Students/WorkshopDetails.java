package com.example.Varsani.Students;

import static com.example.Varsani.utils.Urls.URL_CHECK_STATUS;
import static com.example.Varsani.utils.Urls.URL_QUOTATION_ITEMS;
import static com.example.Varsani.utils.Urls.URL_REG_WORKSHOP;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.Varsani.R;
import com.example.Varsani.Staff.Adapters.AdapterQuotItems;
import com.example.Varsani.Staff.ExhibitionManager.ApplicantDetails;
import com.example.Varsani.Staff.ExhibitionManager.ApprovedApplicants;
import com.example.Varsani.Staff.Mentor.WorkshopApplicants;
import com.example.Varsani.Staff.Mentor.WorkshopInformation;
import com.example.Varsani.Staff.Models.ClientItemsModal;
import com.example.Varsani.utils.SessionHandler;
import com.example.Varsani.utils.Urls;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class WorkshopDetails extends AppCompatActivity {
    private ImageView img_banner;
    private TextView tv_title,tv_type,tv_dates,tv_venue,tv_description,tv_pending_status;
    private Button btn_register;
    private LinearLayout register_layout,layout_approval_notice,layout_join_notice;

    private SessionHandler session;
    private UserModel user;

    private String workshopID,studentName,checkStatus,workshopStatus,approvalStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshop_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        img_banner = findViewById(R.id.img_banner);
        tv_title = findViewById(R.id.tv_title);
        tv_type = findViewById(R.id.tv_type);
        tv_dates = findViewById(R.id.tv_dates);
        tv_venue = findViewById(R.id.tv_venue);
        tv_description = findViewById(R.id.tv_description);
        tv_pending_status = findViewById(R.id.tv_pending_status);
        btn_register = findViewById(R.id.btn_register);
        register_layout = findViewById(R.id.register_layout);
        layout_approval_notice = findViewById(R.id.layout_approval_notice);
        layout_join_notice = findViewById(R.id.layout_join_notice);

        session=new SessionHandler(getApplicationContext());
        user=session.getUserDetails();

        studentName = user.getFirstname() + " " + user.getLastname();

        Intent intent = getIntent();
        workshopID = intent.getStringExtra("workshopID");
        String title = intent.getStringExtra("title");
        String startingDate = intent.getStringExtra("date");
        String venue = intent.getStringExtra("venue");
        String type = intent.getStringExtra("type");
        String bannerImg = intent.getStringExtra("bannerImg");
        String desc = intent.getStringExtra("desc");

        tv_title.setText("Title: " + title);
        tv_description.setText("Description: " + desc);
        tv_type.setText("Type: " + type);
        tv_dates.setText("Date: " + startingDate);
        tv_venue.setText("Link: " + venue);

        // Load image (with Picasso or Glide)
        String url = Urls.ROOT_URL_WORKSHOP_IMAGES;
        Picasso.get().load(url + bannerImg )
                .fit()
                .centerCrop()
                .into(img_banner );

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertReg(v);
            }
        });
        checkStatus();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public void regWorkshop() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REG_WORKSHOP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("RESPONSE", response);
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            String msg = jsonObject.getString("message");
                            Toast toast = Toast.makeText(WorkshopDetails.this, msg, Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP, 0, 250);
                            toast.show();
                            if (status.equals("1")) {
                                finish();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast toast = Toast.makeText(WorkshopDetails.this, e.toString(), Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP, 0, 250);
                            toast.show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast toast = Toast.makeText(WorkshopDetails.this, error.toString(), Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP, 0, 250);
                        toast.show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("workshopID", workshopID);
                params.put("studentID",user.getClientID());
                params.put("studentName", studentName);
                Log.e("PARAMS", "" + params);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    public void checkStatus(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_CHECK_STATUS,
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

                                    checkStatus=jsn.getString("checkStatus");
                                    approvalStatus=jsn.getString("approvalStatus");
                                    workshopStatus=jsn.getString("workshopStatus");

                                    if(checkStatus.equals("Registered")){
                                        register_layout.setVisibility(View.GONE);
                                    }
                                    if(approvalStatus.equals("Pending approval")){
                                        tv_pending_status.setVisibility(View.VISIBLE);
                                        register_layout.setVisibility(View.GONE);
                                    }
                                    if(approvalStatus.equals("Approved")){
                                        layout_approval_notice.setVisibility(View.VISIBLE);
                                        register_layout.setVisibility(View.GONE);
                                    }
                                    if(approvalStatus.equals("Approved") && workshopStatus.equals("Ongoing")){
                                        layout_approval_notice.setVisibility(View.GONE );
                                        layout_join_notice.setVisibility(View.VISIBLE);
                                        register_layout.setVisibility(View.GONE);
                                    }
                                    if(workshopStatus.equals("Completed")){
                                        layout_approval_notice.setVisibility(View.GONE );
                                        layout_join_notice.setVisibility(View.GONE);
                                        register_layout.setVisibility(View.GONE);
                                    }


                                }
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                            //Toast toast=Toast.makeText(getApplicationContext(), e.toString(),Toast.LENGTH_SHORT);
                            //toast.setGravity(Gravity.TOP,0,250);
                            //toast.show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast toast=Toast.makeText(getApplicationContext(), error.toString(),Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP,0,250);
                toast.show();
            }
        }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params =new HashMap<>();
                params.put("userID",user.getClientID());
                params.put("workshopID",workshopID);
                Log.e("Params",""+ params);
                return  params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    public void alertReg(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Register to attendant workshop");

        //builder.setMessage("You are about to approve this artwork?");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //progressBar1.setVisibility(View.VISIBLE);
                regWorkshop();
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }
}