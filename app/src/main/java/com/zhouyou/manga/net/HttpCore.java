package com.zhouyou.manga.net;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author: ZhouYou
 * Date: 2018/7/28.
 * 网络请求基础配置类
 */
public class HttpCore {

    private static final String TAG = "HttpCore";
    private static final int DEFAULT_TIME_OUT = 15;
    private static final int DEFAULT_READ_TIME_OUT = 15;

    private Retrofit mRetrofit;

    private Retrofit getRetrofit() {
        if (mRetrofit == null) {
            synchronized (HttpCore.this) {
                if (mRetrofit == null) {
                    createRetrofit();
                }
            }
        }
        return mRetrofit;
    }

    private void createRetrofit() {
        OkHttpClient mOkHttpClient = createClient();
        mRetrofit = new Retrofit.Builder()
                .client(mOkHttpClient)
                .baseUrl(UrlConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private OkHttpClient createClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);
        builder.writeTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);
        builder.readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);
        return builder.build();
    }

    public <T> T getService(Class<T> serviceClass) {
        return getRetrofit().create(serviceClass);
    }
}
