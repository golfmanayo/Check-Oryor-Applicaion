package th.co.gis.cdg.checkoryorapplication

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.provider.MediaStore
import android.util.Log
import android.util.Rational
import android.util.Size
import android.view.Surface
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_camera.*
import kotlinx.android.synthetic.main.activity_main.*
import th.co.gis.cdg.checkoryorapplication.util.ImageAnalyzer
import java.io.File
import java.io.IOException
import javax.sql.DataSource

class CameraActivity : AppCompatActivity(), LifecycleOwner, ImageAnalyzer.ImageAnalyzerListener {
    override fun onGetTextOryor(string: String) {
        Toast.makeText(baseContext, string, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val SELECT_PHOTO_REQUEST_CODE = 100
    }

    private var detector: FirebaseVisionTextRecognizer? = null
    private lateinit var viewFinder: TextureView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        viewFinder = findViewById(R.id.view_finder)
        Dexter.withActivity(this)
            .withPermissions(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_CONTACTS
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    viewFinder.post { startCamera() }
                    btn_gallery.setOnClickListener {
                        openGallery()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    token?.cancelPermissionRequest()
                }
            }).check()

        check_button.setOnClickListener {
            processingImage()
        }
        btn_history.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }

    }

    private fun processingImage() {
        val bitmap = image_view.drawable.toBitmap()
        val image = FirebaseVisionImage.fromBitmap(bitmap)
        detector = FirebaseVision.getInstance()
            .onDeviceTextRecognizer
        detector!!.processImage(image)
            .addOnSuccessListener { result ->
                val text = Oryor.find(result.text)
                if (text.isNotEmpty()) {
                    text_oryor.text = text
                    val intent = Intent(this, ResultActivity::class.java)
                    intent.putExtra("code", text)
                    startActivity(intent)
                }

            }
            .addOnFailureListener {
                // Task failed with an exception
                // ...
            }

    }

    private fun startCamera() {
        val previewConfig = PreviewConfig.Builder().apply {
            setTargetRotation(getWindowManager().getDefaultDisplay().getRotation())
            setTargetAspectRatio(Rational(3, 4))
        }.build()

        val preview = Preview(previewConfig)
        preview.setOnPreviewOutputUpdateListener {

            // To update the SurfaceTexture, we have to remove it and re-add it
            val parent = viewFinder.parent as ViewGroup
            parent.removeView(viewFinder)
            parent.addView(viewFinder, 0)

            viewFinder.surfaceTexture = it.surfaceTexture
            updateTransform()
        }

        val imageCaptureConfig = ImageCaptureConfig.Builder()
            .apply {
                setTargetAspectRatio(Rational(3, 4))
                // We don't set a resolution for image capture; instead, we
                // select a capture mode which will infer the appropriate
                // resolution based on aspect ration and requested mode
                setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY)
            }.build()

        val imageCapture = ImageCapture(imageCaptureConfig)
        capture_button.setOnClickListener {
            val file = File(
                externalMediaDirs.first(),
                "${System.currentTimeMillis()}.jpg"
            )

            imageCapture.takePicture(file, object : ImageCapture.OnImageSavedListener {
                override fun onImageSaved(file: File) {
                    back_button.visibility = View.VISIBLE
                    view_finder.visibility = View.GONE
                    image_view.visibility = View.VISIBLE
                    layer_camera.visibility = View.GONE
                    layer_text_oryor.visibility = View.VISIBLE

                    Glide.with(this@CameraActivity)
                        .load(file)
                        .into(image_view)
                }

                override fun onError(
                    imageCaptureError: ImageCapture.ImageCaptureError,
                    message: String,
                    cause: Throwable?
                ) {
                    val msg = "Photo capture failed: $message"
                    Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                    Log.e("CameraXApp", msg)
                    cause?.printStackTrace()
                }

            })
        }

        //For real time
//        val analyzerConfig = ImageAnalysisConfig.Builder().apply {
//            setLensFacing(CameraX.LensFacing.BACK)
//            // run the analytics on a background thread so we are not interrupting
//            // the preview
//            val analyzerThread = HandlerThread("OCR").apply { start() }
//            setCallbackHandler(Handler(analyzerThread.looper))
//            // we only care about the latest image in the buffer,
//            // we do not need to analyze each image
//            setImageReaderMode(ImageAnalysis.ImageReaderMode.ACQUIRE_LATEST_IMAGE)
//            setTargetResolution(Size(480, 360))
//        }.build()
//
//        val analyzerUseCase = ImageAnalysis(analyzerConfig).apply {
//            val imageAnalyzer = ImageAnalyzer()
//            analyzer = imageAnalyzer
//            imageAnalyzer.callBack = this@CameraActivity
//        }


        CameraX.bindToLifecycle(this, preview, imageCapture)
    }

    private fun updateTransform() {
        val matrix = Matrix()

        // Compute the center of the view finder
        val centerX = viewFinder.width / 2f
        val centerY = viewFinder.height / 2f

        // Correct preview output to account for display rotation
        val rotationDegrees = when (viewFinder.display.rotation) {
            Surface.ROTATION_0 -> 0
            Surface.ROTATION_90 -> 90
            Surface.ROTATION_180 -> 180
            Surface.ROTATION_270 -> 270
            else -> return
        }
        matrix.postRotate(-rotationDegrees.toFloat(), centerX, centerY)

        // Finally, apply transformations to our TextureView
        viewFinder.setTransform(matrix)
    }


    private fun openGallery() {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent, SELECT_PHOTO_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SELECT_PHOTO_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val uri = data.data
            try {
                back_button.visibility = View.VISIBLE
                view_finder.visibility = View.GONE
                image_view.visibility = View.VISIBLE
                layer_camera.visibility = View.GONE
                layer_text_oryor.visibility = View.VISIBLE
                Glide.with(this@CameraActivity)
                    .load(uri)
                    .into(image_view)
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    override fun onBackPressed() {
        backToCamera()
        super.onBackPressed()

    }

    fun backToCamera(view: View? = null) {
        if (image_view.isVisible) {
            back_button.visibility = View.GONE
            view_finder.visibility = View.VISIBLE
            image_view.visibility = View.GONE
            layer_camera.visibility = View.VISIBLE
            layer_text_oryor.visibility = View.GONE
            return
        }
    }
}
