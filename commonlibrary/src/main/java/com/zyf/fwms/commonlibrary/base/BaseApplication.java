package com.zyf.fwms.commonlibrary.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.BuildConfig;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsListener;
import com.zyf.fwms.commonlibrary.http.Api;
import com.zyf.fwms.commonlibrary.utils.CrashHandler;
import com.zyf.fwms.commonlibrary.utils.LogUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * 创建 by lyf on 27/02/2018.
 * 描述：
 */

public class BaseApplication extends Application {
    private static List<Activity> activityList = new ArrayList<>();


    /**
     * 全局上下文
     */
    private static Context context;
    /**
     * 判断是否被后台杀死
     *
     * @param context
     */
    public static int status = 0;


    public static RefWatcher getRefWatcher(Context context) {
        BaseApplication application = (BaseApplication) context.getApplicationContext();
        return application.refWatcher;
    }

    private RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        status = 1;

        //全局异常捕获
        CrashHandler.getInstance().init(this);
        context = getApplicationContext();
        //初始化ARouter
        initARouter();
        //是否打印log
        initLogger();
        //内存检查
        initlLeakcanary();
        //初始化x5内核
        initX5();

    }

    private void initLogger() {

        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return !Api.isOnLine;
            }
        });
    }

    /**
     * 初始化ARouter
     */
    private void initARouter() {
        ARouter.openLog();     // 打印日志
        ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        ARouter.init(this);
    }


    /**
     * 内存检查
     */
    private void initlLeakcanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        refWatcher = LeakCanary.install(this);
    }

    /**
     * 初始化腾讯X5内核
     */
    private void initX5() {
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                LogUtil.getInstance().e("apptbs" + " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub

            }
        };
        QbSdk.setTbsListener(new TbsListener() {
            @Override
            public void onDownloadFinish(int i) {
                LogUtil.getInstance().e("apptbs" + "onDownloadFinish");
            }

            @Override
            public void onInstallFinish(int i) {
                LogUtil.getInstance().e("apptbs" + "onInstallFinish");
            }

            @Override
            public void onDownloadProgress(int i) {
                LogUtil.getInstance().e("apptbs" + "onDownloadProgress:" + i);
            }
        });

        QbSdk.initX5Environment(getApplicationContext(), cb);
    }


    public static Context getContext() {
        return context;
    }

    /**
     * 添加activity
     */
    public static void addActivity(Activity activity) {
        if (!activityList.contains(activity)) {
            activityList.add(activity);
        }
    }

    /**
     * 移除activity
     */
    public static void removeActivity(Activity activity) {
        if (activityList.contains(activity)) {
            activityList.remove(activity);
        }
    }

    /**
     * 关闭activity
     */
    public static void closeActivity(Activity at) {
        for (Activity activity : activityList) {
            if (activity != at && activity != null) {
                activity.finish();
            }

        }
    }

}
