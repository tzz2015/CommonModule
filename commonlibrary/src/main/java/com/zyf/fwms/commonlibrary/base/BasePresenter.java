package com.zyf.fwms.commonlibrary.base;


import android.content.Context;
import android.support.v4.app.Fragment;

import com.zyf.fwms.commonlibrary.http.HttpTask;


/**
 * 公司：
 * 刘宇飞 创建 on 2017/3/6.
 * 描述：p 基类
 */

public abstract class BasePresenter<T> {
    public Context mContext;
    public T mView;
    public HttpTask httpTask;
    public int page=1;
    public Fragment mFragment;


    public void setView(T v) {
        this.mView = v;
        this.onStart();
    }

    /**
     * 设置fragment
     */
    public void setFragment(Fragment mFragment) {
        this.mFragment = mFragment;
    }


    public void onStart() {
    }

    ;

    public void onDestroy() {
        mView = null;
        mFragment = null;
        mContext = null;
    }

    /**
     * 显示和隐藏错误页面
     */
    public void showErroView(boolean isShow) {
        if (mFragment != null && mFragment instanceof BaseMVPFragment) {
            ((BaseMVPFragment) mFragment).showErroView(isShow);
            return;
        }
        if (mContext != null && mContext instanceof BaseMVPActivity)
            ((BaseMVPActivity) mContext).showErroView(isShow);


    }
}