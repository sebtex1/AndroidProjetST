package com.example.androidprojetst;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HomeActivity extends AppCompatActivity {

    private EditText editTextDate;
    private DatePickerDialog picker;
    private Calendar calendar;
    private SimpleDateFormat format_date;
    private String current_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        editTextDate = findViewById(R.id.idDate);
        editTextDate.setInputType(InputType.TYPE_NULL);
        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(HomeActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                calendar = Calendar.getInstance();
                                format_date=new SimpleDateFormat("yyyy-MM-dd");
                                current_date= format_date.format(calendar.getTime());
                                editTextDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                                if(current_date.compareTo(editTextDate.getText().toString()) < 0 ){
                                    editTextDate.setText(current_date);
                                }
                            }
                        }, year, month, day);
                picker.show();
            }
        });
    }

    public void showDate(View view) {
        Intent intentDate = new Intent(HomeActivity.this, ImageActivity.class);

        editTextDate = findViewById(R.id.idDate);

        Log.e("text date", String.valueOf(editTextDate.getText()));
        intentDate.putExtra("isDate", true);
        intentDate.putExtra("dateUrl", editTextDate.getText().toString());

        startActivity(intentDate);
    }

    public void showToday(View view) {
        Intent intentToday = new Intent(HomeActivity.this, ImageActivity.class);

        intentToday.putExtra("isDate", false);

        startActivity(intentToday);
    }
}