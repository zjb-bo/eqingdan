package com.bwf.qingdan.test;

import android.util.Log;

import com.bwf.qingdan.entity.ResponseArticleComments;
import com.bwf.qingdan.entity.ResponseArticleTitle;
import com.bwf.qingdan.entity.ResponseRelatedArticles;
import com.bwf.qingdan.mvp.presenter.ArticleDetailPresenter;
import com.bwf.qingdan.mvp.presenter.impl.ArticleDetailPresenterImpl;
import com.bwf.qingdan.mvp.view.ArticleDetailView;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by LG on 2016/11/1.
 * Tips:
 */
public class ArticleDetailPresenterImplTest implements ArticleDetailView {
    private ArticleDetailPresenter presenter;
    @Before
    public void setUp() throws Exception {
        presenter = new ArticleDetailPresenterImpl(this);
    }

    @Test
    public void loadDatas() throws Exception {
        presenter.loadDatas(806);
    }

    @Override
    public void showArticleTitle(ResponseArticleTitle articleTitle) {
        Log.d("ArticleDetailPresenterI", articleTitle.getData().getTitle());
    }

    @Override
    public void showArticleDetail(String url) {
        Log.d("ArticleDetailPresenterI", url);

    }

    @Override
    public void showArticleComments(List<ResponseArticleComments.DataBean.CommentsBean> comments) {
        Log.d("ArticleDetailPresenterI", "comments.size():" + comments.size());
    }

    @Override
    public void showRelatedArticles(List<ResponseRelatedArticles.DataBean.ArticlesBean> articles) {
        Log.d("ArticleDetailPresenterI", "articles.size():" + articles.size());
    }

    @Override
    public void showLookUpGoods() {
        Log.d("ArticleDetailPresenterI", "showLookUpGoods");
    }

    @Override
    public void showLikedCount(int likedCount) {
        Log.d("ArticleDetailPresenterI", "likedCount:" + likedCount);
//        Assert.assertEquals(29,likedCount); //断言 likedCount等于29就算测试通过，否则就是失败
    }

    @Override
    public void showCommentsCount(int commentsCount) {
        Log.d("ArticleDetailPresenterI", "commentsCount:" + commentsCount);
    }
}