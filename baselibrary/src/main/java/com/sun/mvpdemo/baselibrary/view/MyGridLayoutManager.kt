package com.sun.mvpdemo.baselibrary.view

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import android.util.AttributeSet

/**
 * @author sun
 * @data 2018-12-26
 * @Explain 解决嵌套滑动冲突
 */
class MyGridLayoutManager : GridLayoutManager {
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    constructor(context: Context, spanCount: Int) : super(context, spanCount)

    constructor(context: Context, spanCount: Int, orientation: Int, reverseLayout: Boolean) : super(context, spanCount, orientation, reverseLayout)

    override fun canScrollVertically(): Boolean {
        return false
    }
}
