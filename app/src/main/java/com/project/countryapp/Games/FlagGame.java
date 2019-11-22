package com.project.countryapp.Games;

// Passing arraylist between activities using intents
// Link: https://stackoverflow.com/questions/13601883/how-to-pass-arraylist-of-objects-from-one-to-another-activity-using-intent-in-an

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.project.countryapp.Fragments.GameFragments.FlagGameFragment;
import com.project.countryapp.Models.Country;
import com.project.countryapp.R;

import java.util.ArrayList;

public class FlagGame extends AppCompatActivity {

    private ArrayList<Country> countries;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.game_base);

        Intent intent = getIntent();
        countries = (ArrayList<Country>) intent.getSerializableExtra("Countries");

        Fragment fragment = new FlagGameFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("Countries", countries);
        fragment.setArguments(bundle);

        swapFragment(fragment);

    }

    private void swapFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.base_content, fragment);
        fragmentTransaction.commit();
    }

}

