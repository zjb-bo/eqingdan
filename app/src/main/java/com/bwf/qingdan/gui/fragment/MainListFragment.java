package com.bwf.qingdan.gui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bwf.qingdan.R;
import com.bwf.qingdan.entity.ResponseMainListData;
import com.bwf.qingdan.entity.ResponseReputation;
import com.bwf.qingdan.gui.activity.ArticleDetailActivity;
import com.bwf.qingdan.gui.adapter.ArticlesRecycleViewAdapter;
import com.bwf.qingdan.gui.adapter.BaseMainListRecycleViewAdapter;
import com.bwf.qingdan.gui.adapter.CollectionsRecycleViewAdapter;
import com.bwf.qingdan.gui.adapter.NodesRecycleViewAdapter;
import com.bwf.qingdan.mvp.presenter.MainListPresenter;
import com.bwf.qingdan.mvp.presenter.impl.MainListPresenterImpl;
import com.bwf.qingdan.mvp.view.MainListView;
import com.bwf.qingdan.utils.Contants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by LG on 2016/10/20.
 * Tips:
 */

public class MainListFragment extends FragmentBase implements MainListView, View.OnClickListener, BaseMainListRecycleViewAdapter.OnItemClickListener {
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    @BindView(R.id.fab_fragment_main_list)
    FloatingActionButton fabFragmentMainList;
    @Override
    protected int getContentViewResId() {
        return R.layout.fragment_main_list;
    }

    /***
     * 代表是哪一种数据类型
     **/
    private int dataCategoryTag;
    /**
     * 代表访问数据的接口
     **/
    private String urlTag;

    private MainListPresenter presenter;
    private BaseMainListRecycleViewAdapter recycleViewAdapter;
    private LinearLayoutManager layoutManager;

    public static MainListFragment newInstance(int categoryTag, String urlTag) {
        MainListFragment fragment = new MainListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("dataCategoryTag", categoryTag);
        bundle.putString("urlTag", urlTag);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(layoutManager);

        Bundle bundle = getArguments();
        dataCategoryTag = bundle.getInt("dataCategoryTag");
        urlTag = bundle.getString("urlTag");
        Log.d("MainListFragment", "dataCategoryTag:" + dataCategoryTag);
        Log.d("MainListFragment", "urlTag:" + urlTag);


        //根据数据的类型 来创建对应的Adapter
        switch (dataCategoryTag) {
            case Contants.TAG_ARTICLES:
                recycleViewAdapter = new ArticlesRecycleViewAdapter(getActivity());
                break;
            case Contants.TAG_COLLECTIONS:
                recycleViewAdapter = new CollectionsRecycleViewAdapter(getActivity());
                break;
            case Contants.TAG_NODES:
                recycleViewAdapter = new NodesRecycleViewAdapter(getActivity());
                break;
        }

        recycleViewAdapter.setOnRetryClickListener(new BaseMainListRecycleViewAdapter.OnRetryClickListener() {
            @Override
            public void onRetryClick() {
                loadNextDatas();
            }
        });
        recycleViewAdapter.setOnItemClickListener(this);
        recycleView.setAdapter(recycleViewAdapter);

        recycleView.addOnScrollListener(onScrollListener);

        //创建Presenter对象
        presenter = new MainListPresenterImpl(this, urlTag);
        loadNextDatas();

        //如果是 "最新"对应的页面  就去加载 口碑数据
        if (dataCategoryTag == Contants.TAG_NODES) {
            presenter.loadReputationData();
        }

        initFab();
    }

    /**
     * 初始化FloatActionButton
     */
    private void initFab(){
        fabFragmentMainList.setOnClickListener(this);
        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(layoutManager.findFirstVisibleItemPosition() == 0){
                    fabFragmentMainList.hide();
                    return;
                }
                if(dy > 0 && fabFragmentMainList.getVisibility() == View.VISIBLE){
                    //如果往上滚动并且可见
                    fabFragmentMainList.hide();
                }else if(dy < 0 && fabFragmentMainList.getVisibility() != View.VISIBLE){
                    //如果往下滚动并且不可见
                    fabFragmentMainList.show();
                }
            }
        });
    }
    private void loadNextDatas() {
        presenter.loadNextPageDatas();
        isLoading = true;
    }

    private boolean isNoMoreData;
    private boolean isLoading;
    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            if (isNoMoreData) {//如果没有下一页数据就不再触发获取下一页数据
                return;
            }
            if (!isLoading && layoutManager.findLastVisibleItemPosition() == layoutManager.getItemCount() - 1) {
                loadNextDatas();
            }
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void showNodesToView(List<ResponseMainListData.DataBean.NodesBean> nodes) {
        recycleViewAdapter.addDatas(nodes);
        isLoading = false;
    }

    @Override
    public void showArticlesToView(List<ResponseMainListData.DataBean.ArticlesBean> articles) {
        recycleViewAdapter.addDatas(articles);
        isLoading = false;

    }

    @Override
    public void showCollectionsToView(List<ResponseMainListData.DataBean.CollectionsBean> collections) {
        recycleViewAdapter.addDatas(collections);
        isLoading = false;
    }

    @Override
    public void showRecycleViewFooterLoading() {
        recycleViewAdapter.updateFooterViewState(BaseMainListRecycleViewAdapter.STATE_LOADING);
    }

    @Override
    public void showRecycleViewFooterLoadFailed() {
        isLoading = false;
        recycleViewAdapter.updateFooterViewState(BaseMainListRecycleViewAdapter.STATE_FAILED);
    }

    @Override
    public void showRecycleViewFooterNoMoreData() {
        isNoMoreData = true;
        Toast.makeText(getActivity(), "没有下一页了！", Toast.LENGTH_SHORT).show();
        recycleViewAdapter.updateFooterViewState(BaseMainListRecycleViewAdapter.STATE_NO_MORE_DATA);
    }

    @Override
    public void showReputation(List<ResponseReputation.DataBean.RankingsBean> rankings) {
        if (recycleViewAdapter instanceof NodesRecycleViewAdapter) {
            NodesRecycleViewAdapter adapter = (NodesRecycleViewAdapter) recycleViewAdapter;
            adapter.setRankings(rankings);
        }
    }

    @Override
    public void onClick(View v) {
        if(v == fabFragmentMainList){
            //让RecycleView滚到第0个位置
//            recycleView.scrollToPosition(0);
            if(layoutManager.findLastVisibleItemPosition() < 10){
                recycleView.smoothScrollToPosition(0);
            }else{
                recycleView.scrollToPosition(0);
            }
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        if(dataCategoryTag == Contants.TAG_NODES){
           ResponseMainListData.DataBean.NodesBean node
                   = (ResponseMainListData.DataBean.NodesBean) recycleViewAdapter.getItem(position);
            Toast.makeText(getActivity(), node.getTitle(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), ArticleDetailActivity.class);
            intent.putExtra("articleId",node.getTarget_id());
            startActivity(intent);
        }else if(dataCategoryTag == Contants.TAG_ARTICLES){
           ResponseMainListData.DataBean.ArticlesBean article
                   = (ResponseMainListData.DataBean.ArticlesBean) recycleViewAdapter.getItem(position);
            Intent intent = new Intent(getActivity(), ArticleDetailActivity.class);
            intent.putExtra("articleId",article.getId());
            startActivity(intent);
            Toast.makeText(getActivity(), article.getTitle(), Toast.LENGTH_SHORT).show();
        }else if(dataCategoryTag == Contants.TAG_COLLECTIONS){
           ResponseMainListData.DataBean.CollectionsBean collections
                   = (ResponseMainListData.DataBean.CollectionsBean) recycleViewAdapter.getItem(position);
            Toast.makeText(getActivity(), collections.getTitle(), Toast.LENGTH_SHORT).show();
        }
    }
}
