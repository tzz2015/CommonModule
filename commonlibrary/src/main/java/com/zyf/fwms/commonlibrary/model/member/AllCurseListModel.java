package com.zyf.fwms.commonlibrary.model.member;

import com.zyf.fwms.commonlibrary.model.BaseRecyclerModel;
import com.zyf.fwms.commonlibrary.utils.TimeUtils;

import java.util.Date;
import java.util.List;

/**
 * 创建 by lyf on 12/04/2018.
 * 描述：
 */

public class AllCurseListModel {
    /**
     * totalCount : 8
     * pageSize : 5
     * totalPage : 2
     * page : 1
     * list : []
     */

    public int totalCount;
    public int pageSize;
    public int totalPage;
    public int page;
    public List<ListBean> list;

    public static class ListBean extends BaseRecyclerModel{
        public int id;
        public int deptId;
        public String thumbnail;
        public String title;
        public String content;
        public Object description;
        public Object link;
        public Object qrcodeUrl;
        public int openInvite;
        public int regionId;
        public String city;
        public Object address;
        public long startTime;
        public long endTime;
        public long regStart;
        public long regEnd;
        public int showStock;
        public int repeatSignup;
        public int signupCount;
        public Object show;
        public int status;
        public int sort;
        public long createTime;
        public Object specList;
        public Object albumList;
        public DeptBean dept;
        public Object signup;
        public Object attend;
        public boolean showAddress;

        public String getStartTime(){
            return TimeUtils.formatData(TimeUtils.dateFormatYMDofChinese,startTime);
        }

        public String getStartTime2(){
            return TimeUtils.formatData(TimeUtils.dateFormatMD,startTime);
        }

        public String getTitle(){
            int offectDay = TimeUtils.getOffectDay(startTime, new Date().getTime());
            if(offectDay<7&&showAddress)
                return "                 "+title;
            else return title;
        }


        public static class DeptBean {
            /**
             * deptId : 3
             * parentId : null
             * name : 思想食堂
             * ename : null
             * logo : null
             * cover : null
             * content : null
             * orderNum : null
             * delFlag : null
             */

            public int deptId;
            public Object parentId;
            public String name;
            public Object ename;
            public Object logo;
            public Object cover;
            public Object content;
            public Object orderNum;
            public Object delFlag;

           
        }
    }
}
