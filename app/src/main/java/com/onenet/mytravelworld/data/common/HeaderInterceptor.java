package com.onenet.mytravelworld.data.common;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * HeaderInterceptor
 *
 * @author HaiYinLong
 * @version 2025/05/23 14:57
 **/

public class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        // 添加公共 Header
        Request newRequest = originalRequest.newBuilder().header("pkg", "com.merge.moto.idletycoon.rider")
            .header("pvc", "7").header("svc", "1").method(originalRequest.method(), originalRequest.body()).build();

        return chain.proceed(newRequest);
    }
}
