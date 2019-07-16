package com.sun.mvpdemo.baselibrary.utils

import android.app.Activity
import android.graphics.drawable.ColorDrawable
import android.view.*
import android.widget.PopupWindow
import com.sun.mvpdemo.baselibrary.R

/**
 * @author sun
 * @data 2018-12-27
 * @Explain
 */
class PopUtils {
    companion object {
        private var mPopupWindow: PopupWindow? = null
        fun showPopwindow(contentView: View,type: Int,  activity: Activity, width: Int, height: Int,flag: Int,view: View): PopupWindow {
            if (mPopupWindow == null) {
                mPopupWindow = PopupWindow(contentView, width, height, true)
            }
            mPopupWindow!!.setBackgroundDrawable(ColorDrawable(0))
            mPopupWindow!!.isFocusable = true
            mPopupWindow!!.isOutsideTouchable = true
            mPopupWindow!!.contentView = contentView
            mPopupWindow!!.animationStyle = R.style.PopAnimationStyle
            //            mPopupWindow.showAtLocation(getActivity().getWindow().getDecorView(),Gravity.CENTER,0,0);
            when(flag){
                1 -> {
                    mPopupWindow!!.showAsDropDown(view)
                    mPopupWindow!!.setOnDismissListener {
                        mPopupWindow!!.dismiss()     // 当点击屏幕时，使popupWindow消失
                        mPopupWindow = null
                    }
                }
                else-> {
                    mPopupWindow!!.showAtLocation(activity.window.decorView, type, 0, 0)
                    darkenBackgroud(0.6f, activity.window)
                    mPopupWindow!!.setOnDismissListener {
                        darkenBackgroud(1f, activity.window)
                        mPopupWindow!!.dismiss()     // 当点击屏幕时，使popupWindow消失
                        mPopupWindow = null
                    }
                }
            }
            //  mPopupWindow.showAsDropDown(activity.getWindow().getDecorView());

            return mPopupWindow!!
        }

        private fun darkenBackgroud(bgcolor: Float?, window: Window) {
            val lp = window.attributes
            lp.alpha = bgcolor!!
            window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            window.attributes = lp
        }
    }
}