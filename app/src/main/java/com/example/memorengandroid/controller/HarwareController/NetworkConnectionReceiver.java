package com.example.memorengandroid.controller.HarwareController;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class NetworkConnectionReceiver extends BroadcastReceiver {
    public static boolean isConnectedToInternet(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();

            if (info != null) {
                for (NetworkInfo networkInfo : info) {
                    if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(!isConnectedToInternet(context)) {
            try {
                Log.i("NetworkReceiver", "NetworkConnectionReceiver received");
            } catch (Exception e) {
                Log.e("NetworkReceiver", "Exception : " + e.getMessage());
            }
        }
    }
}
