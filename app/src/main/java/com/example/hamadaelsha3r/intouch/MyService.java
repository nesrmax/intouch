package com.example.hamadaelsha3r.intouch;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

public class MyService extends IntentService {
    public static boolean isNetworkConnected;

    public MyService() {
        super("MyService");
    }


    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        super.onStart(intent, startId);
        if (MyReceiver.isconect) {
            Toast.makeText(getApplicationContext(), R.string.conected, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), R.string.notconnected, Toast.LENGTH_SHORT).show();

        }


    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        isNetworkConnected = extras.getBoolean("isNetworkConnected");
        Log.d(getResources().getString(R.string.conected), String.valueOf(isNetworkConnected));


    }
}


