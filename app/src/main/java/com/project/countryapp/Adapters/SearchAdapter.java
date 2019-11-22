package com.project.countryapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.project.countryapp.CountryDetail;
import com.project.countryapp.Models.Country;
import com.project.countryapp.R;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private ArrayList<Country> countriesToAdapt;
    private String searchType;

    public void setData(ArrayList<Country> countries, String searchType) {
        this.countriesToAdapt = countries;
        this.searchType = searchType;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_list_item, parent, false);
        return new SearchViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        final Country countryAtPosition = countriesToAdapt.get(position);

        holder.bing(countryAtPosition);
    }

    @Override
    public int getItemCount() {
        return countriesToAdapt.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {

        private TextView country_name;
        private ImageView country_flag;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            country_name = itemView.findViewById(R.id.country);
            country_flag = itemView.findViewById(R.id.country_image);
        }

        public void bing(final Country countryAtPosition) {

            if (searchType.equals("country")) {
                country_name.setText(countryAtPosition.getName());
            } else {
                country_name.setText(countryAtPosition.getCapital());
            }

            Glide.with(itemView.getContext()).load(countryAtPosition.getFlag()).into(country_flag);

            country_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();

                    Intent intent = new Intent(context, CountryDetail.class);
                    intent.putExtra("CountryName", countryAtPosition.getName());
                    context.startActivity(intent);
                }
            });

        }
    }
}
