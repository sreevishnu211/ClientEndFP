package com.example.vinay.sampleupload;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.vinay.sampleupload.util.AppSettings;
import com.example.vinay.sampleupload.util.MySingleton;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MarkAttendanceActivity extends AppCompatActivity implements View.OnClickListener {

    String filename;
    Button UplaodBn, ChooseBn;
    ImageView imgView;
    final int IMG_REQUEST = 1;
    Bitmap bitmap;
    String mCurrentPhotoPath;
    static final int REQUEST_IMAGE_CAPTURE = 2;
    private String uploadUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_attendance);

//        uploadUrl = AppSettings.getValue(getApplicationContext(), AppSettings.PREF_LOCAL_IP, "192.168.31.1:5000");
        uploadUrl = "http://192.168.31.236:5000";


        Bundle bundle = getIntent().getExtras();
        filename = bundle.get("filename").toString();
        filename = filename.replaceAll("[/]", "_");
        UplaodBn = findViewById(R.id.attendanceImageUploadButton);
        ChooseBn = findViewById(R.id.attendanceImageChooseButton);
        imgView = findViewById(R.id.attendanceImage);
        UplaodBn.setOnClickListener(this);
        ChooseBn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.attendanceImageChooseButton:
                selectImage();
                break;

            case R.id.attendanceImageUploadButton:
                uploadImage();
                break;
        }
    }



    void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMG_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case IMG_REQUEST:
                Uri path = data.getData();
                Toast.makeText(getApplicationContext(), path.toString(), Toast.LENGTH_LONG).show();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                    imgView.setImageBitmap(bitmap);
                    imgView.setVisibility(View.VISIBLE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case REQUEST_IMAGE_CAPTURE:

                Bundle extras = data.getExtras();
                bitmap = (Bitmap) extras.get("data");
                imgView.setImageBitmap(bitmap);
                imgView.setVisibility(View.VISIBLE);
                break;
        }

    }

    private void uploadImage() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, uploadUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Intent resultActivityIntent = new Intent(MarkAttendanceActivity.this, ResultActivity.class);
                resultActivityIntent.putExtra("response", response);
                resultActivityIntent.putExtra("type", "mark_attendance");
                startActivity(resultActivityIntent);

                Toast.makeText(MarkAttendanceActivity.this, response, Toast.LENGTH_LONG).show();
                imgView.setImageResource(0);
                imgView.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", filename.trim());
                params.put("file", imageToString(bitmap));

                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(40 * 10000, 0, 0));
        MySingleton.getInstance(MarkAttendanceActivity.this).addToRequestQue(stringRequest);
    }

    private String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);
    }

    public void takePictureIntent(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
}
