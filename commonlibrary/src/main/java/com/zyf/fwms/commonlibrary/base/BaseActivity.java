package com.zyf.fwms.commonlibrary.base;

import android.media.AudioManager;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.Window;

import com.bumptech.glide.Glide;
import com.squareup.leakcanary.RefWatcher;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.trello.rxlifecycle2.components.support.RxFragmentActivity;
import com.zyf.fwms.commonlibrary.R;
import com.zyf.fwms.commonlibrary.model.RxCodeConstants;
import com.zyf.fwms.commonlibrary.utils.CommonUtils;
import com.zyf.fwms.commonlibrary.utils.LogUtil;
import com.zyf.fwms.commonlibrary.utils.RxBus;
import com.zyf.fwms.commonlibrary.utils.StatusBarUtil;
import com.zyf.fwms.commonlibrary.utils.VirtualKeyUtils;
import com.zyf.fwms.commonlibrary.utils.statusbar.StatusBarFontHelper;
import com.zyf.fwms.commonlibrary.view.dialog.LoadingDialog;


import butterknife.ButterKnife;
import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * 创建 by lyf on 2018/9/3.
 * 描述：
 */

public class BaseActivity extends RxFragmentActivity implements BaseView {
    private boolean isNeedTransition = true;
    private CompositeSubscription mCompositeSubscription;
    private RxPermissions rxPermissions;
    private LoadingDialog mProgressDialog;



    @Override
    protected void onStart() {
        super.onStart();
        if (isNeedTransition)
            overridePendingTransition(R.anim.slide_right_entry, R.anim.slide_right_exit);
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //打印类名
        LogUtil.getInstance().e(getClass().toString());

        Glide.get(getApplicationContext()).clearMemory();
        //设置音乐播放声音与手机媒体同步
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        //把activity加入队列
        BaseApplication.addActivity(this);
        initRxbus();
        setStuteColor();


    }
    private void initRxbus() {

        //登录后操作
        Subscription subscribe3 = RxBus.getDefault()
                .toObservable(RxCodeConstants.USER_LOGIN, Integer.class)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer entity) {
                        laterLogin();
                    }

                });
        addSubscription(subscribe3);
    }
    public void setStuteColor() {
        int barMode = StatusBarFontHelper.setStatusBarMode(this, true);
        //如果设置黑色字体成功 设置白色底 否则设置灰色底
        if (barMode != 0)
            StatusBarUtil.setColor(this, CommonUtils.getColor(this, com.zyf.fwms.commonlibrary.R.color.colorWhite), 0);
        else
            StatusBarUtil.setColor(this, CommonUtils.getColor(this, com.zyf.fwms.commonlibrary.R.color.color999999), 0);
        checkNavigationBar();
    }

    public void checkNavigationBar(){
        //判断是否有虚拟键
        boolean navigationBar = CommonUtils.checkDeviceHasNavigationBar(this);
        //虚拟键适配
        if (navigationBar) VirtualKeyUtils.getInstance().init(findViewById(android.R.id.content));
    }

    /**
     * 登录后续操作
     */
    public void laterLogin() {

    }



    public void setNeedTransition(boolean isNeedTransition) {
        this.isNeedTransition = isNeedTransition;
    }

    /**
     * 显示进度框
     */
    @Override
    public final void showInfoProgressDialog(String... arg) {
        if (mProgressDialog == null) {
            mProgressDialog = new LoadingDialog(this);
        }

        if (!mProgressDialog.isShowing())
            mProgressDialog.show();

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
     * toast
     *
     * @param title
     */
    @Override
    public void showToast(String title) {
        CommonUtils.showToast(CommonUtils.getContext(), title);

    }
    /**
     * 请求权限
     */
    public void requestPermission(String[] permissions) {
        if (rxPermissions == null) {
            rxPermissions = new RxPermissions(this);
        }
        Subscription subscribe = rxPermissions
                .request(permissions)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        requestPermissionCallBack(aBoolean);
                    }
                });
        addSubscription(subscribe);
    }
    /**
     * 请求结果
     *
     * @param aBoolean
     */
    protected void requestPermissionCallBack(Boolean aBoolean) {
        // RxBus.getDefault().post(RxCodeConstants.SHOW_TOAST,"请求权限结果:"+aBoolean);
    }


    /**
     * 添加fragment
     *
     * @param fragment
     * @param frameId
     */
    protected void addFragment(BaseMVPFragment fragment, @IdRes int frameId) {
        if (fragment == null) return;
        getSupportFragmentManager().beginTransaction()
                .add(frameId, fragment, fragment.getClass().getSimpleName())
                .commitAllowingStateLoss();

    }

    /**
     * 替换fragment
     *
     * @param fragment
     * @param frameId
     */
    protected void replaceFragment(BaseMVPFragment fragment, @IdRes int frameId) {
        if (fragment == null) return;
        getSupportFragmentManager().beginTransaction()
                .replace(frameId, fragment, fragment.getClass().getSimpleName())
                .commitAllowingStateLoss();

    }
    /**
     * 添加网络请求观察者
     *
     * @param s
     */
    public void addSubscription(Subscription s) {
        if (s == null) {
            return;
        }
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
    public void finish() {
        super.finish();
        if (isNeedTransition)
            overridePendingTransition(R.anim.slide_right_entry, R.anim.slide_right_exit);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeSubscription();
        ButterKnife.bind(this);

        //内存泄露监控
        RefWatcher refWatcher = BaseApplication.getRefWatcher(this);
        refWatcher.watch(this);
        //把activity加入队列
        BaseApplication.removeActivity(this);



    }
}
