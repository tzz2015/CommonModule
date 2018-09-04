package com.zyf.fwms.commonlibrary.http;

import android.content.Context;

import io.reactivex.Observable;
import rx.Subscription;

/**
 *
 * 刘宇飞 创建 on 2017/5/20.
 * 描述：
 */

public interface HttpBuilder<T> {
    HttpBuilder setRequsetId(int requsetId);

    HttpBuilder setContext(Context context);

    HttpBuilder setObservable(Observable observable);


    void create();

    HttpBuilder  setCallBack(HttpTaskListener<T> listener);

    HttpBuilder isShowDialog(boolean isShow);



}
