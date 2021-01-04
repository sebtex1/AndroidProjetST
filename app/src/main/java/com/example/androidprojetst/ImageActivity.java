package com.example.androidprojetst;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.androidprojetst.nasa.ApiNasa;
import com.example.androidprojetst.nasa.Fields;
import com.example.androidprojetst.nasa.Records;
import com.example.androidprojetst.utils.Constant;
import com.example.androidprojetst.utils.FastDialog;
import com.example.androidprojetst.utils.Network;
import com.google.gson.Gson;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageActivity extends AppCompatActivity {

    private TextView textViewTitle;
    private TextView textViewDate;
    private ImageView imageViewUrl;
    private  TextView textViewExplanation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        textViewTitle = findViewById(R.id.textViewTitle);
        textViewDate = findViewById(R.id.textViewDate);
        imageViewUrl = findViewById(R.id.imageViewUrl);
        textViewExplanation = findViewById(R.id.textViewExplanation);

        if(!Network.isNetworkAvailable(ImageActivity.this)) {
            FastDialog.showDialog(ImageActivity.this, FastDialog.SIMPLE_DIALOG, getString(R.string.dialog_no_network));
            return;
        }

        Intent intent = getIntent();

        boolean isDate = intent.getExtras().getBoolean("isDate");
        String dateUrl = intent.getStringExtra("dateUrl");
        //Log.e("params", String.valueOf(isDate));
        //Log.e("dateUrl", String.valueOf(dateUrl));

        if(isDate) {
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = Constant.URL + "&date=" + dateUrl;

            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @SuppressLint("StringFormatInvalid")
                        @Override
                        public void onResponse(String response) {
                            Log.e("volley", response);

                            parseJson(response);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    String json = new String((error.networkResponse.data));
                    Log.e("volley", error.toString());
                    parseJson(json);
                }
            });

            // Add the request to the RequestQueue.
            queue.add(stringRequest);
        } else {
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = Constant.URL;

            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @SuppressLint("StringFormatInvalid")
                        @Override
                        public void onResponse(String response) {
                            Log.e("volley", response);

                            parseJson(response);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    String json = new String((error.networkResponse.data));
                    Log.e("volley", error.toString());
                    parseJson(json);
                }
            });

            // Add the request to the RequestQueue.
            queue.add(stringRequest);
        }
    }


    private void parseJson(String response) {
        // Traitement du JSON
        ApiNasa api = new Gson().fromJson(response, ApiNasa.class);

        textViewTitle.setText(api.getTitle());
        textViewDate.setText(api.getDate());
        textViewExplanation.setText(api.getExplanation());
        Log.e("Image", api.getUrl());

        Picasso.get().load(api.getUrl()).into(imageViewUrl, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Exception e) {

            }
        });
    }
}