package com.sun.mvpdemo.baselibrary.view

import android.content.Context
import androidx.viewpager.widget.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * @author sun
 * @data 2019/4/29
 * @Explain
 */
class MyViewPager : androidx.viewpager.widget.ViewPager {
    //是否可以左右滑动？true 可以，像Android原生ViewPager一样。
    // false 禁止ViewPager左右滑动。
    var isScrollable = false

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)


    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return isScrollable
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return isScrollable
    }
}
