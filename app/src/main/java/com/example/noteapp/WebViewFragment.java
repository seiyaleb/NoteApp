package com.example.noteapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WebViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WebViewFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ProgressBar progressBar;

    public WebViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WebViewFragment newInstance(String param1, String param2) {
        WebViewFragment fragment = new WebViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_web_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progressBar);

        //Bundle経由でnoteUrlを取得
        Bundle bundle = getArguments();
        String noteUrl = bundle.getString("noteUrl");

        //note記事のWebページで表示
        WebView wv_note = view.findViewById(R.id.wv_note);
        wv_note.setWebViewClient(new WebViewClient());
        wv_note.loadUrl(noteUrl);

        //WebViewのJavaScriptを有効へ
        WebSettings webSettings = wv_note.getSettings();
        webSettings.setJavaScriptEnabled(true);

        //WebViewの進捗を表示
        wv_note.setWebViewClient(new MyWebViewClient());
        wv_note.setWebChromeClient(new MyWebChromeClient());

        //戻りボタンを押した際のリスナー設定
        Button bt_return = view.findViewById(R.id.bt_return);
        bt_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //リスト表示フラグメントに置き換え
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                ListFragment listFragment = new ListFragment();
                transaction.replace(R.id.container,listFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, android.graphics.Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

            //読み込み開始時にプログレスバー表示
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            //読み込み完了時にプログレスバー非表示
            progressBar.setVisibility(View.GONE);
        }
    }

    protected class MyWebChromeClient extends WebChromeClient {
        public void onProgressChanged(WebView view, int progress) {

            //プログレスバーの進捗を更新
            progressBar.setProgress(progress);
        }
    }
}