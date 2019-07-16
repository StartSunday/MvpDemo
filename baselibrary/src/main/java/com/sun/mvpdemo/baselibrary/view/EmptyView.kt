package com.sun.mvpdemo.user.baselibrary.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.sun.mvpdemo.baselibrary.R

/**
 * @author sun
 * @data 2018-12-26
 * @Explain 空布局
 */
class EmptyView : LinearLayout {
    private var tvText: TextView? = null
    private var ivImage: ImageView? = null

    constructor(context: Context) : super(context) {
        init(context, R.layout.view_empty)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, R.layout.view_empty)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, R.layout.view_empty)
    }

    constructor(context: Context, imgId: Int, txtId: Int):
        super(context){
        init(context, R.layout.view_empty)
        if (tvText != null) tvText!!.setText(txtId)
        if (ivImage != null) ivImage!!.setImageResource(imgId)
    }

    private fun init(context: Context, layoutId: Int) {
        val layoutView = LayoutInflater.from(context).inflate(if (layoutId == 0) R.layout.view_empty else layoutId, this)
        tvText = layoutView.findViewById(R.id.tv_text)
        ivImage = layoutView.findViewById(R.id.iv_icon)
    }

}
