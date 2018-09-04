package com.zyf.fwms.commonlibrary.base;

/**
 * 公司：
 * 刘宇飞 创建 on 2017/3/6.
 * 描述：view 基类
 */

public interface BaseView {

    /**
     * 显示加载动画
     */
    void showInfoProgressDialog(String... arg);

    /**
     * 隐藏加载动画
     */
    void hideInfoProgressDialog();
    /**
     * toast
     */
    void showToast(String title);
}
