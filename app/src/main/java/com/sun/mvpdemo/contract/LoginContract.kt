package com.sun.mvpdemo.contract

import android.content.Context
import com.sun.mvpdemo.baselibrary.base.BasePresenter
import com.sun.mvpdemo.baselibrary.base.BaseView
import com.sun.mvpdemo.entity.LoginEntity

/**
 * @author sun
 * @data 2018-12-27
 * @Explain 登陆
 */

interface LoginContract{
    interface MvpView : BaseView {
        fun doSuc(loginEntity: LoginEntity?)
        fun doFail(throwable: Throwable)
    }
    abstract class Presenter : BasePresenter<MvpView>() {
        abstract fun getLoginContract(context: Context,user_phone: String,user_pwd: String)
    }
}
