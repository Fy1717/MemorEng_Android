package com.example.memorengandroid.service.Request;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.memorengandroid.model.User;
import com.example.memorengandroid.service.ApiInterface.UserAPI;
import com.example.memorengandroid.service.ApiModel.ErrorHandlerModel;
import com.example.memorengandroid.service.ApiModel.UserResponseModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends ViewModel {
    MutableLiveData<Boolean> status = new MutableLiveData<>();
    public static String defaultErrorMessage = "Kullanıcı girişi sırasında hata..";
    ErrorHandlerModel errorHandlerModel = ErrorHandlerModel.getInstance();

    public LiveData<Boolean> getLoginStatus(String email, String password) {
        login(email, password);

        return status;
    }

    private void login(String email, String password) {
        try {
            errorHandlerModel.setLoginErrorMessage(null);

            UserAPI userAPI = createTrustAllRetrofit().create(UserAPI.class);

            User user = new User(email, password);
            RequestBody body = RequestBody.create(MediaType.parse("application/json"), user.toJsonForLogin());

            Call<ResponseBody> call = userAPI.Login(body);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        Log.i("LOGIN", "RESPONSE : " + response.toString());

                        errorHandlerModel.setLoginErrorMessage(null);

                        Gson gson = new Gson();

                        if (response.isSuccessful()) {
                            ResponseBody myResponseBody = response.body();

                            Log.i("LOGIN", "RESPONSE BODY: " + myResponseBody);

                            UserResponseModel result = gson.fromJson(myResponseBody.string(), UserResponseModel.class);

                            JsonObject data = result.getData();

                            Log.i("LOGIN", "SUCCESS");

                            System.out.println("LOGINMODEL STRING: " + myResponseBody.toString());
                            System.out.println("LOGINMODEL DATA: " + result.getData());

                            try {
                                String accessToken = String.valueOf(data.get("accessToken")).replaceAll("\"", "");
                                String accessTokenExpiration = String.valueOf(data.get("accessTokenExpiration")).replaceAll("\"", "");
                                String refreshTokenExpiration = String.valueOf(data.get("refreshTokenExpiration")).replaceAll("\"", "");
                                String refreshToken = String.valueOf(data.get("refreshToken")).replaceAll("\"", "");

                                System.out.println("LOGIN DATA TYPE: " + data.getClass());

                                System.out.println("LOGIN accessToken: " + accessToken);
                                System.out.println("LOGIN accessTokenExpiration: " + accessTokenExpiration);
                                System.out.println("LOGIN refreshToken: " + refreshToken);
                                System.out.println("LOGIN refreshTokenExpiration: " + refreshTokenExpiration);

                                User user = User.getInstance();

                                user.setRefreshToken(refreshToken);
                                user.setAccessToken(accessToken);
                                user.setAccessTokenExpiration(accessTokenExpiration);
                                user.setRefreshTokenExpiration(refreshTokenExpiration);

                                errorHandlerModel.setLoginErrorMessage(null);

                                status.setValue(true);
                            } catch (Exception e) {
                                errorHandlerModel.setLoginErrorMessage(defaultErrorMessage);
                                Log.e("LOGIN", "ERROR0 : " + defaultErrorMessage);

                                status.setValue(false);
                            }
                        } else {
                            ResponseBody myResponseErrorBody = response.errorBody();

                            Log.e("LOGIN", "ERROR11 : " + myResponseErrorBody);

                            JsonObject errorResult = gson.fromJson(myResponseErrorBody.string(), JsonObject.class);

                            JsonArray errorData = (JsonArray) errorResult.get("errors");

                            Log.e("LOGIN", "ERROR12 : " + errorData.toString());

                            errorHandlerModel.setLoginErrorMessage(errorData.get(0).toString().replaceAll("\"", ""));

                            status.setValue(false);
                        }
                    } catch (Exception e) {
                        errorHandlerModel.setLoginErrorMessage(defaultErrorMessage);
                        Log.e("LOGIN", "ERROR2 : " + e.getLocalizedMessage());

                        status.setValue(false);
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.e("LOGIN", "ERROR3 : " + t.getLocalizedMessage());

                    errorHandlerModel.setLoginErrorMessage("Bağlantı Problemi..");

                    status.setValue(false);
                }
            });
        } catch (Exception e) {
            Log.e("LOGIN", "ERROR4 : " + e.getLocalizedMessage());

            errorHandlerModel.setLoginErrorMessage(defaultErrorMessage);

            status.setValue(false);
        }
    }

    private Retrofit createTrustAllRetrofit() throws NoSuchAlgorithmException, KeyManagementException {
        Gson gson = new GsonBuilder().setLenient().create();

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();

        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        }};

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustAllCerts, new SecureRandom());

        clientBuilder.sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustAllCerts[0]);

        clientBuilder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });

        OkHttpClient client = clientBuilder.build();

        return new Retrofit.Builder()
                .baseUrl("https://uat.api.memoreng.helloworldeducation.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}
