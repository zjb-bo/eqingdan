package com.bwf.qingdan.gui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by LG on 2016/10/14.
 * Tips:
 */

public class GuideActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv = new TextView(this);
        tv.setText("我是引导页面");
        setContentView(tv);
    }
}
