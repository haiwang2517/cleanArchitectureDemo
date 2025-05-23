package com.onenet.mytravelworld.domain.repository;

/**
 * 领域数据回调接口
 *
 * @author HaiYinLong
 * @version 2025/05/23 15:30
 **/
public interface DomainCallback<T> {
    void onSuccess(T result);

    void onFailure(String message);
}
