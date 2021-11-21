package com.example.noteapp.api;

import com.example.noteapp.api.data.Articles;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NoteApiInterface {

    @GET("api/v2/creators/{creator}/contents")
    Call<Articles> API(@Path("creator") String creator,
                       @Query("kind") String kind, @Query("page") int page);
}
