package com.zhuzichu.orange.camerax.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.hardware.Camera
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.HandlerThread
import android.util.DisplayMetrics
import android.util.Log
import android.util.Rational
import android.webkit.MimeTypeMap
import androidx.camera.core.*
import androidx.core.view.setPadding
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.request.RequestOptions
import com.jakewharton.rxbinding3.view.clicks
import com.zhuzichu.mvvm.base.BaseFragment
import com.zhuzichu.mvvm.databinding.view.CLICK_INTERVAL
import com.zhuzichu.mvvm.global.cache.CacheGlobal
import com.zhuzichu.mvvm.global.glide.GlideApp
import com.zhuzichu.mvvm.permissions.RxPermissions
import com.zhuzichu.mvvm.utils.dip2px
import com.zhuzichu.mvvm.utils.toast
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.camerax.CameraActivity
import com.zhuzichu.orange.camerax.utils.ANIMATION_FAST_MILLIS
import com.zhuzichu.orange.camerax.utils.ANIMATION_SLOW_MILLIS
import com.zhuzichu.orange.camerax.utils.AutoFitPreviewBuilder
import com.zhuzichu.orange.camerax.viewmodel.CameraViewModel
import com.zhuzichu.orange.databinding.FragmentCameraBinding
import kotlinx.android.synthetic.main.fragment_camera.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.io.File
import java.nio.ByteBuffer
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class CameraFragment : BaseFragment<FragmentCameraBinding, CameraViewModel>() {

    companion object {
        private const val TAG = "CameraFragment"
        private const val PHOTO_EXTENSION = ".jpg"
        private const val FILENAME = "yyyy-MM-dd-HH-mm-ss-SSS"
    }


    override fun setLayoutId(): Int = R.layout.fragment_camera

    override fun bindVariableId(): Int = BR.viewModel

    private var lensFacing = CameraX.LensFacing.BACK

    private var preview: Preview? = null
    private var imageCapture: ImageCapture? = null
    private var imageAnalyzer: ImageAnalysis? = null

    @SuppressLint("CheckResult")
    override fun initView() {
        super.initView()
        RxPermissions(this)
            .request(Manifest.permission.CAMERA)
            .subscribe { granted ->
                if (granted) {
                    initCamera()
                } else {
                    "权限被拒绝".toast()
                }
            }
    }

    private fun initCamera() {
        post {
            bindCameraUseCases()
            updateCameraUi()
            lifecycleScope.launch(IO) {
                CacheGlobal.getCameraCacheDir().listFiles { file ->
                    arrayOf("JPG").contains(file.extension.toUpperCase())
                }?.sorted()?.reversed()?.firstOrNull()?.let {
                    setGalleryThumbnail(it)
                }
            }
        }
    }

    @SuppressLint("CheckResult", "RestrictedApi")
    private fun updateCameraUi() {
        backBtn.clicks().subscribe {
            _activity.finish()
        }

        switchBtn.clicks()
            .throttleFirst(CLICK_INTERVAL.toLong(), TimeUnit.MILLISECONDS)
            .subscribe {
                lensFacing = if (CameraX.LensFacing.FRONT == lensFacing) {
                    CameraX.LensFacing.BACK
                } else {
                    CameraX.LensFacing.FRONT
                }
                try {
                    CameraX.getCameraWithLensFacing(lensFacing)
                    CameraX.unbindAll()
                    bindCameraUseCases()
                } catch (exc: Exception) {
                }
            }

        captureBtn.clicks()
            .throttleFirst(CLICK_INTERVAL.toLong(), TimeUnit.MILLISECONDS)
            .subscribe {
                imageCapture?.let { imageCapture ->
                    val photoFile = createFile()
                    val metadata = ImageCapture.Metadata().apply {
                        isReversedHorizontal = lensFacing == CameraX.LensFacing.FRONT
                    }
                    imageCapture.takePicture(photoFile, imageSavedListener, metadata)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        container.postDelayed({
                            container.foreground = ColorDrawable(Color.WHITE)
                            container.postDelayed(
                                { container.foreground = null }, ANIMATION_FAST_MILLIS
                            )
                        }, ANIMATION_SLOW_MILLIS)
                    }
                }
            }

        photoBtn.clicks()
            .throttleFirst(CLICK_INTERVAL.toLong(), TimeUnit.MILLISECONDS)
            .subscribe {
                _viewModel.startFragment(AlbumFragment())
            }
    }


    private val imageSavedListener = object : ImageCapture.OnImageSavedListener {
        override fun onError(
            error: ImageCapture.UseCaseError, message: String, exc: Throwable?
        ) {
            Log.e(TAG, "Photo capture failed: $message")
            exc?.printStackTrace()
        }

        override fun onImageSaved(photoFile: File) {
            Log.d(TAG, "Photo capture succeeded: ${photoFile.absolutePath}")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                setGalleryThumbnail(photoFile)
            }
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                requireActivity().sendBroadcast(
                    Intent(Camera.ACTION_NEW_PICTURE, Uri.fromFile(photoFile))
                )
            }
            val mimeType = MimeTypeMap.getSingleton()
                .getMimeTypeFromExtension(photoFile.extension)
            MediaScannerConnection.scanFile(
                context, arrayOf(photoFile.absolutePath), arrayOf(mimeType), null
            )
        }
    }


    private fun createFile() =
        File(
            CacheGlobal.getCameraCacheDir(), SimpleDateFormat(FILENAME, Locale.US)
                .format(System.currentTimeMillis()) + PHOTO_EXTENSION
        )


    private fun setGalleryThumbnail(file: File) {
        photoBtn.post {
            photoBtn.setPadding(dip2px(4f))
            GlideApp.with(photoBtn)
                .load(file)
                .apply(RequestOptions.circleCropTransform())
                .into(photoBtn)
        }
    }

    private fun bindCameraUseCases() {
        val metrics = DisplayMetrics().also { viewFinder.display.getRealMetrics(it) }
        val screenAspectRatio = Rational(metrics.widthPixels, metrics.heightPixels)
        val previewConfig = PreviewConfig.Builder().apply {
            setLensFacing(lensFacing)
            setTargetAspectRatio(screenAspectRatio)
            setTargetRotation(viewFinder.display.rotation)
        }.build()
        preview = AutoFitPreviewBuilder.build(previewConfig, viewFinder)


        val imageCaptureConfig = ImageCaptureConfig.Builder().apply {
            setLensFacing(lensFacing)
            setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY)
            setTargetAspectRatio(screenAspectRatio)
            setTargetRotation(viewFinder.display.rotation)
        }.build()

        imageCapture = ImageCapture(imageCaptureConfig)

        val analyzerConfig = ImageAnalysisConfig.Builder().apply {
            setLensFacing(lensFacing)
            val analyzerThread = HandlerThread("LuminosityAnalysis").apply { start() }
            setCallbackHandler(Handler(analyzerThread.looper))
            setImageReaderMode(ImageAnalysis.ImageReaderMode.ACQUIRE_LATEST_IMAGE)
            setTargetRotation(viewFinder.display.rotation)
        }.build()

        imageAnalyzer = ImageAnalysis(analyzerConfig).apply {
            analyzer = LuminosityAnalyzer { luma ->
                // Values returned from our analyzer are passed to the attached listener
                // We log image analysis results here -- you should do something useful instead!
                val fps = (analyzer as LuminosityAnalyzer).framesPerSecond
                Log.d(
                    TAG, "Average luminosity: $luma. " +
                            "Frames per second: ${"%.01f".format(fps)}"
                )
            }
        }


        CameraX.bindToLifecycle(this, imageAnalyzer, imageCapture, preview)
    }

    private class LuminosityAnalyzer(listener: LumaListener? = null) : ImageAnalysis.Analyzer {
        private val frameRateWindow = 8
        private val frameTimestamps = ArrayDeque<Long>(5)
        private val listeners = ArrayList<LumaListener>().apply { listener?.let { add(it) } }
        private var lastAnalyzedTimestamp = 0L
        var framesPerSecond: Double = -1.0
            private set

        fun onFrameAnalyzed(listener: LumaListener) = listeners.add(listener)

        private fun ByteBuffer.toByteArray(): ByteArray {
            rewind()
            val data = ByteArray(remaining())
            get(data)
            return data
        }

        override fun analyze(image: ImageProxy, rotationDegrees: Int) {
            if (listeners.isEmpty()) return
            frameTimestamps.push(System.currentTimeMillis())
            while (frameTimestamps.size >= frameRateWindow) frameTimestamps.removeLast()
            framesPerSecond = 1.0 / ((frameTimestamps.peekFirst() -
                    frameTimestamps.peekLast()) / frameTimestamps.size.toDouble()) * 1000.0
            if (frameTimestamps.first - lastAnalyzedTimestamp >= TimeUnit.SECONDS.toMillis(1)) {
                lastAnalyzedTimestamp = frameTimestamps.first
                val buffer = image.planes[0].buffer
                val data = buffer.toByteArray()
                val pixels = data.map { it.toInt() and 0xFF }
                val luma = pixels.average()
                listeners.forEach { it(luma) }
            }
        }
    }


}

typealias LumaListener = (luma: Double) -> Unit