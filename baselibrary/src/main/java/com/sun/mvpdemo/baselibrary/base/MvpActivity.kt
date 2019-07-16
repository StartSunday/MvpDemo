package com.sun.mvpdemo.baselibrary.base
import android.os.Bundle
import com.sun.mvpdemo.baselibrary.utils.exception.AppException

/**
 * @author sun
 * @data 2018-12-26
 * @Explain 有使用数据请求的页面
 */
abstract class MvpActivity<P : BasePresenter<V>,V: BaseView> : BaseActivity(){
    protected var mvpPresenter: P? = null

    protected abstract fun createPresenter(): P

    protected abstract fun getMvpView(): V?
    override fun onCreate(savedInstanceState: Bundle?) {
        val mvpView = getMvpView()
        mvpPresenter = createPresenter()
        if (mvpPresenter != null && mvpView != null)
            mvpPresenter!!.attachView(mvpView)
        else
            throw AppException(AppException.CODE_NOT_PARAMETER, "Presenter或者MvpView不能为空！")
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        if (mvpPresenter != null) {
            mvpPresenter!!.detachView()
        }
        super.onDestroy()
    }
}
