package com.zyf.fwms.commonlibrary.http;

import android.content.Context;

import com.example.xrecyclerview.CheckNetwork;
import com.zyf.fwms.commonlibrary.base.BaseActivity;
import com.zyf.fwms.commonlibrary.base.BaseApplication;
import com.zyf.fwms.commonlibrary.model.BaseRespose;
import com.zyf.fwms.commonlibrary.model.mine.AccountInfo;
import com.zyf.fwms.commonlibrary.utils.CommonUtils;
import com.zyf.fwms.commonlibrary.utils.LogUtil;


import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * 刘宇飞 创建 on 2017/5/20.
 * 描述：
 */

public class HttpRequestUtils<T> implements HttpBuilder<T> {
    private Observable observable;
    private int requstId;
    private HttpTaskListener<T> listener;
    private Context context;
    private static HttpRequestUtils httpRequestUtils;
    private boolean isShowDialog = true;

    public static HttpRequestUtils getInstance() {
        httpRequestUtils=new HttpRequestUtils();
        return httpRequestUtils;
    }


    private void request() {
        //避免没有必要的请求
        if (!CheckNetwork.isNetworkConnected(BaseApplication.getContext())) {
            CommonUtils.showToast(BaseApplication.getContext(), "无网络连接,请检查网络");
            if (listener != null) {
                listener.onException(requstId);
            }
            return ;
        }
        if (context != null && isShowDialog) {
            CommonUtils.getInstance().showInfoProgressDialog(context);
        }
        observable.subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .compose(((BaseActivity)context).bindToLifecycle())
               .subscribe(new Observer<BaseRespose<T>>() {
                   @Override
                   public void onSubscribe(Disposable d) {

                   }

                   @Override
                   public void onNext(BaseRespose<T> baseResponseVo) {
                       CommonUtils.getInstance().hideInfoProgressDialog();
                       if (listener != null) {
                           if (baseResponseVo.code == 0) {//请求成功
                               listener.onSuccess(requstId, baseResponseVo.data);
                           } else {
                               String notify=baseResponseVo.msg;
                               if(baseResponseVo.code==1000||baseResponseVo.code==3207){
                                   notify="登录已经过期，请重新登录";
                                   AccountInfo.getInstance().removeAccountInfo();
                               }
                               CommonUtils.showToast(BaseApplication.getContext(), notify);
                               listener.onException(requstId);
                           }

                       }
                       context = null;
                   }
                   @Override
                   public void onError(Throwable e) {
                       LogUtil.getInstance().e("请求id:" + requstId + "--" + e.getMessage());
                       CommonUtils.getInstance().hideInfoProgressDialog();
                       CommonUtils.showToast("网络出了点问题");
                       if (listener != null) {
                           listener.onException(requstId);
                       }

                       context = null;
                   }

                   @Override
                   public void onComplete() {
                       CommonUtils.getInstance().hideInfoProgressDialog();
                       context = null;
                       httpRequestUtils=null;
                   }
               });




    }

    @Override
    public HttpBuilder setRequsetId(int requsetId) {
        this.requstId = requsetId;
        return this;
    }

    @Override
    public HttpBuilder setContext(Context context) {
        this.context = context;
        return this;
    }

    @Override
    public HttpBuilder setObservable(Observable observable) {
        this.observable = observable;
        return this;
    }


    @Override
    public HttpBuilder setCallBack(HttpTaskListener listener) {
        this.listener = listener;
        return this;
    }

    @Override
    public void create() {
        if (observable == null) {
            LogUtil.getInstance().e("请设置observable");
            if (listener != null) listener.onException(requstId);
            return ;
        } else {
             request();
        }

    }

    @Override
    public HttpBuilder isShowDialog(boolean isShow) {
        this.isShowDialog = isShow;
        return this;
    }


}
