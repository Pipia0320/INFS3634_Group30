package com.project.countryapp.Fragments.HomeFragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.project.countryapp.R;

public class HomeFragment extends Fragment {
    private RankFragment countryRankFragment;
    private RankFragment capitalRankFragment;
    private Fragment[] fragmentArray;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        final Button search_country_button = view.findViewById(R.id.country_button);
        final Button search_capital_button = view.findViewById(R.id.capital_button);
        countryRankFragment =  RankFragment.newInstance("country");
        capitalRankFragment =  RankFragment.newInstance("capital");
        fragmentArray = new Fragment[]{ countryRankFragment, capitalRankFragment};

        search_country_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                swapFragment(0);

                search_country_button.setBackgroundColor(Color.parseColor("#FFC107"));
                search_country_button.setTextColor(Color.parseColor("#FFFFFF"));

                search_capital_button.setBackgroundColor(Color.parseColor("#FFFFFF"));
                search_capital_button.setTextColor(Color.parseColor("#000000"));
            }
        });

        search_capital_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                swapFragment(1);

                search_country_button.setBackgroundColor(Color.parseColor("#FFFFFF"));
                search_country_button.setTextColor(Color.parseColor("#000000"));

                search_capital_button.setBackgroundColor(Color.parseColor("#FFC107"));
                search_capital_button.setTextColor(Color.parseColor("#FFFFFF"));
            }
        });
        getChildFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_slot_home, fragmentArray[0])
                .add(R.id.fragment_slot_home,fragmentArray[1])
                .hide(fragmentArray[1])
                .show(fragmentArray[0])
                .commit();


        return view;
    }

    private void swapFragment(int i) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        for (int j = 0; j < fragmentArray.length; j++) {
            transaction.hide(fragmentArray[j]);
        }
        transaction.show(fragmentArray[i]);
        transaction.commit();
    }

}
