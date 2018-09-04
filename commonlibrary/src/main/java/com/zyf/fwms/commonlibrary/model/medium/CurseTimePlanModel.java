package com.zyf.fwms.commonlibrary.model.medium;

import com.zyf.fwms.commonlibrary.model.BaseRecyclerModel;

/**
 * 创建 by lyf on 21/04/2018.
 * 描述：
 */

public class CurseTimePlanModel extends BaseRecyclerModel implements Cloneable{


   
    public int id;
    public int courseId;
    public String title;
    public long startTime;
    public String city;
    public String address;
    public String content;
    public long createTime;
    public long updateTime;
    public int status;
    public boolean real;
    public int position;

    @Override
    public Object clone()  {
        CurseTimePlanModel stu = null;
        try{
            stu = (CurseTimePlanModel)super.clone();
        }catch(CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return stu;
    }
}
