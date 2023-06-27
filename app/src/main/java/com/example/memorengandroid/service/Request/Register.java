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

public class Register extends ViewModel {
    MutableLiveData<Boolean> status = new MutableLiveData<>();
    public static String defaultErrorMessage = "Kullanıcı kaydı sırasında hata..";
    ErrorHandlerModel errorHandlerModel = ErrorHandlerModel.getInstance();

    public LiveData<Boolean> getRegisterStatus(String name, String surname, String username, String email, String password) {
        register(name, surname, username, email, password);

        return status;
    }

    private void register(String name, String surname, String username, String email, String password) {
        try {
            errorHandlerModel.setRegisterErrorMessage(null);

            UserAPI userAPI = createTrustAllRetrofit().create(UserAPI.class);

            User user = new User(name, surname, username, email, password);
            RequestBody body = RequestBody.create(MediaType.parse("application/json"), user.toJsonForRegister());

            Call<ResponseBody> call = userAPI.createUser(body);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        Log.i("REGISTER", "RESPONSE : " + response.toString());

                        errorHandlerModel.setRegisterErrorMessage(null);

                        if (response.isSuccessful()) {
                            Gson gson = new Gson();
                            UserResponseModel result = gson.fromJson(response.body().string(), UserResponseModel.class);

                            ResponseBody myResponse = response.body();

                            JsonObject data = result.getData();

                            Log.i("REGISTER", "SUCCESS");

                            System.out.println("REGISTERMODEL STRING: " + myResponse.toString());
                            System.out.println("REGISTERMODEL DATA: " + result.getData());

                            try {
                                String id = String.valueOf(data.get("id"));
                                String email = String.valueOf(data.get("email"));
                                String username = String.valueOf(data.get("username"));
                                String name = String.valueOf(data.get("name"));
                                String surname = String.valueOf(data.get("surname"));

                                System.out.println("REGISTER DATA TYPE: " + data.getClass());
                                System.out.println("REGISTER email: " + email);
                                System.out.println("REGISTER username: " + username);
                                System.out.println("REGISTER name: " + name);
                                System.out.println("REGISTER surname: " + surname);

                                User user = User.getInstance();

                                user.setEmail(email);
                                user.setUsername(username);
                                user.setName(name);
                                user.setSurname(surname);
                                user.setId(id);

                                errorHandlerModel.setRegisterErrorMessage(null);

                                status.setValue(true);
                            } catch (Exception e) {
                                errorHandlerModel.setRegisterErrorMessage(defaultErrorMessage);
                                Log.e("REGISTER", "ERROR0 : " + defaultErrorMessage);

                                status.setValue(false);
                            }
                        } else {
                            errorHandlerModel.setRegisterErrorMessage(defaultErrorMessage);
                            Log.e("REGISTER", "ERROR1 : " + defaultErrorMessage);

                            status.setValue(false);
                        }
                    } catch (Exception e) {
                        errorHandlerModel.setRegisterErrorMessage(defaultErrorMessage);
                        Log.e("REGISTER", "ERROR2 : " + e.getLocalizedMessage());

                        status.setValue(false);
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.e("REGISTER", "ERROR3 : " + t.getLocalizedMessage());
                }
            });
        } catch (Exception e) {
            Log.e("REGISTER", "ERROR4 : " + e.getLocalizedMessage());
        }
    }

    private Retrofit createTrustAllRetrofit() throws NoSuchAlgorithmException, KeyManagementException {
        Gson gson = new GsonBuilder().setLenient().create();

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();

        // Create a trust manager that trusts all certificates
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                // No client certificate validation needed
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                // No server certificate validation needed
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        }};

        // Create a SSL context with the custom trust manager
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustAllCerts, new SecureRandom());

        // Set the custom SSL context to the client builder
        clientBuilder.sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustAllCerts[0]);

        // Allow all hostnames
        clientBuilder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });

        OkHttpClient client = clientBuilder.build();

        return new Retrofit.Builder()
                .baseUrl("https://qa.api.memoreng.helloworldeducation.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}
