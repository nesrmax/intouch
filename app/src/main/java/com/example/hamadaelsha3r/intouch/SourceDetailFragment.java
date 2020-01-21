package com.example.hamadaelsha3r.intouch;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.hamadaelsha3r.intouch.Adapter.MainAdapter;

public class SourceDetailFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.morenews, container, false);
        WebView webView = view.findViewById(R.id.webview);
        webView.loadUrl(MainAdapter.url);
        return view;
    }
}
