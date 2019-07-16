package com.sun.mvpdemo.baselibrary.moudle

/**
 * @author sun
 * @data 2018-12-25
 * @Explain 通用实体封装
 */
data class HttpResult<T>(val Code: Int, val Data: T, val Msg: String)