package com.example.vinay.sampleupload;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    private static final String TAG = "ResultActivity";
    private ArrayList<String> studentUsn;
    private ListView listView;
    private Bundle bundle;

    @SuppressLint("LogConditional")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        initializeViews();
        defaultConfigurations();

        if( bundle!= null) {
            String jsonResult = (String) bundle.get("response");
            setResponseArrayList(jsonResult);
        } else {
            Toast.makeText(this, "Something went wrong.", Toast.LENGTH_SHORT).show();
        }
    }

    private void defaultConfigurations() {
        bundle = getIntent().getExtras();
    }

    private void initializeViews() {
        listView = findViewById(R.id.listview1);
    }

    private void setResponseArrayList(String result) {
        studentUsn = new ArrayList<>();

        try {
            JSONArray resultArray = new JSONArray(result);
            if (resultArray != null) {
                for (int i = 0; i < resultArray.length(); i++) {
                    studentUsn.add(resultArray.getString(i));
                }
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_list_item_1,
                    studentUsn
            );
            listView.setAdapter(arrayAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
