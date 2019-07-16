package com.sun.mvpdemo

import com.sun.mvpdemo.baselibrary.base.BaseApp
import com.sun.mvpdemo.baselibrary.http.RetrofitManager
import com.sun.mvpdemo.http.ApiService

class MvpApp: BaseApp() {
    companion object {
        @JvmStatic
        val instance: MvpApp by lazy {
            Holder.INSTANCE
        }
    }
    private object Holder{
        val INSTANCE = MvpApp()
    }

    override fun onCreate() {
        super.onCreate()
    }
    fun getService(): ApiService {
        return RetrofitManager.instance.getRetrofit()!!.create<ApiService>(ApiService::class.java)
    }
}
