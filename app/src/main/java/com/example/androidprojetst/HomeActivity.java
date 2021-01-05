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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HomeActivity extends AppCompatActivity {

    private EditText editTextDate;
    private DatePickerDialog picker;
    private Date current_date;
    private String date_string;
    private Date oldest_date;

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
                int year1 = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(HomeActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                SimpleDateFormat format_date = new SimpleDateFormat("yyyy-MM-dd");

                                String old_date= (1995 + "-" + (6 + 1)+ "-" + 16);
                                try {
                                    oldest_date=format_date.parse(old_date);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                date_string = (year1 + "-" + (month + 1)+ "-" + day);
                                try {
                                    current_date= format_date.parse(date_string);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                editTextDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                Date date_user = null;
                                try {
                                    date_user = format_date.parse(editTextDate.getText().toString());
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }


                                if(current_date.before(date_user) || oldest_date.after(date_user)){
                                    editTextDate.setText(date_string);
                                }
                            }
                        }, year1, month, day);
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