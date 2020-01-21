package com.example.hamadaelsha3r.intouch;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.hamadaelsha3r.intouch.Database.NewsDatabase;

import java.util.List;

public class IntouchViewModel extends AndroidViewModel {

    private LiveData<List<Model>> mod_news;

    public IntouchViewModel(@NonNull Application application) {
        super(application);

        NewsDatabase mod_newsDb = NewsDatabase.getsInstance(this.getApplication());

        Log.v("salah","Actively retrive database ");

        mod_news = mod_newsDb.newsDao().loadAllnews();


    }


    public LiveData<List<Model>> getMod_news() {
        return mod_news;
    }
}
