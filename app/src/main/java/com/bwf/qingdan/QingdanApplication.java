package com.bwf.qingdan;

import android.app.Application;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by LG on 2016/10/20.
 * Tips: 可以保存一些整个应用全局的数据
 *
 * Application生命周期比较长，正常退出应用的时候 Application不会销毁
 */

public class QingdanApplication extends Application{
    public static QingdanApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Fresco.initialize(this);
        Log.d("QingdanApplication", "onCreate");
    }
}
