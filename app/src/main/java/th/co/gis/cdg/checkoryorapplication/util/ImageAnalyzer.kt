package th.co.gis.cdg.checkoryorapplication.util

import android.util.Log
import android.widget.Toast
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata
import com.google.firebase.ml.vision.text.FirebaseVisionText
import th.co.gis.cdg.checkoryorapplication.Oryor

class ImageAnalyzer : ImageAnalysis.Analyzer{
    var callBack : ImageAnalyzerListener? = null

    private fun degreesToFirebaseRotation(degrees: Int): Int = when(degrees) {
        0 -> FirebaseVisionImageMetadata.ROTATION_0
        90 -> FirebaseVisionImageMetadata.ROTATION_90
        180 -> FirebaseVisionImageMetadata.ROTATION_180
        270 -> FirebaseVisionImageMetadata.ROTATION_270
        else -> throw Exception("Rotation must be 0, 90, 180, or 270.")
    }

    override fun analyze(imageProxy: ImageProxy?, rotationDegrees: Int) {
        val mediaImage = imageProxy?.image
        val imageRotation = degreesToFirebaseRotation(rotationDegrees)
        if (mediaImage != null) {
            try {
                val image = FirebaseVisionImage.fromMediaImage(mediaImage, imageRotation)
                val detector = FirebaseVision.getInstance()
                    .onDeviceTextRecognizer

                detector.processImage(image)
                    .addOnSuccessListener { result: FirebaseVisionText ->
                        val text = Oryor.find(result.text)
                        if (text.isNullOrEmpty()) {
                            callBack?.onGetTextOryor(text)
                        }
                    }
            }catch (e:Exception){

            }
        }

    }

    interface ImageAnalyzerListener{
        fun onGetTextOryor(string: String)
    }
}