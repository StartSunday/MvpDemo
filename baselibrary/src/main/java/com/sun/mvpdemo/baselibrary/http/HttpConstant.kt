package com.sun.mvpdemo.baselibrary.http

/**
 * @author sun
 * @data 2018-12-25
 * @Explain 本地数据
 */
object HttpConstant {
    var HTTP_TYPE = 1
    val apiBaseUrl: String = getBaseUrl()
    const val CONNECT_TIME_OUT = 15L
    const val READ_TIME_OUT = 15L
    const val WRITE_TIME_OUT = 15L

    const val CODE_SUCCESS = 1
    const val CODE_FAIL = 0
//    val CODE_OFF_LINE = 905 //被踢下线
//    val CODE_TOKEN_PAST = 909 //Token过期

    private fun getBaseUrl():String{
        var apiUrl: String = ""
        when(HTTP_TYPE){
            0-> apiUrl = "http://47.99.191.139:9090"
            1-> apiUrl = "https://go.qingtuyangche.com"
//            2-> apiUrl = "http://218.6.69.201:8999/ecc/"
        }
        return apiUrl
    }
}