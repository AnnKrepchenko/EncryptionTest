package encrypt.test.com.encryptiontext.net;


import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import encrypt.test.com.encryptiontext.MainActivity;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class NetManager {

    private static final String TAG = NetManager.class.getSimpleName();


    private final static String BASE_URL = "http://requestb.in/";
    private final API api;

    public NetManager() {
        api = initRetrofit();
    }

    private API initRetrofit() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        OkHttpClient build = new OkHttpClient.Builder()
                .writeTimeout(8, TimeUnit.SECONDS)
                .readTimeout(8, TimeUnit.SECONDS)
                .connectTimeout(8, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(build)
                .build();

        return retrofit.create(API.class);
    }

    public void sendEncryptedString(String text){
        Call<String> responseCall = api.sendEncryptedString(text);
        responseCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    Log.d(TAG, response.message());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d(TAG, "error = " + t.getMessage());
            }
        });
    }
}
