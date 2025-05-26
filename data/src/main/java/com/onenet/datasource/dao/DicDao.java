package com.onenet.datasource.dao;

import com.onenet.datasource.common.ApiResponse;
import com.onenet.datasource.dto.DicRequest;
import com.onenet.datasource.entity.MergeGiftBoxConfig;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * DicDao
 *
 * @author HaiYinLong
 * @version 2025/05/23 12:12
 **/
public interface DicDao {
    @POST("/service/app/dic/list")
    Call<ApiResponse<MergeGiftBoxConfig>> queryDicByCodes(@Body DicRequest request);
}
