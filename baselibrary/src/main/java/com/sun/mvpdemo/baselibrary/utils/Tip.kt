package com.sun.mvpdemo.baselibrary.utils

import android.content.Context
import android.widget.Toast

/**
 * @author sun
 * @data 2018-12-25
 * @Explain 弹出框
 */
class Tip {
    companion object {
        var toast: Toast? = null
        fun show(context: Context, charSequence: CharSequence) {
            if (toast == null) {
                toast = Toast.makeText(context, charSequence, Toast.LENGTH_SHORT)
            } else {
                toast!!.setText(charSequence)
            }
            toast!!.show()
        }

        fun show(context: Context, res: Int) {
            if (toast == null) {
                toast = Toast.makeText(context, context.getString(res), Toast.LENGTH_SHORT)
            } else {
                toast!!.setText(context.getString(res))
            }
            toast!!.show()
        }
    }
}