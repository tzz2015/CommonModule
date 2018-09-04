package com.zyf.fwms.commonlibrary.utils;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;

import com.zyf.fwms.commonlibrary.base.BaseMVPFragment;


/**
 * Created by 刘宇飞 on 2017/8/22.
 * 邮箱：3494576680@qq.com
 * 描述：提供js调用原生
 */

public class UIHelper {



    /**
     * 今日fragment
     */
    public static BaseMVPFragment getTodayFragment(){
        return (BaseMVPFragment) ARouter.getInstance().build(Constants.TODAY_FRAGMENT).navigation();
    }

    /**
     * 今日fragment
     */
    public static BaseMVPFragment getCurseHomeFragment(){
        return (BaseMVPFragment) ARouter.getInstance().build(Constants.CURSE_HOME_FRAGMENT).navigation();
    }
    /**
     * 会员fragment
     */
    public static BaseMVPFragment getMemberFragment(){
        return (BaseMVPFragment) ARouter.getInstance().build(Constants.MEMBER_FRAGMENT).navigation();
    }
    /**
     * 非会员fragment
     */
    public static BaseMVPFragment getNorMemberFragment(){
        return (BaseMVPFragment) ARouter.getInstance().build(Constants.NOR_MEMBER_FRAGMENT).navigation();
    }
    /**
     * 我的fragment
     */
    public static BaseMVPFragment getMineFragment(){
        return (BaseMVPFragment) ARouter.getInstance().build(Constants.MINE_FRAGMENT).navigation();
    }
    /**
     * 进入视频详情
     */
    public static void enterVideoDetail(){
        ARouter.getInstance()
                .build(Constants.VIDEO_DETAIL)
                .navigation();
    }

    /**
     * 视频播放中间层
     */
    public static void enterMiddleVideo(Activity activity, View view,int type){
        Rect imgRect = new Rect();
        view.getGlobalVisibleRect(imgRect);

        Pair pair = new Pair<>(view, Constants.IMG_TRANSITION);
        ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity, pair);
        Bundle bundle = activityOptions.toBundle();
        ARouter.getInstance().build(Constants.VIDEO_MIDDLE_JUMP)
                .withBundle("view",bundle)
                .withInt("left",imgRect.left)
                .withInt("top",imgRect.top)
                .withInt("width",imgRect.width())
                .withInt("height",imgRect.height())
                .withInt("type",type)
                .navigation();

    }
    /**
     * 加载网页
     */
    public static void enterWebView(String url,boolean fixed){
        ARouter.getInstance()
                .build(Constants.X5_WEBVIEW)
                .withString("URL",url)
                .withBoolean("fixed",fixed)
                .navigation();
    }
    /**
     * 设置界面
     */
    public static void enterSetting(){
        ARouter.getInstance()
                .build(Constants.SETTING_VIEW)
                .navigation();
    }
    /**
     * 微信登录
     */
    public static void wxLogin(){
        ARouter.getInstance()
                .build(Constants.LOGIN_VIEW)
                .navigation();
    }
    /**
     * 课程列表
     */
    public static void enterCurseList(int position,int deptId,String title){
        ARouter.getInstance()
                .build(Constants.CURSE_LIST)
                .withInt("position",position)
                .withInt("deptId",deptId)
                .withString("title",title)
                .navigation();
    }

    /**
     * 进入视频详情
     */
    public static void enterCurseDetail(int id){
        ARouter.getInstance()
                .build(Constants.CURSE_DETAIL_M)
                .withInt("id",id)
                .navigation();
    }


    /**
     * 输入验证码
     */
    public static void enterInputCode(String phone,int type){
        ARouter.getInstance()
                .build(Constants.INPUT_CODE)
                .withInt("type",type)
                .withString("phone",phone)
                .navigation();
    }

    /**
     * 签到
     */
    public static void enterSign(){
        ARouter.getInstance()
                .build(Constants.MINE_SIGN)
                .navigation();
    }





}
