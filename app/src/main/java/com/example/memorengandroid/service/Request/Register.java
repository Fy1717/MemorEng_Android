package com.example.memorengandroid.service.Request;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.memorengandroid.model.User;
import com.example.memorengandroid.service.ApiInterface.UserAPI;
import com.example.memorengandroid.service.ApiModel.ErrorHandlerModel;
import com.example.memorengandroid.service.ApiModel.UserResponseModel;
import com.example.memorengandroid.service.SSLTrustModel.TrustAllCertificates;
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

            UserAPI userAPI = new TrustAllCertificates(true).createTrustAllRetrofit()
                    .create(UserAPI.class);

            User user = new User(name, surname, username, email, password);
            RequestBody body = RequestBody.create(MediaType.parse("application/json"), user.toJsonForRegister());

            Call<ResponseBody> call = userAPI.Register(body);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        Log.i("REGISTER", "RESPONSE : " + response.toString());

                        errorHandlerModel.setRegisterErrorMessage(null);
                        Gson gson = new Gson();

                        if (response.isSuccessful()) {
                            UserResponseModel result = gson.fromJson(response.body().string(), UserResponseModel.class);

                            ResponseBody myResponse = response.body();

                            JsonObject data = result.getData();

                            Log.i("REGISTER", "SUCCESS");

                            try {
                                String id = String.valueOf(data.get("id"));
                                String email = String.valueOf(data.get("email"));
                                String username = String.valueOf(data.get("username"));
                                String name = String.valueOf(data.get("name"));
                                String surname = String.valueOf(data.get("surname"));

                                Log.i("REGISTER", "email: " + email);
                                Log.i("REGISTER", "username: " + username);
                                Log.i("REGISTER", "name: " + name);
                                Log.i("REGISTER", "surname: " + surname);

                                User user = User.getInstance();

                                user.setEmail(email);
                                user.setUsername(username);
                                user.setName(name);
                                user.setSurname(surname);
                                user.setId(id);

                                errorHandlerModel.setRegisterErrorMessage(null);

                                status.setValue(true);
                            } catch (Exception e) {
                                Log.e("REGISTER", "ERROR0 : " + defaultErrorMessage);
                                errorHandlerModel.setRegisterErrorMessage(defaultErrorMessage);

                                status.setValue(false);
                            }
                        } else {
                            ResponseBody myResponseErrorBody = response.errorBody();

                            Log.e("REGISTER", "ERROR11 : " + myResponseErrorBody);

                            JsonObject errorResult = gson.fromJson(myResponseErrorBody.string(), JsonObject.class);

                            JsonArray errorData = (JsonArray) errorResult.get("errors");

                            Log.e("REGISTER", "ERROR12 : " + errorData.toString());

                            errorHandlerModel.setRegisterErrorMessage(errorData.get(0).toString().replaceAll("\"", ""));

                            status.setValue(false);
                        }
                    } catch (Exception e) {
                        Log.e("REGISTER", "ERROR2 : " + e.getLocalizedMessage());
                        errorHandlerModel.setRegisterErrorMessage(defaultErrorMessage);

                        status.setValue(false);
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.e("REGISTER", "ERROR3 : " + t.getLocalizedMessage());
                    errorHandlerModel.setRegisterErrorMessage("Bağlantı problemi..");

                    status.setValue(false);
                }
            });
        } catch (Exception e) {
            Log.e("REGISTER", "ERROR4 : " + e.getLocalizedMessage());

            status.setValue(false);
        }
    }
}
