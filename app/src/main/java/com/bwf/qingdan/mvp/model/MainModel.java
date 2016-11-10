package com.bwf.qingdan.mvp.model;

import com.bwf.qingdan.entity.ResponseBatching;

/**
 * Created by LG on 2016/10/20.
 * Tips:
 */

public interface MainModel {
    void loadDatas(OnBatchingLoadCallback callback);
    public interface OnBatchingLoadCallback{
        void onBatchingLoadSuccess(ResponseBatching batching);
        void onBatchingLoadFailed();
    }
}
