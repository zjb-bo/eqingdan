package com.bwf.qingdan.mvp.model;

import com.bwf.qingdan.entity.ResponseReputationThing;

import java.util.List;

/**
 * Created by Administrator on 2016/11/7.
 */

public interface ReputationThingModel {
    void loadData(String url, Callback callback);
    public interface Callback{
        void loadSuccess(List<ResponseReputationThing.DataBean.ThingsBean> things);
        void loadFailed();
        void noMoreData();
        void noSearchData();

    }
}
