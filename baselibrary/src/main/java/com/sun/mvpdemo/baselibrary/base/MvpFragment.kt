package com.sun.mvpdemo.baselibrary.base

import android.os.Bundle
import com.sun.mvpdemo.baselibrary.utils.exception.AppException

/**
 * @author sun
 * @data 2018-12-26
 * @Explain fragment基类
 */
abstract class MvpFragment<P : BasePresenter<V>, V : BaseView> : BaseFragment() {
    protected var mvpPresenter: P? = null

    protected abstract fun getMvpView(): V
    protected abstract fun createPresenter(): P
    override fun onCreate(savedInstanceState: Bundle?) {
        val mvpView = getMvpView()
        mvpPresenter = createPresenter()
        if (mvpPresenter != null)
            mvpPresenter!!.attachView(mvpView)
        else
            throw AppException(AppException.CODE_NOT_PARAMETER, "presenter或者mvpView为空！")
        return super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        mvpPresenter!!.detachView()
        super.onDestroy()
    }
}