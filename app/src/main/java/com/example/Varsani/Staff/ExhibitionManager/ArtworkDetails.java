package com.example.Varsani.Staff.ExhibitionManager;

import static com.example.Varsani.utils.Urls.URL_APPROVE_ART_BLOG;

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
import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.R;
import com.example.Varsani.utils.SessionHandler;
import com.example.Varsani.utils.Urls;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ArtworkDetails extends AppCompatActivity {
    private ImageView img_banner;
    private TextView tv_title,tv_description,tv_artist;
    private Button btn_approve;
    private String ID,artistID;
    private SessionHandler session;
    private UserModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artwork_details);

        img_banner = findViewById(R.id.img_banner);
        tv_title = findViewById(R.id.tv_title);
        tv_artist = findViewById(R.id.tv_artist);
        tv_description = findViewById(R.id.tv_description);
        btn_approve = findViewById(R.id.btn_approve);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        session=new SessionHandler(getApplicationContext());
        user=session.getUserDetails();

        Intent intent = getIntent();
        ID = intent.getStringExtra("ID");
        artistID = intent.getStringExtra("artistID");
        String artistName = intent.getStringExtra("artistName");
        String title = intent.getStringExtra("title");
        String desc = intent.getStringExtra("desc");
        String imgName = intent.getStringExtra("imgName");
        String exStatus = intent.getStringExtra("exStatus");

        tv_title.setText(title);
        tv_description.setText("Description: " + desc);
        tv_artist.setText("Artist: " + artistName);

        // Load image (with Picasso or Glide)
        String url = Urls.ROOT_URL_ART_IMAGES;
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
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public void approve() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_APPROVE_ART_BLOG,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("RESPONSE", response);
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            String msg = jsonObject.getString("message");
                            Toast toast = Toast.makeText(ArtworkDetails.this, msg, Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP, 0, 250);
                            toast.show();
                            if (status.equals("1")) {
                                finish();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast toast = Toast.makeText(ArtworkDetails.this, e.toString(), Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP, 0, 250);
                            toast.show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast toast = Toast.makeText(ArtworkDetails.this, error.toString(), Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP, 0, 250);
                        toast.show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ID", ID);
                params.put("artistID", artistID);
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
}