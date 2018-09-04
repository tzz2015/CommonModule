package com.zyf.fwms.commonlibrary.x5webview;

import android.content.Context;
import android.view.View;

import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.zyf.fwms.commonlibrary.base.BaseMVPActivity;
import com.zyf.fwms.commonlibrary.http.HttpUtils;
import com.zyf.fwms.commonlibrary.model.mine.AccountInfo;
import com.zyf.fwms.commonlibrary.utils.CommonUtils;
import com.zyf.fwms.commonlibrary.utils.LogUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 创建 by lyf on 23/03/2018.
 * 描述：
 */

public class X5WebViewUtils {

    private BridgeWebView webView;


    public X5WebViewUtils() {

    }




    /**
     * 获取webview实例
     */
    public BridgeWebView getWebView(Context context) {
        webView = new BridgeWebView(context.getApplicationContext());
        initSetting(webView, context);
        return webView;

    }



    /**
     * 销毁所有的webView
     */
    public void destroyWebView() {

        if (webView != null) {
            webView.setVisibility(View.GONE);
            WebSettings settings = webView.getSettings();
            settings.setSupportZoom(false);
            settings.setBuiltInZoomControls(false);
            webView.removeAllViews();
            webView.destroy();
        }
        webView = null;

    }


    public void initSetting(BridgeWebView webView, final Context context) {


        if (webView == null) return;
        final WebSettings settings = webView.getSettings();
        String appCachePath = CommonUtils.getContext().getCacheDir().getAbsolutePath();
        settings.setAppCachePath(appCachePath);
        settings.setAppCacheMaxSize(Long.MAX_VALUE);// 实现8倍缓存
        // 设置可以访问文件
        settings.setAllowFileAccess(true);
        //如果访问的页面中有Javascript，则webview必须设置支持Javascript
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setAppCacheEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setUserAgentString(settings.getUserAgentString() + "Qitou_App(Android" + CommonUtils.getVersionName(CommonUtils.getContext()) + ")");
        settings.setGeolocationDatabasePath(CommonUtils.getContext().getDir("geolocation", 0).getPath());
        //支持缩放
        settings.setBuiltInZoomControls(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        //提高渲染优先级
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        ((BaseMVPActivity) context).showInfoProgressDialog();
        settings.setBlockNetworkImage(true);
        webView.setWebViewClient(new BridgeWebViewClient(webView, context) {
            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);
                ((BaseMVPActivity) context).hideInfoProgressDialog();
                settings.setBlockNetworkImage(false);
            }
        });
        callHandler(webView, 1);


    }

    /**
     * 原生调h5
     *
     * @param type
     */
    public void callHandler(BridgeWebView webView, int type) {
        if (type == 1) {
            //用户登录
            Map<String, String> map = new HashMap<>();
            map.put("token", AccountInfo.getInstance().getToken());
            webView.callHandler("MCTUserToken", HttpUtils.getInstance().getGson().toJson(map), new CallBackFunction() {
                @Override
                public void onCallBack(String data) {
                    LogUtil.getInstance().e("登录回调：" + data);
                }
            });
        }


    }


}
