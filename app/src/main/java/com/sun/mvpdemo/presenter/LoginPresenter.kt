package com.sun.mvpdemo.presenter

import android.content.Context
import com.sun.mvpdemo.contract.LoginContract
import com.sun.mvpdemo.entity.LoginEntity


/**
 * @author sun
 * @data 2018-12-27
 * @Explain 登陆
 */
class LoginPresenter : LoginContract.Presenter() {
    override fun getLoginContract(context: Context, user_phone: String, user_pwd: String) {
        mvpView?.showLoading()
        if (user_phone == "123456" && user_pwd == "123456"){
            mvpView?.doSuc(LoginEntity(1,"","1",2,"1","1"))
        }else{
            mvpView?.doFail(Throwable("账号或密码错误"))
        }
//        val map = HashMap<String,String>()
//        map["user_phone"] = user_phone
//        map["user_pwd"] = user_pwd
//        mvpView?.showLoading()
//        startTask(ExtremeCarApp.instance.getService().getLogin(CommonUtils.generateRequestBody(map)), Consumer {
//                success -> run {
//            //            Strings.uid = success.Data.uid.toString()
//            setUser(success.Data.user_nickname,success.Data.user_phone,success.Data.uid.toString(),success.Data.user_avatar,user_phone,user_pwd)
//            mvpView?.hideLoading()
//            mvpView?.doSuc(success.Data)
//            Log.d("正确----------->$success")
//        } }, Consumer { t -> run {
//            mvpView?.hideLoading()
//            mvpView?.doFail(t!!)
//            Log.d("错误----------->"+t.message)
//        } })

    }
}