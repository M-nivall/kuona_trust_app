package com.example.Varsani.Staff.ExhibitionManager;

import static com.example.Varsani.utils.Urls.URL_UPCOMING_EVENTS;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.Varsani.Artists.Models.ExhibitionModal;
import com.example.Varsani.R;
import com.example.Varsani.Staff.ExhibitionManager.Adapters.AdapterUpcomingExhibitions;
import com.example.Varsani.Staff.Finance.Adapters.AdapterDonations;
import com.example.Varsani.Staff.Finance.Models.DonationsModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UpcomingExhibitions extends AppCompatActivity {
    private List<ExhibitionModal> list;
    private AdapterUpcomingExhibitions adapterUpcomingExhibitions;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_exhibitions);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("Upcoming Exhibitions");
        recyclerView=findViewById(R.id.recyclerView);
        progressBar=findViewById(R.id.progressBar);

        list=new ArrayList<>();
        recyclerView.setLayoutManager( new LinearLayoutManager( getApplicationContext() ) );
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);

        upcomingEvents();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public void upcomingEvents(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_UPCOMING_EVENTS,
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
                                    String exhibitionID=jsn.getString("exhibitionID");
                                    String title=jsn.getString("title");
                                    String exhibitionDesc=jsn.getString("exhibitionDesc");
                                    String startingDate=jsn.getString("startingDate");
                                    String endDate=jsn.getString("endDate");
                                    String venue=jsn.getString("venue");
                                    String exhibitionType=jsn.getString("exhibitionType");
                                    String bannerImg=jsn.getString("bannerImg");
                                    String visibility=jsn.getString("visibility");

                                    ExhibitionModal exhibitionModal=new ExhibitionModal(exhibitionID, title, exhibitionDesc, startingDate, endDate,venue,
                                            exhibitionType, bannerImg, visibility);
                                    list.add(exhibitionModal);
                                }
                                adapterUpcomingExhibitions=new AdapterUpcomingExhibitions(getApplicationContext(),list);
                                recyclerView.setAdapter(adapterUpcomingExhibitions);
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
        });
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