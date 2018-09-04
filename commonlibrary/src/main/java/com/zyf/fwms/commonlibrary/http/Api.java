package com.zyf.fwms.commonlibrary.http;

/**
 * 刘宇飞 创建 on 2017/5/15.
 * 描述：url 存放库
 */

public class Api {

    public static boolean isTest = false;//是否为测试环境

    public static boolean isOnLine=false;// 是否为线上版本

    public static String HOST_URL = "";//



    public static void initApi() {
        HOST_URL = isTest ? "http://test.qitoujia.mobi:9999/api/" : "http://www.qitoujia.mobi:8080/api/";
    }

    /**
     * 我的听课券
     */
    public static final String MY_COUPON_LIST="http://m.dtsxst.cn/test/couponList";
    /**
     * 资讯
     */
    public static final  String NEWS="http://m.dtsxst.cn/test/today/news/%s";
    /**
     * 活动
     */
    public static final String ACTIVITY="http://m.dtsxst.cn/test/activity/detail/%s";
    /**
     * 今日
     */
    public static final String TODAY_LIST="module/today";
    /**
     * 微信登录
     */
    public static final String WX_LOGIN="oauth/wechat/mobile/login";
    /**
     * 用户信息
     */
    public static final String USER_INFO="user/me";
    /**
     * 会员信息
     */
    public static final String MENBER_INFO="user/membership/mine";
    /**
     * 会员学院列表
     */
    public static final String DEPT_LIST="dept/list";
    /**
     * 学院全部课程
     */
    public static final String ALL_CURSE="course/all";
    /**
     * 课程分类
     */
    public static final String CATE_LIST="course/cate/list";
    /**
     * 课程列表 知识模块下
     */
    public static final String NEWS_LIST="news/list";
    /**
     * 获取分类和课程数量
     */
    public static final String CURSE_COUNT="course/count";
    /**
     * 课程详情
     */
    public static final String CURSE_DETAIL="course/";
    /**
     * 课程视频列表
     */
    public static final String CURSE_VIDEO_LIST="course/%s/albums";
    /**
     * 手机登录发送验证码
     */
    public static final String SEND_PHONE_CODE="account/phone/login/code";
    /**
     * 手机修改发送验证码
     */
    public static final String SEND_CHANGE_PHONE_CODE="account/phone/change/code";
    /**
     * 手机登录
     */
    public static final String PHONE_LOGIN="oauth/mobile/login";
    /**
     * 手机绑定
     */
    public static final String PHONE_BIND="account/phone/change";
    /**
     * 绑定微信
     */
    public static final String WX_BIND="user/bindWechat";




}
