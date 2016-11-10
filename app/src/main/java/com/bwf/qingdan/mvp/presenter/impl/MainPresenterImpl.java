package com.bwf.qingdan.mvp.presenter.impl;

import com.bwf.qingdan.entity.ResponseBatching;
import com.bwf.qingdan.mvp.model.MainModel;
import com.bwf.qingdan.mvp.model.impl.MainModelImpl;
import com.bwf.qingdan.mvp.presenter.MainPresenter;
import com.bwf.qingdan.mvp.view.MainView;

/**
 * Created by LG on 2016/10/20.
 * Tips:
 */

public class MainPresenterImpl implements MainPresenter {
    private MainModel mainModel;
    private MainView mainView;
    public MainPresenterImpl(MainView mainView) {
        this.mainModel = new MainModelImpl();
        this.mainView = mainView;
    }

    @Override
    public void loadBatching() {
        //Presenter通过Model去加载数据
        mainModel.loadDatas(new MainModel.OnBatchingLoadCallback() {
            @Override
            public void onBatchingLoadSuccess(ResponseBatching batching) {
                mainView.showBatchingData(batching);
            }

            @Override
            public void onBatchingLoadFailed() {
                mainView.showLoadBatchingError();
            }
        });
    }
}
