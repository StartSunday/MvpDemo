package com.sun.mvpdemo.baselibrary.utils.exception

/**
* @author  sun
* @data 2019-01-03
* @Explain
*/
class AppException constructor(private val mCode: Int,private val msg: String): RuntimeException(){
    companion object {
        val CODE_NOT_PARAMETER = 88000//mvp参数不齐
        val CODE_NONE_NETWORK = 88001//网络异常
        val CODE_REQUEST_UNKNOWN_ERROR = 88002//请求异常
        val CODE_RESPONSE_RESULT_ERROR = 88003//结果返回异常
        val CODE_SUCCESS = 1
        val CODE_FAIL = 0
    }
    fun getCode(): Int {
        return mCode
    }
    fun getMsg(): String {
        return msg
    }
}