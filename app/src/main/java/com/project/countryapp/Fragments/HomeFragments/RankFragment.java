package com.project.countryapp.Fragments.HomeFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.project.countryapp.R;
import com.project.countryapp.Scores.Score;
import com.project.countryapp.Scores.ScoreDataBase;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RankFragment extends Fragment {
    private String type;
    private TextView scoreTotal;
    private TextView scoreAverage;
    private TextView score1;
    private TextView score2;
    private TextView score3;

    public static RankFragment newInstance(String type) {
        RankFragment rankFragment = new RankFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        rankFragment.setArguments(bundle);
        return rankFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_scores, container, false);
        type = getArguments().getString("type");

        scoreTotal = view.findViewById(R.id.score_totalplays);
        scoreAverage = view.findViewById(R.id.score_average);
        score1 = view.findViewById(R.id.score_top1);
        score2 = view.findViewById(R.id.score_top2);
        score3 = view.findViewById(R.id.score_top3);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        ScoreDataBase quizDataBase = Room.databaseBuilder(getActivity(), ScoreDataBase.class, "score.db")
                .allowMainThreadQueries()
                .build();

        List<Score> list = quizDataBase.dao().queryList(type);
        scoreTotal.setText("Number of games played: " + list.size());

        int total = 0;

        for (int i = 0; i < list.size(); i++) {
            total += list.get(i).getScore();
        }

        if (total == 0) {
            scoreAverage.setText("Average score: 0");
        } else {
            DecimalFormat df = new DecimalFormat("0.00");
            scoreAverage.setText("Average score: " + df.format((float) total / list.size()));
        }

        Collections.sort(list, new Comparator<Score>() {
            @Override
            public int compare(Score o1, Score o2) {
                return o2.getScore() - o1.getScore();
            }
        });

        if (list.size() > 0) {
            score1.setText("Top 1: " + list.get(0).getScore() + "/10");
        }
        if (list.size() > 1) {
            score2.setText("Top 2: " + list.get(1).getScore() + "/10");
        }
        if (list.size() > 2) {
            score3.setText("Top 3: " + list.get(2).getScore() + "/10");
        }
    }
}
