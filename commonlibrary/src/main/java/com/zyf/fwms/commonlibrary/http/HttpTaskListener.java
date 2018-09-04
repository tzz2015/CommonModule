package com.zyf.fwms.commonlibrary.http;

/**
 *
 * 刘宇飞 创建 on 2017/5/20.
 * 描述：
 */

public interface HttpTaskListener<T> {
    /**
     * 请求网络成功
     */
    void onSuccess(int requestId, T object);

    /**
     * 请求网络失败
     * @param requestId
     */
    void onException(int requestId);
}
