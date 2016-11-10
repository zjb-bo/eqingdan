package com.bwf.qingdan.gui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bwf.qingdan.R;
import com.bwf.qingdan.entity.ResponseMainListData;
import com.bwf.qingdan.entity.ResponseReputation;
import com.bwf.qingdan.gui.activity.RepuationThingActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by LG on 2016/10/24.
 * Tips:
 */

public class NodesRecycleViewAdapter extends BaseMainListRecycleViewAdapter<ResponseMainListData.DataBean.NodesBean> {

    public NodesRecycleViewAdapter(Context context) {
        super(context);
    }

    private List<ResponseReputation.DataBean.RankingsBean> rankings = new ArrayList<>();


    public void setRankings(List<ResponseReputation.DataBean.RankingsBean> rankings) {
        this.rankings.clear();
        this.rankings.addAll(rankings);
        notifyItemChanged(0);
    }

    @Override
    protected int getFooterCount() {
        return 1;
    }

    @Override
    protected int getHeaderCount() {
        return 1;
    }

    private static final int TYPE_CONTENT = 0;
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_HEADER = 2;

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return TYPE_FOOTER;
        } else if (position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_CONTENT;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            return new HeaderViewHolder(inflater.inflate(R.layout.layout_head_enter_rankings, parent, false));
        }
        if (viewType == TYPE_CONTENT) {
            return new MyViewHolder(inflater.inflate(R.layout.main_list_item, parent, false));
        }
        return new FooterViewHoder(inflater.inflate(R.layout.subview_recycleview_loadfooter, parent, false));
    }

    private List<SimpleDraweeView> simpleDraweeViews;
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position < getHeaderCount()) {
            //TODO  去设置头部视图的数据(有需要的话)
            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
            if(headerHolder.llItemRankingEnter.getChildCount() == 0){ //为了防止一直重复添加
                //根据口碑数据动态向一个线性容器里面添加View
                simpleDraweeViews = new ArrayList<>();
                for(final ResponseReputation.DataBean.RankingsBean ranking :rankings){
                    View view = inflater.inflate(R.layout.subview_reputation,headerHolder.llItemRankingEnter,false);
                    view.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            //跳转到RepuationThingActivity
                            Intent intent = new Intent(getContext(), RepuationThingActivity.class);
                            intent.putExtra(RepuationThingActivity.RANKING_ID,ranking.getId());
                            getContext().startActivity(intent);
                        }
                    });
                    simpleDraweeViews.add((SimpleDraweeView) view.findViewById(R.id.img_ranking_all_topic_enter));
                    headerHolder.llItemRankingEnter.addView(view);
                }
            }
            //为什么不把设置图片的操作放在上面的if里面？
            for (int i = 0; i < simpleDraweeViews.size(); i++) {
                simpleDraweeViews.get(i).setImageURI(rankings.get(i).getThumbnailImageUrl());
            }
            return;
        }
        if (position >= getItemCount() - getFooterCount()) {
            //TODO  去设置脚部视图的数据(有需要的话)
            onBindFooterViewHolder(holder, position);
            return;
        }
        MyViewHolder mHolder = (MyViewHolder) holder;
        ResponseMainListData.DataBean.NodesBean nodesBean = getItem(position);
        mHolder.imageViewFrontTopImage.setImageURI(nodesBean.getFeaturedImageUrl());
        mHolder.textViewFrontMainTitle.setText(nodesBean.getTitle());
        mHolder.textViewFrontSubtitle.setText(nodesBean.getSubtitle());
        mHolder.textViewFrontNumLiked.setText(nodesBean.getLikeCount() + "");
        mHolder.textViewNumReviews.setText(nodesBean.getHitCount() + "");
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ll_item_ranking_enter)
        LinearLayout llItemRankingEnter;
        @BindView(R.id.img_ranking_all_topic_enter)
        ImageView imgRankingAllTopicEnter;
        public HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
