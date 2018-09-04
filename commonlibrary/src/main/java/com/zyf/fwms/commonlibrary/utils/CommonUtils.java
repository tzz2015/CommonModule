package com.zyf.fwms.commonlibrary.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.text.format.DateUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.tencent.smtt.sdk.WebView;
import com.zyf.fwms.commonlibrary.R;
import com.zyf.fwms.commonlibrary.base.BaseApplication;
import com.zyf.fwms.commonlibrary.model.RxCodeConstants;
import com.zyf.fwms.commonlibrary.server.killSelfService;
import com.zyf.fwms.commonlibrary.view.dialog.LoadingDialog;
import com.zyf.fwms.commonlibrary.x5webview.X5WebView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jingbin on 2016/11/22.
 * 获取原生资源
 */
public class CommonUtils {
    private LoadingDialog mProgressDialog;//进度窗体
    private static volatile CommonUtils commonUtils;//构建单例模式
    private CompositeSubscription mCompositeSubscription; //网络管理器
    private static Gson mGson;

    public static CommonUtils getInstance() {
        if (commonUtils == null) {
            synchronized (CommonUtils.class) {
                if (commonUtils == null) {
                    commonUtils = new CommonUtils();
                }
            }
        }
        return commonUtils;
    }


    /**
     * 获取gson
     */
    public static Gson getGson() {
        if (mGson != null) {
            mGson = new Gson();
        }
        return mGson;
    }

    /**
     * 随机颜色
     */
    public static int randomColor() {
        Random random = new Random();
        int red = random.nextInt(150) + 50;//50-199
        int green = random.nextInt(150) + 50;//50-199
        int blue = random.nextInt(150) + 50;//50-199
        return Color.rgb(red, green, blue);
    }

    /**
     * 得到年月日的"日"
     */
    private String getDate() {
        Date date = new Date();
        SimpleDateFormat dateFm = new SimpleDateFormat("dd");
        return dateFm.format(date);
    }

    /**
     * 获取屏幕px
     *
     * @param context
     * @return 分辨率
     */
    static public int getScreenWidthPixels(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
                .getMetrics(dm);
        return dm.widthPixels;
    }

//	public static void RunOnUiThread(Runnable r) {
//		CloudReaderApplication.getInstance().getMainLooper().post(r);
//	}

    public static Drawable getDrawable(Context context, int resid) {
        return getResoure(context).getDrawable(resid);
    }

    public static int getColor(Context context, int resid) {
        return getResoure(context).getColor(resid);
    }

    public static Resources getResoure(Context context) {
        return context.getResources();
    }

    public static String[] getStringArray(Context context, int resid) {
        return getResoure(context).getStringArray(resid);
    }

    public static String getString(Context context, int resid) {
        return getResoure(context).getString(resid);
    }

    public static float getDimens(Context context, int resId) {
        return getResoure(context).getDimension(resId);
    }

    public static void removeSelfFromParent(View child) {

        if (child != null) {

            ViewParent parent = child.getParent();

            if (parent instanceof ViewGroup) {

                ViewGroup group = (ViewGroup) parent;

                group.removeView(child);
            }
        }
    }

    /**
     * 隐藏状态栏
     *
     * @param context
     */
    public static void hideStatusLan(Activity context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            VirtualKeyUtils.getInstance().noBarResizeChildOfContent();

        }

    }


    /**
     * 字符串是否为空
     *
     * @param input
     * @return
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input) || "null".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串是否为空
     *
     * @param value
     * @return
     */
    public static boolean isNotEmpty(String value) {
        return !isEmpty(value);
    }
    /**
     * 配置url
     *
     * @param value
     * @return
     */
    public static boolean isHttpUrl(String value) {
        if(isEmpty(value)) return false;
        return value.startsWith("http")||value.startsWith("https")||value.startsWith("arouter");
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    protected static Toast toast = null;

    public static void showToast(Context context, String title) {

        if (toast == null) {
            toast = Toast.makeText(context, title, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            toast.setText(title);
            toast.show();
        }
    }

    /**
     * 显示进度框
     *
     * @param str
     */
    public void showInfoProgressDialog(Context context, final String... str) {
        if (mProgressDialog == null) {
            mProgressDialog = new LoadingDialog(context);
        }


        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    /**
     * 添加网络请求观察者
     *
     * @param s
     */
    public void addSubscription(Subscription s) {
        if (s == null) {
            return;
        }
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        this.mCompositeSubscription.add(s);
    }

    /**
     * 移除网络请求
     */
    public void removeSubscription() {
        if (this.mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            this.mCompositeSubscription.unsubscribe();
            this.mCompositeSubscription = null;
        }
    }

    /**
     * 隐藏等待条
     */
    public void hideInfoProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    /**
     * 格式化时间段 取月尾
     */
    public static String subTime(String start,String end){
        if(CommonUtils.isEmpty(start)||CommonUtils.isEmpty(end)) return start+"-"+end;
        if(start.equals(end)) return start;
        String[] startSplit = start.split("/");
        String[] endSplit = end.split("/");
        if(startSplit.length==3&&endSplit.length==3){
            if(startSplit[1].equals(endSplit[1]))
                return start+"-"+endSplit[2];
        }else
            return  start+"-"+end;
        return  start+"-"+end;
    }

    /**
     * md5编码
     *
     * @param string
     * @return
     */
    public static String md5(String string) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    public static void setTextValue(TextView tv_dse, String dse_name) {
        if (tv_dse == null) return;
        if (!isEmpty(dse_name))
            tv_dse.setText(dse_name);
        else
            tv_dse.setText("");
    }
    /**
     * 设置字体
     */
    public static void setTextViewFont(TextView textView,int fontId){
        Typeface tf= ResourcesCompat.getFont(getContext(), fontId);
        textView.setTypeface(tf);
    }

    /**
     * 格式化时间
     *
     * @param pattern
     * @param milli
     * @return
     */
    public static String formatTime(String pattern, long milli) {
        int m = (int) (milli / DateUtils.MINUTE_IN_MILLIS);
        int s = (int) ((milli / DateUtils.SECOND_IN_MILLIS) % 60);
        String mm = String.format(Locale.getDefault(), "%02d", m);
        String ss = String.format(Locale.getDefault(), "%02d", s);
        return pattern.replace("mm", mm).replace("ss", ss);
    }

    /**
     * 显示和隐藏输入法键盘
     *
     * @param context
     */
    public static void showSoftInputFromWindow(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 显示和隐藏输入法键盘
     *
     * @param context
     */
    public static void showSoftInputFromWindow(Context context, View view, int type) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        // 强制显示软键盘
        if (type == 1) imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
        else// 强制隐藏软键盘
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 隐藏手机号码中间四位
     */
    public static String hidePhoneCenter(String phone) {
        try {
            return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 隐藏IDcard关键位数
     */
    public static String hideIdcardCenter(String idCard) {
        try {
            return idCard.replaceAll("(\\d{4})\\d{10}(\\w{4})", "$1*****$2");
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 获取N位随机字符串
     *
     * @param length
     * @return
     */
    public static String getRandomString(int length) { //length表示生成字符串的长度
        String base = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 获取内置SD卡路径
     *
     * @return
     */
    public static String getInnerSDCardPath() {
        String file = Environment.getExternalStorageDirectory().getPath() + "/meihao/";
        File file1 = new File(file);
        if (!file1.exists()) file1.mkdirs();
        return file;
    }
    /**
     * 得到应用程序的包名
     *
     * @return
     */
    public static String getPackageName(Context context) {
        return context.getPackageName();
    }

    //版本名
    public static String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }

    //版本号
    public static int getVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }
    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }

    /**
     * 判断字符串都是数字
     */
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }
    /**
     * 判断字符串都是数字
     */
    public static boolean isNumericAndAZ(String str){
        Pattern pattern = Pattern.compile("[0-9a-zA-Z]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }
    /**
     * 复制链接到剪切板
     */
    public static void copyStringToBoad(Context context,String text){
        if(CommonUtils.isEmpty(text)) return;;
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);

        cm.setText(text);
    }
    /**
     * 设置中划线
     */
    public static void drawMiddleLine(TextView textView){
        if(textView==null) return;
        textView.getPaint().setAntiAlias(true);//抗锯齿

        textView.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线

        textView.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);  // 设置中划线并加清晰
    }

    public static void setIndicator(Context context, TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout ll_tab = null;
        try {
            ll_tab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) (context.getResources().getDisplayMetrics().density * leftDip);
        int right = (int) (context.getResources().getDisplayMetrics().density* rightDip);

        for (int i = 0; i < ll_tab.getChildCount(); i++) {
            View child = ll_tab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }



    /**
     * 获取内部缓存地址
     *
     * @return
     */
    public static String getCacheDirectory(Context context) {
        String appCacheDir = context.getCacheDir().getPath() + "/";
        if (CommonUtils.isEmpty(appCacheDir)) {
            appCacheDir = "/data/data/" + context.getPackageName() + "/cache/";
        }
        return appCacheDir;
    }
    /**
     * 判断是否存在虚拟按键
     * @return
     */
    public static    boolean checkDeviceHasNavigationBar(Activity context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class<?> systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {

        }
        return hasNavigationBar;
    }

    /**
     * 获取全局上下文
     *
     * @return
     */
    public static Context getContext() {
        return BaseApplication.getContext();
    }
    /**
     * 重启app
     *
     * @param context
     * @param Delayed
     */
    public static void restartAPP(Context context, long Delayed) {


        Intent intent1 = new Intent(context, killSelfService.class);
        intent1.putExtra("PackageName", context.getPackageName());
        intent1.putExtra("Delayed", Delayed);
        context.startService(intent1);
        android.os.Process.killProcess(android.os.Process.myPid());
    }
    /**
     * showToast
     */
    public static void showToast(String s) {
        CommonUtils.showToast(getContext(), s);
    }
    /**
     * 判断是否是手机号码
     *
     * @param phoneNumber
     * @return
     */
    public static boolean isMobile(String phoneNumber) {
        if (CommonUtils.isEmpty(phoneNumber)) {
//            CommonUtils.showToast("请检查您的手机号码是否正确");
            return false;
        }
        if (!phoneNumber.matches("1[0-9]{10}")) {
//            CommonUtils.showToast("请检查您的手机号码是否正确");
            return false;
        }
        return true;
    }


    /**
     * 设置布局的margin
     */
    public static void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            AutoUtils.autoMargin(v);
            v.requestLayout();
        }
    }






    public static String getFPrice(double price) {
        if (price == 0) return "免费";
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
        double v = price / 100d;
        String format = df.format(v);
        if (format.startsWith(".")) {
            format = "0" + format;
        }

        return format;
    }



    /**
     * 自定义dialog全屏展示
     *
     * @param activity
     * @param dialog
     */
    public static void FullScreen(Activity activity, Dialog dialog, double scale) {
        WindowManager m = activity.getWindowManager();
        Display d = m.getDefaultDisplay();  //为获取屏幕宽、高
        WindowManager.LayoutParams p = dialog.getWindow().getAttributes();  //获取对话框当前的参数值
        //p.height = (int) (d.getHeight() * 0.3);   //高度设置为屏幕的0.3
        p.width = (int) (d.getWidth() * scale);    //宽度设置为全屏
        dialog.getWindow().setAttributes(p);     //设置生效
    }

    private static  String[] gV = new String[]{"一", "二", "三", "四", "五", "六", "七", "八", "九"};
    private static String[] sV = new String[]{"十", "百", "千", "万", "零"};

    public static String getDay(int position) {
        int fd = position / 10;
        if (fd < 1) {//各位
            return gV[position - 1];
        } else if (fd >= 1 && fd < 10) {//十位
            int sd = position % 10;
            String sw = fd > 1 ? gV[fd - 1] : "";
            String ww = sd > 0 ? gV[sd - 1] : "";
            return sw + sV[0] + ww;
        } else if (fd >= 10 && fd < 100) {//百位
            int sdd = position % 100%10;
            int sw = position % 100 / 10;
            String sws=sw>0?gV[sw-1]+sV[0]:sV[4];
            String gws=sdd>0?gV[sdd-1]:"";
            return gV[position/100-1]+sV[1]+sws+gws;
        }
        return "";
    }


}
