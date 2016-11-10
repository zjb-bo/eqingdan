package com.bwf.qingdan.mvp.presenter.impl;

import com.bwf.qingdan.entity.ResponseArticleComments;
import com.bwf.qingdan.entity.ResponseArticleTitle;
import com.bwf.qingdan.entity.ResponseRelatedArticles;
import com.bwf.qingdan.mvp.model.ArticleDetailModel;
import com.bwf.qingdan.mvp.model.impl.ArticleDetailModelImpl;
import com.bwf.qingdan.mvp.presenter.ArticleDetailPresenter;
import com.bwf.qingdan.mvp.view.ArticleDetailView;

import java.util.List;

/**
 * Created by LG on 2016/10/31.
 * Tips:
 */

public class ArticleDetailPresenterImpl implements ArticleDetailPresenter {
    private ArticleDetailModel model;
    private ArticleDetailView view;

    public ArticleDetailPresenterImpl(ArticleDetailView view) {
        this.view = view;
        this.model = new ArticleDetailModelImpl();
    }

    @Override
    public void loadDatas(int articleId) {

        model.loadDatas(articleId, new ArticleDetailModel.Callback() {
            @Override
            public void loadArticleTitleSuccess(ResponseArticleTitle articleTitle) {
                //收到Model的回调之后，再通过View显示界面
                view.showArticleTitle(articleTitle);

                //通知View显示总评论个数
                view.showCommentsCount(articleTitle.getData().getCommentCount());
                //如果评论>4条 就显示 “加载更多评论”
                if(articleTitle.getData().getCommentCount() > 4){
                    view.showMoreCommentsView();
                }
                //通知View显示总喜欢个数
                view.showLikedCount(articleTitle.getData().getLikeCount());

                if(articleTitle.getData().getThingCount() > 0 ){
                    view.showLookUpGoods();
                }
            }

            @Override
            public void loadArticleDetailSuccess(String url) {
                view.showArticleDetail(url);
            }

            @Override
            public void loadCommentsSuccess(ResponseArticleComments responseArticleComments) {
                //通知View显示评论区数据
                if(responseArticleComments.getData().getComments() == null || responseArticleComments.getData().getComments().size() == 0){
                    view.showNoComments();
                }else{
                    view.showArticleComments(responseArticleComments.getData().getComments());
                }


            }

            @Override
            public void loadRelatedArticlesSuccess(List<ResponseRelatedArticles.DataBean.ArticlesBean> relatedArticles) {
                view.showRelatedArticles(relatedArticles);
            }

            @Override
            public void loadFailed() {
                view.showLoadFailed();
            }
        });
    }
}
