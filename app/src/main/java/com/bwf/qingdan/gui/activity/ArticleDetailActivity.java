package com.bwf.qingdan.gui.activity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bwf.qingdan.R;
import com.bwf.qingdan.entity.ResponseArticleComments;
import com.bwf.qingdan.entity.ResponseArticleTitle;
import com.bwf.qingdan.entity.ResponseRelatedArticles;
import com.bwf.qingdan.mvp.presenter.ArticleDetailPresenter;
import com.bwf.qingdan.mvp.presenter.impl.ArticleDetailPresenterImpl;
import com.bwf.qingdan.mvp.view.ArticleDetailView;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by LG on 2016/10/31.
 * Tips:
 */

public class ArticleDetailActivity extends ActivityBase implements ArticleDetailView {
    @BindView(R.id.textview_title_subview_title)
    TextView textviewTitleSubviewTitle;
    @BindView(R.id.imageView_back_subview_title)
    ImageView imageViewBackSubviewTitle;
    @BindView(R.id.imageView_big_subview_article_title)
    SimpleDraweeView imageViewBigSubviewArticleTitle;
    @BindView(R.id.textView_tag_subview_article_title)
    TextView textViewTagSubviewArticleTitle;
    @BindView(R.id.textView_title_subview_article_title)
    TextView textViewTitleSubviewArticleTitle;
    @BindView(R.id.imageView_author_subview_article_title)
    SimpleDraweeView imageViewAuthorSubviewArticleTitle;
    @BindView(R.id.textView_author_nickname_subview_article_title)
    TextView textViewAuthorNicknameSubviewArticleTitle;
    @BindView(R.id.textView_publish_time_subview_article_title)
    TextView textViewPublishTimeSubviewArticleTitle;
    @BindView(R.id.textView_author_tag_subview_article_title)
    TextView textViewAuthorTagSubviewArticleTitle;
    @BindView(R.id.textView_like_count)
    TextView textViewLikeCount;
    @BindView(R.id.layout_like_count)
    LinearLayout layoutLikeCount;
    @BindView(R.id.textView_comments_count)
    TextView textViewCommentsCount;
    @BindView(R.id.layout_comments_count)
    LinearLayout layoutCommentsCount;
    @BindView(R.id.layout_shared)
    LinearLayout layoutShared;
    @BindView(R.id.textView_lookup_goods)
    TextView textViewLookupGoods;
    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.textView_write_comment)
    TextView textViewWriteComment;
    @BindView(R.id.rela_no_comments)
    RelativeLayout relaNoComments;
    @BindView(R.id.layout_container_subiew_comments)
    LinearLayout layoutContainerSubiewComments;
    @BindView(R.id.layout_comments)
    LinearLayout layoutComments;
    @BindView(R.id.refreshLayout)
    MaterialRefreshLayout refreshLayout;
    @BindView(R.id.root)
    LinearLayout root;
    private ArticleDetailPresenter presenter;
    private int articleId;

    @Override
    protected void initDatas() {
        //获取上一个界面传进来的文章id
        articleId = getIntent().getIntExtra("articleId", 0);

        presenter = new ArticleDetailPresenterImpl(this);
        //调用ArticleDetailPresenter获取数据的方法
//        presenter.loadDatas(articleId);

        //一进来界面就自动刷新一下
        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                refreshLayout.autoRefresh();
            }
        }.sendEmptyMessageDelayed(0, 500);

    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
        webview.getSettings().setJavaScriptEnabled(true); //设置支持JavaScript

        refreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                //去重新加载数据
                presenter.loadDatas(articleId);
            }
        });

        //判断19版本以上才设置pading
        if(Build.VERSION.SDK_INT >= 19)
        root.setPadding(0,getStatusHeight(),0,0);
    }

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_article_detail;
    }


    @Override
    public void showArticleTitle(ResponseArticleTitle articleTitle) {
        //告诉refreshLayout 刷新成功
        refreshLayout.finishRefresh();
        imageViewBigSubviewArticleTitle.setImageURI(articleTitle.getData().getFeaturedImageUrl());
        textViewTitleSubviewArticleTitle.setText(articleTitle.getData().getTitle());
        imageViewAuthorSubviewArticleTitle.setImageURI(articleTitle.getData().getAuthor().getAvatarUrl());
        textViewAuthorNicknameSubviewArticleTitle.setText(articleTitle.getData().getAuthor().getNickname());
        textViewAuthorTagSubviewArticleTitle.setText(articleTitle.getData().getAuthor().getTagline());
        textViewPublishTimeSubviewArticleTitle.setText(articleTitle.getData().getPublishedAtDiffForHumans());
        if (articleTitle.getData().getCategories() != null && articleTitle.getData().getCategories().size() != 0) {
            textViewTagSubviewArticleTitle.setText(articleTitle.getData().getCategories().get(0).getName());
        }
    }

    @Override
    public void showArticleDetail(String url) {
        Log.d("ArticleDetailActivity", "showArticleDetail:" + url);
        webview.loadUrl(url);
    }

    @Override
    public void showArticleComments(List<ResponseArticleComments.DataBean.CommentsBean> comments) {

        Log.d("ArticleDetailActivity", "showArticleComments:" + comments.size());
        //根据评论数据动态向容器中添加View并设置View数据
        LayoutInflater inflater = LayoutInflater.from(this);
        layoutComments.setVisibility(View.VISIBLE);
        layoutComments.removeAllViews();//不加这一行在刷新的时候 评论条数会一直增加
        for (int i = 0; i < comments.size(); i++) {
            View view = inflater.inflate(R.layout.list_item_comment, layoutComments, false);
            CommmentItemViewHolder holder = new CommmentItemViewHolder(view);
            ResponseArticleComments.DataBean.CommentsBean comment = comments.get(i);
            holder.imageViewAuthorAvatar.setImageURI(comment.getUser().getAvatarUrl());
            holder.textViewAuthorName.setText(comment.getUser().getNickname());
            holder.textViewCommentTimeTag.setText(comment.getCreatedAtDiffForHumans());

            if (comment.getReplyToUser() == null) {
                holder.textViewComments.setText(comment.getBody());
            } else {
                String replyUserNickName = comment.getReplyToUser().getNickname();
                holder.textViewComments.setText("回复 " + replyUserNickName + "：" + comment.getBody());
                //TODO  一个TextView显示多种颜色的文字
            }

            holder.textViewCommentLikeCount.setText(comment.getUpvoteCount() + "");
            layoutComments.addView(view);
        }

    }

    @Override
    public void showRelatedArticles(List<ResponseRelatedArticles.DataBean.ArticlesBean> articles) {
        Log.d("ArticleDetailActivity", "showRelatedArticles:" + articles.size());
    }

    @Override
    public void showLookUpGoods() {
        textViewLookupGoods.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLikedCount(int likedCount) {
        textViewLikeCount.setText(getString(R.string.like_count, likedCount));
    }

    @Override
    public void showCommentsCount(int commentsCount) {
        textViewCommentsCount.setText(getString(R.string.comments_count, commentsCount));
    }

    @Override
    public void showNoComments() {
        relaNoComments.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMoreCommentsView() {
        //TODO
    }

    @Override
    public void showLoadFailed() {
        refreshLayout.finishRefresh();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation

    }

    @OnClick({R.id.imageView_back_subview_title, R.id.layout_like_count, R.id.layout_comments_count, R.id.layout_shared, R.id.textView_lookup_goods})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageView_back_subview_title:
                finish();
                break;
            case R.id.layout_like_count:
                break;
            case R.id.layout_comments_count:
                break;
            case R.id.layout_shared:
                break;
            case R.id.textView_lookup_goods:
                break;
        }
    }

    static class CommmentItemViewHolder {
        @BindView(R.id.imageView_author_avatar)
        SimpleDraweeView imageViewAuthorAvatar;
        @BindView(R.id.textView_author_name)
        TextView textViewAuthorName;
        @BindView(R.id.textView_comment_time_tag)
        TextView textViewCommentTimeTag;
        @BindView(R.id.textView_comment_like_count)
        TextView textViewCommentLikeCount;
        @BindView(R.id.imageView_comment_like)
        ImageView imageViewCommentLike;
        @BindView(R.id.textView_comments)
        TextView textViewComments;

        CommmentItemViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
