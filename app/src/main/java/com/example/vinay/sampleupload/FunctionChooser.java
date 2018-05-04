package com.example.vinay.sampleupload;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FunctionChooser extends AppCompatActivity {


    Button attendance_button,register_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function_chooser);
        attendance_button = findViewById(R.id.attendance_button);
        register_button = findViewById(R.id.register_button);
    }

    public void attendance_markup(View view) {
        Intent attendance_intent=new Intent(this,Attendance.class);
        startActivity(attendance_intent);
    }

    public void attendance_register(View view) {
        Intent register_intent=new Intent(this,MainActivity.class);
        startActivity(register_intent);
    }
}
