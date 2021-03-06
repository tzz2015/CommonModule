package com.zyf.fwms.commonlibrary.base.baseadapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zyf.fwms.commonlibrary.model.BaseRecyclerModel;
import com.zyf.fwms.commonlibrary.utils.AutoUtils;

import butterknife.ButterKnife;

/**
 * Created by jingbin on 2016/11/25
 */
public abstract class BaseRecyclerViewHolder< D extends ViewDataBinding> extends RecyclerView.ViewHolder {

    public D binding;

    public BaseRecyclerViewHolder(ViewGroup viewGroup, int layoutId) {
        // 注意要依附 viewGroup，不然显示item不全!!
        super(DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), layoutId, viewGroup, false).getRoot());
        // 得到这个View绑定的Binding
        binding = DataBindingUtil.getBinding(this.itemView);
        ButterKnife.bind(this,this.itemView);
        AutoUtils.auto(this.itemView);
    }

    /**
     * @param object   绑定的数据
     * @param position 数据索引
     */
    public abstract void onBindViewHolder(BaseRecyclerModel object, final int position);

    /**
     * 当数据改变时，binding会在下一帧去改变数据，如果我们需要立即改变，就去调用executePendingBindings方法。
     */
    void onBaseBindViewHolder(BaseRecyclerModel object, final int position) {
        onBindViewHolder(object, position);
        binding.executePendingBindings();
    }

    public void isLastItem(boolean isLast){

    }

}
