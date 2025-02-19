package com.sun.mvpdemo.baselibrary.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import java.io.IOException
import java.net.HttpURLConnection
import java.net.NetworkInterface
import java.net.SocketException
import java.net.URL

/**
 * @author sun
 * @data 2018-12-25
 * @Explain 网络检查
 */
class CheckNetwork {

    companion object {
        var NET_CNNT_BAIDU_OK = 1 // NetworkAvailable
        var NET_CNNT_BAIDU_TIMEOUT = 2 // no NetworkAvailable
        var NET_NOT_PREPARE = 3 // Net no ready
        var NET_ERROR = 4 //net error
        private val TIMEOUT = 3000 // TIMEOUT
        /**
         * check NetworkAvailable
         *
         * @param context
         * @return
         */
        @SuppressLint("MissingPermission")
        @JvmStatic
        fun isNetworkAvailable(context: Context): Boolean {
            val manager = context.applicationContext.getSystemService(
                    Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val info = manager.activeNetworkInfo
            return !(null == info || !info.isConnected)
        }

        /**
         * 得到ip地址
         *
         * @return
         */
        @JvmStatic
        fun getLocalIpAddress(): String {
            var ret = ""
            try {
                val en = NetworkInterface.getNetworkInterfaces()
                while (en.hasMoreElements()) {
                    val enumIpAddress = en.nextElement().inetAddresses
                    while (enumIpAddress.hasMoreElements()) {
                        val netAddress = enumIpAddress.nextElement()
                        if (!netAddress.isLoopbackAddress) {
                            ret = netAddress.hostAddress.toString()
                        }
                    }
                }
            } catch (ex: SocketException) {
                ex.printStackTrace()
            }

            return ret
        }


        /**
         * ping "http://www.baidu.com"
         *
         * @return
         */
        @JvmStatic
        private fun pingNetWork(): Boolean {
            var result = false
            var httpUrl: HttpURLConnection? = null
            try {
                httpUrl = URL("http://www.baidu.com")
                        .openConnection() as HttpURLConnection
                httpUrl.connectTimeout = TIMEOUT
                httpUrl.connect()
                result = true
            } catch (e: IOException) {
            } finally {
                if (null != httpUrl) {
                    httpUrl.disconnect()
                }
            }
            return result
        }

        /**
         * check is3G
         *
         * @param context
         * @return boolean
         */
//        @SuppressLint("MissingPermission")
//        @JvmStatic
//        fun is3G(context: Context): Boolean {
//            val connectivityManager = context
//                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//            val activeNetInfo = connectivityManager.activeNetworkInfo
//            return activeNetInfo != null && activeNetInfo.type == ConnectivityManager.TYPE_MOBILE
//        }

        /**
         * isWifi
         *
         * @param context
         * @return boolean
         */
//        @SuppressLint("MissingPermission")
//        @JvmStatic
//        fun isWifi(context: Context): Boolean {
//            val connectivityManager = context
//                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//            val activeNetInfo = connectivityManager.activeNetworkInfo
//            return activeNetInfo != null && activeNetInfo.type == ConnectivityManager.TYPE_WIFI
//        }

        /**
         * is2G
         *
         * @param context
         * @return boolean
         */
//        @SuppressLint("MissingPermission")
//        @JvmStatic
//        fun is2G(context: Context): Boolean {
//            val connectivityManager = context
//                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//            val activeNetInfo = connectivityManager.activeNetworkInfo
//            return activeNetInfo != null && (activeNetInfo.subtype == TelephonyManager.NETWORK_TYPE_EDGE
//                    || activeNetInfo.subtype == TelephonyManager.NETWORK_TYPE_GPRS || activeNetInfo
//                    .subtype == TelephonyManager.NETWORK_TYPE_CDMA)
//        }

//        /**
//         * is wifi on
//         */
//        @SuppressLint("MissingPermission")
//        @JvmStatic
//        fun isWifiEnabled(context: Context): Boolean {
//            val mgrConn = context
//                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//            val mgrTel = context
//                    .getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
//            return mgrConn.activeNetworkInfo != null && mgrConn
//                    .activeNetworkInfo.state == NetworkInfo.State.CONNECTED || mgrTel
//                    .networkType == TelephonyManager.NETWORK_TYPE_UMTS
//        }
//
    }
}

