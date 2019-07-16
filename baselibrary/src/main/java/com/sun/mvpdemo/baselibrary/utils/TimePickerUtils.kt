package com.sun.mvpdemo.baselibrary.utils

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bigkoo.pickerview.view.TimePickerView

/**
 * @author sun
 * @data 2018-12-25
 * @Explain 时间选择器
 */
class TimePickerUtils {
    companion object {
        @SuppressLint("StaticFieldLeak")
        private var timepicker: TimePickerView? = null

        /**
         * 时间选择器
         */
        fun initTimePicker(view: TextView, context: Context, b: Boolean): TimePickerView {
            timepicker = TimePickerBuilder(context, OnTimeSelectListener { date, _ ->
                view.text = Utils.getData(date)
            })
                    .setTimeSelectChangeListener { Log.i("pvTime", "onTimeSelectChanged") }
                    .setType(booleanArrayOf(true, true, true, b, b, b))
                    .isDialog(true)
                    .build()
            val dialog = timepicker!!.dialog
            if (dialog != null) {
                val params = FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        Gravity.BOTTOM)

                params.leftMargin = 0
                params.rightMargin = 0
                timepicker!!.dialogContainerLayout.layoutParams = params
                val dialogWindow = dialog.window
                if (dialogWindow != null) {
                    dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim)//修改动画样式
                    dialogWindow.setGravity(Gravity.BOTTOM)//改成Bottom,底部显示
                }
            }
            return timepicker!!
        }
    }
}