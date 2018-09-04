package com.zyf.fwms.commonlibrary.http;

import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.Map;

import okhttp3.RequestBody;

/**
 * 杭州融科网络
 * 刘宇飞创建 on 2017/6/28.
 * 描述：
 */

public class CommonRequstUtils {
    private static int mTime = 60;//重新获取验证码时间
    private static TextView textView;
    private static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mTime > 0) {
                mTime--;
                if (textView != null) {
                    reduceTime();
                    textView.setText("重新获取(" + mTime + ")");
                    textView.setClickable(false);
                }
            } else {
                if (textView != null) {
                    textView.setText("重新获取");
                    textView.setClickable(true);
                }

            }


        }
    };

    /**
     * 一秒钟减去1
     */
    private static void reduceTime() {
        if(handler==null) return;
        handler.sendEmptyMessageDelayed(0, 1000);
    }

    public static void endCodeSend() {
        if (handler != null && textView != null) {
            handler.removeMessages(0);
            textView = null;
        }
        mHttpTask=null;

    }


    /**
     * 将map转换成requestbody
     */
    public static RequestBody turnMapToBody(Map<String,Object> map){
     if(map==null) return null;
        Gson gson = HttpUtils.getInstance().getGson();
        String json = gson.toJson(map);
        return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),json);
    }


    protected volatile static HttpTask mHttpTask;

    public static HttpTask getHttpTask() {
        if (mHttpTask == null) {
            synchronized (CommonRequstUtils.class) {
                if (mHttpTask == null) {
                    mHttpTask = HttpUtils.getInstance().createRequest(HttpTask.class);
                }
            }
        }
        return mHttpTask;
    }



}
