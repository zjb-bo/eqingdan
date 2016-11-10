package com.bwf.qingdan.gui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LG on 2016/10/26.
 * Tips:
 */

public abstract  class BaseRecycleView<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<T> datas;
    protected LayoutInflater inflater;
    private Context context;
    public BaseRecycleView(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        datas = new ArrayList<>();
    }

    public Context getContext() {
        return context;
    }

    /**
     * 新增数据
     * @param datas
     */
    public void addDatas(List<T> datas){
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }
    public void setDatas(List<T> datas){
        this.datas.clear();
        addDatas(datas);
    }
    public void clearDatas(){
        this.datas.clear();
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        //TODO  到时候要考虑header和footer
        return datas.size() + getHeaderCount() + getFooterCount();
    }

    /**
     * 返回脚部视图的个数
     * @return
     */
    protected abstract int getFooterCount();

    /**
     * 返回头部视图的个数
     * @return
     */
    protected abstract int getHeaderCount();

    /**
     * 获取对应位置的数据
     * @param position
     * @return
     */
    public T getItem(int position){
        //TODO  到时候要考虑header和footer
        return datas.get(position-getHeaderCount());
    }

}
