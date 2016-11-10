package com.bwf.qingdan.mvp.view;

import com.bwf.qingdan.entity.ResponseArticleComments;
import com.bwf.qingdan.entity.ResponseArticleTitle;
import com.bwf.qingdan.entity.ResponseRelatedArticles;

import java.util.List;

/**
 * Created by LG on 2016/10/31.
 * Tips:
 */

public interface ArticleDetailView {
    /**显示文章标题区域**/
    void showArticleTitle(ResponseArticleTitle articleTitle);
    void showArticleDetail(String url);
    void showArticleComments(List<ResponseArticleComments.DataBean.CommentsBean> comments);
    void showRelatedArticles(List<ResponseRelatedArticles.DataBean.ArticlesBean> articles);
    void showLookUpGoods();
    void showLikedCount(int likedCount);
    void showCommentsCount(int commentsCount);
    void showNoComments();

    void showMoreCommentsView();

    void showLoadFailed();
}
