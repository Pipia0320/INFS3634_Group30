package com.project.countryapp.Fragments.SearchFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.project.countryapp.Adapters.SearchAdapter;
import com.project.countryapp.Models.Country;
import com.project.countryapp.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SearchBarFragment extends Fragment {
    private ArrayList<Country> countries = new ArrayList<Country>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_list, container, false);

        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        final EditText editText = view.findViewById(R.id.search_text);
        final ImageButton imageButton = view.findViewById(R.id.search_button);

        final RecyclerView recyclerView = view.findViewById(R.id.reclcyer_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        final SearchAdapter searchAdapter = new SearchAdapter();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://restcountries.eu/rest/v2/all",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();

                        Type arrayListType = new TypeToken<ArrayList<Country>>(){}.getType();
                        final ArrayList<Country> countries = gson.fromJson(response, arrayListType);

                        final String searchType = getArguments().getString("SearchType");

                        final ArrayList<Country> suggestions = new ArrayList<Country>();

                        if (searchType.equals("country")) {

                            if (editText.getText().toString().matches("")) {
                                suggestions.addAll(countries);
                            }

                        } else {

                            if (editText.getText().toString().matches("")) {

                                ArrayList<Country> temp_obj = new ArrayList<Country>(countries);

                                Collections.sort(temp_obj, new Comparator<Country>() {
                                    @Override
                                    public int compare(Country o1, Country o2) {

                                        if (!o1.getCapital().matches("") && !o2.getCapital().matches("")) {
                                            return o1.getCapital().compareTo(o2.getCapital());
                                        }

                                        return (o1.getCapital().matches("")) ? 1 : -1;

                                    }
                                });

                                suggestions.addAll(temp_obj);
                            }

                        }

                        searchAdapter.setData(suggestions, searchType);
                        recyclerView.setAdapter(searchAdapter);


                        // On click functions
                        imageButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                suggestions.clear();

                                if (searchType.equals("country")) {
                                    for (Country c : countries) {
                                        if (c.getName().toLowerCase().trim().contains(editText.getText().toString().toLowerCase().trim())) {
                                            suggestions.add(c);
                                        }
                                    }
                                } else {
                                    for (Country c : countries) {
                                        if (c.getCapital().toLowerCase().trim().contains(editText.getText().toString().toLowerCase().trim())) {
                                            suggestions.add(c);
                                        }
                                    }
                                }

                                searchAdapter.setData(suggestions, searchType);
                                recyclerView.setAdapter(searchAdapter);
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


    private void storeData(ArrayList<Country> c) {
        System.out.println(c.size());
        this.countries.addAll(c);
    }
}
