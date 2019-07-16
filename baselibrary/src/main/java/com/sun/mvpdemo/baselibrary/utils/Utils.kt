package com.sun.mvpdemo.baselibrary.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import android.graphics.Bitmap



/**
 * @author sun
 * @data 2018-12-25
 * @Explain
 */
object Utils {
    @SuppressLint("SimpleDateFormat")
    var format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    fun getData(date: Date): String{
        return format.format(date)
    }

    fun date2TimeStamp(date: String): String {
        try {
            return (format.parse(date).time / 1000).toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }

    fun getMeta(context: Context, key: String): String? {
        var value: String = ""
        try {
            val appInfo = context.packageManager.getApplicationInfo(
                    context.packageName, PackageManager.GET_META_DATA)
            value = appInfo.metaData.getString(key)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return value
    }

    fun isMobileNO(mobileNums: String): Boolean {
        /**
         * 判断字符串是否符合手机号码格式
         * 移动号段: 134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188
         * 联通号段: 130,131,132,145,155,156,170,171,175,176,185,186
         * 电信号段: 133,149,153,170,173,177,180,181,189
         * @param
         * @return 待检测的字符串
         */
        val telRegex = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$"// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        return if (TextUtils.isEmpty(mobileNums))
            false
        else
            mobileNums.matches(telRegex.toRegex())
    }

    /**
     * 验证输入的身份证号是否合法
     */
    fun isLegalId(id: String): Boolean {
        return id.toUpperCase().matches("(^\\d{15}$)|(^\\d{17}([0-9]|X)$)".toRegex())
    }

    /**
     * 强制显示软键盘
     */
    @JvmStatic
    fun showSoftInput(context: Context, view: View) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED)
    }

    /**
     * 强制隐藏软键盘
     */
    @JvmStatic
    fun hideSoftInput(context: Context, view: View) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun replce(name: String): String {
        var names = name
        if (names.contains("市")) {
            names = name.replace("市", "")
        }
        return names
    }
    @SuppressLint("SimpleDateFormat")
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    fun getdata(): String{
        return sdf.format(Date())
    }
    fun getdata2(): String{
        return format.format(Date())
    }
    /*
    * 将时间戳转换为时间
    *
    * s就是时间戳
    */
    @SuppressLint("SimpleDateFormat")
    fun  stampToDate(s: String):String {

        val res: String
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val lt = s.toLong()
        val date = Date(lt*1000)
        res = simpleDateFormat.format(date)
        return res

//        val res: String
//        //如果它本来就是long类型的,则不用写这一步
//        val lt =  s.toLong()
//        val date = Date(lt)
//        res = format.format(date)
//        return res
    }

    /**
     * 规则1：至少包含大小写字母及数字中的一种
     * @return 是否包含
     */
    fun isLetterOrDigit(str: String): Boolean {
        var isLetterOrDigit = false
        //定义一个boolean值，用来表示是否包含字母或数字
        for (i in 0 until str.length) {
            if (Character.isLetterOrDigit(str[i])) {
                // 用char包装类中的判断数字的方法判断每一个字符
                isLetterOrDigit = true
            }
        }
        val regex = "^[a-zA-Z0-9]+$"
        return isLetterOrDigit && str.matches(regex.toRegex())
    }


    fun returnBitMap(url: String?): Bitmap {
        var bitmap: Bitmap ? = null
//        Thread(Runnable {
            val imageurl: URL?
            try {
                imageurl = URL(url)
                val conn = imageurl.openConnection() as HttpURLConnection
                conn.doInput = true
                conn.connect()
                val `is` = conn.inputStream
                bitmap = BitmapFactory.decodeStream(`is`)
                `is`.close()
            }
            catch (e: MalformedURLException) {
                e.printStackTrace()
            }
            catch (e: IOException) {
                e.printStackTrace()
            }
//        }).start()
        return bitmap!!
    }

}