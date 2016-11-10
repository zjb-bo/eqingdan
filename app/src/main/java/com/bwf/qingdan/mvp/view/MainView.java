package com.bwf.qingdan.mvp.view;

import com.bwf.qingdan.entity.ResponseBatching;

/**
 * Created by LG on 2016/10/20.
 * Tips:
 */

public interface MainView {
    void showBatchingData(ResponseBatching batching);
    void showLoadBatchingError();
}
