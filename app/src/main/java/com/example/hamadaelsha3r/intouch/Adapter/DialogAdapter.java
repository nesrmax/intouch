package com.example.hamadaelsha3r.intouch.Adapter;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hamadaelsha3r.intouch.R;
import com.example.hamadaelsha3r.intouch.Screen.MainScreen;

import java.util.HashMap;

public class DialogAdapter extends RecyclerView.Adapter<DialogAdapter.DialogHolder> {


    Context context;
    HashMap<String, String> hashMap;
    String[] array1;
    public static String code = "eg";


    public DialogAdapter(Context context, HashMap<String, String> hashMap, String[] array1) {
        this.context = context;
        this.hashMap = hashMap;
        this.array1 = array1;
    }

    public class DialogHolder extends RecyclerView.ViewHolder {
        TextView text_country_row;

        public DialogHolder(View itemView) {
            super(itemView);
            text_country_row = itemView.findViewById(R.id.country_row);

        }


    }

    @Override
    public DialogHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_countries, parent, false);
        return new DialogHolder(view);
    }

    @Override
    public void onBindViewHolder(DialogHolder holder, final int position) {
        holder.text_country_row.setText(array1[position]);
        holder.text_country_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code = hashMap.get(array1[position]);
                Intent intent = new Intent(context, MainScreen.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return array1.length;
    }


}