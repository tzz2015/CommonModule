package com.zyf.fwms.commonlibrary.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.zyf.fwms.commonlibrary.R;
import com.zyf.fwms.commonlibrary.R2;
import com.zyf.fwms.commonlibrary.utils.AutoUtils;
import com.zyf.fwms.commonlibrary.utils.CommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建 by lyf on 28/03/2018.
 * 描述：
 */

public class ConfirmDialog extends Dialog {

    @BindView(R2.id.tv_msg)
    TextView tvMsg;
    @BindView(R2.id.tv_line)
    TextView tvLine;
    @BindView(R2.id.tv_btn_left)
    TextView tvBtnLeft;
    @BindView(R2.id.tv_m_line)
    TextView tvMLine;
    @BindView(R2.id.tv_btn_right)
    TextView tvBtnRight;
    private Context context;
    private String[] arg;
    private ConfirmDialoglisener dialoglisener;

    public ConfirmDialog(Context context, ConfirmDialoglisener dialoglisener, String... arg) {
        super(context, R.style.bottom_select_dialog);
        this.context = context;
        this.dialoglisener = dialoglisener;
        this.arg = arg;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_comfirm, null);
        AutoUtils.auto(view);
        setContentView(view);
        CommonUtils.FullScreen((Activity) context, this, 0.8);
        ButterKnife.bind(this);
        // 点击Dialog外部消失
        setCanceledOnTouchOutside(true);
        initView();
    }

    private void initView() {
        //中间提示文字
        if (arg == null) return;
        if (arg.length >= 1)
            CommonUtils.setTextValue(tvMsg,arg[0]);
        //左侧按钮
        if(arg.length>=2){
            if(CommonUtils.isNotEmpty(arg[1]))
                tvBtnLeft.setText(arg[1]);
            else {
                tvBtnLeft.setVisibility(View.GONE);
                tvMLine.setVisibility(View.GONE);
            }
        }
        //右侧按钮
        if(arg.length>=3){
            if(CommonUtils.isNotEmpty(arg[2]))
                tvBtnRight.setText(arg[2]);
            else {
                tvBtnLeft.setVisibility(View.GONE);
                tvMLine.setVisibility(View.GONE);
            }
        }

    }

    @OnClick({R2.id.tv_btn_left, R2.id.tv_btn_right})
    public void onViewClicked(View view) {
        int viewId = view.getId();
        if(viewId==R.id.tv_btn_left){
            if (dialoglisener != null) dialoglisener.liftOnclick();
        }else if(viewId==R.id.tv_btn_right){
            if (dialoglisener != null) dialoglisener.rightOnclick();

        }

        dismiss();
    }

    public interface ConfirmDialoglisener {
        void liftOnclick();

        void rightOnclick();

    }
}
