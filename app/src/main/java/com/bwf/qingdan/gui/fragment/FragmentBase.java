package com.bwf.qingdan.gui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by LG on 2016/10/14.
 * Tips:
 */

public abstract class FragmentBase extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        return inflater.inflate(getContentViewResId(),null);
    }

    protected abstract int getContentViewResId();
}
