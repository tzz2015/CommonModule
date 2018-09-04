package com.zyf.fwms.commonlibrary.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.zyf.fwms.commonlibrary.R;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class AdViewpagerUtil {

    private Context context;
    private ViewPager viewPager;
    private AdPagerAdapter mimageViewPagerAdapter;
    private List<ImageView> mImageViews = new ArrayList<>();
    private List<String> urls;
    private LinearLayout dotlayout;
    private ImageView[] dotViews;

    private boolean isRight = true; // 判断viewpager是不是向右滑动
    private int lastPosition = 1; // 记录viewpager从哪个页面滑动到当前页面，从而区分滑动方向
    private int autoIndex = 1; // 自动轮询时自增坐标，能确定导航圆点的位置
    private int currentIndex = 0; //当前item
    private int delayTime = 4000; //自动轮播的时间间隔
    private int imgsize = 0; //图片的数量，item的数量
    private boolean isLoop = true;//轮播开关

    private OnAdPageChangeListener onAdPageChangeListener; //pagechange回调
    private OnAdItemClickListener onAdItemClickListener; //点击事件回调

    private int dotsize = 8; //小圆点的大小宽度
    private int dotoffset = 4; //小圆点的间距
    private static volatile AdViewpagerUtil instance;

    public static AdViewpagerUtil getInstance() {
        if (instance == null) {
            synchronized (AdViewpagerUtil.class) {
                if (instance == null) {
                    instance = new AdViewpagerUtil();
                }
            }
        }
        return instance;
    }

    public void setHandlerNull() {
        if (handler != null) handler.removeMessages(0);
        handler = null;
        if (dotlayout != null) {
            dotlayout.removeAllViews();
            dotlayout = null;
        }
        context = null;
        instance = null;


    }


    /**
     * 不带小圆点
     *
     * @param context
     * @param viewPager
     * @param urls
     */
    public AdViewpagerUtil(Context context, ViewPager viewPager, List<String> urls) {
        this.context = context;
        this.viewPager = viewPager;
        this.urls = urls;
        initVps();
    }

    /**
     * 有小圆点
     *
     * @param context
     * @param viewPager
     * @param dotlayout
     * @param dotsize
     * @param dotoffset
     * @param urls
     */
    public AdViewpagerUtil(Context context, ViewPager viewPager, LinearLayout dotlayout, int dotsize, int dotoffset, List<String> urls) {
        this.context = context;
        this.viewPager = viewPager;
        this.dotlayout = dotlayout;
        this.dotsize = dotsize;
        this.urls = urls;
        initVps();
    }

    public AdViewpagerUtil(Context context, ViewPager viewPager, LinearLayout dotlayout, List<String> urls) {
        setHandlerNull();
        if(this.viewPager!=null) this.viewPager.removeAllViews();
        this.context = context;
        this.viewPager = viewPager;
        this.dotlayout = dotlayout;
        this.urls = urls;
        initVps();
    }

    public AdViewpagerUtil() {

    }

    public void initView(Context context, ViewPager viewPager, LinearLayout dotlayout, List<String> urls) {
        this.context = context;
        this.viewPager = viewPager;
        this.dotlayout = dotlayout;
        this.urls = urls;
        initVps();
    }


    /**
     * 监听滑动
     *
     * @param onAdPageChangeListener
     */
    public void setOnAdPageChangeListener(OnAdPageChangeListener onAdPageChangeListener) {
        this.onAdPageChangeListener = onAdPageChangeListener;
    }

    /**
     * 监听点击
     *
     * @param onAdItemClickListener
     */
    public void setOnAdItemClickListener(OnAdItemClickListener onAdItemClickListener) {
        this.onAdItemClickListener = onAdItemClickListener;
    }

    /**
     * 初始化图片
     *
     * @param urls
     */
    private void initAdimgs(List<String> urls) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        int length = urls.size() + 2;
        mImageViews.clear();
        for (int i = 0; i < length; i++) {
            ImageView imageView = new ImageView(context);
            imageView.setLayoutParams(params);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            mImageViews.add(imageView);
        }
        setImg(length, urls);
    }

    /**
     * 显示图片
     *
     * @param length
     * @param urls
     */
    private void setImg(int length, final List<String> urls) {
        if (urls.size() > 0) {
            imgsize = length;
            for (int i = 0; i < length; i++) {
                if (i < length - 2) {
                    final int index = i;
                    final String url = urls.get(i);
                    ImgLoadUtil.display(context, mImageViews.get(i + 1), url);
                    mImageViews.get(i + 1).setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            if (onAdItemClickListener != null) {
                                onAdItemClickListener.onItemClick(mImageViews.get(index + 1), index, url);
                            }
                        }
                    });
                }
            }
            ImgLoadUtil.display(context, mImageViews.get(0), urls.get(urls.size() - 1));
            ImgLoadUtil.display(context, mImageViews.get(length - 1), urls.get(0));
        }
    }

    /**
     * 初始化viewpager
     */
    public void initVps() {
        initAdimgs(urls);
        initDots(urls.size());
        mimageViewPagerAdapter = new AdPagerAdapter(context, mImageViews);
        viewPager.setAdapter(mimageViewPagerAdapter);
        viewPager.setOffscreenPageLimit(mImageViews.size());
        startLoopViewPager();
        viewPager.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        if (isLoop) {
                            stopLoopViewPager();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        if (isLoop) {
                            startLoopViewPager();
                        }

                    default:
                        break;
                }
                return false;
            }
        });
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub
                if (isRight) {
                    if (arg0 != 1) {
                        if (lastPosition == 0) {
                            viewPager.setCurrentItem(imgsize - 2, true);
                        } else if (lastPosition == imgsize - 1) {
                            viewPager.setCurrentItem(1, false);
                        }
                    }
                }

                if (onAdPageChangeListener != null) {
                    onAdPageChangeListener.onPageScrollStateChanged(arg0);
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub
                if (!isRight) {
                    if (arg1 < 0.01) {
                        if (arg0 == 0) {
                            viewPager.setCurrentItem(imgsize - 2, true);
                        } else if (arg0 == imgsize - 1) {
                            viewPager.setCurrentItem(1, true);
                        }
                    }
                }
                if (onAdPageChangeListener != null) {
                    onAdPageChangeListener.onPageScrolled(arg0, arg1, arg2);
                }
            }

            @Override
            public void onPageSelected(int arg0) {
                // TODO Auto-generated method stub
                autoIndex = arg0;
                if (lastPosition < arg0 && lastPosition != 0) {
                    isRight = true;
                } else if (lastPosition == imgsize - 1) {
                    isRight = true;
                }
                if (lastPosition > arg0 && lastPosition != imgsize - 1) {
                    isRight = false;
                } else if (lastPosition == 0) {
                    isRight = false;
                }
                lastPosition = arg0;

                if (arg0 == 0) {
                    currentIndex = imgsize - 2;
                } else if (arg0 == imgsize - 1) {
                    currentIndex = 1;
                } else {
                    currentIndex = arg0;
                }
                if (dotViews != null) {
                    for (int i = 0; i < dotViews.length; i++) {
                        if (i == currentIndex - 1) {
                            dotViews[i].setSelected(true);
                        } else {
                            dotViews[i].setSelected(false);
                        }
                    }
                }


                if (onAdPageChangeListener != null) {
                    onAdPageChangeListener.onPageSelected(arg0);
                }
            }

        });
        viewPager.setCurrentItem(1, true);// 初始化时设置显示第一页（ViewPager中索引为1）


        /**
         * 通过反射来修改 ViewPager的mScroller属性
         */
        try {
            Class clazz = Class.forName("android.support.v4.view.ViewPager");
            Field f = clazz.getDeclaredField("mScroller");
            FixedSpeedScroller fixedSpeedScroller = new FixedSpeedScroller(context, new LinearOutSlowInInterpolator());
            fixedSpeedScroller.setmDuration(1000);
            f.setAccessible(true);
            f.set(viewPager, fixedSpeedScroller);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 初始化标识点
     *
     * @param length
     */
    public void initDots(int length) {
        if (dotlayout == null || length == 1) {
            isCanLoop(false);
            return;
        }
        dotlayout.removeAllViews();
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(dip2px(context, dotsize), dip2px(context, dotsize));
        mParams.setMargins(dip2px(context, dotoffset), 0, dip2px(context, dotoffset), 0);//设置小圆点左右之间的间隔

        dotViews = new ImageView[length];

        for (int i = 0; i < length; i++) {
            ImageView imageView = new ImageView(context);
            imageView.setLayoutParams(mParams);
            imageView.setImageResource(R.drawable.dot_selector);
            if (i == 0) {
                imageView.setSelected(true);//默认启动时，选中第一个小圆点
            } else {
                imageView.setSelected(false);
            }
            dotViews[i] = imageView;//得到每个小圆点的引用，用于滑动页面时，（onPageSelected方法中）更改它们的状态。
            dotlayout.addView(imageView);//添加到布局里面显示
        }
    }

    public static MyHandler handler;


    private class MyHandler extends Handler {
        WeakReference<Context> mActivityReference;

        MyHandler(Context context) {
            mActivityReference = new WeakReference<Context>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            final Context context = mActivityReference.get();
            if (context != null) {
                if (handler != null && viewPager.getChildCount() > 0 && isLoop) {
                    handler.sendEmptyMessageDelayed(0, delayTime);
                    autoIndex++;
                    viewPager.setCurrentItem(autoIndex % imgsize, true);
                }
            } else handler = null;
        }
    }


    /**
     * 是否可以轮播
     */
    public void isCanLoop(boolean loop) {
        isLoop = loop;
    }

    /**
     * 开始自动轮播
     */
    public void startLoopViewPager() {
        stopLoopViewPager();
        if (handler == null) handler = new MyHandler(context);
        if (viewPager != null && isLoop) {
            handler.sendEmptyMessageDelayed(0, delayTime);// 每两秒执行一次runnable.
        }

    }

    /**
     * 停止自动轮播
     */
    public void stopLoopViewPager() {
        if (viewPager != null && handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    public void noticeAdapter(List<String> list) {
        urls = list;
        initAdimgs(urls);
        initDots(urls.size());
        viewPager.setOffscreenPageLimit(mImageViews.size());
        mimageViewPagerAdapter.notifyDataSetChanged();
        viewPager.setCurrentItem(1, false);
        startLoopViewPager();
    }

    public interface OnAdItemClickListener {
        void onItemClick(View v, int position, String url);
    }

    public interface OnAdPageChangeListener {
        void onPageScrollStateChanged(int arg0);

        void onPageScrolled(int arg0, float arg1, int arg2);

        void onPageSelected(int arg0);
    }

    public int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }


    /**
     * 广告适配器
     */
    private static class AdPagerAdapter extends PagerAdapter {

        private List<ImageView> imageViews;
        private int size;
        private Context context;

        public AdPagerAdapter(Context context, List<ImageView> imageViews) {
            this.context = context;
            this.imageViews = imageViews;
            size = imageViews.size();
        }

        @Override
        public int getCount() {
            return imageViews.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//		((ViewPager) container).removeView((View) object);// 完全溢出view,避免数据多时出现重复现象
            container.removeView(imageViews.get(position));//删除页卡
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(imageViews.get(position), 0);
            return imageViews.get(position);
        }
    }

    /**
     * 利用这个类来修正ViewPager的滑动速度
     * 我们重写 startScroll方法，忽略传过来的 duration 属性
     * 而是采用我们自己设置的时间
     */
    public class FixedSpeedScroller extends Scroller {

        public int mDuration = 1500;

        public FixedSpeedScroller(Context context) {
            super(context);
        }

        public FixedSpeedScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        public FixedSpeedScroller(Context context, Interpolator interpolator, boolean flywheel) {
            super(context, interpolator, flywheel);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            startScroll(startX, startY, dx, dy, mDuration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            //管你 ViewPager 传来什么时间，我完全不鸟你
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        public int getmDuration() {
            return mDuration;
        }

        public void setmDuration(int duration) {
            mDuration = duration;
        }
    }


}
