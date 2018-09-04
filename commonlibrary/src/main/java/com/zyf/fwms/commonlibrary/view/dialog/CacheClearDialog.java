package com.zyf.fwms.commonlibrary.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zyf.fwms.commonlibrary.R;
import com.zyf.fwms.commonlibrary.R2;
import com.zyf.fwms.commonlibrary.utils.AutoUtils;
import com.zyf.fwms.commonlibrary.utils.CommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 创建 by lyf on 29/03/2018.
 * 描述：
 */

public class CacheClearDialog extends Dialog {
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.ll_root)
    LinearLayout llRoot;
    @BindView(R2.id.iv_image)
    ImageView ivImage;
    private Context context;
    private String title;
    private int imgUrl;
    private View view;

    public CacheClearDialog(@NonNull Context context) {
        super(context, R.style.bottom_select_dialog);
        this.context = context;
    }

    public CacheClearDialog(@NonNull Context context, String title) {
        super(context, R.style.bottom_select_dialog);
        this.context = context;
        this.title = title;
    }

    public CacheClearDialog(@NonNull Context context, String title, int imgUrl) {
        super(context, R.style.bottom_select_dialog);
        this.context = context;
        this.title = title;
        this.imgUrl = imgUrl;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.dialog_clear_cache, null);

        AutoUtils.auto(view);
        setContentView(view);
        CommonUtils.FullScreen((Activity) context, this, 0.8);
        ButterKnife.bind(this);
        // 点击Dialog外部消失
        setCanceledOnTouchOutside(true);
        view.findViewById(R.id.ll_root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        if (CommonUtils.isNotEmpty(title)) CommonUtils.setTextValue(tvTitle, title);
        if (imgUrl > 0) ivImage.setImageResource(imgUrl);
    }

    /**
     * 自动销毁
     */
    public void autoDismiss(int time){
        if(view!=null){
            view.postDelayed(new Runnable() {
                @Override
                public void run() {
                    dismiss();
                }
            },time);
        }
    }
}
