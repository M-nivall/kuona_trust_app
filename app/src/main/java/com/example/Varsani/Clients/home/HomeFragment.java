package com.example.Varsani.Clients.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.Varsani.Clients.About;
import com.example.Varsani.Clients.Adapters.AdapterProducts;
import com.example.Varsani.Clients.Adapters.AdapterServices;
import com.example.Varsani.Clients.Profile;
import com.example.Varsani.MainActivity;
import com.example.Varsani.utils.SessionHandler;
import com.example.Varsani.utils.Urls;
import com.example.Varsani.Clients.Models.ProductModal;
import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private SessionHandler session;
    private UserModel user;
    private List<ExhibitionModal> list;
    private List<ArtworkModel> list2;
    private AdapterExhibitions adapterExhibitions;
    private AdapterArtWork adapterArtWork;
    private ImageView icon_home,icon_about,icon_profile;
    private RecyclerView rv_exhibitions ,rv_artworks;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        rv_exhibitions=root.findViewById(R.id.rv_exhibitions);
        rv_artworks=root.findViewById(R.id.rv_artworks);

        icon_home = root.findViewById(R.id.icon_home);
        icon_about= root.findViewById(R.id.icon_about);
        icon_profile = root.findViewById(R.id.icon_profile);


        icon_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        icon_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ght = new Intent(getContext(), About.class);
                startActivity(ght);
            }
        });
        icon_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ght = new Intent(getContext(), Profile.class);
                startActivity(ght);
            }
        });

        rv_exhibitions.setLayoutManager( new LinearLayoutManager( getContext()));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        rv_exhibitions.setLayoutManager(mLayoutManager);

        rv_artworks.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerView.LayoutManager nLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        rv_artworks.setLayoutManager(nLayoutManager);

        session=new SessionHandler(getContext());
        user=session.getUserDetails();
        list= new ArrayList<>();
        list2= new ArrayList<>();
        getExhibition();
        getArt();

        return root;
    }
    public void getExhibition(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Urls.URL_GET_EXHIBITIONS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{

                            Log.e("Response",""+response);
                            JSONObject jsonObject=new JSONObject(response);
                            String status=jsonObject.getString("status");

                            if(status.equals("1")){
                                JSONArray jsonArray=jsonObject.getJSONArray("products");
                                for (int i=0; i < jsonArray.length();i++){
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
                                //progressBar.setVisibility(View.GONE);
                                adapterExhibitions=new AdapterExhibitions(getContext(),list);
                                rv_exhibitions.setAdapter(adapterExhibitions);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(getContext(),e.toString(),Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    public void getArt(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Urls.URL_ART_WORK_GALLERY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{

                            Log.e("Response",""+response);
                            JSONObject jsonObject=new JSONObject(response);
                            String status=jsonObject.getString("status");

                            if(status.equals("1")){
                                JSONArray jsonArray=jsonObject.getJSONArray("products");
                                for (int i=0; i < jsonArray.length();i++){
                                    JSONObject jsn=jsonArray.getJSONObject(i);
                                    String artID=jsn.getString("artID");
                                    String artistID=jsn.getString("artistID");
                                    String title=jsn.getString("title");
                                    String desc=jsn.getString("desc");
                                    String imgUrl=jsn.getString("image");
                                    String fullName=jsn.getString("fullName");
                                    String username=jsn.getString("username");

                                    ArtworkModel artworkModel=new ArtworkModel( artID, artistID, title, desc, imgUrl, fullName, username);
                                    list2.add(artworkModel);
                                }
                                //progressBar.setVisibility(View.GONE);
                                adapterArtWork=new AdapterArtWork(getContext(),list2);
                                rv_artworks.setAdapter(adapterArtWork);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(getContext(),e.toString(),Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }


}
