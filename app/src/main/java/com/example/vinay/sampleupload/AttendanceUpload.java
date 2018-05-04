package com.example.vinay.sampleupload;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AttendanceUpload extends AppCompatActivity implements View.OnClickListener {

    String filename;
    Button UplaodBn, ChooseBn;
    ImageView imgView;
    final int IMG_REQUEST = 1;
    Bitmap bitmap;
    private String UploadUrl = "http://192.168.31.236:5000/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_upload);
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
        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri path = data.getData();
            Toast.makeText(getApplicationContext(), path.toString(), Toast.LENGTH_LONG).show();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                imgView.setImageBitmap(bitmap);
                imgView.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UploadUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(AttendanceUpload.this, response, Toast.LENGTH_LONG).show();
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
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(40 * 10000, 0,
                0));
        MySingleton.getInstance(AttendanceUpload.this).addToRequestQue(stringRequest);
    }

    private String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);
    }
}
