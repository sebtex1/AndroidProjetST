package com.example.androidprojetst;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

        if (getIntent().getExtras() != null) {
            String Date = getIntent().getExtras().getString("Date");
            boolean isDate = getIntent().getExtras().getBoolean("isDate");

            if(isDate) {
                RequestQueue queue = Volley.newRequestQueue(this);
                String url = Constant.URL + "date=" + Date;

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @SuppressLint("StringFormatInvalid")
                            @Override
                            public void onResponse(String response) {
                                Log.e("volley", response);

                                ApiNasa api = new Gson().fromJson(response, ApiNasa.class);

                                List<Records> records = api.getRecords();

                                if (api.getRecords() != null && api.getRecords().size() > 0) {

                                    for (int i=0; i<records.size(); i++) {

                                        Fields fields = records.get(i).getFields();

                                        textViewTitle.setText(fields.getTitle());
                                        textViewDate.setText(fields.getDate());
                                        textViewExplanation.setText(fields.getExplanation());

                                        Picasso.get().load(fields.getUrlImage()).into(imageViewUrl, new Callback() {
                                            @Override
                                            public void onSuccess() {

                                            }

                                            @Override
                                            public void onError(Exception e) {

                                            }
                                        });
                                    }
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String json = new String((error.networkResponse.data));
                        Log.e("volley", json);
                    }
                });

                // Add the request to the RequestQueue.
                queue.add(stringRequest);
            } else {
                RequestQueue queue = Volley.newRequestQueue(this);
                String url = Constant.URL;

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @SuppressLint("StringFormatInvalid")
                            @Override
                            public void onResponse(String response) {
                                Log.e("volley", response);

                                ApiNasa api = new Gson().fromJson(response, ApiNasa.class);

                                List<Records> records = api.getRecords();

                                if (api.getRecords() != null && api.getRecords().size() > 0) {

                                    for (int i=0; i<records.size(); i++) {

                                        Fields fields = records.get(i).getFields();

                                        textViewTitle.setText(fields.getTitle());
                                        textViewDate.setText(fields.getDate());
                                        textViewExplanation.setText(fields.getExplanation());

                                        Picasso.get().load(fields.getUrlImage()).into(imageViewUrl, new Callback() {
                                            @Override
                                            public void onSuccess() {

                                            }

                                            @Override
                                            public void onError(Exception e) {

                                            }
                                        });
                                    }
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String json = new String((error.networkResponse.data));
                        Log.e("volley", json);
                    }
                });

                // Add the request to the RequestQueue.
                queue.add(stringRequest);
            }
            }
        }
}