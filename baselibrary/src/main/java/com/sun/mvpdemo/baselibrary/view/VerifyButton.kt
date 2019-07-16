package com.sun.mvpdemo.baselibrary.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import androidx.core.content.ContextCompat
import android.util.AttributeSet
import android.widget.Button
import com.sun.mvpdemo.baselibrary.R

/**
 * @author  sun
 * @data 2019-01-03
 * @Explain 获取验证码按钮 带倒计时
 */
class VerifyButton(mContext: Context, attrs: AttributeSet) : Button(mContext, attrs) {
    private val mHandler: Handler
    private var mCount = 60
    private var mOnVerifyBtnClick: OnVerifyBtnClick? = null


    init {
        this.text = "获取验证码"
        mHandler = Handler()

    }

    /*
        倒计时，并处理点击事件
     */
    fun requestSendVerifyNumber() {
        mHandler.postDelayed(countDown, 0)
        if (mOnVerifyBtnClick != null) {
            mOnVerifyBtnClick!!.onClick()
        }

    }

    /*
        倒计时
     */
    private val countDown = object : Runnable {
        @SuppressLint("SetTextI18n")
        override fun run() {
            this@VerifyButton.text = mCount.toString() + "s "
            this@VerifyButton.setBackgroundColor(ContextCompat.getColor(context, R.color.text_gray))
            this@VerifyButton.setTextColor(ContextCompat.getColor(context,R.color.white))
            this@VerifyButton.isEnabled = false

            if (mCount > 0) {
                mHandler.postDelayed(this, 1000)
            } else {
                resetCounter()
            }
            mCount--
        }
    }


    fun removeRunable() {
        mHandler.removeCallbacks(countDown)
    }

    /*
        恢复到初始状态
     */
    fun resetCounter(vararg text: String) {
        this.isEnabled = true
        if (text.isNotEmpty() && "" != text[0]) {
            this.text = text[0]
        } else {
            this.text = "重获验证码"
        }
        this.setBackgroundColor(ContextCompat.getColor(context,R.color.transparent))
        this.setTextColor(ContextCompat.getColor(context,R.color.colorPrimary))
        mCount = 60
    }

    /*
        点击事件接口
     */
    interface OnVerifyBtnClick {
        fun onClick()
    }

    fun setOnVerifyBtnClick(onVerifyBtnClick: OnVerifyBtnClick) {
        this.mOnVerifyBtnClick = onVerifyBtnClick
    }
}