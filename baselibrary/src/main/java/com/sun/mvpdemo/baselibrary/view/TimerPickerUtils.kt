package com.sun.mvpdemo.baselibrary.view

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bigkoo.pickerview.view.TimePickerView
import com.sun.mvpdemo.baselibrary.utils.Utils

/**
 * @author  sun
 * @data 2019/4/5
 * @Explain 时间选择器
 */
class TimerPickerUtils {
    companion object {
        @SuppressLint("StaticFieldLeak")
        private var timepicker: TimePickerView? = null

        /**
         * 时间选择器
         */
        fun initTimePicker(view: TextView, context: Context, b:Boolean): TimePickerView {
            timepicker = TimePickerBuilder(context, OnTimeSelectListener { date, v ->
                view.text = Utils.getData(date)
//                Toast.makeText(context, StringUtils.getData(date), Toast.LENGTH_SHORT).show()
//                Log.i("pvTime", "onTimeSelect")
            })
                    .setTimeSelectChangeListener {
//                        Log.i("pvTime", "onTimeSelectChanged")
                    }
                    .setType(booleanArrayOf(true, true, true, b, b, b))
                    .isDialog(true)
                    .build()
            var dialog = timepicker!!.dialog
            if (dialog != null){
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