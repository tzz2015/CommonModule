package com.zyf.fwms.commonlibrary.view;

import android.view.ViewGroup;

import com.zyf.fwms.commonlibrary.base.baseadapter.BaseRecyclerViewHolder;
import com.zyf.fwms.commonlibrary.databinding.ItemEmptyViewBinding;
import com.zyf.fwms.commonlibrary.model.BaseRecyclerModel;
import com.zyf.fwms.commonlibrary.utils.CommonUtils;

/**
 * 创建 by lyf on 25/04/2018.
 * 描述：
 */

public  class EmptyViewHolder extends BaseRecyclerViewHolder<ItemEmptyViewBinding>{

    public EmptyViewHolder(ViewGroup viewGroup, int layoutId) {
        super(viewGroup, layoutId);
    }

    /**
     * @param object   绑定的数据
     * @param position 数据索引
     */
    @Override
    public void onBindViewHolder(BaseRecyclerModel object, int position) {
        CommonUtils.setTextValue(binding.tvDse,object.x_text);
        if(object.object!=null && object.object instanceof Integer){
            int id=Integer.valueOf(object.object.toString());
            binding.ivImage.setImageResource(id);
        }
    }
}
