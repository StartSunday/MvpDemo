package com.sun.mvpdemo.baselibrary.base

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

/**
 * @author sun
 * @data 2018-12-25
 * @Explain 处理数据
 */
open class BasePresenter<V> {
    var mvpView: V? = null
    var mCompositeDisposable:CompositeDisposable? = null

    fun attachView(mvpView: V){
        this.mvpView = mvpView
    }
    fun detachView(){
        this.mvpView = null
        onUnSubscribe()
    }

    private fun onUnSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable!!.dispose()
            mCompositeDisposable!!.clear()
        }
    }

    /**
     * 启动网络任务
     *
     * @param single
     * @param onSuccess
     * @param onError
     */
     fun <D> startTask(single: Single<D>, onSuccess: Consumer<in D>, onError: Consumer<in Throwable>) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = CompositeDisposable()
        }
        mCompositeDisposable!!.add(single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(onSuccess, onError))
    }

    fun <D> startTask(single: Observable<D>, onNext: Consumer<in D>, onError: Consumer<in Throwable>) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = CompositeDisposable()
        }
        mCompositeDisposable!!.add(single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(onNext, onError))
    }

}