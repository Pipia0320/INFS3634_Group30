package com.project.countryapp.Scores;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = { Score.class}, version=1,exportSchema = false)
public abstract class ScoreDataBase extends RoomDatabase {

    public abstract ScoreDao dao();
}