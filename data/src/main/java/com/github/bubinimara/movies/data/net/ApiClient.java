package com.github.bubinimara.movies.data.net;

import android.support.annotation.VisibleForTesting;
import android.util.Log;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
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
    private final String apiKey;

    public ApiClient(String apiKey) {
        this.apiKey = apiKey;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(buildOkHttp())
                .build();
        apiTmb = retrofit.create(ApiTmb.class);
    }

    @VisibleForTesting
    ApiClient(String apiKey, HttpUrl url) {
        this.apiKey = apiKey;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(buildOkHttp())
                .build();
        apiTmb = retrofit.create(ApiTmb.class);
    }

    private OkHttpClient buildOkHttp(){
        return new OkHttpClient.Builder()
                .addInterceptor(new LogInterceptor())
                .addInterceptor(new AuthInterceptor(apiKey))
                .build();
    }

    public ApiTmb getApiTmb() {
        return apiTmb;
    }

    private static class AuthInterceptor implements Interceptor {
        public static final String NAME= "api_key";
        private final String apiKey;

        public AuthInterceptor(String apiKey) {
            this.apiKey = apiKey;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            HttpUrl url = request.url().newBuilder().addQueryParameter(NAME,apiKey).build();
            request = request.newBuilder().url(url).build();
            return chain.proceed(request);
        }
    }

    private static class LogInterceptor implements Interceptor{

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            //Log.d(TAG, "intercept: "+request.url().toString());
            Response response = chain.proceed(request);
            return response;
        }
    }
}
