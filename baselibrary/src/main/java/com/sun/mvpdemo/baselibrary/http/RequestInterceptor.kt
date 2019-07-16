package com.sun.mvpdemo.baselibrary.http

import android.content.Context
import com.sun.mvpdemo.baselibrary.utils.CheckNetwork
import com.sun.mvpdemo.baselibrary.utils.exception.AppException
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @author sun
 * @data 2018-12-25
 * @Explain
 */
class RequestInterceptor
    constructor(private val mContext: Context): Interceptor{

    override fun intercept(chain: Interceptor.Chain?): Response {
        //当没有网络连接时
        if (!CheckNetwork.isNetworkAvailable(mContext)) {
            throw AppException(AppException.CODE_NONE_NETWORK, "请检查您的网络连接")
        }
        if (chain == null) throw AppException(AppException.CODE_REQUEST_UNKNOWN_ERROR, "请求异常")
        val original = chain.request()
        val builder = original.newBuilder()
//        builder.addHeader("Content-Type", "multipart/form-data")
//        builder.addHeader("Content-Type", "application/json; charset=UTF-8")
//        builder.addHeader("version_code", SystemUtil.getVersionCode(mContext).toString());
//        builder.addHeader("platform", "android")
//        if (CommonBiz.getInstance().isLogin(mContext)) {
//            builder.addHeader("token", PreferencesHelper.getInstance().getToken(mContext))
//        }
        val request = builder.method(original.method(), original.body()).build()
        return chain.proceed(request)
    }
}