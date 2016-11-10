package com.bwf.qingdan.mvp.model.impl;
import com.alibaba.fastjson.JSON;
import com.bwf.qingdan.entity.ResponseArticleComments;
import com.bwf.qingdan.entity.ResponseArticleTitle;
import com.bwf.qingdan.entity.ResponseRelatedArticles;
import com.bwf.qingdan.mvp.model.ArticleDetailModel;
import com.bwf.qingdan.utils.Apis;
import com.bwf.qingdan.utils.UrlHandler;
import com.bwf.qingdan.utils.http.HttpUtils;
import com.bwf.qingdan.utils.http.Request;
import com.bwf.qingdan.utils.http.qingdan.HttpClient;
/**
 * Created by LG on 2016/10/31.
 * Tips:
 */
public class ArticleDetailModelImpl implements ArticleDetailModel{
    private int articleId;
    private Callback callback;
    @Override
    public void loadDatas(int articleId, Callback callback) {
        this.articleId = articleId;
        this.callback = callback;
        loadArticleTitle();
        loadArticleDetail();
        loadComments();
        loadRelatedArticles();
    }
    @Override
    public void loadArticleTitle() {
        String url = UrlHandler.handlUrl(Apis.URL_ARTICLE_TITLE,articleId);
        Request.Builder builder = new Request.Builder()
                .url(url)
                .get();
        HttpClient.excute(builder, new HttpUtils.Callback() {
            @Override
            public void onResponse(String response) {
                ResponseArticleTitle responseArticleTitle
                        = JSON.parseObject(response, ResponseArticleTitle.class);
                callback.loadArticleTitleSuccess(responseArticleTitle);

            }
            @Override
            public void onError() {
                callback.loadFailed();
            }
        });
    }
    @Override
    public void loadArticleDetail() {
        callback.loadArticleDetailSuccess(UrlHandler.handlUrl(Apis.URL_ARTICLE_DETAIL,articleId));
    }
    @Override
    public void loadComments() {
        String url = UrlHandler.handlUrl(Apis.URL_ARTICLE_COMMENTS,articleId);
        Request.Builder builder = new Request.Builder()
                .url(url)
                .get();
        HttpClient.excute(builder, new HttpUtils.Callback() {
            @Override
            public void onResponse(String response) {
                ResponseArticleComments responseArticleComments
                        = JSON.parseObject(response, ResponseArticleComments.class);
                callback.loadCommentsSuccess(responseArticleComments);

            }
            @Override
            public void onError() {
                callback.loadFailed();
            }
        });
    }
    @Override
    public void loadRelatedArticles() {
        String url = UrlHandler.handlUrl(Apis.URL_RELATED_ARTICLES,articleId);
        Request.Builder builder = new Request.Builder()
                .url(url)
                .get();
        HttpClient.excute(builder, new HttpUtils.Callback() {
            @Override
            public void onResponse(String response) {
                ResponseRelatedArticles responseRelatedArticles
                        = JSON.parseObject(response, ResponseRelatedArticles.class);
                callback.loadRelatedArticlesSuccess(responseRelatedArticles.getData().getArticles());
            }
            @Override
            public void onError() {
                callback.loadFailed();
            }
        });
    }
}
