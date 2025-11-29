package com.example.Varsani.Staff.ExhibitionManager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.Varsani.Artists.ExhibitionDetails;
import com.example.Varsani.Artists.SubmitArtProject;
import com.example.Varsani.R;
import com.example.Varsani.utils.Urls;
import com.squareup.picasso.Picasso;

public class UpcomingDetails extends AppCompatActivity {
    private ImageView img_banner;
    private TextView tv_title,tv_type,tv_dates,tv_venue,tv_description;
    private Button btn_view_applicants,btn_approved_applicants;
    private String exhibitionID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_details);

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

        Intent intent = getIntent();
        exhibitionID = intent.getStringExtra("exhibitionID");
        String title = intent.getStringExtra("title");
        String startingDate = intent.getStringExtra("startingDate");
        String endDate = intent.getStringExtra("endDate");
        String venue = intent.getStringExtra("venue");
        String type = intent.getStringExtra("type");
        String bannerImg = intent.getStringExtra("bannerImg");
        String desc = intent.getStringExtra("desc");

        tv_title.setText("Title: " + title);
        tv_description.setText("Description: " + desc);
        tv_type.setText("Type: " + type);
        tv_dates.setText("Date: " + startingDate);
        tv_venue.setText("Venue: " + venue);

        // Load image (with Picasso or Glide)
        String url = Urls.ROOT_URL_EXHIBITION_IMAGES;
        Picasso.get().load(url + bannerImg )
                .fit()
                .centerCrop()
                .into(img_banner );

        btn_view_applicants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpcomingDetails.this, Applicants.class);
                intent.putExtra("exhibitionID", exhibitionID);
                startActivity(intent);
            }
        });

        btn_approved_applicants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpcomingDetails.this, ApprovedApplicants.class);
                intent.putExtra("exhibitionID", exhibitionID);
                startActivity(intent);
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
}