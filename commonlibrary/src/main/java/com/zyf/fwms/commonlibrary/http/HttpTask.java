package com.zyf.fwms.commonlibrary.http;


import com.zyf.fwms.commonlibrary.model.BaseRespose;

import retrofit2.http.Field;
import retrofit2.http.GET;

import com.zyf.fwms.commonlibrary.model.BaseRespose;
import com.zyf.fwms.commonlibrary.model.medium.CurseDetailModel;
import com.zyf.fwms.commonlibrary.model.medium.CurseRecomABModel;
import com.zyf.fwms.commonlibrary.model.medium.CurseSpectModel;
import com.zyf.fwms.commonlibrary.model.medium.CurseTimePlanModel;
import com.zyf.fwms.commonlibrary.model.medium.CurseVideoListModel;
import com.zyf.fwms.commonlibrary.model.member.AllCurseListModel;
import com.zyf.fwms.commonlibrary.model.member.CurseCountModel;
import com.zyf.fwms.commonlibrary.model.member.DeptListModel;
import com.zyf.fwms.commonlibrary.model.member.KnowlegeDotModel;
import com.zyf.fwms.commonlibrary.model.member.MemberCurseListModel;
import com.zyf.fwms.commonlibrary.model.mine.AccountInfo;
import com.zyf.fwms.commonlibrary.model.mine.MemberCardModel;
import com.zyf.fwms.commonlibrary.model.today.TodayListModel;


import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;
import io.reactivex.Observable;

/**
 * 刘宇飞 创建 on 2017/5/20.
 * 描述：
 */

public interface HttpTask {

    /**
     * 共同get请求
     */
    @GET("{url}")
    Observable<BaseRespose<Object>> requestCommonGet(@Path(value = "url", encoded = true) String url);

    /**
     * 共同post请求
     */
    @FormUrlEncoded
    @POST("{url}")
    Observable<BaseRespose<Object>> requestCommonPost(@Path(value = "url", encoded = true) String url);

    /**
     * 请求今日列表数据
     */
    @GET(Api.TODAY_LIST)
    Observable<BaseRespose<TodayListModel>> requestTodayList(@Query("page") int page, @Query("pageSize") int pageSize);

    /**
     * 微信登录
     */
    @FormUrlEncoded
    @POST(Api.WX_LOGIN)
    Observable<BaseRespose<AccountInfo>> requestWxLogin(@Field("openid") String openid, @Field("accessToken") String accessToken);

    /**
     * 获取用户信息
     */
    @GET(Api.USER_INFO)
    Observable<BaseRespose<AccountInfo>> requestUserInfo();

    /**
     * 获取会员信息信息
     */
    @GET(Api.MENBER_INFO)
    Observable<BaseRespose<List<MemberCardModel>>> requestMemberInfo();

    /**
     * 获取学院列表
     */
    @GET(Api.DEPT_LIST)
    Observable<BaseRespose<List<DeptListModel>>> requestDeptList();

    /**
     * 获取学院课程列表
     */
    @GET(Api.ALL_CURSE)
    Observable<BaseRespose<AllCurseListModel>> requestAllCurseList(@Query("page") int page, @Query("pageSize") int pageSize, @Query("deptId") int deptId, @Query("type") String type);

    /**
     * 课程分类列表
     */
    @GET(Api.CATE_LIST)
    Observable<BaseRespose<List<KnowlegeDotModel>>> requestCateList(@Query("deptId") int deptId);

    /**
     * 模块下课程列表
     */
    @GET(Api.ALL_CURSE)
    Observable<BaseRespose<MemberCurseListModel>> requestCurseList(@Query("deptId") int deptId,@Query("cateId") int cateId, @Query("page") int page, @Query("pageSize") int pageSize);

    /**
     * 获取分类和课程数量
     */
    @GET(Api.CURSE_COUNT)
    Observable<BaseRespose<CurseCountModel>> requestCurseCount(@Query("deptId") int deptId);

    /**
     * 课程详情
     */
    @GET(Api.CURSE_DETAIL + "{id}")
    Observable<BaseRespose<CurseDetailModel>> requestCurseDetail(@Path(value = "id", encoded = true) String id);
    /**
     * 课程课程列表
     */
    @GET("{url}")
    Observable<BaseRespose<List<CurseVideoListModel>>> requestCurseVideoList(@Path(value = "url", encoded = true) String url);
    /**
     * 推荐书单
     */
    @GET("course/{id}/book/list")
    Observable<BaseRespose<List<CurseRecomABModel>>> requestRecomBook(@Path(value = "id", encoded = true) String id);
    /**
     * 推荐文章
     */
    @GET("course/{id}/read/list")
    Observable<BaseRespose<List<CurseRecomABModel>>> requestRecoArticle(@Path(value = "id", encoded = true) String id);
    /**
     * 计划安排
     */
    @GET("course/{id}/arrange/list")
    Observable<BaseRespose<List<CurseTimePlanModel>>> requestCurseTimeList(@Path(value = "id", encoded = true) String id);
    /**
     * 课程场次
     */
    @GET("course/{id}/spec/list")
    Observable<BaseRespose<List<CurseSpectModel>>> requestCurseSpecList(@Path(value = "id", encoded = true) String id);
    /**
     * 报名课程
     */
    @POST("course/{id}/signup")
    Observable<BaseRespose> requestCurseSignup(@Path(value = "id") String id,@Query("specId") int specId);
    /**
     * 取消报名
     */
    @POST("course/{id}/unsignup")
    Observable<BaseRespose> requestCurseUnSignup(@Path(value = "id") String id);
    /**
     * 发送验证码
     */
    @FormUrlEncoded
    @POST(Api.SEND_PHONE_CODE)
    Observable<BaseRespose> requestLoginPhoneCode(@Field(value = "phone") String mobile);
    /**
     * 发送修改手机的验证码
     */
    @FormUrlEncoded
    @POST(Api.SEND_CHANGE_PHONE_CODE)
    Observable<BaseRespose> requestChangePhoneCode(@Field(value = "phone") String mobile);
    /**
     * 验证码登录
     */
    @FormUrlEncoded
    @POST(Api.PHONE_LOGIN)
    Observable<BaseRespose<AccountInfo>> requestLoginByPhone(@Field(value = "phone") String phone ,@Field(value = "code") String code);
    /**
     * 修改手机号码
     */
    @FormUrlEncoded
    @POST(Api.PHONE_BIND)
    Observable<BaseRespose> requestChangePhone(@Field(value = "phone") String mobile ,@Field(value = "code") String code);
    /**
     * 修改手机号码
     */
    @FormUrlEncoded
    @POST(Api.WX_BIND)
    Observable<BaseRespose> requestBindWX(@Field(value = "accessToken") String accessToken ,@Field(value = "openid") String openid);
}
