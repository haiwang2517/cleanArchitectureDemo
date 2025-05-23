package com.onenet.mytravelworld.data.dao;

import com.onenet.mytravelworld.data.common.ApiResponse;
import com.onenet.mytravelworld.data.dto.DicRequest;
import com.onenet.mytravelworld.data.entity.MergeGiftBoxConfig;

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
