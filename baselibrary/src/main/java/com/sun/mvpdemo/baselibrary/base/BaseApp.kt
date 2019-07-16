package com.sun.mvpdemo.baselibrary.base

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.sun.mvpdemo.baselibrary.BuildConfig
import com.sun.mvpdemo.baselibrary.http.RetrofitManager
import com.sun.mvpdemo.baselibrary.utils.Desity
import com.squareup.leakcanary.LeakCanary

/**
 * @author sun
 * @data 2018-12-24
 * @Explain: app
 */
open class BaseApp : Application(){
    override fun onCreate() {
        super.onCreate()
        instance = this
        init()
        Desity.setDensity(this)
        initLeakCanary()
        initARouter()

    }

    private fun init() {
        RetrofitManager.instance.init(this)
    }
    private fun initARouter() {
        //初始化路由
        if (BuildConfig.DEBUG){    // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog()//打印日志
            ARouter.openDebug()//开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this)// 尽可能早，推荐在Application中初始化
    }

    companion object {
        @JvmStatic
        lateinit var instance: BaseApp
            private set
        val url: String = ""
    }
    private fun initLeakCanary(){
        if (BuildConfig.DEBUG){
            //内存泄露检查工具
            if (LeakCanary.isInAnalyzerProcess(this)){
                return
            }
            LeakCanary.install(this)
        }
    }

}