package com.zyf.fwms.commonlibrary.model.today;

import com.zyf.fwms.commonlibrary.R;
import com.zyf.fwms.commonlibrary.model.BaseRecyclerModel;
import com.zyf.fwms.commonlibrary.utils.CommonUtils;
import com.zyf.fwms.commonlibrary.utils.TimeUtils;

import java.util.List;

/**
 * 创建 by lyf on 27/03/2018.
 * 描述：
 */

public class TodayListModel {

    /**
     * totalCount : 2
     * pageSize : 20
     * totalPage : 1
     * page : 1
     * list : [{"id":2,"type":"course","foreignId":34,"status":1,"createTime":1521936000000,"data":{"id":34,"thumbnail":"http://qiniu.dtsxst.cn/img/7d672c48265741d1b046a615e2efa0d7.jpg","title":"课程预约丨3月31日青岛，和胡泳、孟华老师一起寻找传播中的关键节点","description":null,"content":"<br>","regionId":null,"city":null,"address":null,"link":null,"startTime":1522486800000,"endTime":1522515600000,"regStart":1521072000000,"regEnd":1522454340000,"status":1,"createTime":1517579308000,"signupId":null,"signupStatus":null,"signupTime":null,"attendStatus":null,"attendTime":null,"openInvite":0,"specList":null,"albumList":null,"sort":997,"showStock":1,"repeatSignup":0}},{"id":1,"type":"news","foreignId":1,"status":1,"createTime":1521936000000,"data":{"archiveId":1,"postType":2,"title":"思想食堂社群活动","adminId":2,"createTime":"2017-10-13 15:35:17","description":"加入思想食堂分会，与2000位学友同行","keywords":null,"viewRank":0,"viewCount":0,"writer":"","source":"","pubTime":1507908917000,"updateTime":1508326262000,"thumbnail":null,"lastReply":null,"canReply":0,"goodNum":0,"badNum":0,"checkAdmin":0,"content":"<br>","favor":0,"isFavor":null,"id":1,"collectTime":1519561082000,"cateId":1,"foreignId":null,"status":1,"articleCate":null}}]
     * startRow : 0
     */

    public int totalCount;
    public int pageSize;
    public int totalPage;
    public int page;
    public int startRow;
    public List<ListBean> list;


    public static class ListBean extends BaseRecyclerModel {
        /**
         * id : 2
         * type : course
         * foreignId : 34
         * status : 1
         * createTime : 1521936000000
         * data : {"id":34,"thumbnail":"http://qiniu.dtsxst.cn/img/7d672c48265741d1b046a615e2efa0d7.jpg","title":"课程预约丨3月31日青岛，和胡泳、孟华老师一起寻找传播中的关键节点","description":null,"content":"<br>","regionId":null,"city":null,"address":null,"link":null,"startTime":1522486800000,"endTime":1522515600000,"regStart":1521072000000,"regEnd":1522454340000,"status":1,"createTime":1517579308000,"signupId":null,"signupStatus":null,"signupTime":null,"attendStatus":null,"attendTime":null,"openInvite":0,"specList":null,"albumList":null,"sort":997,"showStock":1,"repeatSignup":0}
         */

        public int id;
        public String type;
        public int foreignId;
        public int status;
        public long createTime;
        public long pubTime;
        public DataBean data;

        public String getType() {
            if (CommonUtils.isNotEmpty(type) && type.equals("course"))
                return "课程";
            if (CommonUtils.isNotEmpty(type) && type.equals("news"))
                return "资讯";
            return "活动";
        }

        public String createTimes() {
            if (pubTime > 0) {
                return TimeUtils.formatData(TimeUtils.dateFormatYMD, pubTime);
            } else {
                return TimeUtils.formatData(TimeUtils.dateFormatYMD, createTime);
            }
        }


        public static class DataBean {
            /**
             * id : 34
             * thumbnail : http://qiniu.dtsxst.cn/img/7d672c48265741d1b046a615e2efa0d7.jpg
             * title : 课程预约丨3月31日青岛，和胡泳、孟华老师一起寻找传播中的关键节点
             * description : null
             * content : <br>
             * regionId : null
             * city : null
             * address : null
             * link : null
             * startTime : 1522486800000
             * endTime : 1522515600000
             * regStart : 1521072000000
             * regEnd : 1522454340000
             * status : 1
             * createTime : 1517579308000
             * signupId : null
             * signupStatus : null
             * signupTime : null
             * attendStatus : null
             * attendTime : null
             * openInvite : 0
             * specList : null
             * albumList : null
             * sort : 997
             * showStock : 1
             * repeatSignup : 0
             */

            public int id;
            public String thumbnail;
            public String title;
            public String description;
            public String content;
            public String regionId;
            public String city;
            public String address;
            public String link;
            public long startTime;
            public long pubTime;
            public long endTime;
            public long regStart;
            public long regEnd;
            public int status;
            public String createTime;
            public int signupId;
            public int signupStatus;
            public long signupTime;
            public int attendStatus;
            public long attendTime;
            public int openInvite;
            public String specList;
            public String albumList;
            public int sort;
            public int showStock;
            public int repeatSignup;
            public DeptBean dept;

            public String getFrom() {
                if (dept == null) return "";
                return "来自  " + dept.name;
            }

            public static class DeptBean {
                public int deptId;
                public String name;
            }


        }
    }
}
