package com.sun.mvpdemo.baselibrary.base

import android.content.res.Configuration
import android.os.Bundle
import androidx.core.content.ContextCompat
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.githang.statusbar.StatusBarCompat
import com.maning.mndialoglibrary.MProgressDialog
import com.sun.mvpdemo.baselibrary.R
import com.sun.mvpdemo.baselibrary.utils.Desity
import com.sun.mvpdemo.baselibrary.utils.Tip
import com.sun.mvpdemo.baselibrary.utils.exception.AppException
import com.sun.mvpdemo.baselibrary.view.Loadingdialog
import com.trello.rxlifecycle.components.support.RxAppCompatActivity
import org.greenrobot.eventbus.EventBus

/**
 * @author sun
 * @data 2018-12-24
 * @Explain 基类
 */
abstract class BaseActivity: RxAppCompatActivity() {
    private var mLoadingDialog: Loadingdialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        Desity.setOrientation(this, Desity.WIDTH,Configuration.ORIENTATION_PORTRAIT)
        super.onCreate(savedInstanceState)
        setContentView(layoutResID())
        ARouter.getInstance().inject(this)
        initView(savedInstanceState)
        setSystemUi()
        if (getNeedEvenBus()){
            EventBus.getDefault().register(this)
        }
    }
    protected abstract fun layoutResID(): Int

    protected abstract fun initView(savedInstanceState: Bundle?)
    /**
     * 是否需要EventBus
     */
    open fun getNeedEvenBus():Boolean{
        return false
    }
    fun setStateBarColor(b: Boolean){
        if (b)   StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this,android.R.color.white), true)
        else StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary), true)


    }
    /**
     * 是否隐藏状态栏
     */
    open fun isHideStateBar():Boolean{
        return false
    }

    /**
     * 是否不隐藏底部导航栏
     */
    open fun isHideNavigationBar(): Boolean{
        return false
    }
    /**
     * 修改系统ui
     * SYSTEM_UI_FLAG_LAYOUT_STABLE   可以防止显示/隐藏 status bar 时界面尺寸变化。
     * SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN 内容显示在 status bar 的后面
     * SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION  内容显示在 navigation bar 的后面
     */
    private fun setSystemUi(){
        when{
            isHideStateBar() -> window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            isHideNavigationBar() -> window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            else -> {}
        }
    }

    protected fun defOnError(t: Throwable) {
        dismissRunningDialog()
        if (t is AppException) {
//            if (HttpConstant.CODE_OFF_LINE == t.getCode() || HttpConstant.CODE_TOKEN_PAST == t.getCode()) {
//                EventBus.getDefault().post(LogoutEvevt())
//            }
            Tip.show(this, t.getMsg())
        } else {
            Tip.show(this, t.message!!)
        }
    }

    protected fun showRunningDialog() {
        MProgressDialog.showProgress(this)

//        if (mLoadingDialog == null) {
//            mLoadingDialog = Loadingdialog(this).builder()
//        }
//        mLoadingDialog!!.show()
    }

    protected fun showRunningDialog(isCancelable: Boolean) {
        if (mLoadingDialog == null) {
            mLoadingDialog = Loadingdialog(this).builder()
        }
        mLoadingDialog!!.setCancelable(isCancelable)
        mLoadingDialog!!.show()
    }

    protected fun dismissRunningDialog() {
        MProgressDialog.dismissProgress()
//        if (mLoadingDialog != null) {
//            mLoadingDialog!!.dismiss()
//        }
    }


    /**
     * 系统调用到onPostCreate时，Activity应该已经 start-up
     */
    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        if (window != null)
            BaseHelper.initSwipeRefreshLayoutColor(window.decorView)
    }

    override fun onDestroy() {
        if (getNeedEvenBus()){
            EventBus.getDefault().unregister(this)
        }
        super.onDestroy()
    }

    /**
     * 隐藏布局
     * @param views
     */
    protected fun gone(vararg views: View) {
        if (views.size > 0) {
            for (view in views) {
                view.visibility = View.GONE
            }
        }
    }
    /**
     * 显示布局
     * @param views
     */
    protected fun visible(vararg views: View) {
        if (views.size > 0) {
            for (view in views) {
                view.visibility = View.VISIBLE
            }
        }
    }
}