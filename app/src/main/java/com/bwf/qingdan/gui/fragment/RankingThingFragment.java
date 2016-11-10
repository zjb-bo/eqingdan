package com.bwf.qingdan.gui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bwf.qingdan.R;
import com.bwf.qingdan.entity.ResponseReputationThing;
import com.bwf.qingdan.gui.adapter.RankingThingAdapter;
import com.bwf.qingdan.mvp.presenter.ReputationThingPresenter;
import com.bwf.qingdan.mvp.presenter.impl.ReputationThingPresenterImpl;
import com.bwf.qingdan.mvp.view.ReputationThingView;
import com.cjj.MaterialRefreshLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by LG on 2016/11/8.
 * Tips:
 */

public class RankingThingFragment extends FragmentBase implements ReputationThingView {
    private int rankingId;
    private int sortTag;
    private ReputationThingPresenter presenter;
    @BindView(R.id.recycleView_ranking_thing)
    RecyclerView recycleViewRankingThing;
    @BindView(R.id.refreshLayout)
    MaterialRefreshLayout refreshLayout;


    private RankingThingAdapter adapter;

    protected int getContentViewResId() {
        return R.layout.fragment_ranking_thing;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
    public static RankingThingFragment newInstance(int rankingId, int sortTag) {
        RankingThingFragment fragment = new RankingThingFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("rankingId", rankingId);
        bundle.putInt("sortTag", sortTag);
        fragment.setArguments(bundle);
        return fragment;
    }
    private LinearLayoutManager layoutManager;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recycleViewRankingThing.setLayoutManager(layoutManager);
        adapter = new RankingThingAdapter(getActivity());
        recycleViewRankingThing.setAdapter(adapter);
        initData();
    }
    private String searchKey = "";

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }
    private void initData() {
        Bundle bundle = getArguments();
        rankingId = bundle.getInt("rankingId");
        sortTag = bundle.getInt("sortTag");
        Log.d("RankingThingFragment", "rankingId:" + rankingId);
        Log.d("RankingThingFragment", "sortTag:" + sortTag);
        presenter = new ReputationThingPresenterImpl(this, sortTag, rankingId);

        loadNextData();
    }

    /**
     * 这个方法是让Activity来调用的
     * @param searchKey
     */
    public void showFragment(String searchKey){
        if(searchKey.equals(this.searchKey)){
            return;
        }
        adapter.clearDatas();//先清空列表中的item数据，在去搜索
        this.searchKey = searchKey;
        presenter.refreshData(searchKey);
    }
    private void loadNextData() {
        presenter.loadNextData(searchKey);
    }
    @Override
    public void showThingsToView(int page, List<ResponseReputationThing.DataBean.ThingsBean> things) {
        adapter.addDatas(things);
    }

    @Override
    public void showFooterLoading() {

    }

    @Override
    public void showFooterLoadFailed() {

    }

    @Override
    public void showFooterNoMoreData() {

    }

    @Override
    public void showFooterNoSearchData() {

    }
}
