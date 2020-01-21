package com.example.hamadaelsha3r.intouch.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import com.example.hamadaelsha3r.intouch.Model;

@Database(entities = {Model.class}, version = 1, exportSchema = false)
public abstract class NewsDatabase extends RoomDatabase {
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "favoritelist";
    private static NewsDatabase sInstance;

    public static NewsDatabase getsInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d("DB", "Creating the DB");
                sInstance = Room.databaseBuilder(
                        context.getApplicationContext(),
                        NewsDatabase.class,
                        NewsDatabase.DATABASE_NAME).build();
            }
        }
        Log.d("DB", "Getting DB instance");
        return sInstance;
    }

    public abstract NewsDao newsDao();
}
