package com.example.vinay.sampleupload;

import android.content.Intent;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vinay.sampleupload.util.AppSettings;

import java.io.BufferedOutputStream;

public class FunctionChooserActivity extends AppCompatActivity {


    Button attendance_button,register_button;
    EditText localIpInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function_chooser);
        attendance_button = findViewById(R.id.attendance_button);
        register_button = findViewById(R.id.register_button);
    }

    public void attendance_markup(View view) {
        Intent attendance_intent=new Intent(this,AttendanceActivity.class);
        startActivity(attendance_intent);
    }

    public void attendance_register(View view) {
        Intent register_intent=new Intent(this,RegisterActivity.class);
        startActivity(register_intent);
    }

    public void setNewLocalIp(View view) {
        localIpInput = findViewById(R.id.setting_text_input);
        String input = localIpInput.getText().toString();
        if(Patterns.IP_ADDRESS.matcher(input).matches()) {
            AppSettings.setValue(getApplicationContext(), AppSettings.PREF_LOCAL_IP + ":5000", input);
            Toast.makeText(this, "IP Address Saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Invalid IP Address", Toast.LENGTH_SHORT).show();
        }

    }
}
