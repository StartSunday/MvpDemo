package com.sun.mvpdemo.baselibrary.http

import android.content.Context
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.sun.mvpdemo.baselibrary.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author sun
 * @data 2018-12-25
 * @Explain 一个请求方法
 */
class RetrofitManager {
    private var mRetrofit: Retrofit? = null
    private object Holder{
        val INSTANCE = RetrofitManager()
    }
    companion object {
        val instance: RetrofitManager by lazy {
            Holder.INSTANCE
        }
    }


    fun init(context: Context) {
        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            // Log信息拦截器
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            //设置 Debug Log 模式
            builder.addInterceptor(loggingInterceptor)
                    .addNetworkInterceptor(StethoInterceptor())
        }
        builder.addInterceptor(RequestInterceptor(context))
        builder.addInterceptor(ResponseInterceptor())
        builder.connectTimeout(HttpConstant.CONNECT_TIME_OUT, TimeUnit.SECONDS)
        builder.readTimeout(HttpConstant.READ_TIME_OUT, TimeUnit.SECONDS)
        builder.writeTimeout(HttpConstant.WRITE_TIME_OUT, TimeUnit.SECONDS)
        val okHttpClient = builder.build()
        mRetrofit = Retrofit.Builder()
                .baseUrl(HttpConstant.apiBaseUrl)
//                .addConverterFactory(NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()

    }

    fun getRetrofit(): Retrofit? {
        return mRetrofit
    }
}