package com.example.vinay.sampleupload;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AttendanceActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    int mYear,mMonth,mDay;
    Spinner semSpinner,classSpinner;
    EditText dateEditText;
    Boolean isDateChoosen,isSemChoosen,isClassChoosen;
    String dateString,semString,classString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_detail);
        isClassChoosen=false;
        isDateChoosen=false;
        isSemChoosen=false;
        dateEditText = findViewById(R.id.dateEditText);
        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentDate = Calendar.getInstance();
                mYear = mcurrentDate.get(Calendar.YEAR);
                mMonth = mcurrentDate.get(Calendar.MONTH);
                mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(AttendanceActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        Calendar myCalendar = Calendar.getInstance();
                        myCalendar.set(Calendar.YEAR, selectedyear);
                        myCalendar.set(Calendar.MONTH, selectedmonth);
                        myCalendar.set(Calendar.DAY_OF_MONTH, selectedday);
                        String myFormat = "dd/MM/yy"; //Change as you need
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
                        dateEditText.setText(sdf.format(myCalendar.getTime()));
                        isDateChoosen=true;
                        dateString=sdf.format(myCalendar.getTime());
                        mDay = selectedday;
                        mMonth = selectedmonth;
                        mYear = selectedyear;
                    }
                }, mYear, mMonth, mDay);
                //mDatePicker.setTitle("Select date");
                mDatePicker.show();
            }
        });
        semSpinner = findViewById(R.id.semSpinner);
        semSpinner.setOnItemSelectedListener(this);
        List<String> sem_categories = new ArrayList<String>();
        sem_categories.add("1");
        sem_categories.add("2");
        sem_categories.add("3");
        sem_categories.add("4");
        sem_categories.add("5");
        sem_categories.add("6");
        sem_categories.add("7");
        sem_categories.add("8");
        ArrayAdapter<String> dataAdapterSem = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sem_categories);
        dataAdapterSem.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        semSpinner.setAdapter(dataAdapterSem);
        classSpinner = findViewById(R.id.classSpinner);
        classSpinner.setOnItemSelectedListener(this);
        List<String> class_categories = new ArrayList<String>();
        class_categories.add("1");
        class_categories.add("2");
        class_categories.add("3");
        class_categories.add("4");
        class_categories.add("5");
        class_categories.add("6");
        ArrayAdapter<String> dataAdapterClass = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, class_categories);
        dataAdapterClass.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classSpinner.setAdapter(dataAdapterClass);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_SHORT).show();
        switch (parent.getId()){
            case R.id.classSpinner:
                isClassChoosen=true;
                classString=item;
                break;
            case R.id.semSpinner:
                isSemChoosen=true;
                semString=item;
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void goToAttendanceUpload(View view) {
        if(isDateChoosen && isSemChoosen && isClassChoosen){
            Intent intent = new Intent(AttendanceActivity.this, MarkAttendanceActivity.class);
            String extraString = dateString + "_" + semString + "_" + classString;
            intent.putExtra("filename", extraString);
            startActivity(intent);
        }else{
            Toast.makeText(getApplicationContext(),"Choose the Values",Toast.LENGTH_SHORT).show();
        }
    }
}
