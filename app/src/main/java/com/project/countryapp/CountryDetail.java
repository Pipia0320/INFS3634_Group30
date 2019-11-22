package com.project.countryapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.project.countryapp.Models.Country;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CountryDetail extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Do the design here
        setContentView(R.layout.country_detail);

        // Passed a intent string call CountryName
        Intent intent = getIntent();
        final String country_name = intent.getStringExtra("CountryName");

        final RequestQueue requestQueue = Volley.newRequestQueue(this);

        // Defind all TextView or ImageViews here
        final ImageView countryFlag = findViewById(R.id.country_flag);
        final TextView countryName = findViewById(R.id.country_name);
        final TextView capital = findViewById(R.id.capital);
        final TextView population = findViewById(R.id.population);
        final TextView region = findViewById(R.id.region);
        final TextView subRegion = findViewById(R.id.subregion);
        final TextView area = findViewById(R.id.area);
        final TextView gini = findViewById(R.id.gini);
        final TextView currencies_symbol = findViewById(R.id.currencies_symbol);
        final TextView currencies_name = findViewById(R.id.currencies_name);
        final TextView currencies_code = findViewById(R.id.currencies_code);



        String url = "https://restcountries.eu/rest/v2/name/"+ country_name + "?fullText=true";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();

                Type arrayListType = new TypeToken<ArrayList<Country>>(){}.getType();
                ArrayList<Country> target = gson.fromJson(response, arrayListType);

                Country country = target.get(0);

                Glide.with(getApplicationContext()).load(country.getFlag()).into(countryFlag);
                countryName.setText(country.getName());

                capital.setText("Capital: " + country.getCapital());
                population.setText("Population: " + country.getPopulation());
                region.setText("Region: " + country.getRegion());
                subRegion.setText("Sub Region: " + country.getSubregion());
                area.setText("Area: " + country.getArea() + " km²");
                gini.setText("Gini: " + country.getGini());
                currencies_name.setText("Currency Name: " + country.getCurrencies().get(0).getName());
                currencies_symbol.setText("Currency Symbol: " + country.getCurrencies().get(0).getSymbol());
                currencies_code.setText("Currency Code: " + country.getCurrencies().get(0).getCode());

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getBaseContext(),"The request failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        requestQueue.stop();
                    }
                });
        requestQueue.add(stringRequest);
    }
}
