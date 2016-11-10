package com.bwf.qingdan.gui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bwf.qingdan.R;
import com.bwf.qingdan.entity.ResponseBatching;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LG on 2016/10/20.
 * Tips:
 */

public class MainSlidePagerAdapter extends PagerAdapter {
    private List<ResponseBatching.DataBean1.SlidesBean1.BodyBean.DataBean.SlidesBean> datas;
    private List<View> views;
    public MainSlidePagerAdapter(Context context,List<ResponseBatching.DataBean1.SlidesBean1.BodyBean.DataBean.SlidesBean> datas) {
        this.datas = datas;
        //根据数据源，初始化views
        views = new ArrayList<>();
        LayoutInflater inflater = LayoutInflater.from(context);//获取布局加载器
        for (int i = 0; i < datas.size(); i++) {
            View view = inflater.inflate(R.layout.subview_main_slide_page,null);
            views.add(view);
        }
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = views.get(position% views.size());
        SimpleDraweeView imageView = (SimpleDraweeView) view.findViewById(R.id.iamgeView_subview_main_slide_page);
        //使用fresco加载网络图片
        imageView.setImageURI(datas.get(position% views.size()).getFeaturedImageUrl());
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = views.get(position% views.size());
        container.removeView(view);
    }
}
