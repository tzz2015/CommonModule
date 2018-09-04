package com.zyf.fwms.commonlibrary.utils;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * 创建 by lyf on 28/03/2018.
 * 描述：展开动画
 */

public class DropDownAnim extends Animation {
    /**
     * 目标的高度
     */
    private int targetHeight;
    /**
     * 目标view
     */
    private View view;
    /**
     * 是否向下展开
     */
    private boolean down;

    /**
     * 构造方法
     *
     * @param targetview 需要被展现的view
     * @param vieweight  目的高
     * @param isdown     true:向下展开，false:收起
     */
    public DropDownAnim(View targetview, int vieweight, boolean isdown) {
        this.view = targetview;
        this.targetHeight = vieweight;
        this.down = isdown;
    }

    //down的时候，interpolatedTime从0增长到1，这样newHeight也从0增长到targetHeight
    @Override
    protected void applyTransformation(float interpolatedTime,
                                       Transformation t) {
        int newHeight;
        if (down) {
            newHeight = (int) (targetHeight * interpolatedTime);
        } else {
            newHeight = (int) (targetHeight * (1 - interpolatedTime));
        }
        view.getLayoutParams().height = newHeight;
        view.requestLayout();
        if (view.getVisibility() == View.GONE) {
            view.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void initialize(int width, int height, int parentWidth,
                           int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
    }

    @Override
    public boolean willChangeBounds() {
        return true;
    }

}
