package com.example.hamadaelsha3r.intouch.Screen;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.example.hamadaelsha3r.intouch.Adapter.DialogAdapter;
import com.example.hamadaelsha3r.intouch.Adapter.MainAdapter;
import com.example.hamadaelsha3r.intouch.Database.NewsDatabase;
import com.example.hamadaelsha3r.intouch.IntouchViewModel;
import com.example.hamadaelsha3r.intouch.Model;
import com.example.hamadaelsha3r.intouch.MyReceiver;
import com.example.hamadaelsha3r.intouch.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.fabric.sdk.android.Fabric;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class MainScreen extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {


    SwipeRefreshLayout mSwipeRefreshLayout;

    Dialog dialog;

    Toolbar toolbar;

    Menu menu11 = null;

    ArrayList<Model> List_Of_news = new ArrayList<Model>();

    Intent intentservice;

    HashMap<String, String> hashMap;

    RecyclerView Recycler_Of_News;

    RecyclerView Recycler_Of_Couuntries;

    String[] Counter;

    String Categtry = "";

    private NewsDatabase newsDb;

    int x;
    String id;


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainscreen);
        intentservice = new Intent(getApplicationContext(), MyReceiver.class);

        Country_Dialog();

        hashMap = new HashMap<String, String>();
        Recycler_Of_News = findViewById(R.id.recycle_of_news);
        Recycler_Of_News.setEnabled(true);
        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), calculateBestSpanCount(300));
        Recycler_Of_News.setHasFixedSize(true);
        Recycler_Of_News.setLayoutManager(gridLayoutManager);

        mSwipeRefreshLayout = findViewById(R.id.swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        Fabric.with(this, new Crashlytics());
        Getcountry();


        if (savedInstanceState == null) {
            GetJson(DialogAdapter.code, Categtry, getResources().getString(R.string.Key));
        } else {
            GetJson(DialogAdapter.code, Categtry, getResources().getString(R.string.Key));
        }

        newsDb = NewsDatabase.getsInstance(getApplicationContext());

    }


    private int calculateBestSpanCount(int posterWidth) {
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float screenWidth = outMetrics.widthPixels;
        return Math.round(screenWidth / posterWidth);
    }


    void Categry_Dialog() {

        AlertDialog.Builder builderSingle = new AlertDialog.Builder(MainScreen.this);
        builderSingle.setTitle(getApplicationContext().getString(R.string.SelectACategtry));

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainScreen.this
                , android.R.layout.select_dialog_singlechoice);

        arrayAdapter.add(getApplicationContext().getString(R.string.business));
        arrayAdapter.add(getApplicationContext().getString(R.string.entertainment));
        arrayAdapter.add(getApplicationContext().getString(R.string.technology));
        arrayAdapter.add(getApplicationContext().getString(R.string.sports));
        arrayAdapter.add(getApplicationContext().getString(R.string.science));
        arrayAdapter.add(getApplicationContext().getString(R.string.health));


        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (which == 0) {
                    Categtry = getApplicationContext().getString(R.string.business);
                    GetJson(DialogAdapter.code, Categtry, getResources().getString(R.string.Key));

                } else if (which == 1) {
                    Categtry = getApplicationContext().getString(R.string.entertainment);
                    GetJson(DialogAdapter.code, Categtry, getResources().getString(R.string.Key));

                } else if (which == 2) {
                    Categtry = getApplicationContext().getString(R.string.technology);
                    GetJson(DialogAdapter.code, Categtry, getResources().getString(R.string.Key));

                } else if (which == 3) {
                    Categtry = getApplicationContext().getString(R.string.sports);
                    GetJson(DialogAdapter.code, Categtry, getResources().getString(R.string.Key));

                } else if (which == 4) {
                    Categtry = getApplicationContext().getString(R.string.science);
                    GetJson(DialogAdapter.code, Categtry, getResources().getString(R.string.Key));

                } else if (which == 5) {
                    Categtry = getApplicationContext().getString(R.string.health);
                    GetJson(DialogAdapter.code, Categtry, getResources().getString(R.string.Key));

                }
            }
        });
        builderSingle.show();


    }

    void Country_Dialog() {

        dialog = new Dialog(MainScreen.this);
        dialog.requestWindowFeature(Window.FEATURE_CONTEXT_MENU);
        dialog.setTitle(getApplicationContext().getString(R.string.counrties));
        dialog.setContentView(R.layout.recycle_country_dialog);
        Recycler_Of_Couuntries = dialog.findViewById(R.id.recycyle_of_countries);
        Recycler_Of_Couuntries.setEnabled(true);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(dialog.getContext(), LinearLayout.VERTICAL, false);
        Recycler_Of_Couuntries.setLayoutManager(layoutManager1);
        Recycler_Of_Couuntries.setHasFixedSize(true);
    }

    private void showFavorites() {

/*
        final LiveData<List<Model>> movie = newsDb.newsDao().loadAllnews();
*/

        IntouchViewModel viewModel = ViewModelProviders.of(this).get(IntouchViewModel.class);
        viewModel.getMod_news().observe(this, new Observer<List<Model>>() {
            @Override
            public void onChanged(@Nullable List<Model> news1) {
                Log.d("DB", "DB CHANGED");
                if (news1 != null && news1.size() != 0) {
                    MainAdapter adabter = new MainAdapter(MainScreen.this, news1);
                    Log.v("salah","update list from view model");
                    Recycler_Of_News.setAdapter(adabter);
                } else {
                    Toast toast = Toast.makeText
                            (MainScreen.this, getApplicationContext().getString(R.string.There_is_no_favorite_movies_to_show),
                                    Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
    }

    void reload() {

        finish();
        startActivity(getIntent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main1, menu);
        this.menu11 = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.favorite_Screen_button:

                Intent intent = new Intent(getApplicationContext(), FavouriteScreen.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
              /*  if (item.isChecked()) {
                    item.setChecked(false);
                    item.setIcon(R.drawable.ic_favorite_border_black_24dp);
                    reload();
                } else {
                    showFavorites();
                    item.setChecked(true);
                    item.setIcon(R.drawable.ic_favorite_blllue_24dp);
                }*/
                break;

            case R.id.country_dialog_option:
                dialog.show();
                break;

            case R.id.catogery_dialog_option:
                Categry_Dialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    void Getcountry() {
        try {
            JSONArray jsonArray = new JSONArray(streams());
            Counter = new String[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Counter[i] = jsonObject.getString("name");
                hashMap.put(jsonObject.getString("name"), jsonObject.getString("code"));

            }
            DialogAdapter adapter = new DialogAdapter(getApplicationContext(), hashMap, Counter);
            Recycler_Of_Couuntries.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sendBroadcast(intentservice);
                GetJson(DialogAdapter.code, Categtry, getResources().getString(R.string.Key));
                mSwipeRefreshLayout.setRefreshing(false);

            }
        }, 2000);

        Toast.makeText(this, R.string.refresh, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }


    public String streams() {
        InputStream stream = getResources().openRawResource(R.raw.countries);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
            int x;
            while ((x = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, x);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String jsonString = writer.toString();
        return jsonString;

    }

    public interface GetData {
        @GET("top-headlines")
        Call<ResponseBody> TopHeadLine(@Query("country") String ccuntry, @Query("category") String category
                , @Query("apiKey") String apikey);
    }


    void GetJson(String counntry, String category, String apiKey) {
        List_Of_news.clear();
        Recycler_Of_News.getRecycledViewPool().clear();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(getResources().getString(R.string.baseurl)).build();
        final GetData getData = retrofit.create(GetData.class);
        Call<ResponseBody> call = getData.TopHeadLine(counntry, category, apiKey);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(response.body().string());
                    JSONArray jsonArray = jsonObject.getJSONArray("articles");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        x = i;
                        id = Integer.toString(x);

                        JSONObject object = jsonArray.getJSONObject(i);
                        JSONObject object1 = object.getJSONObject("source");
                        String srcname = object1.optString("name");
                        String Title = object.optString("title");
                        String Description = object.optString("description");
                        String url = object.optString("url");
                        String img = object.optString("urlToImage");
                        String Date = object.optString("publishedAt");
                        String conten = object.optString("content");

                        List_Of_news.add(new Model(id, srcname, Title, Description, url, Date, img, conten));
                    }

                    MainAdapter mainAdapter = new MainAdapter(getApplicationContext(), List_Of_news);
                    mainAdapter.notifyDataSetChanged();
                    Recycler_Of_News.setAdapter(mainAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }


}
