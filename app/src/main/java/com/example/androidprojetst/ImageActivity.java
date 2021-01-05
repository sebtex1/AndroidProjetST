package com.example.androidprojetst;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
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

import static android.view.View.VISIBLE;

public class ImageActivity extends AppCompatActivity {

    private TextView textViewTitle;
    private TextView textViewDate;
    private ImageView imageViewUrl;
    private TextView textViewExplanation;
    private TextView invisibleBox;
    private TextView textViewNoImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        textViewTitle = findViewById(R.id.textViewTitle);
        textViewDate = findViewById(R.id.textViewDate);
        imageViewUrl = findViewById(R.id.imageViewUrl);
        textViewExplanation = findViewById(R.id.textViewExplanation);
        invisibleBox = findViewById(R.id.invisibleBox);
        textViewNoImage = findViewById(R.id.textViewNoImage);

        // Vérification de la connexion
        if(!Network.isNetworkAvailable(ImageActivity.this)) {
            FastDialog.showDialog(ImageActivity.this, FastDialog.SIMPLE_DIALOG, getString(R.string.dialog_no_network));
            return;
        }

        Intent intent = getIntent();

        boolean isDate = intent.getExtras().getBoolean("isDate");
        String dateUrl = intent.getStringExtra("dateUrl");
        //Log.e("params", String.valueOf(isDate));
        //Log.e("dateUrl", String.valueOf(dateUrl));

        // Réalise la requête à l'API si la date à était renseigner
        if(isDate) {
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = Constant.URL + "&date=" + dateUrl;

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

            queue.add(stringRequest);
        // Si la date n'a pas était renseigner réaliser une requête avec le contenu du jour
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

        // Attribut les valeurs du JSON aux éléments de Image Activity
        textViewTitle.setText(api.getTitle());
        textViewDate.setText(api.getDate());
        textViewExplanation.setText(api.getExplanation());
        // Permet de stocker l'URL des images ou vidéos
        invisibleBox.setText(api.getUrl());
        Log.e("Image", api.getUrl());

        if(!api.getMedia_type().equals("image")){
            textViewNoImage.setVisibility(VISIBLE);
        }
        else{
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

    // Permet de récupérer l'URL des images et vidéos sans l'afficher à l'utilisateur
    public void web(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(invisibleBox.getText().toString()));
        startActivity(intent);
    }
}