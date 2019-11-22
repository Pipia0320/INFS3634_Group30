package com.project.countryapp.Scores;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ScoreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void add(Score... scores);

    @Query("SELECT * FROM Score WHERE type = (:type)")
    List<Score> queryList(String type);


}
