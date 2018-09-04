package com.zyf.fwms.commonlibrary.base;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.squareup.leakcanary.RefWatcher;
import com.zyf.fwms.commonlibrary.R;
import com.zyf.fwms.commonlibrary.databinding.FragmentBaseBinding;
import com.zyf.fwms.commonlibrary.http.HttpTask;
import com.zyf.fwms.commonlibrary.http.HttpUtils;
import com.zyf.fwms.commonlibrary.utils.Constants;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.zyf.fwms.commonlibrary.http.Api;
import com.zyf.fwms.commonlibrary.model.RxCodeConstants;
import com.zyf.fwms.commonlibrary.utils.AutoUtils;
import com.zyf.fwms.commonlibrary.utils.CommonUtils;
import com.zyf.fwms.commonlibrary.utils.LogUtil;
import com.zyf.fwms.commonlibrary.utils.RxBus;
import com.zyf.fwms.commonlibrary.utils.SharedPreUtil;
import com.zyf.fwms.commonlibrary.utils.TUtil;
import com.zyf.fwms.commonlibrary.utils.UIHelper;
import com.zyf.fwms.commonlibrary.view.dialog.LoadingDialog;

import butterknife.ButterKnife;
import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * 刘宇飞创建 on 2017/5/18.
 * 描述：
 */
public abstract class BaseMVPFragment<T extends BasePresenter, SV extends ViewDataBinding, V extends BaseView> extends BaseFragment  {
    //布局view
    protected SV mBindingView;
    // 内容布局
    protected RelativeLayout mContainer;
    //加载失败
    protected HttpTask mHttpTask;
    public T mPresenter;
    private FragmentBaseBinding mBaseBinding;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (CommonUtils.isEmpty(Api.HOST_URL)) Api.initApi();
        mHttpTask = HttpUtils.getInstance().createRequest(HttpTask.class);
        try {
            mPresenter = TUtil.getT(this, 0);
        }catch (Exception e){
            LogUtil.getInstance().e(e.getMessage());
        }
        if (mPresenter != null) {
            mPresenter.httpTask = mHttpTask;
            mPresenter.mContext = mContext;
            mPresenter.setView((V) this);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBaseBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_base, container, false);
        mBindingView = DataBindingUtil.inflate(getActivity().getLayoutInflater(), setContent(), null, false);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mBindingView.getRoot().setLayoutParams(params);
        mContainer = mBaseBinding.container;
        mContainer.addView(mBindingView.getRoot());
        mContext = getContext();
        ButterKnife.bind(this, mBaseBinding.getRoot());
        initLisener();
        AutoUtils.auto(mBaseBinding.getRoot());
        initView();

        initRxbus();
        initData();
        return mBaseBinding.getRoot();
    }


    protected abstract void initView();



    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    private void initLisener() {
        mBaseBinding.llErrorRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });

    }



    /**
     * 布局
     */
    public abstract int setContent();

    /**
     * 加载失败后点击后的操作
     */
    public abstract void initData() ;



    /**
     * 显示错误页面或正常页面
     */
    public void showErroView(boolean isShow) {
        if (isShow) {
            mBaseBinding.llErrorRefresh.setVisibility(View.VISIBLE);
            mBindingView.getRoot().setVisibility(View.GONE);
        } else {
            mBaseBinding.llErrorRefresh.setVisibility(View.GONE);
            mBindingView.getRoot().setVisibility(View.VISIBLE);
        }
    }



    private void initRxbus() {

        //登录后操作
        Subscription subscribe3 = RxBus.getDefault().toObservable(RxCodeConstants.USER_LOGIN, Integer.class)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer entity) {
                        laterLogin();
                    }

                });
        addSubscription(subscribe3);
    }


    /**
     * 登录后续操作
     */
    public void laterLogin() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //防止内存泄露
        if (mPresenter != null) {
            mPresenter.onDestroy();
            mPresenter = null;
        }
    }


}
