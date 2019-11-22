package com.project.countryapp.Fragments.SearchFragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.project.countryapp.R;

public class SearchFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        final Button search_country_button = view.findViewById(R.id.country_button);
        final Button search_capital_button = view.findViewById(R.id.capital_button);

        Fragment fragment = new SearchBarFragment();
        Bundle b = new Bundle();
        b.putSerializable("SearchType", "country");
        fragment.setArguments(b);

        swapFragment(fragment);

        search_country_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new SearchBarFragment();
                Bundle b = new Bundle();
                b.putSerializable("SearchType", "country");
                fragment.setArguments(b);
                swapFragment(fragment);

                search_country_button.setBackgroundColor(Color.parseColor("#FFC107"));
                search_country_button.setTextColor(Color.parseColor("#FFFFFF"));

                search_capital_button.setBackgroundColor(Color.parseColor("#FFFFFF"));
                search_capital_button.setTextColor(Color.parseColor("#000000"));
            }
        });

        search_capital_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new SearchBarFragment();
                Bundle b = new Bundle();
                b.putSerializable("SearchType", "capital");
                fragment.setArguments(b);
                swapFragment(fragment);

                search_country_button.setBackgroundColor(Color.parseColor("#FFFFFF"));
                search_country_button.setTextColor(Color.parseColor("#000000"));

                search_capital_button.setBackgroundColor(Color.parseColor("#FFC107"));
                search_capital_button.setTextColor(Color.parseColor("#FFFFFF"));
            }
        });



        return view;
    }

    private void swapFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_slot_search, fragment);
        fragmentTransaction.commit();
    }
}
