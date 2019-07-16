package com.sun.mvpdemo.baselibrary.view

import android.content.Context
import android.os.CountDownTimer
import android.util.AttributeSet
import com.sun.mvpdemo.baselibrary.R

/**
 * @author sun
 * @data 2019-01-03
 * @Explain 验证码按钮
 */

class VerityCodeButton @JvmOverloads constructor(private val mContext: Context, attrs: AttributeSet? = null, defStyleAttr: Int = android.R.attr.buttonStyle) : androidx.appcompat.widget.AppCompatButton(mContext, attrs, defStyleAttr) {
    private var mClickedBackground: Int = 0//点击后背景
    private var mBackground: Int = 0//当前背景
    private var mCountdownownText: String? = null
    private var mCountdownTime = 60
    private var mTimeCount: TimeCount? = null

    init {
        initAttrs(attrs)
        init()
    }

    private fun initAttrs(attrs: AttributeSet?) {
        val typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.VerityCodeButton)
        mBackground = typedArray.getResourceId(R.styleable.VerityCodeButton_android_background, mBackground)
        mClickedBackground = typedArray.getResourceId(R.styleable.VerityCodeButton_clickedBackground, mClickedBackground)
        mCountdownTime = typedArray.getInt(R.styleable.VerityCodeButton_countdownTime, mCountdownTime)
        mCountdownownText = typedArray.getString(R.styleable.VerityCodeButton_countdownText)
        typedArray.recycle()
    }

    private fun init() {
        setBackgroundResource(mBackground)
        mTimeCount = TimeCount((mCountdownTime * 1000).toLong(), 1000)
    }

    /**
     * 开始计时
     */
    fun start() {
        mTimeCount!!.start()
    }

    /**
     * 取消计时
     */
    fun cancle() {
        mTimeCount!!.cancel()
        isClickable = true
        text = if (mCountdownownText != null) mCountdownownText else ""
        setBackgroundResource(mBackground)
    }

    internal inner class TimeCount
    /**
     * @param millisInFuture    总时间
     * @param countDownInterval 间隔时间
     */
    (millisInFuture: Long, countDownInterval: Long) : CountDownTimer(millisInFuture, countDownInterval) {

        /**
         * @param millisUntilFinished 当前时间
         */
        override fun onTick(millisUntilFinished: Long) {
            isClickable = false
            text = ((millisUntilFinished / 1000).toString() + "s").toString()
            setBackgroundResource(mClickedBackground)
        }

        override fun onFinish() {
            isClickable = true
            text = if (mCountdownownText != null) mCountdownownText else ""
            setBackgroundResource(mBackground)
        }
    }
}
