package com.sun.mvpdemo.baselibrary.http

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.sun.mvpdemo.baselibrary.moudle.HttpResult
import com.sun.mvpdemo.baselibrary.utils.exception.AppException
import okhttp3.*


/**
 * @author sun
 * @data 2018-12-25
 * @Explain 处理结果拦截器
 */
class ResponseInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain?): Response {
        if (chain == null) throw AppException(AppException.CODE_REQUEST_UNKNOWN_ERROR, "请求异常")
        val request = chain.request()
        val response = chain.proceed(request)
        if (response.body() != null && response.body()!!.contentType() != null) {
            //复制一个requestBody
            val mediaType = response.body()!!.contentType()
            val content = response.body()!!.string()
            val responseBody = ResponseBody.create(mediaType, content)

            //解析返回结果
            val adapter = GsonBuilder().create().getAdapter(TypeToken.get(HttpResult::class.java))
            val baseEntity = adapter.fromJson(content)
            if (baseEntity.Code != 1){
                throw AppException(baseEntity.Code,baseEntity.Msg)
            }
            return response.newBuilder().body(responseBody).build()
        }
        return response
    }
}