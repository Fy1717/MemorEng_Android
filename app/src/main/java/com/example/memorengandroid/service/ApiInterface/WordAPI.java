package com.example.memorengandroid.service.ApiInterface;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface WordAPI {
    @GET("api/english")
    Call<ResponseBody> getEnlishWords(@Header("Authorization") String authorization);
}
