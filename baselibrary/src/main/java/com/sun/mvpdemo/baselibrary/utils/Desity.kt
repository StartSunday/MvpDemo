package com.sun.mvpdemo.baselibrary.utils

import android.app.Activity
import android.app.Application
import android.content.ComponentCallbacks
import android.content.res.Configuration
import android.util.DisplayMetrics

import java.text.DecimalFormat

/**
 * @author  sun
 * @data 2018-12-25
 * @Explain 屏幕适配问题
 * https://juejin.im/post/5b3094fc6fb9a00e52398ae4
 */
class Desity {
    companion object {

        val DP_WIDTH = 360//设计图默认宽度dp
        val DP_HEIGHT = 640 //设计图默认高度dp
        var WIDTH = "width"
        var HEIGHT = "height"
        private var appDensity: Float = 0.toFloat()
        private var appScaledDensity: Float = 0.toFloat()
        private var appDisplayMetrics: DisplayMetrics? = null

        fun setDensity(application: Application) {
            //获取application的DisplayMetrics
            appDisplayMetrics = application.resources.displayMetrics

            if (appDensity == 0f) {
                //初始化的时候赋值
                appDensity = appDisplayMetrics!!.density
                appScaledDensity = appDisplayMetrics!!.scaledDensity

                //添加字体变化的监听
                application.registerComponentCallbacks(object : ComponentCallbacks {
                    override fun onConfigurationChanged(newConfig: Configuration?) {
                        //字体改变后,将appScaledDensity重新赋值
                        if (newConfig != null && newConfig.fontScale > 0) {
                            appScaledDensity = application.resources.displayMetrics.scaledDensity
                        }
                    }

                    override fun onLowMemory() {}
                })
            }
        }

        //此方法在BaseActivity中做初始化(如果不封装BaseActivity的话,直接用下面那个方法就好了)
        fun setDefault(activity: Activity) {
            setAppOrientation(activity, WIDTH, Configuration.ORIENTATION_PORTRAIT)
        }

        //此方法用于在某一个Activity里面更改适配的方向
        fun setOrientation(activity: Activity, orientation: String) {
            setAppOrientation(activity, orientation, Configuration.ORIENTATION_PORTRAIT)
        }

        fun setOrientation(activity: Activity, orientation: String, screenOrientation: Int) {
            setAppOrientation(activity, orientation, screenOrientation)
        }

        /**
         * targetDensity
         * targetScaledDensity
         * targetDensityDpi
         * 这三个参数是统一修改过后的值
         *
         *
         * orientation:方向值,传入width或height
         * sreenOrientation 屏幕真实方向
         */
        private fun setAppOrientation(activity: Activity?, orientation: String, screenOrientation: Int) {
            var targetDensity = 0f
            try {
                val division: Double?
                //根据带入参数选择不同的适配方向
                if (orientation == HEIGHT) {
                    division = division(appDisplayMetrics!!.heightPixels.toDouble(), (if (screenOrientation == Configuration.ORIENTATION_PORTRAIT) DP_HEIGHT else DP_WIDTH).toDouble())
                } else {
                    division = division(appDisplayMetrics!!.widthPixels.toDouble(), (if (screenOrientation == Configuration.ORIENTATION_PORTRAIT) DP_WIDTH else DP_HEIGHT).toDouble())
                }
                //由于手机的长宽不尽相同,肯定会有除不尽的情况,有失精度,所以在这里把所得结果做了一个保留两位小数的操作
                val df = DecimalFormat("0.00")
                val s = df.format(division)
                targetDensity = java.lang.Float.parseFloat(s)
            } catch (e: NumberFormatException) {
                e.printStackTrace()
            }

            val targetScaledDensity = targetDensity * (appScaledDensity / appDensity)
            val targetDensityDpi = (160 * targetDensity).toInt()
            /**
             *
             * 最后在这里将修改过后的值赋给系统参数
             *
             * 只修改Activity的density值
             */
            val activityDisplayMetrics = activity!!.resources.displayMetrics
            activityDisplayMetrics.density = targetDensity
            activityDisplayMetrics.scaledDensity = targetScaledDensity
            activityDisplayMetrics.densityDpi = targetDensityDpi
        }

        fun division(a: Double, b: Double): Double? {
            val div: Double
            if (b != 0.0) {
                div = a / b
            } else {
                div = 0.0
            }
            return div
        }
    }
}
