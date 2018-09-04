package com.zyf.fwms.commonlibrary.model;

import java.io.Serializable;

/**
 *
 * 刘宇飞创建 on 2017/5/18.
 * 描述：
 */

public class BaseRecyclerModel implements Serializable{
    public int viewType;
    public String x_text;//冗余
    public Object object;

    public BaseRecyclerModel() {

    }
    public BaseRecyclerModel(int viewType) {
        this.viewType = viewType;
    }

    public BaseRecyclerModel(int viewType, String x_text) {
        this.viewType = viewType;
        this.x_text = x_text;
    }

    public BaseRecyclerModel(int viewType, Object object) {
        this.viewType = viewType;
        this.object = object;
    }

    public BaseRecyclerModel(int viewType, String x_text, Object object) {
        this.viewType = viewType;
        this.x_text = x_text;
        this.object = object;
    }
}
