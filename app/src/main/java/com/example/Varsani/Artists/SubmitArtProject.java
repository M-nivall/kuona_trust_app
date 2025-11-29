package com.example.Varsani.Artists;

import static com.example.Varsani.utils.Urls.URL_SUBMIT_EXHIBITION_ART;

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
import com.example.Varsani.Clients.CompleteProfile;
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

public class SubmitArtProject extends AppCompatActivity {

    private EditText edt_artwork_title,edt_artwork_desc;
    private Button btn_upload_image,btn_submit;
    private ImageView img_preview;
    private ProgressBar progressBar;

    private Uri uri;
    private String displayName = null;
    private RequestQueue rQueue;

    private SessionHandler session;
    private UserModel user;

    private String exhibitionID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_art_project);

        edt_artwork_title = findViewById(R.id.edt_artwork_title);
        edt_artwork_desc = findViewById(R.id.edt_artwork_desc);
        btn_upload_image = findViewById(R.id.btn_upload_image);
        img_preview = findViewById(R.id.img_preview);
        btn_submit = findViewById(R.id.btn_submit);
        progressBar = findViewById(R.id.progress_bar);

        session=new SessionHandler(getApplicationContext());
        user=session.getUserDetails();


        Intent intent=getIntent();
        exhibitionID=intent.getStringExtra("exhibitionID");

        btn_upload_image.setOnClickListener(v -> {
            Intent i = new Intent();
            i.setAction(Intent.ACTION_GET_CONTENT);
            i.setType("image/*"); // For images
            startActivityForResult(i, 1);
        });

        btn_submit.setOnClickListener(v -> SubmitProject(displayName, uri));
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
            img_preview.setImageURI(uri);  // <-- Display the image

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
    private void SubmitProject(final String pdfname, Uri pdffile) {

        InputStream iStream = null;

        final String title = edt_artwork_title.getText().toString().trim();
        final String desc = edt_artwork_desc.getText().toString().trim();

        progressBar.setVisibility(View.VISIBLE);
        btn_upload_image.setVisibility(View.GONE);
        btn_submit.setVisibility(View.GONE);


        try {

            iStream = getContentResolver().openInputStream(pdffile);
            final byte[] inputData = getBytes(iStream);

            VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, URL_SUBMIT_EXHIBITION_ART,
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
                                btn_upload_image.setVisibility(View.VISIBLE);
                                btn_submit.setVisibility(View.VISIBLE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressBar.setVisibility(View.GONE);
                            btn_upload_image.setVisibility(View.VISIBLE);
                            btn_submit.setVisibility(View.VISIBLE);
                            Log.e("E ", "" + e);
                        }
                    }
                    ,
                    error -> {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                        progressBar.setVisibility(View.GONE);
                        btn_upload_image.setVisibility(View.VISIBLE);
                        btn_submit.setVisibility(View.VISIBLE);
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
                    params.put("exhibitionID",exhibitionID);
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
            rQueue = Volley.newRequestQueue(SubmitArtProject.this);
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