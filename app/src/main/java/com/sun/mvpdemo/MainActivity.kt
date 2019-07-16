package com.sun.mvpdemo

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.sun.mvpdemo.baselibrary.base.BaseActivity
import com.sun.mvpdemo.baselibrary.utils.RouterParams

@Route(path = RouterParams.User.PATH_MAIN)
class MainActivity : BaseActivity() {
    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun layoutResID(): Int = R.layout.activity_main

}
