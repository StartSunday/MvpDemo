package com.sun.mvpdemo.baselibrary.base

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialog
import android.view.View
import com.sun.mvpdemo.baselibrary.R
import com.sun.mvpdemo.baselibrary.utils.TakePictureManager
import java.io.File

/**
 * @author sun
 * @data 2018-12-26
 * @Explain 选择相册
 */
abstract class BaseTakePictureMvpActivity<P : BasePresenter<V>, V : BaseView> : MvpActivity<P, V>() {
    protected var bottomSheetDialog: BottomSheetDialog? =null
    private var takePictureManager: TakePictureManager? = null

    //选择完结果
    private val mTakePictureListener = object : TakePictureManager.takePictureCallBackListener {
        override fun successful(isTailor: Boolean, outFile: File, filePath: Uri) {
            takePictureManagerSuccessful(isTailor, outFile, filePath)
        }

        override fun failed(errorCode: Int, deniedPermissions: List<String>) {
            takePictureManagerFailed(errorCode, deniedPermissions)
        }

    }

    protected abstract fun takePictureManagerSuccessful(isTailor: Boolean, outFile: File, filePath: Uri)

    protected abstract fun takePictureManagerFailed(errorCode: Int, deniedPermissions: List<String>)

    override fun initView(savedInstanceState: Bundle?) {
        initSelectImg()
    }

    /**
     * 初始化选择图片（相册或者拍照）
     */
    fun initSelectImg() {
        bottomSheetDialog = BottomSheetDialog(this)
        val bottomView = View.inflate(this, R.layout.camera_pop, null)
        bottomView.findViewById<View>(R.id.take_photo).setOnClickListener { this.startPhoto() }
        bottomView.findViewById<View>(R.id.from_album).setOnClickListener { this.startAlbum() }
        bottomView.findViewById<View>(R.id.cancel).setOnClickListener { bottomSheetDialog?.dismiss() }
        bottomSheetDialog!!.setContentView(bottomView)
    }

    /**
     * 启动拍照
     *
     * @param
     */
     fun startPhoto() {
        bottomSheetDialog?.dismiss()
        takePictureManager = TakePictureManager(this)
        //开启裁剪 比例 1:3 宽高 350 350  (默认不裁剪)
//                takePictureManager!!.setTailor(1, 3, 350, 350)
        //拍照方式
        takePictureManager!!.startTakeWayByCarema()
        //回调
        takePictureManager!!.setTakePictureCallBackListener(mTakePictureListener)
    }

    /**
     * 启动相册
     *
     * @param
     */
    fun startAlbum() {
        bottomSheetDialog?.dismiss()
        takePictureManager = TakePictureManager(this)
        //        takePictureManager.setTailor(1, 1, 1000, 1000);
        takePictureManager!!.startTakeWayByAlbum()
        takePictureManager!!.setTakePictureCallBackListener(mTakePictureListener)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == TakePictureManager.CODE_ORIGINAL_PHOTO_ALBUM) {
            if (resultCode == RESULT_OK) {
                showRunningDialog()
                Thread { takePictureManager!!.attachToActivityForResult(requestCode, resultCode, data) }.start()
            }
        }else if (requestCode == TakePictureManager.CODE_ORIGINAL_PHOTO_CAMERA){
            if (resultCode == RESULT_OK) {
                showRunningDialog()
                Thread { takePictureManager!!.attachToActivityForResult(requestCode, resultCode, data) }.start()
            }
        }else if (requestCode == TakePictureManager.CODE_TAILOR_PHOTO){
            if (resultCode == RESULT_OK) {
                showRunningDialog()
                Thread { takePictureManager!!.attachToActivityForResult(requestCode, resultCode, data) }.start()
            }
        }
    }

//     fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//    }
    //onRequestPermissionsResult()方法权限回调绑定到对象
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        takePictureManager!!.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    companion object {
        const val MAX_IMG = 3
    }
}
