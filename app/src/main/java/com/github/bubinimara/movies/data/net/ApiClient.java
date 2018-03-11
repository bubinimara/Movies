package com.github.bubinimara.movies.data.net;

import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by davide.
 */

public class ApiClient {
    public static final String TAG = ApiClient.class.getSimpleName();
    private ApiTmb apiTmb;

    public ApiClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(buildOkHttp())
                .build();
        apiTmb = retrofit.create(ApiTmb.class);
    }

    private OkHttpClient buildOkHttp(){
        // TODO: Add interceptor for apy-key and language
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(
                chain -> {
                    Request request = chain.request();
                    Log.d(TAG, "intercept: "+ chain.request().url().toString());
                    Response response = chain.proceed(request);
                    return response;
                }).build();
        return client;
    }
    public ApiTmb getApiTmb() {
        return apiTmb;
    }
}
