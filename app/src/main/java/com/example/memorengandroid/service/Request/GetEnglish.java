package com.example.memorengandroid.service.Request;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.memorengandroid.model.User;
import com.example.memorengandroid.service.ApiInterface.UserAPI;
import com.example.memorengandroid.service.ApiInterface.WordAPI;
import com.example.memorengandroid.service.ApiModel.ErrorHandlerModel;
import com.example.memorengandroid.service.ApiModel.ResponseModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

public class GetEnglish extends ViewModel {
    MutableLiveData<Boolean> status = new MutableLiveData<>();
    public static String defaultErrorMessage = "Kelimelerin listelenmesinde hata..";
    ErrorHandlerModel errorHandlerModel = ErrorHandlerModel.getInstance();

    public LiveData<Boolean> getEnglishStatus() {
        getEnglish();

        return status;
    }

    private void getEnglish() {
        try {
            errorHandlerModel.setGetEnglishWordsErrorMessage(null);

            WordAPI wordAPI = createTrustAllRetrofit().create(WordAPI.class);

            User user = User.getInstance();

            RequestBody body = RequestBody.create(MediaType.parse("text/plain"), "");

            String accessToken = "Bearer " + user.getAccessToken();

            Call<ResponseBody> call = wordAPI.getEnlishWords(accessToken);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        Log.i("ENGLISH", "RESPONSE : " + response.toString());

                        errorHandlerModel.setGetEnglishWordsErrorMessage(null);

                        if (response.isSuccessful()) {
                            Gson gson = new Gson();
                            ResponseModel result = gson.fromJson(response.body().string(), ResponseModel.class);

                            ResponseBody myResponse = response.body();

                            JsonObject data = result.getData();

                            Log.i("ENGLISH", "SUCCESS");

                            System.out.println("ENGLISHMODEL STRING: " + myResponse.toString());
                            System.out.println("ENGLISHMODEL DATA: " + result.getData());

                            try {
                                errorHandlerModel.setGetEnglishWordsErrorMessage(null);

                                status.setValue(true);
                            } catch (Exception e) {
                                errorHandlerModel.setGetEnglishWordsErrorMessage(defaultErrorMessage);
                                Log.e("ENGLISH", "ERROR0 : " + defaultErrorMessage);

                                status.setValue(false);
                            }
                        } else {
                            errorHandlerModel.setGetEnglishWordsErrorMessage(defaultErrorMessage);
                            Log.e("ENGLISH", "ERROR1 : " + defaultErrorMessage);

                            status.setValue(false);
                        }
                    } catch (Exception e) {
                        errorHandlerModel.setGetEnglishWordsErrorMessage(defaultErrorMessage);
                        Log.e("ENGLISH", "ERROR2 : " + e.getLocalizedMessage());

                        status.setValue(false);
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.e("ENGLISH", "ERROR3 : " + t.getLocalizedMessage());

                    errorHandlerModel.setGetEnglishWordsErrorMessage(defaultErrorMessage);

                }
            });
        } catch (Exception e) {
            Log.e("ENGLISH", "ERROR4 : " + e.getLocalizedMessage());

            errorHandlerModel.setGetEnglishWordsErrorMessage(defaultErrorMessage);
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
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
