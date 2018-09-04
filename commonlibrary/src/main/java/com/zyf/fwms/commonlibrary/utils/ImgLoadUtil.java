package com.zyf.fwms.commonlibrary.utils;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.zyf.fwms.commonlibrary.R;

import java.io.File;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.HashMap;

import jp.wasabeef.glide.transformations.BlurTransformation;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;


/**
 * Created by jingbin on 2016/11/26.
 */

public class ImgLoadUtil {

    private static ImgLoadUtil instance;
    private static GifDrawable gifDrawable;
    private static String alImgPrams = "?x-oss-process=image/format,webp";//阿里云图片后缀
    private static String qnImgPrams = "?imageMogr2/thumbnail/!75p/format/webp/ignore-error/1";//七牛图片后缀


    private static HashMap<String, SoftReference<GifDrawable>> cacheBit = new HashMap<>();//回收gif

    private ImgLoadUtil() {
    }

    public static ImgLoadUtil getInstance() {
        if (instance == null) {
            instance = new ImgLoadUtil();
        }
        return instance;
    }


    public static void displayGif(String url, ImageView imageView) {

        Glide.with(imageView.getContext()).load(url)
                .asGif()
                .placeholder(R.drawable.defaut_img_show)
                .error(R.drawable.defaut_img_show)
                .skipMemoryCache(true) //跳过内存缓存
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)// 缓存图片源文件（解决加载gif内存溢出问题）
//                .into(new GlideDrawableImageViewTarget(imageView, 1));
                .into(imageView);
    }


    /**
     * 显示高斯模糊效果（电影详情页）
     */
    private static void displayGaussian(Context context, String url, ImageView imageView) {
        // "23":模糊度；"4":图片缩放4倍后再进行模糊
        Glide.with(context)
                .load(formatUrl(url))
                .error(R.drawable.defaut_img_show)
                .placeholder(R.drawable.defaut_img_show)
                .crossFade(500)
                .bitmapTransform(new BlurTransformation(context, 23, 4))
                .into(imageView);
    }

    /**
     * 加载圆角图,暂时用到显示头像
     */
    public static void displayCircle(ImageView imageView, String imageUrl) {
        Glide.with(imageView.getContext())
                .load(formatUrl(imageUrl))
                .crossFade(500)
                .placeholder(R.mipmap.morne_touxiang)
                .error(R.mipmap.morne_touxiang)
                .transform(new GlideCircleTransform(imageView.getContext()))
                .into(imageView);


    }

    /**
     * 加载圆角图,暂时用到显示头像
     */
    public static void displayCircle(ImageView imageView, int imageUrl) {
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .crossFade(500)
                .placeholder(R.drawable.defaut_img_show)
                .error(R.mipmap.morne_touxiang)
                .transform(new GlideCircleTransform(imageView.getContext()))
                .into(imageView);


    }

    /**
     * 加载圆角图片
     *
     * @param imageView
     * @param imageUrl
     * @param round
     */
    public static void displayRound(ImageView imageView, String imageUrl, int round) {
        Glide.with(imageView.getContext())
                .load(formatUrl(imageUrl))
                .crossFade(500)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .error(R.drawable.defaut_img_show)
                .transform(new GlideRoundTransform(imageView.getContext(), round))
                .into(imageView);
    }


    public static void display(Context context, ImageView imageView, String url, int placeholder, int error) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context).load(formatUrl(url)).placeholder(placeholder)
                .error(error).crossFade().into(imageView);
    }

    public static void display(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context).load(formatUrl(url))
//                .asBitmap()
//                .format(DecodeFormat.PREFER_ARGB_8888)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.drawable.defaut_img_show)
                .error(R.drawable.defaut_img_show)
                .into(imageView);
    }

    // 加载一般图片
    @BindingAdapter({"url"})
    public static void loadImage(ImageView view, String url) {
        display(view.getContext(), view, url);
    }

    // 加载小图
    @BindingAdapter({"surl"})
    public static void loadSImage(ImageView view, String url) {
        displaySmSOURCEPhoto(view.getContext(), view, url);
    }

    // 加载圆形图片
    @BindingAdapter({"curl"})
    public static void loadCircleImage(ImageView view, String url) {
        displayCircle(view, url);
    }

    public static void display(Context context, ImageView imageView, File url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }

        Glide.with(context).load(url )
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.drawable.defaut_img_show)
                .error(R.drawable.defaut_img_show)
                .crossFade().into(imageView);
    }

    public static void displaySmSOURCEPhoto(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context).load(formatUrl(url))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.drawable.defaut_img_show)
                .error(R.drawable.defaut_img_show)
                .into(imageView);
    }

    public static void displayBigPhoto(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context).load(formatUrl(url)).asBitmap()
                .format(DecodeFormat.PREFER_ARGB_8888)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.drawable.defaut_img_show)
                .error(R.drawable.defaut_img_show)
                .into(imageView);
    }

    public static void display(Context context, ImageView imageView, int url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context).load(url)
                .asBitmap()
                .format(DecodeFormat.PREFER_ARGB_8888)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.drawable.defaut_img_show)
                .error(R.drawable.defaut_img_show)
                .into(imageView);
    }

    public static void displayRound(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context).load(formatUrl(url))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .error(R.drawable.defaut_img_show)
                .transform(new GlideCircleTransform(context)).into(imageView);
    }

    public static void displayRound(Context context, ImageView imageView, File url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context).load(url)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .error(R.drawable.defaut_img_show)
                .transform(new GlideCircleTransform(context)).into(imageView);
    }

    /**
     * 自适应宽度加载图片。保持图片的长宽比例不变，通过修改imageView的高度来完全显示图片。
     */
    public static void loadIntoUseFitWidth(Context context, final ImageView imageView, final String imageUrl) {
        Glide.with(context)
                .load(formatUrl(imageUrl))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }


                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        if (imageView == null) {
                            return false;
                        }
                        if (imageView.getScaleType() != ImageView.ScaleType.FIT_XY) {
                            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        }
                        ViewGroup.LayoutParams params = imageView.getLayoutParams();
                        //int vw = imageView.getWidth() - imageView.getPaddingLeft() - imageView.getPaddingRight();
                        int vw = AutoUtils.designWidth;
                        float scale = (float) vw / (float) resource.getIntrinsicWidth();
                        int vh = Math.round(resource.getIntrinsicHeight() * scale);
                        params.height = vh + imageView.getPaddingTop() + imageView.getPaddingBottom();
                        imageView.setLayoutParams(params);
                        AutoUtils.auto(imageView);
                        return false;
                    }
                })
                .placeholder(R.drawable.defaut_img_show)
                .error(R.drawable.defaut_img_show)
                .into(imageView);
    }

    /**
     * 自适应宽度加载图片。保持图片的长宽比例不变，通过修改imageView的高度来完全显示图片。
     */
    public static void loadIntoUseFitWidth(Context context, final ImageView imageView, final int width, final String imageUrl) {
        Glide.with(context)
                .load(formatUrl(imageUrl))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        LogUtil.getInstance().e(e.getMessage());
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        if (imageView == null) {
                            return false;
                        }
                        if (imageView.getScaleType() != ImageView.ScaleType.FIT_XY) {
                            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        }
                        ViewGroup.LayoutParams params = imageView.getLayoutParams();
                        //int vw = imageView.getWidth() - imageView.getPaddingLeft() - imageView.getPaddingRight();
                        int vw = width;
                        float scale = (float) vw / (float) resource.getIntrinsicWidth();
                        int vh = Math.round(resource.getIntrinsicHeight() * scale);
                        params.height = vh + imageView.getPaddingTop() + imageView.getPaddingBottom();
                        imageView.setLayoutParams(params);
                        AutoUtils.auto(imageView);
                        // display(imageView.getContext(), imageView, imageUrl);
                        LogUtil.getInstance().e("loadIntoUseFitWidth----width:" + vw + "---height:" + vh);

                        return false;
                    }
                })
                .placeholder(R.drawable.defaut_img_show)
                .error(R.drawable.defaut_img_show)
                .into(imageView);
    }

    /**
     * 高度自适应2
     *
     * @param context
     * @param imageView
     * @param userWidth
     * @param imageUrl
     */
    public static void loadIntoUseFitWidth2(final Context context, final ImageView imageView, final int userWidth, final String imageUrl) {
        imageView.setImageResource(R.drawable.defaut_img_show);
        if (CommonUtils.isNotEmpty(imageUrl)) {//字符串切割获取长宽
            setEHByUrl(imageUrl, imageView, userWidth);
        } else return;
        imageView.setTag(imageUrl);
        //设置gif 加载本地图片
        if (imageUrl.endsWith(".gif") && imageView instanceof GifImageView) {
            boolean exists = downGif(context, imageUrl, imageView);
            //如果本地存在不需要加载本地图片
            if (exists) return;
        }


        //获取图片真正的宽高
        Glide.with(context)
                .load(formatUrl(imageUrl))
                .asBitmap()//强制Glide返回一个Bitmap对象
                .format(DecodeFormat.PREFER_ARGB_8888)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {

                        if (imageView.getTag().toString().equals(imageUrl)) {
                            int width = bitmap.getWidth();
                            int height = bitmap.getHeight();
                            if (imageView.getScaleType() != ImageView.ScaleType.FIT_XY) {
                                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                            }
                            ViewGroup.LayoutParams params = imageView.getLayoutParams();
                            //int vw = imageView.getWidth() - imageView.getPaddingLeft() - imageView.getPaddingRight();
                            float scale = (float) userWidth / (float) width;
                            int vh = Math.round(height * scale);
                            params.width = userWidth;
                            params.height = vh;
                            imageView.setLayoutParams(params);
                            AutoUtils.auto(imageView);
                            imageView.setImageBitmap(bitmap);
                        }
                    }
                });

    }

    /**
     * 下载gif图片
     *
     * @param imageUrl
     * @param imageView
     */
    public static boolean downGif(Context context, final String imageUrl, final ImageView imageView) {
        int indexOf = imageUrl.lastIndexOf("/");
        String fileName = imageUrl.substring(indexOf + 1, imageUrl.length());
        final File file = new File(CommonUtils.getCacheDirectory(context) + fileName);
        if (!file.exists()) {
            DownLoadUtils loadUtils = new DownLoadUtils();
            loadUtils.download(imageUrl, file.getPath());
            loadUtils.setOnDownloadListener(new DownLoadUtils.OnDownloadListener() {
                @Override
                public void onDownloadConnect(DownLoadUtils manager) {

                }

                @Override
                public void onDownloadUpdate(DownLoadUtils manager, int percent) {

                }

                @Override
                public void onDownloadComplete(DownLoadUtils manager, Object result) {
                    setGifDrawable(imageUrl, file, imageView);
                }

                @Override
                public void onDownloadError(DownLoadUtils manager, Exception e) {

                }
            });
            return false;
        } else {
            setGifDrawable(imageUrl, file, imageView);
        }
        return true;
    }

    /**
     * 获取 gifDrawable 并设置到控件上
     */
    public static void setGifDrawable(String imageUrl, File file, ImageView imageView) {
        try {
            if (cacheBit.get(imageUrl) == null || cacheBit.get(imageUrl).get() == null || cacheBit.get(imageUrl).get().isRecycled()) {
                gifDrawable = new GifDrawable(file);
                cacheBit.put(imageUrl, new SoftReference<GifDrawable>(gifDrawable));
            }
            imageView.setImageDrawable(cacheBit.get(imageUrl).get());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据url  设置图片的宽高
     */
    public static void setEHByUrl(String imageUrl, ImageView imageView, int userWidth) {
        if (imageView == null) return;
        imageView.setImageResource(R.drawable.defaut_img_show);
        if (CommonUtils.isNotEmpty(imageUrl)) {//字符串切割获取长宽
            String[] wh = getWh(imageUrl);
            if (wh != null) {
                setWHByW(imageView, userWidth, wh);
            }
        }
    }


    /**
     * 根据url获取宽高
     */
    public static String[] getWh(String imageUrl) {
        if (CommonUtils.isNotEmpty(imageUrl)) {//字符串切割获取长宽
            int one = imageUrl.indexOf("_");
            int two = imageUrl.lastIndexOf("_");
            int three = imageUrl.lastIndexOf(".");
            if (one != 0 && one != two && two != 0 && three != 0 && two != three) {
                String width = imageUrl.substring(one + 1, two);
                String height = imageUrl.substring(two + 1, three);
                if (CommonUtils.isNumeric(width) && CommonUtils.isNumeric(height)) {//判断是否都属于数字
                    String[] wh = new String[]{width, height};
                    return wh;
                } else return null;
            } else return null;
        } else return null;

    }


    /**
     * 根据设定宽度 等比例设置图片的宽高
     */
    public static void setWHByW(ImageView imageView, int userWidth, String[] wh, int... defh) {
        if (wh == null && defh == null) return;
        ViewGroup.LayoutParams params = imageView.getLayoutParams();
        if (wh == null) {
            params.width = userWidth;
            params.height = defh[0];
        } else {
            float scale = (float) userWidth / Float.valueOf(wh[0]);
            int vh = Math.round(Float.valueOf(wh[1]) * scale);
            params.width = userWidth;
            params.height = vh;
        }


        imageView.setLayoutParams(params);
        AutoUtils.autoSize(imageView);
    }

    /**
     * Bitmap缩小的方法
     */

    private static Bitmap smSOURCE(Bitmap bitmap, float scale) {
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale); //长和宽放大缩小的比例
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    /**
     * 高斯模糊
     */
    public static void loadGaoSiImg(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(formatUrl(url))
                .placeholder(R.drawable.defaut_img_show)
                .error(R.drawable.defaut_img_show)
                .bitmapTransform(new BlurTransformation(context, 25, 4))  // “23”：设置模糊度(在0.0到25.0之间)，默认”25";"4":图片缩放比例,默认“1”。
                .into(imageView);


    }

    /**
     * 转换url格式
     */
    public static String formatUrl(String url) {
        if(CommonUtils.isEmpty(url)) return url;
        if (url.endsWith(".jpg") || url.endsWith(".png")){
            if(url.startsWith("http://qitoujia-dev.oss-cn-hangzhou.aliyuncs.com")){//阿里云图片
                return url+alImgPrams;
            }else if(url.startsWith("http://qiniu.dtsxst.cn")){//七牛
               return url+qnImgPrams;
            }
        }

         return url;
    }

}
