package com.bwf.qingdan.mvp.view;


import com.bwf.qingdan.entity.ResponseReputationThing;

import java.util.List;

/**
 * Created by Administrator on 2016/11/7.
 */

public interface ReputationThingView {
    void showThingsToView(int page, List<ResponseReputationThing.DataBean.ThingsBean> things);

    void showFooterLoading();
    void showFooterLoadFailed();
    void showFooterNoMoreData();
    void showFooterNoSearchData();


}
