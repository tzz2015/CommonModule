package com.zyf.fwms.commonlibrary.utils;

import android.support.annotation.NonNull;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * 创建 by lyf on 23/03/2018.
 * 描述：
 */

public class RxTimerUtil {


    private static Subscription timerSubscribe;
    private  Subscription interSubscribe;

    /**
     * milliseconds毫秒后执行next操作
     *
     * @param milliseconds
     * @param next
     */
    public static void timer(long milliseconds, final IRxNext next) {

        //取消订阅
        timerSubscribe = Observable.timer(milliseconds, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {


                    @Override
                    public void onNext(@NonNull Long number) {

                        if (next != null) {
                            try {
                                next.doNext(number);

                            } catch (Exception e) {
                                cancelTimer();
                                LogUtil.getInstance().e(e.getMessage());
                            }
                        }
                    }

                    /**
                     * Notifies the Observer that the {@link Observable} has finished sending push-based notifications.
                     * <p>
                     * The {@link Observable} will not call this method if it calls {@link #onError}.
                     */
                    @Override
                    public void onCompleted() {
                        //取消订阅
                        cancelTimer();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        //取消订阅
                        cancelTimer();
                    }


                });
    }


    /**
     * 每隔milliseconds毫秒后执行next操作
     *
     * @param milliseconds
     * @param next
     */
    public  void interval(long milliseconds, final IRxNext next) {
        interSubscribe = Observable.interval(milliseconds, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {


                    @Override
                    public void onNext(@NonNull Long number) {
                        if (next != null) {
                            try {
                                next.doNext(number);

                            } catch (Exception e) {
                                cancelInterval();
                                LogUtil.getInstance().e(e.getMessage());
                            }
                        }
                    }

                    /**
                     * Notifies the Observer that the {@link Observable} has finished sending push-based notifications.
                     * <p>
                     * The {@link Observable} will not call this method if it calls {@link #onError}.
                     */
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        cancelInterval();
                    }


                });
    }



    public static void cancelTimer() {

        if (timerSubscribe != null && !timerSubscribe.isUnsubscribed()) {
            timerSubscribe.unsubscribe();
            timerSubscribe = null;
        }
    }

    public  void cancelInterval() {
        if (interSubscribe != null && !interSubscribe.isUnsubscribed()) {
            interSubscribe.unsubscribe();
            interSubscribe = null;
        }

    }

    public interface IRxNext {
        void doNext(long number);
    }
}





