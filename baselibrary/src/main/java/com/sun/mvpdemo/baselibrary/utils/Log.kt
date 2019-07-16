package com.sun.mvpdemo.baselibrary.utils

import com.sun.mvpdemo.baselibrary.BuildConfig
import com.safframework.log.L


/**
 * @author sun
 * @data 2018-12-29
 * @Explain 日志打印
 */
class Log {
    companion object {
        @JvmStatic
        fun d(string: String){
            if (BuildConfig.DEBUG)
                L.d(string)
        }
        fun json(string: String){
            if (BuildConfig.DEBUG)
                L.json(string)
        }
        fun e(string: String){
            if (BuildConfig.DEBUG)
               L.e(string)
        }
        fun w(string: String){
            if (BuildConfig.DEBUG)
                L.w(string)
        }
        fun json(obj: Any){
            if (BuildConfig.DEBUG)
                L.json(obj)
        }
        fun i(string: String){
            if (BuildConfig.DEBUG)
                L.i(string)
        }
    }
}