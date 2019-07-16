package com.sun.mvpdemo.baselibrary.base;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sun.mvpdemo.baselibrary.R;
import com.sun.mvpdemo.baselibrary.moudle.Pager;
import com.sun.mvpdemo.baselibrary.view.CustomLadMoreView;
import com.sun.mvpdemo.user.baselibrary.view.EmptyView;

import java.util.List;

/**
 * Created by ChenYuYun on 2018/3/26.
 * 标准列表的RecyclerView页面
 */
public abstract class BaseListFragment<T, P extends BasePresenter<V>, V extends BaseView> extends MvpFragment<P,V> {
    RecyclerView rvList;
    protected SwipeRefreshLayout swipeLayout;
    protected BaseQuickAdapter mAdapter;
    private int mPageIndex = 1;
    private int mPageSize = 10;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_list;
    }


    public int getPageIndex() {
        return mPageIndex;
    }
    public int getPageSize() {
        return mPageSize;
    }

    //获取分页信息
    public Pager getPager(int pageSize) {
        mPageSize = pageSize;
        return new Pager(mPageIndex, mPageSize);
    }

    public void setPageIndex(int mPageIndex) {
        this.mPageIndex =mPageIndex;
    }

    //获取分页信息
    public Pager getPager() {
        return new Pager(mPageIndex, mPageSize);
    }

//    //获取排序的分页信息
//    public Pager getPagerSort() {
//        return new Pager(mPageIndex, mPageSize,"createTime","desc");
//    }

    @Override
    protected void initView(View contentView) {
        bindView(contentView);
        swipeLayout.setOnRefreshListener(this::refresh);
        initRecyclerView();
        refresh();
    }

    private void bindView(View contentView) {
        rvList = contentView.findViewById(R.id.rv_list);
        swipeLayout = contentView.findViewById(R.id.swipe_layout);
    }

    /**
     * 是否分页
     *
     * @return
     */
    protected boolean isPage() {
        return true;
    }

    protected void setRecyclerView(RecyclerView recyclerView) {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
    }

    private void initRecyclerView() {
        setRecyclerView(rvList);
        mAdapter = getAdapter();
        rvList.setAdapter(mAdapter);
        if (isPage()) {
            mAdapter.setOnLoadMoreListener(this::loadData, rvList);
            mAdapter.disableLoadMoreIfNotFullPage();
        }
        View emptyView = getEmptyView();
        mAdapter.setEmptyView(emptyView);
        mAdapter.setLoadMoreView(new CustomLadMoreView());
        emptyView.setOnClickListener(this::onClickEmpty);
        mAdapter.setOnItemClickListener(this::itemClick);
        mAdapter.setOnItemChildClickListener(this::itemChildClick);
    }

    private void onClickEmpty(View view) {
        refresh();
    }

    //返回空控制
    protected View getEmptyView() {
        return new EmptyView(getActivity());
    }

    private void loadData() {
        swipeLayout.setRefreshing(true);
        swipeLayout.setEnabled(false);
        getData();
    }

    public void refresh() {
        mPageIndex = 1;
        loadData();
    }

    public abstract BaseQuickAdapter getAdapter();

    public abstract void itemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int position);

    public abstract void itemClick(BaseQuickAdapter baseQuickAdapter, View view, int position);

    public abstract void getData();


    //数据请求成功后的结果处理
    public void doSuc(List<T> rows, int totalPageCount) {
        if (swipeLayout != null) {
            swipeLayout.setRefreshing(false);
            swipeLayout.setEnabled(true);
        }
        mAdapter.loadMoreComplete();
        //if (!CommonUtils.isEmpty(rows)) {
        if (mPageIndex == 1) {
            mAdapter.setNewData(rows);
        } else {
            mAdapter.addData(rows);
        }
        //}
        if (mPageIndex++ >= totalPageCount) {
            mAdapter.loadMoreEnd(true);
        }
    }

    /**
     * 根据是否存在下一页
     *
     * @param rows
     */
    public void doSucNew(List<T> rows) {
        boolean hasNext = rows != null && rows.size() >= mPageSize;
        doSuc(rows, hasNext ? mPageIndex + 2 : mPageIndex);
    }


    //数据请求后失败处理
    protected void doError(Throwable throwable) {
        swipeLayout.setEnabled(true);
        swipeLayout.setRefreshing(false);
        mAdapter.loadMoreFail();
        defOnError(throwable);
    }

    public void isRefresh() {
        swipeLayout.setEnabled(false);
    }

    public void setRefresh(boolean b) {
        swipeLayout.setRefreshing(b);
    }
}
