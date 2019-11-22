package com.project.countryapp.Fragments.GameFragments;

// How to rng unique numbers
// Link: https://stackoverflow.com/questions/8115722/generating-unique-random-numbers-in-java

// How to shuffle around arraylist elements
// Link: https://stackoverflow.com/questions/1519736/random-shuffling-of-an-array/1519753#1519753

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.project.countryapp.MainActivity;
import com.project.countryapp.Models.Country;
import com.project.countryapp.R;
import com.project.countryapp.Scores.Score;
import com.project.countryapp.Scores.ScoreDataBase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FlagGameFragment extends Fragment {
    private int questionNo = 1;
    private int correct = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.game_question, container, false);

        // setting up mcq display
        final ConstraintLayout mcqSpace = view.findViewById(R.id.mcq_space);
        final ConstraintLayout mcqChoices = (ConstraintLayout) inflater.inflate(R.layout.game_mcq, container, false);
        mcqSpace.addView(mcqChoices);

        // setting up results display
        final ConstraintLayout resultsSpace = (ConstraintLayout) inflater.inflate(R.layout.game_result, container, false);
        final TextView resultsOutcome = resultsSpace.findViewById(R.id.first);
        final TextView resultsAnswer = resultsSpace.findViewById(R.id.second);
        final Button nextButton = resultsSpace.findViewById(R.id.next_button);

        // setting up mcq choices
        final ArrayList<Country> allCountries = (ArrayList<Country>) getArguments().getSerializable("Countries");
        final List<Integer> randomCountries = MainActivity.generateRandomNumbers(0, allCountries.size(), 4);

        final ArrayList<Country> selectedCountries = new ArrayList<>();
        final ArrayList<Button> buttonsForGame = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            selectedCountries.add(allCountries.get(randomCountries.get(i)));
        }

        buttonsForGame.add((Button) mcqChoices.findViewById(R.id.answer1));
        buttonsForGame.add((Button) mcqChoices.findViewById(R.id.answer2));
        buttonsForGame.add((Button) mcqChoices.findViewById(R.id.answer3));
        buttonsForGame.add((Button) mcqChoices.findViewById(R.id.answer4));

        Collections.shuffle(buttonsForGame);

        // setting up the question
        TextView gameTitlePage = view.findViewById(R.id.question_text);
        gameTitlePage.setText("Q" + (questionNo) + "." + " What country is this flag from?");
        final ImageView flag = view.findViewById(R.id.flag_image);
        Glide.with(this).load(selectedCountries.get(0).getFlag()).into(flag);

        // setting up player choices
        int choice = 0;
        for (Button b : buttonsForGame) {
            b.setText(selectedCountries.get(choice).getName());


            if (choice == 0) { // correct answer
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("Correct Answer");
                        mcqSpace.removeView(mcqChoices);
                        mcqSpace.addView(resultsSpace);
                        correct++;

                        resultsOutcome.setText("Correct");
                        resultsOutcome.setTextColor(Color.parseColor("#2ECC71"));
                        resultsAnswer.setText("This correct answer is " + selectedCountries.get(0).getName() + ".");

                    }
                });
            } else { // wrong answer
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("Wrong Answer");
                        mcqSpace.removeView(mcqChoices);
                        mcqSpace.addView(resultsSpace);

                        resultsOutcome.setText("Wrong");
                        resultsOutcome.setTextColor(Color.parseColor("#E74C3C"));
                        resultsAnswer.setText("This is " + selectedCountries.get(0).getName());

                    }
                });
            }
            choice++;
        }

        // recording score into local database
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (questionNo == 10) {

                    new AlertDialog.Builder(getActivity())
                            .setTitle("Game complete!")
                            .setMessage("You got " + correct + " out of 10 correct!")
                            .setPositiveButton("Cool!", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                    ScoreDataBase quizDataBase = Room.databaseBuilder(getActivity(), ScoreDataBase.class, "score.db")
                                            .allowMainThreadQueries()
                                            .build();
                                    Score score = new Score();
                                    score.setScore(correct);
                                    score.setType("country");
                                    quizDataBase.dao().add(score);
                                    getActivity().finish();
                                }
                            })
                            .create().show();

                } else {
                    questionNo++;
                    closeFragment();
                }

            }
        });

        return view;
    }

    private void closeFragment() {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction trans = manager.beginTransaction();
        trans.detach(this);
        trans.attach(this);
        trans.commit();

    }

}

