package com.sun.mvpdemo.baselibrary.base

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.sun.mvpdemo.baselibrary.R
import com.sun.mvpdemo.baselibrary.moudle.Pager
import com.sun.mvpdemo.baselibrary.utils.Strings
import com.sun.mvpdemo.baselibrary.view.CustomLadMoreView
import com.sun.mvpdemo.user.baselibrary.view.EmptyView
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.view_toolbar.*

/**
 * @author sun
 * @data 2018-12-26
 * @Explain 列表基类
 */
abstract class BaseListActivity<T, P : BasePresenter<V>, V : BaseView> : MvpActivity<P, V>() {
    private var mAdapter: BaseQuickAdapter<T, *>? =null
    private var mPageIndex = 1
    private var mPageSize = 10
    override fun layoutResID(): Int = R.layout.activity_list

    abstract fun getTitleName(): CharSequence?

    override fun initView(savedInstanceState: Bundle?) {
        swipe_layout.setOnRefreshListener { this.refresh() }
        initRecyclerView(rv_list)
        refresh()
        iv_back.setOnClickListener { finish() }
        tv_title_name.text = getTitleName()
    }
    fun getPageIndex(): Int {
        return mPageIndex
    }

    fun getPageSize(): Int {
        return mPageSize
    }
    fun setPageIndex(mPageIndex: Int) {
        this.mPageIndex = mPageIndex
    }


    //获取分页信息
    fun getPager(page: Int) = Pager(mPageIndex, page)

    private fun initRecyclerView(rv_list: androidx.recyclerview.widget.RecyclerView?) {
        val manger = androidx.recyclerview.widget.LinearLayoutManager(this)
        rv_list!!.layoutManager = manger
        mAdapter = getAdapter()
        rv_list.adapter = mAdapter
        mAdapter!!.setOnLoadMoreListener({ this.loadData() },rv_list)
        mAdapter!!.disableLoadMoreIfNotFullPage()
        val emptyView = getEmptyView()
        mAdapter!!.emptyView = emptyView
        mAdapter!!.setLoadMoreView(CustomLadMoreView())
        emptyView.setOnClickListener {onClickEmpty() }
        mAdapter!!.setOnItemClickListener { baseQuickAdapter, view, position -> this.itemClick(baseQuickAdapter, view, position) }
        mAdapter!!.setOnItemChildClickListener { baseQuickAdapter, view, position -> this.itemChildClick(baseQuickAdapter, view, position) }
    }

    abstract fun itemChildClick(baseQuickAdapter: BaseQuickAdapter<*, *>?, view: View?, position: Int)


    abstract fun itemClick(baseQuickAdapter: BaseQuickAdapter<*, *>?, view: View?, position: Int)

    private fun onClickEmpty() {
        refresh()
    }
    open fun getEmptyView() = EmptyView(this)

    private fun loadData(){
        swipe_layout.isRefreshing = true
        swipe_layout.isEnabled = false
        getData()
    }

    abstract fun getAdapter(): BaseQuickAdapter<T, *>
    abstract fun getData()
    private fun refresh() {
        mPageIndex = 1
        loadData()
    }

    //数据请求成功后的结果处理
    fun doSuc(rows: List<T>, totalPageCount: Int) {
        swipe_layout.isRefreshing = false
        swipe_layout.isEnabled = true
        mAdapter!!.loadMoreComplete()
        if (mPageIndex == 1) {
            mAdapter!!.setNewData(rows)
        } else {
            mAdapter!!.addData(rows)
        }
        if (mPageIndex++ >= totalPageCount) {
            mAdapter!!.loadMoreEnd(false)
        }
    }

    /**
     * 根据是否存在下一页
     *
     * @param rows
     */
    fun doSucNew(rows: List<T>?) {
        val hasNext = rows != null && rows.size >= Strings.PAGE_SIZE
        if (rows != null) {
            doSuc(rows, if (hasNext) mPageIndex + 2 else mPageIndex)
        }
    }

    //数据请求后失败处理
    protected fun doError(throwable: Throwable) {
        swipe_layout.isEnabled = true
        swipe_layout.isRefreshing = false
        mAdapter!!.loadMoreFail()
        defOnError(throwable)
    }

    fun isRefresh() {
        swipe_layout.isEnabled = false
    }
    fun setRefresh(b: Boolean) {
        swipe_layout.isRefreshing = b
    }
}