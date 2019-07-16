package com.sun.mvpdemo.baselibrary.view

import com.chad.library.adapter.base.loadmore.LoadMoreView
import com.sun.mvpdemo.baselibrary.R

/**
 * @author sun
 * @data 2018-12-26
 * @Explain 加载更多
 */
class CustomLadMoreView : LoadMoreView() {
    override fun getLayoutId(): Int = R.layout.view_load_more

    override fun getLoadingViewId(): Int = R.id.load_more_loading_view

    override fun getLoadFailViewId(): Int = R.id.load_more_load_fail_view

    override fun getLoadEndViewId(): Int =R.id.load_more_load_end_view

}