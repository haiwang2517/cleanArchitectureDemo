package com.onenet.domain.repository;

import java.util.List;

import com.google.gson.Gson;
import com.onenet.datasource.common.ApiResponse;
import com.onenet.datasource.dao.DicDao;
import com.onenet.datasource.dto.DicRequest;
import com.onenet.datasource.entity.DicGiftBoxConfigEntity;
import com.onenet.datasource.entity.MergeGiftBoxConfig;
import com.onenet.domain.Constants;

import android.util.Log;
import jakarta.inject.Inject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * DictionaryRepository
 *
 * @author HaiYinLong
 * @version 2025/05/22 17:52
 **/
public class DictionaryRepository {
    private DicDao dicDao;

    @Inject
    public DictionaryRepository(DicDao dicDao) {
        this.dicDao = dicDao;
    }

    public DictionaryRepository() {}

    public void queryGiftBoxConfig(DomainCallback<DicGiftBoxConfigEntity> giftBoxConfigCallback) {
        DicRequest request = new DicRequest();
        request.setCodes(List.of(Constants.DIC_GIFT_BOX_CONFIG));
        Call<ApiResponse<MergeGiftBoxConfig>> call = dicDao.queryDicByCodes(request);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ApiResponse<MergeGiftBoxConfig>> call,
                Response<ApiResponse<MergeGiftBoxConfig>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    MergeGiftBoxConfig mergeGiftBoxConfig = response.body().getData();
                    try {
                        DicGiftBoxConfigEntity dicGiftBoxConfig =
                            new Gson().fromJson(mergeGiftBoxConfig.getGiftBoxCg(), DicGiftBoxConfigEntity.class);
                        giftBoxConfigCallback.onSuccess(dicGiftBoxConfig);
                    } catch (Exception e) {
                        Log.e("Merge", "获取数据字典GiftBoxCg返回解析失败", e);
                        giftBoxConfigCallback.onFailure("JSON 解析失败");
                    }
                } else {
                    giftBoxConfigCallback.onFailure("请求失败：" + response.body().getMsg());
                    Log.e("Merge", "请求失败：" + response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<MergeGiftBoxConfig>> call, Throwable throwable) {
                giftBoxConfigCallback.onFailure("请求失败：" + throwable.getMessage());
                Log.e("Merge", "获取数据字典GiftBoxCg异常：", throwable);
            }
        });
    }
}
