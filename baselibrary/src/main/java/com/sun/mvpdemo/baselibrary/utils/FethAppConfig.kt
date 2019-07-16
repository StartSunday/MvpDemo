package com.sun.mvpdemo.baselibrary.utils


/**
 * @author sun
 * @data 2018-12-25
 * @Explain 全局能用
 */
class FethAppConfig {
    companion object {
         fun isFirst()= SharedPreferencesUtils.getParam("isFirst",true)!!

        fun getUserName()= SharedPreferencesUtils.getParam("user_nickname","")!!

        fun getUserPhone()= SharedPreferencesUtils.getParam("user_phone","")!!

        fun getUid()= SharedPreferencesUtils.getParam("uid","")!!

        fun getUserImg()= SharedPreferencesUtils.getParam("user_avatar","")!!

        fun getUser()= SharedPreferencesUtils.getParam("user","")!!

        fun getPwd()= SharedPreferencesUtils.getParam("pwd","")!!
    }
}