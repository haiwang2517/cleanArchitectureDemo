package com.onenet.datasource.di;

import com.onenet.common.AppConfig;
import com.onenet.datasource.common.HeaderInterceptor;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * RetrofitDiModule
 *
 * @author HaiYinLong
 * @version 2025/05/23 12:02
 **/
@Module
@InstallIn(ViewModelComponent.class)
public class RetrofitDiModule {

    @Provides
    public Retrofit provideRetrofit(AppConfig appConfig) {
        String baseUrl = appConfig.getBaseUrl();
        // 创建日志拦截器
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // 打印请求头 + 请求体
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        // 创建OkHttpClient
        OkHttpClient client =
            new OkHttpClient.Builder().addInterceptor(new HeaderInterceptor()).addInterceptor(logging).build();
        // 创建Retrofit
        return new Retrofit.Builder().baseUrl(baseUrl).client(client).addConverterFactory(GsonConverterFactory.create())
            .build();
    }
}
