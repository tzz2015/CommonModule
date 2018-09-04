package com.zyf.fwms.commonlibrary.model.mine;

import com.zyf.fwms.commonlibrary.utils.CommonUtils;
import com.zyf.fwms.commonlibrary.utils.SharedPreUtil;

import java.io.Serializable;
import java.util.List;

/**
 * 刘宇飞 创建 on 2017/3/5.
 * 描述：用户信息
 */

public class AccountInfo implements Serializable {
    public volatile static AccountInfo userInfoModel;

    public long id;
    public String unionId;
    public String nickname;
    public String headimgurl;
    public String name;
    public String sex;
    public String phone;
    public Object country;
    public Object province;
    public Object city;
    public Object district;
    public Object address;
    public Object company;
    public Object job;
    public Object trade;
    public Object money;
    public Object qrcodeUrl;
    public Object score;
    public Object regip;
    public Object state;
    public Object loginCount;
    public long lastLoginTime;
    public Object lastLoginIp;
    public Object currLoginTime;
    public Object currLoginIp;
    public Object learnTime;
    public Object continueLearnDay;
    public Object attendCourseCount;
    public Object lastLearnTime;
    public Object updateTime;
    public long createTime;
    public Object inviterId;
    public String token;
    public List<Integer> memberId;//会员id
    public List<String> school;//学院期数
    public boolean bindWeChat;

    public AccountInfo() {
    }

    public static AccountInfo getInstance() {
        if (userInfoModel == null) {
            synchronized (AccountInfo.class) {
                if (userInfoModel == null) {
                    userInfoModel = new AccountInfo();
                }
            }
        }
        return userInfoModel;
    }

    public void setAccountInfo() {
        setId(id);
        setNickname(nickname);
        setHeadimgurl(headimgurl);
        setToken(token);
        setPhone(phone);
        setBindWeChat(bindWeChat);
    }

    public void removeAccountInfo() {
        SharedPreUtil.clearByKey(CommonUtils.getContext(), "user_id");
        SharedPreUtil.clearByKey(CommonUtils.getContext(), "nickname");
        SharedPreUtil.clearByKey(CommonUtils.getContext(), "headimgurl");
        SharedPreUtil.clearByKey(CommonUtils.getContext(), "token");
        SharedPreUtil.clearByKey(CommonUtils.getContext(), "memberId");
        SharedPreUtil.clearByKey(CommonUtils.getContext(), "school");
        SharedPreUtil.clearByKey(CommonUtils.getContext(), "phone");
        SharedPreUtil.clearByKey(CommonUtils.getContext(), "bindWeChat");
    }

    public void setNickname(String nickname) {
        SharedPreUtil.saveString(CommonUtils.getContext(), "nickname", nickname);
    }

    public String getNickname() {
        return SharedPreUtil.getString(CommonUtils.getContext(), "nickname", "");
    }

    public void setPhone(String phone) {
        SharedPreUtil.saveString(CommonUtils.getContext(), "phone", phone);
    }

    public String getPhone() {
        return SharedPreUtil.getString(CommonUtils.getContext(), "phone", "");
    }

    public void setHeadimgurl(String headimgurl) {
        SharedPreUtil.saveString(CommonUtils.getContext(), "headimgurl", headimgurl);

    }

    public String getHeadimgurl() {
        return SharedPreUtil.getString(CommonUtils.getContext(), "headimgurl", "");
    }

    public void setToken(String token) {
        if (CommonUtils.isEmpty(token))
            return;
        SharedPreUtil.saveString(CommonUtils.getContext(), "token", token);

    }

    public String getToken() {
        return SharedPreUtil.getString(CommonUtils.getContext(), "token", "");
    }

    public void setId(long id) {
        SharedPreUtil.saveLong(CommonUtils.getContext(), "user_id", id);

    }

    public long getId() {
        return SharedPreUtil.getLong(CommonUtils.getContext(), "user_id", 0);
    }

    public void setHaveMember(boolean isHaveMember) {
        SharedPreUtil.saveBoolean(CommonUtils.getContext(), "isHaveMember", isHaveMember);

    }

    public boolean getBindWeChat() {
        return SharedPreUtil.getBoolean(CommonUtils.getContext(), "bindWeChat", false);
    }

    public void setBindWeChat(boolean bindWeChat) {
        SharedPreUtil.saveBoolean(CommonUtils.getContext(), "bindWeChat", bindWeChat);
    }

    public void setMemberIdList(List<Integer> memberId) {
        SharedPreUtil.setDataList(CommonUtils.getContext(), "memberId", memberId);

    }

    public List<Integer> getMemberList() {
        return SharedPreUtil.getDataList(CommonUtils.getContext(), "memberId", Integer.class);
    }

    public void setSchoolList(List<String> memberId) {
        SharedPreUtil.setDataList(CommonUtils.getContext(), "school", memberId);

    }

    public List<String> getSchoolList() {
        return SharedPreUtil.getDataList(CommonUtils.getContext(), "school", String.class);
    }


}
