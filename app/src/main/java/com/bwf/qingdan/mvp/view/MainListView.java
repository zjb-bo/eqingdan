package com.bwf.qingdan.mvp.view;

import com.bwf.qingdan.entity.ResponseMainListData;
import com.bwf.qingdan.entity.ResponseReputation;

import java.util.List;

/**
 * Created by LG on 2016/10/25.
 * Tips:
 */

public interface MainListView {

    void showNodesToView(List<ResponseMainListData.DataBean.NodesBean> nodes);
    void showArticlesToView(List<ResponseMainListData.DataBean.ArticlesBean> articles);
    void showCollectionsToView(List<ResponseMainListData.DataBean.CollectionsBean> collections);

    void showRecycleViewFooterLoading();
    void showRecycleViewFooterLoadFailed();
    void showRecycleViewFooterNoMoreData();

    void showReputation(List<ResponseReputation.DataBean.RankingsBean> rankings);
}
