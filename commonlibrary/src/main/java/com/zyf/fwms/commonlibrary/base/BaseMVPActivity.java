package com.zyf.fwms.commonlibrary.base;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.squareup.leakcanary.RefWatcher;
import com.zyf.fwms.commonlibrary.R;
import com.zyf.fwms.commonlibrary.databinding.ActivityBaseBinding;
import com.zyf.fwms.commonlibrary.http.HttpTask;
import com.zyf.fwms.commonlibrary.http.HttpUtils;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.zyf.fwms.commonlibrary.http.Api;
import com.zyf.fwms.commonlibrary.model.RxCodeConstants;
import com.zyf.fwms.commonlibrary.swipeback.SwipeBackLayout;
import com.zyf.fwms.commonlibrary.utils.VirtualKeyUtils;
import com.zyf.fwms.commonlibrary.utils.AutoUtils;
import com.zyf.fwms.commonlibrary.utils.CommonUtils;
import com.zyf.fwms.commonlibrary.utils.LogUtil;
import com.zyf.fwms.commonlibrary.utils.RxBus;
import com.zyf.fwms.commonlibrary.utils.StatusBarUtil;
import com.zyf.fwms.commonlibrary.utils.TUtil;
import com.zyf.fwms.commonlibrary.utils.statusbar.StatusBarFontHelper;
import com.zyf.fwms.commonlibrary.view.dialog.LoadingDialog;

import butterknife.ButterKnife;
import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;


/**
 * 刘宇飞创建 on 2017/5/18.
 * 描述：
 */
public abstract class BaseMVPActivity<E extends BasePresenter, SV extends ViewDataBinding, V extends BaseView> extends BaseActivity  {
    //布局view
    protected SV mBindingView;
    protected Context mContext;
    protected HttpTask mHttpTask;
    public E mPresenter;
    public ActivityBaseBinding mBaseBinding;
    private SwipeBackLayout swipeBackLayout;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (CommonUtils.isEmpty(Api.HOST_URL)) Api.initApi();
        mHttpTask = HttpUtils.getInstance().createRequest(HttpTask.class);
        try {
            mPresenter = TUtil.getT(this, 0);
            if (mPresenter != null) {
                mPresenter.mContext = this;
                mPresenter.httpTask = mHttpTask;
                mPresenter.setView((V) this);
            }
        } catch (Exception e) {
            LogUtil.getInstance().e(e.getMessage());
        }
        setContentView(getLayoutId());



    }


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        setUserView(layoutResID);
        //根据设计稿设定 preview 切换至对应的尺寸
        AutoUtils.setSize(this, false, 750, 1335);
        //自适应页面
        AutoUtils.auto(this);
        initView();
        //刷新页面
        initData();
    }



    /**
     * 设置显示的布局
     */
    private void setUserView(@LayoutRes int layoutResID) {
        //判断是否被后台杀死
        if (BaseApplication.status == 0) {
            CommonUtils.restartAPP(this, 100);
            return;
        }
        //标题栏已经在activity_base 不用到每个布局里面添加
        mBaseBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_base, null, false);
        mBindingView = DataBindingUtil.inflate(getLayoutInflater(), layoutResID, null, false);
        // content
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mBindingView.getRoot().setLayoutParams(params);
        RelativeLayout mContainer = mBaseBinding.container;
        mContainer.addView(mBindingView.getRoot(), 0);
        //添加侧滑返回
        swipeBackLayout = new SwipeBackLayout(this);
        swipeBackLayout.addView(mBaseBinding.getRoot());
        getWindow().setContentView(swipeBackLayout);
        ButterKnife.bind(this);
        initLisener();
        mContext = this;
    }


    @Override
    protected void onResume() {
        super.onResume();
        //判断是否被后台杀死
        if (BaseApplication.status == 0) {
            CommonUtils.restartAPP(mContext, 100);
            return;
        }
    }



    private void initLisener() {
        mBaseBinding.llErrorRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });
        mBaseBinding.commonTitle.llLiftBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    /**
     * 隐藏标题下划线
     */
    protected void hideTitleLine() {
        mBaseBinding.commonTitle.tvCommonLine.setVisibility(View.GONE);
    }

    /**
     * 隐藏标题栏
     */
    protected void hideTitleBar() {
        mBaseBinding.commonTitle.rlTitleBar.setVisibility(View.GONE);
    }

    /**
     * 显示标题栏
     */
    protected void showTitleBar() {
        mBaseBinding.commonTitle.rlTitleBar.setVisibility(View.VISIBLE);
    }

    /**
     * 设置标题透明背景
     */
    protected void titleBarColor(int color) {
        mBaseBinding.commonTitle.rlTitleBar.setBackgroundColor(CommonUtils.getColor(mContext, color));
    }

    /**
     * 隐藏返回箭头
     */
    protected void hideBackImg() {
        mBaseBinding.commonTitle.llLiftBack.setVisibility(View.GONE);
    }

    /**
     * 设置标题
     */
    protected void setTitle(String title) {
       // mBaseBinding.commonTitle.tvTitle.setTypeface(Typeface.DEFAULT,Typeface.BOLD);
        mBaseBinding.commonTitle.tvTitle.setText(CommonUtils.isNotEmpty(title) ? title : "");
    }

    /**
     * 设置标题
     */
    protected String getUTitle() {
        return mBaseBinding.commonTitle.tvTitle.getText().toString();
    }

    /**
     * 设置右侧文字
     */
    protected void setRightTitle(String rightTitle, View.OnClickListener listener) {
        mBaseBinding.commonTitle.tvRightText.setText(CommonUtils.isNotEmpty(rightTitle) ? rightTitle : "");
        mBaseBinding.commonTitle.llRightText.setVisibility(View.VISIBLE);
        mBaseBinding.commonTitle.llRightImg.setVisibility(View.GONE);
        if (listener != null) {
            mBaseBinding.commonTitle.llRightText.setOnClickListener(listener);
        }
    }

    /**
     * 设置右侧图片
     */
    protected void setRightImg(int img, View.OnClickListener listener) {
        mBaseBinding.commonTitle.llRightText.setVisibility(View.GONE);
        mBaseBinding.commonTitle.llRightImg.setVisibility(View.VISIBLE);
        if (img > 0) {
            mBaseBinding.commonTitle.ivRightImg.setImageResource(img);
        }
        if (listener != null) {
            mBaseBinding.commonTitle.llRightImg.setOnClickListener(listener);
        }
    }

    /**
     * 隐藏右侧购物车
     */
    protected void hideRightImg() {
        mBaseBinding.commonTitle.llRightImg.setVisibility(View.GONE);
    }

    /**
     * 设置右侧图片
     */
    protected void setRightImg(int img, View.OnClickListener listener, float width, float height) {
        mBaseBinding.commonTitle.llRightText.setVisibility(View.GONE);
        mBaseBinding.commonTitle.llRightImg.setVisibility(View.VISIBLE);
        if (img > 0) {
            mBaseBinding.commonTitle.ivRightImg.setImageResource(img);
        }
        if (listener != null) {
            mBaseBinding.commonTitle.llRightImg.setOnClickListener(listener);
        }
        if (width > 0 && height > 0) {
            ViewGroup.LayoutParams layoutParams = mBaseBinding.commonTitle.ivRightImg.getLayoutParams();
            layoutParams.width = CommonUtils.dip2px(mContext, width);
            layoutParams.height = CommonUtils.dip2px(mContext, height);
            mBaseBinding.commonTitle.ivRightImg.setLayoutParams(layoutParams);
        }
    }

    protected abstract void initView();

    protected abstract int getLayoutId();

    /**
     * 失败后点击刷新
     */
    public abstract void initData();

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
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Glide.get(getApplicationContext()).clearMemory();
        hideInfoProgressDialog();
        if (mPresenter != null) {
            mPresenter.onDestroy();
            mPresenter = null;
        }

    }
    /**
     * 获取侧滑返回布局
     */
    public SwipeBackLayout getSwipeBackLayout() {
        return swipeBackLayout;
    }



}
