package com.example.noteapp.api;

import android.app.Activity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.noteapp.api.data.Articles;
import com.example.noteapp.api.data.Content;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiCall {

    private String baseUrl = "https://note.com/";
    private String[] from = {"name","publishAt"};
    private int[] to = {android.R.id.text1,android.R.id.text2};

    //API実行メソッド
    public void api_execution(String creator_id, ListView lv_article, Activity activity) {

        //Retrofitインスタンスを生成
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //APIインスタンスを生成
        NoteApiInterface service = retrofit.create(NoteApiInterface.class);

        //Callインスタンスを生成
        Call<Articles> call = service.API(creator_id,"note",1);

        //非同期処理でAPIを呼び出し→レスポンス処理
        call.enqueue(new Callback<Articles>() {

            //リクエスト成功時
            @Override
            public void onResponse(Call<Articles> call, Response<Articles> response) {

                Log.i("message", "Successed to request");

                //List<Content>型を取得
                Articles articles = response.body();
                List<Content> contents = articles.getData().getContents();

                List<Map<String,String>> list_article = new ArrayList<>();

                //List<Content>型のデータをある分だけ繰り返し
                //Map<String,String>に一記事分の各情報を追加
                //List<Map<String,String>>に追加
                for(Content content: contents) {

                    Map<String,String> article = new HashMap<>();
                    article.put("name",content.getName());
                    article.put("publishAt",content.getPublishAt().substring(0,10));
                    article.put("noteUrl",content.getNoteUrl());
                    list_article.add(article);
                }

                //Adapter生成
                SimpleAdapter adapter = new SimpleAdapter(activity,list_article, android.R.layout.simple_list_item_2,from,to);

                //ListViewにAdapter設定
                lv_article.setAdapter(adapter);

            }

            //リクエスト失敗時
            @Override
            public void onFailure(Call<Articles> call, Throwable t) {

                Log.i("message", t.toString());
            }
        });

    }
}
