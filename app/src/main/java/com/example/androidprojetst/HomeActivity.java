package com.example.androidprojetst;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.androidprojetst.utils.FastDialog;

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

                //Instanciation d'un calendrier pour récupérer la date d'aujourd'hui
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year1 = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(HomeActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            // On lance le calendrier quand on veut saisir une date
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                //Le format de la date (celui utilisé dans la requete api)
                                SimpleDateFormat format_date = new SimpleDateFormat("yyyy-MM-dd");

                                //creation de la date minimale de l'api
                                String old_date= (1995 + "-" + (6 + 1)+ "-" + 16);  //on fait le mois +1 car les mois vont de 0 à 11
                                try {
                                    oldest_date=format_date.parse(old_date);    //String to Date
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                //creation de la date d'aujourd'hui
                                date_string = (year1 + "-" + (month + 1)+ "-" + day);
                                try {
                                    current_date= format_date.parse(date_string);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                //récupération de la date entré par l'utilisateur
                                editTextDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                Date date_user = null;
                                try {
                                    date_user = format_date.parse(editTextDate.getText().toString());
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                //Se lance si la date n'est pas comprise entre 1995/06/16 et aujourd'hui
                                if(current_date.before(date_user) || oldest_date.after(date_user)){
                                    //message qui averti l'utilisateur
                                    FastDialog.showDialog(HomeActivity.this, FastDialog.SIMPLE_DIALOG, getString(R.string.fastDialogue_wrongDate));
                                    editTextDate.setText(date_string);
                                }
                            }
                        }, year1, month, day);
                picker.show();
            }
        });
    }

    //se lance si l'utilisateur présise une date
    public void showDate(View view) {
        Intent intentDate = new Intent(HomeActivity.this, ImageActivity.class);

        editTextDate = findViewById(R.id.idDate);

        Log.e("text date", String.valueOf(editTextDate.getText()));
        intentDate.putExtra("isDate", true);
        intentDate.putExtra("dateUrl", editTextDate.getText().toString());

        startActivity(intentDate);
    }

    //Si aucune date n'est renseigné lance le contenu du jour
    public void showToday(View view) {
        Intent intentToday = new Intent(HomeActivity.this, ImageActivity.class);

        intentToday.putExtra("isDate", false);

        startActivity(intentToday);
    }
}