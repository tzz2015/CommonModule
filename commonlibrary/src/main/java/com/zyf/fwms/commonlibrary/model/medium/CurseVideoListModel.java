package com.zyf.fwms.commonlibrary.model.medium;

import com.zyf.fwms.commonlibrary.model.BaseRecyclerModel;

import java.util.List;

/**
 * 创建 by lyf on 21/04/2018.
 * 描述：
 */

public class CurseVideoListModel {


    public int id;
    public int deptId;
    public String thumbnail;
    public String name;
    public int sort;
    public int status;
    public String updateTime;
    public int updateId;
    public String createTime;
    public int createId;
    public List<VideoBean> videoList;


    public static class VideoBean extends BaseRecyclerModel{


        public int id;
        public int deptId;
        public int albumId;
        public String name;
        public String videoUrl;
        public String thumbnail;
        public String ext;
        public int duration;
        public int isComment;
        public int isRecommend;
        public String remark;
        public int favor;
        public int view;
        public long updateTime;
        public long createTime;
        public AlbumBean album;
        public int position;
        public int nPosition;
        public boolean isLast;
        public boolean isPlaying;

        public String getName(){
            return position+". "+name;
        }
        public String getMinutes(){
           int hour=duration/60;
           String minnte=(duration-60*hour)<10?"0"+(duration-60*hour):""+(duration-60*hour);
           return "时长："+hour+":"+minnte+"";
        }


        public static class AlbumBean {


            public int id;
            public String thumbnail;
            public String name;


        }
    }
}
