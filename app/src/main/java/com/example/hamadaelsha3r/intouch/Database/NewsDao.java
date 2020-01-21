package com.example.hamadaelsha3r.intouch.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.hamadaelsha3r.intouch.Model;

import java.util.List;

@Dao
public interface NewsDao {

    @Query("SELECT * FROM newss")
    LiveData<List<Model>> loadAllnews();

    @Query("SELECT * FROM newss WHERE id = :id")
    LiveData<Model> loadNews(String id);

    @Insert
    void insertNews(Model news);

    @Delete
    void deleteNews(Model news);
}
