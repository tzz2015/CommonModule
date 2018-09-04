package com.zyf.fwms.commonlibrary.model.medium;

import com.zyf.fwms.commonlibrary.model.BaseRecyclerModel;
import com.zyf.fwms.commonlibrary.utils.CommonUtils;
import com.zyf.fwms.commonlibrary.utils.TimeUtils;

import java.util.List;

/**
 * 创建 by lyf on 21/04/2018.
 * 描述：
 */

public class CurseDetailModel {

    public int id;
    public int deptId;
    public int cateId;
    public String thumbnail;
    public String title;
    public String content;
    public int openInvite;
    public int regionId;
    public String city;
    public long startTime;
    public long endTime;
    public long regStart;
    public long regEnd;
    public int showStock;
    public int repeatSignup;
    public int signupCount;
    public int status;
    public int sort;
    public long createTime;
    public String requirement;
    public DeptBean dept;
    public String description;
    public List<TycoonBean> tycoonList;
    public SignupBean signup;

    public String getNTitle() {
        if (signup == null)
            return title;
        else return "          " + title;
    }

    public String getDate() {
        String start = TimeUtils.formatData(TimeUtils.dateFormatYMD2, startTime);
        String end = TimeUtils.formatData(TimeUtils.dateFormatYMD2, endTime);
        return CommonUtils.subTime(start, end);

    }


    public static class SignupBean {


        public int id;
        public int courseId;
        public int userId;
        public int specId;
        public int status;
        public long createTime;


    }


    public static class DeptBean {
        /**
         * deptId : 3
         * name : 思想食堂
         */

        public int deptId;
        public String name;


    }

    public static class TycoonBean extends BaseRecyclerModel {


        public int id;
        public int deptId;
        public String name;
        public String headimgurl;
        public String description;
        public long createTime;
        public long updateTime;
        public int status;


    }
}
