package com.bwf.qingdan.gui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bwf.qingdan.R;
import com.bwf.qingdan.entity.ResponseBatching;
import com.bwf.qingdan.gui.adapter.MainSlidePagerAdapter;
import com.bwf.qingdan.gui.adapter.MainTabFragmentPagerAdapter;
import com.bwf.qingdan.gui.view.CustomRefreshLayout;
import com.bwf.qingdan.gui.widget.PagerDotIndicator;
import com.bwf.qingdan.mvp.presenter.MainPresenter;
import com.bwf.qingdan.mvp.presenter.impl.MainPresenterImpl;
import com.bwf.qingdan.mvp.view.MainView;

import java.util.List;

/**
 * Created by LG on 2016/10/14.
 * Tips:
 */

public class MainFragment extends FragmentBase implements MainView {
    private MainPresenter mainPresenter;
    private MainSlidePagerAdapter mainSlidePagerAdapter;
    private ViewPager mainSlideViewPager;
    /**ViewPager的指示器容器**/
    private LinearLayout mainIndicatorContainer;
    /**管理指示器的对象**/
    private PagerDotIndicator pagerDotIndicator;

    private TabLayout tabLayout;
    private ViewPager mainListFragmentViewPager; //装Fragment的ViewPager
    private MainTabFragmentPagerAdapter mainTabFragmentPagerAdapter;

    private AppBarLayout appBarLayout;
    private CustomRefreshLayout refreshLayout;
    @Override
    protected int getContentViewResId() {
        return R.layout.fragment_main;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mainSlideViewPager = (ViewPager) getView().findViewById(R.id.viewPager_main_list_slide);
        mainPresenter = new MainPresenterImpl(this);
        //调用Presenter 加载数据
        mainPresenter.loadBatching();

        mainIndicatorContainer = (LinearLayout) getView().findViewById(R.id.linearLayout_pager_indicator);

        pagerDotIndicator = new PagerDotIndicator(getActivity(),mainIndicatorContainer,mainSlideViewPager);

        tabLayout = (TabLayout) getView().findViewById(R.id.tabLayout_main);
        mainListFragmentViewPager = (ViewPager) getView().findViewById(R.id.viewPager_main_fragment);
        //将TabLayout关联上ViewPager
        tabLayout.setupWithViewPager(mainListFragmentViewPager);

        appBarLayout = (AppBarLayout) getView().findViewById(R.id.appBarLayout);
        refreshLayout = (CustomRefreshLayout) getView().findViewById(R.id.refreshLayout);
        //监听AppBarLayout的滚动
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                Log.d("MainFragment", "verticalOffset:" + verticalOffset);
                if(verticalOffset == 0){
                    //RefreshLayout可以触发下拉
                    refreshLayout.setCanPull(true);
                }else{
                    //RefreshLayout不可以触发下拉
                    refreshLayout.setCanPull(false);
                }
            }
        });
    }
    @Override
    public void showBatchingData(ResponseBatching batching) {
        Toast.makeText(getActivity(), batching.getMessage(), Toast.LENGTH_SHORT).show();
        List<ResponseBatching.DataBean1.SlidesBean1.BodyBean.DataBean.SlidesBean> slides =
                batching.getData().getSlides().getBody().getData().getSlides();
        mainSlidePagerAdapter = new MainSlidePagerAdapter(getActivity(), slides);
        mainSlideViewPager.setAdapter(mainSlidePagerAdapter);
        pagerDotIndicator.setDotNums(slides.size());

        //设置下面的ViewPager
        mainTabFragmentPagerAdapter = new MainTabFragmentPagerAdapter(
                batching.getData().getChannels().getBody().getData().getChannels(),getFragmentManager());
        mainListFragmentViewPager.setAdapter(mainTabFragmentPagerAdapter);

    }

    @Override
    public void showLoadBatchingError() {
        Toast.makeText(getActivity(), "LoadBatchingError", Toast.LENGTH_SHORT).show();
    }
}
