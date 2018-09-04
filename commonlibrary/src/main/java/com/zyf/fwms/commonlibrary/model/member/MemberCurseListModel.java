package com.zyf.fwms.commonlibrary.model.member;

import com.zyf.fwms.commonlibrary.model.BaseRecyclerModel;

import java.util.List;

/**
 * 创建 by lyf on 19/04/2018.
 * 描述：
 */

public class MemberCurseListModel {
    /**
     * totalCount : 1
     * pageSize : 1000
     * totalPage : 1
     * page : 1
     * list : [{"archiveId":1,"title":"思想食堂社群活动","adminId":2,"createTime":"2017-10-13 07:35:17","description":"加入思想食堂分会，与2000位学友同行","viewRank":0,"viewCount":0,"goodNum":0,"badNum":0,"favor":0,"id":1,"collectTime":1519532282000,"cateId":1,"deptId":2,"status":1,"cate":{"id":1,"fid":0,"name":"测试","status":0,"sort":50},"dept":{"deptId":2,"name":"企投会"}}]
     */

    public int totalCount;
    public int pageSize;
    public int totalPage;
    public int page;
    public List<ListBean> list;



    public static class ListBean  extends BaseRecyclerModel{
        /**
         * archiveId : 1
         * title : 思想食堂社群活动
         * adminId : 2
         * createTime : 2017-10-13 07:35:17
         * description : 加入思想食堂分会，与2000位学友同行
         * viewRank : 0
         * viewCount : 0
         * goodNum : 0
         * badNum : 0
         * favor : 0
         * id : 1
         * collectTime : 1519532282000
         * cateId : 1
         * deptId : 2
         * status : 1
         * cate : {"id":1,"fid":0,"name":"测试","status":0,"sort":50}
         * dept : {"deptId":2,"name":"企投会"}
         */

        public int archiveId;
        public String title;
        public int adminId;
        public String createTime;
        public String description;
        public int viewRank;
        public int viewCount;
        public int goodNum;
        public int badNum;
        public int favor;
        public int id;
        public long collectTime;
        public int cateId;
        public int deptId;
        public int status;
        public CateBean cate;
        public DeptBean dept;


        public static class CateBean {
            /**
             * id : 1
             * fid : 0
             * name : 测试
             * status : 0
             * sort : 50
             */

            public int id;
            public int fid;
            public String name;
            public int status;
            public int sort;


            }
        }

        public static class DeptBean {
            /**
             * deptId : 2
             * name : 企投会
             */

            public int deptId;
            public String name;



    }
}
