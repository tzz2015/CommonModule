package com.zyf.fwms.commonlibrary.model.mine;

import com.zyf.fwms.commonlibrary.model.BaseRecyclerModel;

import java.util.List;

/**
 * 创建 by lyf on 10/04/2018.
 * 描述：
 */

public class MemberCardModel extends BaseRecyclerModel {

   
    public boolean isMore=false;
    public int id;
    public int userId;
    public int memberId;
    public String name;
    public String thumbnail;
    public String description;
    public DeptBean dept;
    public int courseLimit;
    public long startTime;
    public long endTime;
    public Object createTime;
    public Object updateTime;
    public List<RightsBean> rights;
    public String school;
    public String timeColor;

   

    public static class DeptBean {


        public int deptId;
        public String name;

       
    }
    public static class RightsBean extends BaseRecyclerModel{

        /**
         * id : 5
         * membershipId : 8
         * icon : http://p53ibj6x1.bkt.clouddn.com/Group12.png
         * title : 企投家学习体系
         * status : 1
         * createTime : null
         */

        public int id;
        public int membershipId;
        public String icon;
        public String title;
        public int status;
        public Object createTime;

       
    }

}
