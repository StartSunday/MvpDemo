package com.sun.mvpdemo.baselibrary.base

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.maning.mndialoglibrary.MProgressDialog
import com.sun.mvpdemo.baselibrary.utils.Tip
import com.sun.mvpdemo.baselibrary.utils.exception.AppException
import com.sun.mvpdemo.baselibrary.view.Loadingdialog
import org.greenrobot.eventbus.EventBus

/**
 * @author sun
 * @Explain 基类fragment
 * @data 2018-12-25
 */
abstract class BaseFragment : androidx.fragment.app.Fragment() {

    private var mContentView: View? = null
    //private ProgressDialog mRunningDialog;
    private var mLoadingDialog: Loadingdialog? = null
    private var mContext: Context? = null
    private var baseActivity: BaseActivity? = null
    private var isInitData = true /*标志位 判断数据是否初始化*/
    private var isVisibleToUser = false /*标志位 判断fragment是否可见*/
    private var isPrepareView = false /*标志位 判断view已经加载完成 避免空指针操作*/

    protected abstract fun getLayoutId(): Int

    protected abstract fun initView(contentView: View)
    /**
     * 是否需要eventBus
     *
     * @return
     */
    open fun getNeedEventBus(): Boolean {
        return false
    }

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        baseActivity = mContext as BaseActivity
//        if (mContext != null) {
//            this.mContext = mContext
//        } else {
//            this.mContext = activity
//        }
//    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mContentView == null) {
            mContentView = inflater.inflate(getLayoutId(), container,false)
        }
        if (getNeedEventBus()) {
            EventBus.getDefault().register(this)
        }
        return mContentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        BaseHelper.initSwipeRefreshLayoutColor(view)
        initView(mContentView!!)
        isPrepareView = true
        lazyInitData()
    }

    override fun onDestroyView() {
        if (getNeedEventBus()) {
            EventBus.getDefault().unregister(this)
        }
        super.onDestroyView()
        isInitData = true
        isVisibleToUser = false
        isPrepareView = false

    }

    protected fun defOnError(t: Throwable) {
        dismissRunningDialog()
        if (t is AppException) {
//            if (HttpConstant.CODE_OFF_LINE == t.getCode() || HttpConstant.CODE_TOKEN_PAST == t.getCode()) {
//                EventBus.getDefault().post(LogoutEvevt())
//            }
//            Log.e(t.getMsg())
            Tip.show(activity!!, t.getMsg())
        } else {
            Tip.show(activity!!, t.message!!)
        }
    }

    protected fun showRunningDialog() {
        MProgressDialog.showProgress(activity!!)
//        if (mLoadingDialog == null) {
//            mLoadingDialog = Loadingdialog(activity!!).builder()
//        }
//        mLoadingDialog!!.show()
    }

    protected fun dismissRunningDialog() {
        MProgressDialog.dismissProgress()
//        if (mLoadingDialog != null) {
//            mLoadingDialog!!.dismiss()
//        }
    }

//    /*加载数据的方法,由子类实现*/
//    abstract fun initData()
//
    /*懒加载方法*/
    private fun lazyInitData() {
        if (!isInitData && !isVisibleToUser && !isPrepareView) {
          return
        }
//        initData()
        //数据加载完毕,恢复标记,防止重复加载
        isInitData = false
    }

    /*当fragment由可见变为不可见和不可见变为可见时回调*/
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
       if (isVisibleToUser){
           this.isVisibleToUser = true
            lazyInitData()
       }else{
           this.isVisibleToUser = false
       }
    }

}
