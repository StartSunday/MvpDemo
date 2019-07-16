package com.sun.mvpdemo.baselibrary.http

import java.lang.reflect.Type

import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit

/**
 * @author sun
 * @data 2018-12-25
 * @Explain 本地数据
 */

class NullOnEmptyConverterFactory : Converter.Factory() {

    override fun responseBodyConverter(type: Type?, annotations: Array<Annotation>?, retrofit: Retrofit?): Converter<ResponseBody, *>? {
        val delegate = retrofit!!.nextResponseBodyConverter<Any>(this, type!!, annotations!!)

        return Converter<ResponseBody, Any> { value -> if (value.contentLength() == 0L) null else delegate.convert(value) }
    }
}