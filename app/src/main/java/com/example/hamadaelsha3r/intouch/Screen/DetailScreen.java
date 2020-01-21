package com.example.hamadaelsha3r.intouch.Screen;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hamadaelsha3r.intouch.ContainerMoreDetail;
import com.example.hamadaelsha3r.intouch.Database.AppExecutors;
import com.example.hamadaelsha3r.intouch.Database.NewsDatabase;
import com.example.hamadaelsha3r.intouch.Model;
import com.example.hamadaelsha3r.intouch.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Objects;

public class DetailScreen extends AppCompatActivity {


    public static boolean flag = true;

    Button share_button;

    ImageView poster;
    TextView Title;
    TextView Detail;
    TextView Date;
    CheckBox favoriteCheckBox;


    Intent intent;
    Bundle bundle;
    Model model;


    Menu menu2 = null;
    private AdView mobAd;

    NewsDatabase news1Db;


    public static String NewsId;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailscreen);
        Toolbar toolbar = findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        share_button = findViewById(R.id.share_option);
        favoriteCheckBox = findViewById(R.id.favorite_checkbox);

        intent = getIntent();
        bundle = intent.getExtras();
        model = new Model(
                bundle.getString("id"),
                bundle.getString("src"),
                bundle.getString("title"),
                bundle.getString("description"),
                bundle.getString("url"),
                bundle.getString("date"),
                bundle.getString("img"),
                bundle.getString("content"));
        Title = findViewById(R.id.detail_title);
        Date = findViewById(R.id.date);
        Detail = findViewById(R.id.detail);
        poster = findViewById(R.id.poster);
        AD_show();
        populate_UI();
        news1Db = NewsDatabase.getsInstance(getApplicationContext());
        favoriteCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (favoriteCheckBox.isChecked()) {
                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            news1Db.newsDao().insertNews(model);
                        }
                    });
                    Toast toast = Toast.makeText
                            (DetailScreen.this, "Movie has been added to fav", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            news1Db.newsDao().deleteNews(model);
                        }
                    });
                    Toast toast = Toast.makeText
                            (DetailScreen.this, "Movie has been removed from fav", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        checkIfFav(model.getId());
    }


    private void checkIfFav(String id) {
        LiveData<Model> movie = news1Db.newsDao().loadNews(id);
        movie.observe(this, new Observer<Model>() {
            @Override
            public void onChanged(@Nullable Model movies) {
                if (movies == null)
                    favoriteCheckBox.setChecked(false);
                else favoriteCheckBox.setChecked(true);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main2, menu);
        this.menu2 = menu;

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    TaskStackBuilder.create(this)
                            .addNextIntentWithParentStack(upIntent)
                            .startActivities();
                } else {
                    NavUtils.navigateUpTo(this, upIntent);
                }
                break;
            case R.id.share_option:
                shareContent();
                break;


        }


        return super.onOptionsItemSelected(item);
    }

    public void extend(View view) {
        Intent intent = new Intent(getApplicationContext(), ContainerMoreDetail.class);
        startActivity(intent);

    }

    void populate_UI() {

        Title.setText(bundle.getString("title"));
        Date.setText(bundle.getString("date"));

        if ((bundle.getString("img")).equals("null")) {
            poster.setImageResource(R.drawable.replace_pic);

        } else {
            Picasso.with(this).load(bundle.getString("img")).into(poster);
        }


        if ((bundle.getString("description").equals("null") || bundle.getString("description").isEmpty()) && !bundle.getString("content").equals("null")) {
            Detail.setText(bundle.getString("content").substring(0, bundle.getString("content").length() - 13));


        } else if (!(bundle.getString("description")).equals("null") && bundle.getString("content").equals("null")) {
            Detail.setText(bundle.getString("description"));


        } else if (!bundle.getString("description").equals("null") && !bundle.getString("content").equals("null")) {
            Detail.setText(bundle.getString("description"));
        }
    }


    void AD_show() {
        MobileAds.initialize(getApplicationContext(), getResources().getString(R.string.banner_ad_unit_id));
        mobAd = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mobAd.loadAd(adRequest);
    }


    private void shareContent() {

        Bitmap bitmap = getBitmapFromView(poster);
        try {
            File file = new File(this.getExternalCacheDir(), "logicchip.png");
            FileOutputStream fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            file.setReadable(true, false);
            final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Intent.EXTRA_TEXT, model.getTitle());
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
            intent.setType("image/png");
            startActivity(Intent.createChooser(intent, "Share image via"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private Bitmap getBitmapFromView(View view) {
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            bgDrawable.draw(canvas);
        } else {
            canvas.drawColor(Color.WHITE);
        }
        view.draw(canvas);
        return returnedBitmap;
    }


}

