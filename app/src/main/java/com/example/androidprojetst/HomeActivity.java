package com.example.androidprojetst;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class HomeActivity extends AppCompatActivity {

    private EditText editTextDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void showDate(View view) {
        Intent intentDate = new Intent(HomeActivity.this, ImageActivity.class);

        editTextDate = findViewById(R.id.idDate);
        intentDate.putExtra("Date", editTextDate.getText());
        intentDate.putExtra("isDate", true);

        startActivity(intentDate);
    }

    public void showToday(View view) {
        Intent intentToday = new Intent(HomeActivity.this, ImageActivity.class);

        intentToday.putExtra("isDate", false);

        startActivity(intentToday);
    }
}