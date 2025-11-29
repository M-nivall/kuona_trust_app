package com.example.Varsani.Staff.Mentor;

import static com.example.Varsani.utils.Urls.URL_CREATE_WORKSHOP;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.MainActivity;
import com.example.Varsani.R;
import com.example.Varsani.Staff.ExhibitionManager.CreateExhibition;
import com.example.Varsani.VolleyMultipartRequest;
import com.example.Varsani.utils.SessionHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CreateWorkshop extends AppCompatActivity {
    private EditText edtTitle, edtDescription, edtDate, edtLocation;
    private Spinner spinnerType;
    private Button btnUploadBanner,btnSubmit;

    private SessionHandler session;
    private UserModel user;

    private ProgressBar progressBar;
    private Uri uri;
    private String displayName = null;
    private RequestQueue rQueue;

    private String date;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private DatePickerDialog datePicker;

    @RequiresApi(api = Build.VERSION_CODES.N)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_workshop);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        edtTitle = findViewById(R.id.edtTitle);
        edtDescription = findViewById(R.id.edtDescription);
        edtDate = findViewById(R.id.edtDate);
        spinnerType = findViewById(R.id.spinnerType);
        edtLocation = findViewById(R.id.edtLocation);
        btnUploadBanner = findViewById(R.id.btnUploadBanner);
        btnSubmit = findViewById(R.id.btnSubmit);
        progressBar = findViewById(R.id.progress_bar);

        session=new SessionHandler(getApplicationContext());
        user=session.getUserDetails();

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        date = dateFormat.format(calendar.getTime());
        edtDate.setText(date);

        final Calendar calendar2 = Calendar.getInstance();
        final int day = calendar2.get(Calendar.DAY_OF_MONTH);
        final int year = calendar2.get(Calendar.YEAR);
        final int month = calendar2.get(Calendar.MONTH);

        datePicker = new DatePickerDialog(CreateWorkshop.this);

        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker = new DatePickerDialog(CreateWorkshop.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        // adding the selected date in the edittext
                        edtDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);

                // set maximum date to be selected as today
                datePicker.getDatePicker().setMinDate(calendar.getTimeInMillis());

                // show the dialog
                datePicker.show();
            }
        });

        btnUploadBanner.setOnClickListener(v -> {
            Intent i = new Intent();
            i.setAction(Intent.ACTION_GET_CONTENT);
            i.setType("image/*"); // For images
            startActivityForResult(i, 1);
        });

        btnSubmit.setOnClickListener(v -> createExhibition(displayName, uri));
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null && data.getData() != null) {
            uri = data.getData();
            //img_profile.setImageURI(uri);  // <-- Display the image

            String uriString = uri.toString();
            File myFile = new File(uriString);
            displayName = null;

            if (uriString.startsWith("content://")) {
                Cursor cursor = null;
                try {
                    cursor = this.getContentResolver().query(uri, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                        Log.d("name  ", displayName);
                    }
                } finally {
                    if (cursor != null) cursor.close();
                }
            } else if (uriString.startsWith("file://")) {
                displayName = myFile.getName();
                Log.d("name  ", displayName);
            }
        }
    }
    private void createExhibition(final String pdfname, Uri pdffile) {

        InputStream iStream = null;

        final String title = edtTitle.getText().toString().trim();
        final String desc = edtDescription.getText().toString().trim();
        final String start_date = edtDate.getText().toString().trim();
        final String location = edtLocation.getText().toString().trim();
        final String exhibition_type = spinnerType.getSelectedItem().toString();

        progressBar.setVisibility(View.VISIBLE);
        btnUploadBanner.setVisibility(View.GONE);
        btnSubmit.setVisibility(View.GONE);

        if(TextUtils.isEmpty(title)){
            Toast.makeText(getApplicationContext(), "Enter Title", Toast.LENGTH_SHORT).show();
            btnSubmit.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            return;
        }
        if(TextUtils.isEmpty(desc)){
            Toast.makeText(getApplicationContext(), "Enter Description", Toast.LENGTH_SHORT).show();
            btnSubmit.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            return;
        }
        if(TextUtils.isEmpty(start_date)){
            Toast.makeText(getApplicationContext(), "Select Date", Toast.LENGTH_SHORT).show();
            btnSubmit.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            return;
        }
        if(TextUtils.isEmpty(location)){
            Toast.makeText(getApplicationContext(), "Enter location/Link", Toast.LENGTH_SHORT).show();
            btnSubmit.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            return;
        }
        if(TextUtils.isEmpty(exhibition_type)){
            Toast.makeText(getApplicationContext(), "Select either physical or online", Toast.LENGTH_SHORT).show();
            btnSubmit.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            return;
        }


        try {

            iStream = getContentResolver().openInputStream(pdffile);
            final byte[] inputData = getBytes(iStream);

            VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, URL_CREATE_WORKSHOP,
                    response -> {
                        Log.d("ressssssoo", new String(response.data));
                        rQueue.getCache().clear();
                        try {
                            JSONObject jsonObject = new JSONObject(new String(response.data));
                            String status = jsonObject.getString("status");
                            String message = jsonObject.getString("message");

                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                            if (status.equals("1")) {
                                // Success
                                Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            } else {
                                // Failure
                                progressBar.setVisibility(View.GONE);
                                btnUploadBanner.setVisibility(View.VISIBLE);
                                btnSubmit.setVisibility(View.VISIBLE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressBar.setVisibility(View.GONE);
                            btnUploadBanner.setVisibility(View.VISIBLE);
                            btnSubmit.setVisibility(View.VISIBLE);
                            Log.e("E ", "" + e);
                        }
                    }
                    ,
                    error -> {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                        progressBar.setVisibility(View.GONE);
                        btnUploadBanner.setVisibility(View.VISIBLE);
                        btnSubmit.setVisibility(View.VISIBLE);
                        Log.e("ERROR ", "" + error);
                    }) {

                /*
                 * If you want to add more parameters with the pdf
                 * you can do it here
                 * here we have only one parameter with the image
                 * which is tags
                 * */
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("title",title);
                    params.put("userID",user.getClientID());
                    params.put("desc",desc);
                    params.put("workshop_date",start_date);
                    params.put("venue",location);
                    params.put("exhibitionType",exhibition_type);
                    Log.e("PARAMS ", "" + params);
                    return params;
                }

                /*
                 *pass files using below method
                 * */
                @Override
                protected Map<String, VolleyMultipartRequest.DataPart> getByteData() {
                    Map<String, DataPart> params = new HashMap<>();

                    params.put("filename", new DataPart(pdfname, inputData));
                    Log.e("FILE NAME ", "" + params);
                    return params;
                }
            };


            volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                    0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            rQueue = Volley.newRequestQueue(CreateWorkshop.this);
            rQueue.add(volleyMultipartRequest);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }
}