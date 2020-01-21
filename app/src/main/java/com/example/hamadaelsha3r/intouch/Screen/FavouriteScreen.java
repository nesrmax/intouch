package com.example.hamadaelsha3r.intouch.Screen;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.widget.Toast;

import com.example.hamadaelsha3r.intouch.Adapter.FavouriteAdapter;
import com.example.hamadaelsha3r.intouch.IntouchViewModel;
import com.example.hamadaelsha3r.intouch.Model;
import com.example.hamadaelsha3r.intouch.R;

import java.util.List;

public class FavouriteScreen extends AppCompatActivity {

    RecyclerView FavoutieList;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favoritescreen);

        FavoutieList = findViewById(R.id.favList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), calculateBestSpanCount(300));
        FavoutieList.setLayoutManager(layoutManager);
        FavoutieList.setEnabled(true);
        FavoutieList.setHasFixedSize(true);

        showFavorites();
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private int calculateBestSpanCount(int posterWidth) {
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float screenWidth = outMetrics.widthPixels;
        return Math.round(screenWidth / posterWidth);
    }


    private void showFavorites() {

        IntouchViewModel viewModel = ViewModelProviders.of(this).get(IntouchViewModel.class);
        viewModel.getMod_news().observe(this, new Observer<List<Model>>() {
            @Override
            public void onChanged(@Nullable List<Model> news1) {
                Log.d("DB", "DB CHANGED");
                if (news1 != null && news1.size() != 0) {
                    FavouriteAdapter adabter = new FavouriteAdapter(FavouriteScreen.this, news1);
                    Log.v("salah", "update list from view model");
                    FavoutieList.setAdapter(adabter);
                } else {
                    Toast toast = Toast.makeText
                            (FavouriteScreen.this, getApplicationContext()
                                            .getString(R.string.There_is_no_favorite_movies_to_show),
                                    Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
    }
}


