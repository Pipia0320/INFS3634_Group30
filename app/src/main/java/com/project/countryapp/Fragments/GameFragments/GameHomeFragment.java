package com.project.countryapp.Fragments.GameFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.project.countryapp.Games.CapitalGame;
import com.project.countryapp.Games.FlagGame;
import com.project.countryapp.Models.Country;
import com.project.countryapp.R;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class GameHomeFragment extends Fragment {

    private RequestQueue requestQueue;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_home, container, false);

        requestQueue = Volley.newRequestQueue(getActivity());

        final Button flag = view.findViewById(R.id.button_flag);
        final Button capital = view.findViewById(R.id.button_capital);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://restcountries.eu/rest/v2/all",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();

                        Type arrayListType = new TypeToken<ArrayList<Country>>(){}.getType();
                        final ArrayList<Country> countries = gson.fromJson(response, arrayListType);

                        flag.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getContext(), FlagGame.class);
                                intent.putExtra("Countries", countries);
                                startActivity(intent);

                            }
                        });
                        capital.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(getContext(), CapitalGame.class);
                                intent.putExtra("Countries", countries);
                                startActivity(intent);
                            }
                        });

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(),"The request failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        requestQueue.stop();
                    }
                });

        requestQueue.add(stringRequest);

        return view;
    }


}
