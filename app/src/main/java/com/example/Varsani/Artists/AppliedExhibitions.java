package com.example.Varsani.Artists;

import static com.example.Varsani.utils.Urls.URL_APPLIED_EXHIBITIONS;
import static com.example.Varsani.utils.Urls.URL_GET_APPLICANTS;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.Varsani.Artists.Adapters.AdapterAppliedExhibition;
import com.example.Varsani.Artists.Models.AppliedExhibitionModel;
import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.R;
import com.example.Varsani.Staff.ExhibitionManager.Adapters.AdapterApplicant;
import com.example.Varsani.Staff.ExhibitionManager.Models.ApplicantModel;
import com.example.Varsani.utils.SessionHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppliedExhibitions extends AppCompatActivity {
    private List<AppliedExhibitionModel> list;
    private AdapterAppliedExhibition adapterAppliedExhibition;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;

    private SessionHandler session;
    private UserModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applied_exhibitions);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("Applied Exhibitions");
        recyclerView=findViewById(R.id.recyclerView);
        progressBar=findViewById(R.id.progressBar);

        session=new SessionHandler(getApplicationContext());
        user=session.getUserDetails();

        list=new ArrayList<>();
        recyclerView.setLayoutManager( new LinearLayoutManager( getApplicationContext() ) );
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);

        getApplicants();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public void getApplicants(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_APPLIED_EXHIBITIONS,
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
                                    String ID=jsn.getString("ID");
                                    String exhibitionID=jsn.getString("exhibitionID");
                                    String artistID=jsn.getString("artistID");
                                    String artistName=jsn.getString("artistName");
                                    String title=jsn.getString("title");
                                    String artDesc=jsn.getString("artDesc");
                                    String imageName=jsn.getString("imageName");
                                    String exStatus=jsn.getString("exStatus");

                                    AppliedExhibitionModel appliedExhibitionModel=new AppliedExhibitionModel(ID,exhibitionID,artistID,
                                            artistName,title,artDesc,imageName,exStatus);
                                    list.add(appliedExhibitionModel);
                                }
                                adapterAppliedExhibition=new AdapterAppliedExhibition(getApplicationContext(),list);
                                recyclerView.setAdapter(adapterAppliedExhibition);
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
    @Override
    public void onRestart()
    {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
}