package com.example.memorengandroid.service.SSLTrustModel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TrustAllCertificates {
    public static Boolean withGson = true;

    public TrustAllCertificates(Boolean withGson) {
        this.withGson = withGson;
    }

    public static Retrofit createTrustAllRetrofit() throws NoSuchAlgorithmException, KeyManagementException {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();

        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}

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

        if (withGson) {
            Gson gson = new GsonBuilder().setLenient().create();

            return new Retrofit.Builder()
                  .baseUrl("https://uat.api.memoreng.helloworldeducation.com/")
                  .client(client)
                  .addConverterFactory(GsonConverterFactory.create(gson))
                  .build();
        } else {
            return new Retrofit.Builder()
                 .baseUrl("https://uat.api.memoreng.helloworldeducation.com/")
                 .client(client)
                 .addConverterFactory(GsonConverterFactory.create())
                 .build();
        }
    }


}
