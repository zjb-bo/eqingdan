package com.bwf.qingdan.gui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bwf.qingdan.R;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by LG on 2016/10/25.
 * Tips:
 */

public abstract class BaseMainListRecycleViewAdapter<T> extends BaseRecycleView<T>{

    public BaseMainListRecycleViewAdapter(Context context) {
        super(context);
    }
    public static final int STATE_LOADING = 1;
    public static final int STATE_FAILED = 2;
    public static final int STATE_NO_MORE_DATA = 3;
    private int state;
    public void updateFooterViewState(int state) {
        this.state = state;
        notifyDataSetChanged(); //刷新adapter
    }

    public void onBindFooterViewHolder(RecyclerView.ViewHolder holder, int position){
        FooterViewHoder footerHolder = (FooterViewHoder) holder;
        switch (state){
            case STATE_LOADING:
                footerHolder.progressbarSubviewRecycleviewLoadfooter.setVisibility(View.VISIBLE);
                footerHolder.textviewSubviewRecycleviewLoadfooter.setVisibility(View.GONE);
                break;
            case STATE_NO_MORE_DATA:
                footerHolder.progressbarSubviewRecycleviewLoadfooter.setVisibility(View.GONE);
                footerHolder.textviewSubviewRecycleviewLoadfooter.setVisibility(View.VISIBLE);
                footerHolder.textviewSubviewRecycleviewLoadfooter.setText("没有更多数据了哦~");
                break;
            case STATE_FAILED:
                footerHolder.progressbarSubviewRecycleviewLoadfooter.setVisibility(View.GONE);
                footerHolder.textviewSubviewRecycleviewLoadfooter.setVisibility(View.VISIBLE);
                footerHolder.textviewSubviewRecycleviewLoadfooter.setText("加载失败，点击重试~");
                break;
        }
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.imageView_front_top_image)
        SimpleDraweeView imageViewFrontTopImage;
        @BindView(R.id.textView_front_main_title)
        TextView textViewFrontMainTitle;
        @BindView(R.id.textView_front_subtitle)
        TextView textViewFrontSubtitle;
        @BindView(R.id.textView_front_num_liked)
        TextView textViewFrontNumLiked;
        @BindView(R.id.textView_num_reviews)
        TextView textViewNumReviews;
        @BindView(R.id.rotate_textView_date)
        TextView rotateTextViewDate;
        @BindView(R.id.linear_bottom_count)
        LinearLayout linearBottomCount;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    int clickPosition = getAdapterPosition(); //获取正在使用这个view的item的位置
                    if(onItemClickListener != null){
                        onItemClickListener.onItemClick(v,clickPosition);//回调
                    }
                }
            });
        }
    }

    public  class FooterViewHoder extends RecyclerView.ViewHolder {
        @BindView(R.id.progressbar_subview_recycleview_loadfooter)
        ProgressBar progressbarSubviewRecycleviewLoadfooter;
        @BindView(R.id.textview_subview_recycleview_loadfooter)
        TextView textviewSubviewRecycleviewLoadfooter;
        @BindView(R.id.layout_subview_recycleview_loadfooter)
        RelativeLayout layoutSubviewRecycleviewLoadfooter;

        FooterViewHoder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            textviewSubviewRecycleviewLoadfooter.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        listener.onRetryClick();//告诉Fragment重试按钮被点击了
                    }
                }
            });
        }
    }

    //失败重试按钮的监听
    public interface OnRetryClickListener{
        void onRetryClick();
    }
    private OnRetryClickListener listener;
    public void setOnRetryClickListener(OnRetryClickListener listener){
        this.listener = listener;
    }

    //item单击监听
    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }
    private OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
