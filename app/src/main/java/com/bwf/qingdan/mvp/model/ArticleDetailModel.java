package com.bwf.qingdan.mvp.model;

import com.bwf.qingdan.entity.ResponseArticleComments;
import com.bwf.qingdan.entity.ResponseArticleTitle;
import com.bwf.qingdan.entity.ResponseRelatedArticles;

import java.util.List;

/**
 * Created by LG on 2016/10/31.
 * Tips:
 */

public interface ArticleDetailModel {

    void loadDatas(int articleId,Callback callback);
    void loadArticleTitle();
    void loadArticleDetail();
    void loadComments();
    void loadRelatedArticles();
    public interface Callback{
        void loadArticleTitleSuccess(ResponseArticleTitle articleTitle);
        void loadArticleDetailSuccess(String url);
        void loadCommentsSuccess(ResponseArticleComments responseArticleComments);
        void loadRelatedArticlesSuccess(List<ResponseRelatedArticles.DataBean.ArticlesBean> relatedArticles);

        void loadFailed();
    }
}
