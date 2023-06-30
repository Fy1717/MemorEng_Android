package com.example.memorengandroid.service.ApiInterface;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserAPI {
    @Headers("Content-Type: application/json")
    @POST("api/user/createuser")
    Call<ResponseBody> Register(@Body RequestBody body);

    @Headers("Content-Type: application/json")
    @POST("api/auth/createtoken")
    Call<ResponseBody> Login(@Body RequestBody body);

    @Headers("Content-Type: application/json")
    @POST("api/auth/createtokenbyclient")
    Call<ResponseBody> LoginAnonymous(@Body RequestBody body);

    @Headers("Content-Type: application/json")
    @POST("api/auth/revokerefreshtoken")
    Call<ResponseBody> Logout(@Body RequestBody body);

}
