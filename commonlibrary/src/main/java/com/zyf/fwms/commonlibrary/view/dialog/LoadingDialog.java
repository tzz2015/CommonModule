package com.zyf.fwms.commonlibrary.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.zyf.fwms.commonlibrary.R;
import com.zyf.fwms.commonlibrary.R2;
import com.zyf.fwms.commonlibrary.utils.AutoUtils;
import com.zyf.fwms.commonlibrary.utils.CommonUtils;
import com.zyf.fwms.commonlibrary.utils.ImgLoadUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.droidsonroids.gif.GifDrawable;

/**
 * 创建 by lyf on 28/03/2018.
 * 描述：
 */

public class LoadingDialog extends Dialog {

   private Context context;
    private GifDrawable gifDrawable;

    public LoadingDialog(Context context) {
        super(context, R.style.trans_dialog);
        this.context = context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_loading, null);
        ImageView ivLoading=view.findViewById(R.id.iv_image);
        AutoUtils.auto(view);
        setContentView(view);
        CommonUtils.FullScreen((Activity) context, this, 0.8);
        ButterKnife.bind(this);
        // 点击Dialog外部消失
        setCanceledOnTouchOutside(true);
        try {
            if (gifDrawable != null && !gifDrawable.isRecycled()) gifDrawable=null;
            gifDrawable = new GifDrawable(context.getResources(), R.drawable.loading);
             ivLoading.setImageDrawable(gifDrawable);
        }catch (Exception e){

        }

    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (gifDrawable != null && !gifDrawable.isRecycled()) gifDrawable=null;
    }
}
