package com.example.Varsani.Staff.Patron;

import androidx.appcompat.app.AppCompatActivity;
import androidx.print.PrintHelper;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Varsani.R;

public class ViewReceipt extends AppCompatActivity {
    private TextView txtArtist, txtProject, txtPaymentMode, txtPaymentCode,txtAmount,txvDonor;
    private ImageView imgCompanyLogo;
    private ImageView btn_printfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_receipt);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Initialize views
        imgCompanyLogo = findViewById(R.id.imgCompanyLogo);
        txtArtist = findViewById(R.id.txtArtist);
        txtProject = findViewById(R.id.txtProject);
        txtPaymentMode = findViewById(R.id.txtPaymentMode);
        txtPaymentCode = findViewById(R.id.txtPaymentCode);
        txtAmount = findViewById(R.id.txtAmount);
        txvDonor = findViewById(R.id.txvDonor);
        btn_printfile = findViewById(R.id.btn_printfile);

        // Retrieve data from Intent
        Intent intent = getIntent();
        String artist = intent.getStringExtra("artist");
        String donor = intent.getStringExtra("donor");
        String amount = intent.getStringExtra("amount");
        String paymentMethod = intent.getStringExtra("paymentMethod");
        String refCode = intent.getStringExtra("refCode");
        String title = intent.getStringExtra("title");


        // Set data to TextViews
        txtArtist.setText("Artist Name: " + artist);
        txtProject.setText("Project: " + title);
        txtPaymentMode.setText("Payment Method: " + paymentMethod);
        txtPaymentCode.setText("Payment Code: " + refCode);
        txtAmount.setText("Donation Amount: KES " + amount);
        txvDonor.setText("Donor: " + donor);
        btn_printfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                print();
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
    private void print(){
        btn_printfile.setVisibility(View.GONE);

        View view = getWindow().getDecorView().findViewById(android.R.id.content);
        view.setDrawingCacheEnabled(true);
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),View. MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache(true);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);

        PrintHelper photoPrinter = new PrintHelper(this); // Assume that 'this' is your activity
        photoPrinter.setScaleMode(PrintHelper.SCALE_MODE_FIT);
        photoPrinter.printBitmap("print", bitmap);

        btn_printfile.setVisibility(View.VISIBLE);
    }
    private void showToast(String message) {
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 250);
        toast.show();
    }
}