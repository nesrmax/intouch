package com.example.hamadaelsha3r.intouch.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hamadaelsha3r.intouch.Model;
import com.example.hamadaelsha3r.intouch.R;
import com.example.hamadaelsha3r.intouch.Screen.DetailScreen;
import com.squareup.picasso.Picasso;

import java.util.List;


public class MainAdapter extends RecyclerView.Adapter<MainAdapter.NewsHolder> {


    public static List<Model> the_list_of_news;
    Context context;
    public static String detail;
    public static String url;
    public static String poster_url;
    public static String Title;

    public MainAdapter(Context context, List<Model> movieList) {
        this.context = context;
        the_list_of_news = movieList;
    }

    public static class NewsHolder extends RecyclerView.ViewHolder {
        ImageView poster;
        TextView the_title_text;
        TextView the_source_text;

        public NewsHolder(View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.favo_poster);
            the_title_text = itemView.findViewById(R.id.title);
            the_source_text = itemView.findViewById(R.id.source);
        }

    }

    @Override
    public NewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_news, parent, false);
        return new NewsHolder(view);
    }

    @Override
    public void onBindViewHolder(final NewsHolder holder, final int position) {
        final String img = the_list_of_news.get(position).getImg();

        if (img.equals("null")) {
            holder.poster.setImageResource(R.drawable.replace_pic);
        } else {
            Picasso.with(context).load(the_list_of_news.get(position).getImg()).into(holder.poster);
        }
        holder.the_title_text.setText(the_list_of_news.get(position).getTitle());
        holder.the_source_text.setText(the_list_of_news.get(position).getSrcName());
        holder.poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((the_list_of_news.get(position).getDescription().equals("null") || the_list_of_news.get(position).getDescription().isEmpty()) && !the_list_of_news.get(position).getContent().equals("null")) {
                    detail = "";
                    detail = the_list_of_news.get(position).getContent();

                } else if (!the_list_of_news.get(position).getDescription().equals("null")) {
                    detail = "";
                    detail = the_list_of_news.get(position).getDescription();


                } else if (the_list_of_news.get(position).getDescription().equals("null") && the_list_of_news.get(position).getContent().equals("null")) {
                    detail = "";


                    detail = the_list_of_news.get(position).getUrl();
                }

                Intent intent = new Intent(context, DetailScreen.class);

                Title = the_list_of_news.get(position).getTitle();
                poster_url = the_list_of_news.get(position).getImg();
                if (the_list_of_news.get(position).getDescription().isEmpty()) {
                    intent.putExtra("description", "");

                } else {

                    intent.putExtra("description", the_list_of_news.get(position).getDescription());
                }
                intent.putExtra("url", the_list_of_news.get(position).getUrl());
                intent.putExtra("title", the_list_of_news.get(position).getTitle());
                intent.putExtra("date", the_list_of_news.get(position).getDate());
                intent.putExtra("img", the_list_of_news.get(position).getImg());
                intent.putExtra("content", the_list_of_news.get(position).getContent());
                intent.putExtra("src", the_list_of_news.get(position).getSrcName());
                intent.putExtra("id", the_list_of_news.get(position).getId());

                url = the_list_of_news.get(position).getUrl();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);


            }

        });
    }


    @Override
    public int getItemCount() {
        return the_list_of_news.size();
    }


}


