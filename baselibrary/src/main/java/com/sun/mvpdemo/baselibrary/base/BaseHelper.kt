package com.sun.mvpdemo.baselibrary.base

import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import android.view.View
import android.view.ViewGroup
import com.sun.mvpdemo.baselibrary.R

/**
* @author  sun
* @data 2019-01-03
* @Explain
*/
class BaseHelper {
    companion object {
        fun initSwipeRefreshLayoutColor(view: View) {
            val colorPrimary = ContextCompat.getColor(view.context, R.color.colorPrimary)
            if (view is SwipeRefreshLayout) {
                view.setColorSchemeColors(colorPrimary)
            } else if (view is ViewGroup) {
                for (i in 0 until view.childCount) {
                    initSwipeRefreshLayoutColor(view.getChildAt(i))
                }
            }
        }
    }
}