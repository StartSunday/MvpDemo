package com.sun.mvpdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import com.sun.mvpdemo.baselibrary.base.MvpActivity
import com.sun.mvpdemo.baselibrary.utils.RouterParams
import com.sun.mvpdemo.baselibrary.utils.Tip
import com.sun.mvpdemo.contract.LoginContract
import com.sun.mvpdemo.entity.LoginEntity
import com.sun.mvpdemo.presenter.LoginPresenter
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : MvpActivity<LoginContract.Presenter,LoginContract.MvpView>(),LoginContract.MvpView {
    override fun createPresenter(): LoginContract.Presenter = LoginPresenter()

    override fun getMvpView(): LoginContract.MvpView?  = this

    override fun layoutResID(): Int = R.layout.activity_login

    override fun initView(savedInstanceState: Bundle?) {
        login.setOnClickListener {
            mvpPresenter?.getLoginContract(this,user_name_et.text.toString(),password_et.text.toString())
        }
    }

    override fun doSuc(loginEntity: LoginEntity?) {
        ARouter.getInstance().build(RouterParams.User.PATH_MAIN).navigation()
    }

    override fun doFail(throwable: Throwable) {
        Tip.show(this,throwable.message+"")
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

}
