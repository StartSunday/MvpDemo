package com.sun.mvpdemo.baselibrary.view

import android.app.Dialog
import android.content.Context
import android.view.Display
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import com.sun.mvpdemo.baselibrary.R
import com.sun.mvpdemo.baselibrary.utils.ImageHelper


/**
 * @author sun
 * @data 2018-12-26
 * @Explain 加载框
 *
 */
class Loadingdialog(context: Context) {
    private var context: Context? = context
    private var dialog: Dialog? = null
    private var ivLoading: ImageView? = null
    private var tvLoading: TextView? = null
    private var display: Display? = null

    init {
        val windowManager = context.getSystemService(Context
                .WINDOW_SERVICE) as WindowManager
        display = windowManager.defaultDisplay
    }

    fun builder(): Loadingdialog {
        // 获取Dialog布局
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null)

        // 获取自定义Dialog布局中的控件
        tvLoading = view.findViewById(R.id.tv_loading)
        ivLoading = view.findViewById(R.id.iv_loading)
        ImageHelper.load(ivLoading, R.mipmap.loading)

        // 定义Dialog布局和参数
        dialog = Dialog(context, R.style.AlertDialogStyle)
        dialog!!.setContentView(view)


        // 调整dialog背景大小
        /* lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display.getWidth() * 0.8)
                , LayoutParams.WRAP_CONTENT));*/
        val dialogWindow = dialog!!.window
        val lp = dialogWindow!!.attributes
        lp.width = (display!!.width * 0.8).toInt()

        return this
    }

    fun setCancelable(flag: Boolean) {
        dialog!!.setCancelable(flag)
    }

    fun setLoadText(loadText: String): Loadingdialog {
        if ("" != loadText) {
            tvLoading!!.text = loadText
        }
        return this
    }

    fun show() {
        dialog!!.show()
    }

    fun dismiss() {
        if (dialog != null && dialog!!.isShowing) {
            dialog!!.dismiss()
        }
    }

}