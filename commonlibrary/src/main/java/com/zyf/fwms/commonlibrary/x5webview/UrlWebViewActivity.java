package com.zyf.fwms.commonlibrary.x5webview;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.zyf.fwms.commonlibrary.R;
import com.zyf.fwms.commonlibrary.base.BaseMVPActivity;
import com.zyf.fwms.commonlibrary.databinding.ActivityUrlWebViewBinding;
import com.zyf.fwms.commonlibrary.utils.CommonUtils;
import com.zyf.fwms.commonlibrary.utils.Constants;
import com.zyf.fwms.commonlibrary.utils.VirtualKeyUtils;
import com.zyf.fwms.commonlibrary.utils.statusbar.StatusBarFontHelper;

/**
 * 创建 by lyf on 23/03/2018.
 * 描述：
 */
@Route(path = Constants.X5_WEBVIEW)
public class UrlWebViewActivity extends BaseMVPActivity<UrlWebViewActivityPresenter, ActivityUrlWebViewBinding, UrlWebViewActivityContact.View> implements UrlWebViewActivityContact.View {
    @Autowired
    String url;
    @Autowired
    int mWebType;///浏览器类型  0:今日 我的课程 课程详情阅读 1：学院

    private BridgeWebView x5WebView;
    private View llTitle;
    public TextView tvTtitle;
    private TextView tvLine;
    private ImageView iconBack;
    private X5WebViewUtils x5WebViewUtils;


    @Override
    protected void initView() {
        mWebType=getIntent().getIntExtra("mWebType",0);
        x5WebViewUtils = new X5WebViewUtils();
        x5WebView = x5WebViewUtils.getWebView(mContext);
        mBindingView.flRoot.addView(x5WebView, 0, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        url = getIntent().getStringExtra("URL");
        loadUrl(url);
        llTitle = mBindingView.inTitle.getRoot();
        tvTtitle = mBindingView.inTitle.getRoot().findViewById(R.id.tv_title);
        tvTtitle.setTypeface(Typeface.DEFAULT);
        tvLine = mBindingView.inTitle.getRoot().findViewById(R.id.tv_line);
        iconBack = mBindingView.inTitle.getRoot().findViewById(R.id.iv_icon_back);
        boolean isHaveImg = getIntent().getBooleanExtra("fixed", false);

        iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        x5WebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (CommonUtils.isNotEmpty(title)) {
                    setTitle(title);
                    tvTtitle.setText(title);
                }
            }
        });

        changeTitleType(isHaveImg);


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_url_web_view;
    }

    /**
     * 失败后点击刷新
     */
    @Override
    public void initData() {
        x5WebView.registerHandler("callSlidePage", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                if (data != null) {
                    if (data.equals("0")) {
                        llTitle.setBackgroundColor(Color.argb((int) 0, 255, 255, 255));
                        changeTitleType(false);
                        iconBack.setImageResource(R.mipmap.icon_back);

                    } else {
                        mBindingView.inTitle.getRoot().setVisibility(View.VISIBLE);
                        llTitle.setBackgroundColor(Color.argb((int) 255, 255, 255, 255));
                        // changeTitleType(true);
                        iconBack.setImageResource(R.mipmap.back);
                        StatusBarFontHelper.setStatusBarMode(UrlWebViewActivity.this, true);
                        tvTtitle.setTextColor(CommonUtils.getColor(mContext, R.color.color333333));
                        tvTtitle.setText("活动详情");
                    }
                }
            }
        });
    }

    /**
     * 加载url
     */
    @Override
    public void loadUrl(String url) {
        if (CommonUtils.isEmpty(url) || x5WebView == null) {
            finish();
            return;
        }
        if (url.startsWith("www")) url = "https://" + url;
        if (url.startsWith("http") || url.startsWith("https") || url.startsWith("file"))
            x5WebView.loadUrl(url);
        else finish();


    }


    /**
     * 切换滚动标题和固定标题
     */
    @Override
    public void changeTitleType(boolean fixed) {


        if (fixed) {//固定

            mBindingView.inTitle.getRoot().setVisibility(View.GONE);

        } else {//滚动
            CommonUtils.hideStatusLan(this);
            hideTitleBar();
            tvTtitle.setTextColor(Color.argb(0, 0, 0, 0));
            tvLine.setVisibility(View.GONE);
            iconBack.setImageResource(R.mipmap.icon_back);
            StatusBarFontHelper.setStatusBarMode(this, false);
            CommonUtils.hideStatusLan(this);
            x5WebView.setOnScrollListener(listener);
            VirtualKeyUtils.getInstance().noBarResizeChildOfContent();
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        x5WebView.setOnScrollListener(null);
        x5WebViewUtils.destroyWebView();
        mBindingView.flRoot.removeAllViews();
    }

    private X5WebView.OnScrollChangeListener listener = new X5WebView.OnScrollChangeListener() {

        @Override
        public void onPageEnd(int l, int t, int oldl, int oldt) {

        }

        @Override
        public void onPageTop(int l, int t, int oldl, int oldt) {

        }

        @Override
        public void onScrollChanged(int l, int t, int oldl, int oldt) {
            if (t <= 300) {   //设置标题的背景颜色
                llTitle.setBackgroundColor(Color.argb((int) 0, 255, 255, 255));
                changeTitleType(false);
                iconBack.setImageResource(R.mipmap.icon_back);
            } else if (t > 300 && t <= 1200) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
                float scale = (float) t / 1200;
                float alpha = (255 * scale);
                llTitle.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
                tvTtitle.setTextColor(Color.argb((int) alpha, 0, 0, 0));
            } else {

                // changeTitleType(true);
                llTitle.setBackgroundColor(CommonUtils.getColor(mContext,R.color.colorWhite));
                iconBack.setImageResource(R.mipmap.back);
                StatusBarFontHelper.setStatusBarMode(UrlWebViewActivity.this, true);
            }
        }
    };


}
