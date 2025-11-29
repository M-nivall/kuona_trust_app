package com.example.Varsani.Clients;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.Varsani.Artists.Adapters.AdapterArtWork;
import com.example.Varsani.Artists.Adapters.AdapterExhibitions;
import com.example.Varsani.Artists.Models.ArtworkModel;
import com.example.Varsani.Artists.Models.ExhibitionModal;
import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.MainActivity;
import com.example.Varsani.R;
import com.example.Varsani.utils.SessionHandler;
import com.example.Varsani.utils.Urls;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Search extends AppCompatActivity {

    private SessionHandler session;
    private UserModel user;
    private List<ArtworkModel> list;
    private List<ExhibitionModal> list2;
    private AdapterArtWork adapterArtWork;
    private AdapterExhibitions adapterExhibitions;

    private RecyclerView recyclerViewArt, recyclerViewExhibition;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        progressBar = findViewById(R.id.progressBar);
        recyclerViewArt = findViewById(R.id.recyclerViewArt);
        recyclerViewExhibition = findViewById(R.id.recyclerViewExhibition);

        recyclerViewArt.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerViewExhibition.setLayoutManager(new GridLayoutManager(this, 1));

        session = new SessionHandler(getApplicationContext());
        user = session.getUserDetails();

        list = new ArrayList<>();
        list2 = new ArrayList<>();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Search");

        getArtWork();
        getExhibition();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_bar, menu);
        MenuItem search = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);
        return true;
    }

    private void search(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (adapterArtWork != null) {
                    adapterArtWork.getFilter().filter(newText);
                }
                if (adapterExhibitions != null) {
                    adapterExhibitions.getFilter().filter(newText);
                }
                return true;
            }
        });
    }

    public void getArtWork() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.URL_ART_WORK_GALLERY,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getString("status").equals("1")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("products");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsn = jsonArray.getJSONObject(i);
                                list.add(new ArtworkModel(
                                        jsn.getString("artID"),
                                        jsn.getString("artistID"),
                                        jsn.getString("title"),
                                        jsn.getString("desc"),
                                        jsn.getString("image"),
                                        jsn.getString("fullName"),
                                        jsn.getString("username")
                                ));
                            }
                            adapterArtWork = new AdapterArtWork(getApplicationContext(), list);
                            recyclerViewArt.setAdapter(adapterArtWork);
                            progressBar.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void getExhibition() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.URL_GET_EXHIBITIONS,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getString("status").equals("1")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("products");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsn = jsonArray.getJSONObject(i);
                                list2.add(new ExhibitionModal(
                                        jsn.getString("exhibitionID"),
                                        jsn.getString("title"),
                                        jsn.getString("exhibitionDesc"),
                                        jsn.getString("startingDate"),
                                        jsn.getString("endDate"),
                                        jsn.getString("venue"),
                                        jsn.getString("exhibitionType"),
                                        jsn.getString("bannerImg"),
                                        jsn.getString("visibility")
                                ));
                            }
                            adapterExhibitions = new AdapterExhibitions(getApplicationContext(), list2);
                            recyclerViewExhibition.setAdapter(adapterExhibitions);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(i);
    }
}
