package com.zyf.fwms.commonlibrary.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.bumptech.glide.Glide;
import com.squareup.leakcanary.RefWatcher;
import com.zyf.fwms.commonlibrary.http.Api;
import com.zyf.fwms.commonlibrary.http.HttpTask;
import com.zyf.fwms.commonlibrary.http.HttpUtils;
import com.zyf.fwms.commonlibrary.utils.CommonUtils;
import com.zyf.fwms.commonlibrary.utils.LogUtil;
import com.zyf.fwms.commonlibrary.utils.TUtil;
import com.zyf.fwms.commonlibrary.view.dialog.LoadingDialog;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * 创建 by lyf on 2018/9/4.
 * 描述：
 */

public class BaseFragment extends Fragment implements BaseView {
    protected Context mContext;
    private CompositeSubscription mCompositeSubscription;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getActivity();
        //打印类名
        LogUtil.getInstance().e(getClass().toString());
        Glide.get(getContext()).clearMemory();

    }

    /**
     * 显示toast
     *
     * @param title
     */
    public void showToast(String title) {
        CommonUtils.showToast(mContext, title);
    }

    private LoadingDialog mProgressDialog;

    /**
     * 显示进度框
     */
    @Override
    public final void showInfoProgressDialog(String... arg) {
        if (mProgressDialog == null) {
            mProgressDialog = new LoadingDialog(mContext);
        }

        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    /**
     * 隐藏等待条
     */
    @Override
    public final void hideInfoProgressDialog() {
        CommonUtils.getInstance().hideInfoProgressDialog();
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }
    /**
     * 添加网络请求观察者
     *
     * @param s
     */
    public void addSubscription(Subscription s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        this.mCompositeSubscription.add(s);
    }

    /**
     * 移除网络请求
     */
    public void removeSubscription() {
        CommonUtils.getInstance().removeSubscription();
        if (this.mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            this.mCompositeSubscription.unsubscribe();
            this.mCompositeSubscription = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Glide.get(mContext.getApplicationContext()).clearMemory();
        hideInfoProgressDialog();
        removeSubscription();
        //内存泄露监控
        RefWatcher refWatcher = BaseApplication.getRefWatcher(mContext);
        refWatcher.watch(this);
    }


}
