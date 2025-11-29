package com.example.Varsani.Staff.Mentor;

import static com.example.Varsani.utils.Urls.URL_CHECK_WORKSHOP_STATUS;
import static com.example.Varsani.utils.Urls.URL_COMPLETE_WORKSHOP;
import static com.example.Varsani.utils.Urls.URL_START_WORKSHOP;

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
import com.example.Varsani.Staff.ExhibitionManager.Applicants;
import com.example.Varsani.Staff.ExhibitionManager.ApprovedApplicants;
import com.example.Varsani.Staff.ExhibitionManager.UpcomingDetails;
import com.example.Varsani.Staff.Finance.PaymentDetails;
import com.example.Varsani.utils.Urls;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class WorkshopInformation extends AppCompatActivity {
    private ImageView img_banner;
    private TextView tv_title,tv_type,tv_dates,tv_venue,tv_description;
    private Button btn_view_applicants,btn_approved_applicants,btn_start_workshop,btn_compete_workshop;
    private String workshopID,workshopStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshop_information);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        img_banner = findViewById(R.id.img_banner);
        tv_title = findViewById(R.id.tv_title);
        tv_type = findViewById(R.id.tv_type);
        tv_dates = findViewById(R.id.tv_dates);
        tv_venue = findViewById(R.id.tv_venue);
        tv_description = findViewById(R.id.tv_description);
        btn_view_applicants = findViewById(R.id.btn_view_applicants);
        btn_approved_applicants = findViewById(R.id.btn_approved_applicants);
        btn_start_workshop = findViewById(R.id.btn_start_workshop);
        btn_compete_workshop = findViewById(R.id.btn_compete_workshop);

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

        btn_view_applicants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WorkshopInformation.this, WorkshopApplicants.class);
                intent.putExtra("workshopID", workshopID);
                startActivity(intent);
            }
        });

        btn_approved_applicants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WorkshopInformation.this, ApprovedList.class);
                intent.putExtra("workshopID", workshopID);
                startActivity(intent);
            }
        });
        btn_start_workshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertStart();
            }
        });
        btn_compete_workshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertComplete();
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
    public void confirmStart(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_START_WORKSHOP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.e("RESPONSE",response);
                            JSONObject jsonObject=new JSONObject(response);
                            String status=jsonObject.getString("status");
                            String msg=jsonObject.getString("message");
                            if (status.equals("1")){

                                Toast toast= Toast.makeText(WorkshopInformation.this, msg, Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP,0,250);
                                toast.show();
                                finish();
                            }else{

                                Toast toast= Toast.makeText(WorkshopInformation.this, msg, Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP,0,250);
                                toast.show();
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                            Toast toast= Toast.makeText(WorkshopInformation.this, e.toString(), Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP,0,250);
                            toast.show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast toast= Toast.makeText(WorkshopInformation.this, error.toString(), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP,0,250);
                toast.show();
            }
        }){
            @Override
            protected Map<String,String> getParams()throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("workshopID",workshopID);
                Log.e("PARAMS",""+params);
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    public void confirmComplete(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_COMPLETE_WORKSHOP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.e("RESPONSE",response);
                            JSONObject jsonObject=new JSONObject(response);
                            String status=jsonObject.getString("status");
                            String msg=jsonObject.getString("message");
                            if (status.equals("1")){

                                Toast toast= Toast.makeText(WorkshopInformation.this, msg, Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP,0,250);
                                toast.show();
                                finish();
                            }else{

                                Toast toast= Toast.makeText(WorkshopInformation.this, msg, Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP,0,250);
                                toast.show();
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                            Toast toast= Toast.makeText(WorkshopInformation.this, e.toString(), Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP,0,250);
                            toast.show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast toast= Toast.makeText(WorkshopInformation.this, error.toString(), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP,0,250);
                toast.show();
            }
        }){
            @Override
            protected Map<String,String> getParams()throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("workshopID",workshopID);
                Log.e("PARAMS",""+params);
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    public void checkStatus(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_CHECK_WORKSHOP_STATUS,
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

                                    workshopStatus=jsn.getString("workshopStatus");


                                    if(workshopStatus.equals("Ongoing")){
                                        btn_start_workshop.setVisibility(View.GONE);
                                        btn_compete_workshop.setVisibility(View.VISIBLE);
                                    }
                                    if(workshopStatus.equals("Completed")){
                                        btn_start_workshop.setVisibility(View.GONE);
                                        btn_compete_workshop.setVisibility(View.GONE);
                                        btn_approved_applicants.setVisibility(View.GONE);
                                        btn_view_applicants.setVisibility(View.GONE);
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
                params.put("workshopID",workshopID);
                Log.e("Params",""+ params);
                return  params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    public void alertStart(){
        android.app.AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setMessage("Confirm the start of mentorship workshop");
        alertDialog.setCancelable(false);
        alertDialog.setButton2("Close", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

                return;
            }
        });
        alertDialog.setButton("Confirm ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                confirmStart();
                return;
            }
        });
        alertDialog.show();
    }
    public void alertComplete(){
        android.app.AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setMessage("Confirm the completion of mentorship workshop");
        alertDialog.setCancelable(false);
        alertDialog.setButton2("Close", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

                return;
            }
        });
        alertDialog.setButton("Confirm ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                confirmComplete();
                return;
            }
        });
        alertDialog.show();
    }
}