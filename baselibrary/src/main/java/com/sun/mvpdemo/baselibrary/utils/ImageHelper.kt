package com.sun.mvpdemo.baselibrary.utils

import android.content.Context
import android.graphics.Bitmap
import androidx.annotation.DrawableRes
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.sun.mvpdemo.baselibrary.R

/**
* @author  sun
* @data 2019-01-03
* @Explain
*/
class ImageHelper {
    companion object {

        fun load(iv: ImageView, url: String) {
            load(iv, url, R.mipmap.item_head_img)
        }

        fun load(iv: ImageView?, url: String, defaultResourceId: Int) {
            if (iv == null) {
                return
            }
            //Glide.with(iv.getContext()).load(url).placeholder(defaultResourceId).into(iv);
            Glide.with(iv.context).load(url).apply(RequestOptions().placeholder(defaultResourceId)).into(iv)
        }

        fun loadNoDefault(iv: ImageView?, url: String) {
            if (iv == null) {
                return
            }
            Glide.with(iv.context).load(url).into(iv)
        }
        @JvmStatic
        fun load(iv: ImageView?, @DrawableRes res: Int) {
            if (iv == null) {
                return
            }
            Glide.with(iv.context).load(res).into(iv)
        }

        fun getBitmap(context: Context,url: String?): Bitmap{
            var bitmap: Bitmap? = null
            Thread(Runnable {
                bitmap = Glide.with(context).asBitmap()
                        .load(url)
                        .submit(50,50)
                        .get()
            }).start()
            return bitmap!!
        }
        fun getBitmap(context: Context,url:String, target: SimpleTarget<Bitmap>){
            Glide.with(context).asBitmap().load(url).into(target)
        }
    }
}
