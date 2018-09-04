package com.zyf.fwms.commonlibrary.x5webview;

import com.zyf.fwms.commonlibrary.base.BasePresenter;
import com.zyf.fwms.commonlibrary.base.BaseView;

/**
 * Created by 刘宇飞 on 2017/8/15.
 * 邮箱：3494576680@qq.com
 * 描述：
 */

public interface UrlWebViewActivityContact {
    interface View extends BaseView {

        /**
         * 加载url
         */
        void loadUrl(String url);
        /**
         * 切换滚动标题和固定标题
         */
        void changeTitleType(boolean fixed);


    }

    abstract class Presenter extends BasePresenter<View> {




    }
}
