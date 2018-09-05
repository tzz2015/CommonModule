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



    public static void enterA(){
        ARouter.getInstance()
                .build(Constants.AA)
                .navigation();
    }

    public static void enterB(){
        ARouter.getInstance()
                .build(Constants.BA)
                .navigation();
    }



}
