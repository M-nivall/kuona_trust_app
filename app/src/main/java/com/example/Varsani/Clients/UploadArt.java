package com.example.Varsani.Clients;

import static com.example.Varsani.utils.Urls.URL_UPLOAD_ART;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.MainActivity;
import com.example.Varsani.R;
import com.example.Varsani.VolleyMultipartRequest;
import com.example.Varsani.utils.SessionHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class UploadArt extends AppCompatActivity {
    private EditText edt_title,edt_description;
    private ImageView img_selected;
    private Button btn_choose_image,btn_upload;
    private ProgressBar progressBar;
    private Uri uri;
    private String displayName = null;
    private RequestQueue rQueue;
    private SessionHandler session;
    private UserModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_art);

        edt_title = findViewById(R.id.edt_title);
        edt_description = findViewById(R.id.edt_description);
        img_selected = findViewById(R.id.img_selected);
        btn_choose_image = findViewById(R.id.btn_choose_image);
        btn_upload = findViewById(R.id.btn_upload);
        progressBar = findViewById(R.id.progress_bar);

        session=new SessionHandler(getApplicationContext());
        user=session.getUserDetails();

        btn_choose_image.setOnClickListener(v -> {
            Intent i = new Intent();
            i.setAction(Intent.ACTION_GET_CONTENT);
            i.setType("image/*"); // For images
            startActivityForResult(i, 1);
        });

        btn_upload.setOnClickListener(v -> uploadArt(displayName, uri));
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
            img_selected.setImageURI(uri);  // <-- Display the image

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
    private void uploadArt(final String pdfname, Uri pdffile) {

        InputStream iStream = null;

        final String title = edt_title.getText().toString().trim();
        final String desc = edt_description.getText().toString().trim();

        progressBar.setVisibility(View.VISIBLE);
        btn_choose_image.setVisibility(View.GONE);
        btn_upload.setVisibility(View.GONE);


        try {

            iStream = getContentResolver().openInputStream(pdffile);
            final byte[] inputData = getBytes(iStream);

            VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, URL_UPLOAD_ART,
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
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                // Failure
                                progressBar.setVisibility(View.GONE);
                                btn_choose_image.setVisibility(View.VISIBLE);
                                btn_upload.setVisibility(View.VISIBLE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressBar.setVisibility(View.GONE);
                            btn_choose_image.setVisibility(View.VISIBLE);
                            btn_upload.setVisibility(View.VISIBLE);
                            Log.e("E ", "" + e);
                        }
                    }
                    ,
                    error -> {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                        progressBar.setVisibility(View.GONE);
                        btn_choose_image.setVisibility(View.VISIBLE);
                        btn_upload.setVisibility(View.VISIBLE);
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
                    params.put("userID",user.getClientID());
                    params.put("title",title);
                    params.put("desc",desc);
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
            rQueue = Volley.newRequestQueue(UploadArt.this);
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