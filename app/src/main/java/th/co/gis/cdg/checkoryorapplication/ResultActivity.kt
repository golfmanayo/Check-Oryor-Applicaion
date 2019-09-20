package th.co.gis.cdg.checkoryorapplication

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener

import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.cloud.FirebaseVisionCloudDetectorOptions
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.text.FirebaseVisionCloudTextRecognizerOptions
import com.google.firebase.ml.vision.text.FirebaseVisionText
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {

    val oryor = Oryor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        buttonTest.setOnClickListener {
            val  oryortext = oryor.find("20-2-04858-2-0016")
            val i=0
        }

    }

    fun imageToText(bitmap : Bitmap){
        val img = FirebaseVisionImage.fromBitmap(bitmap)
        val detector = FirebaseVision.getInstance().onDeviceTextRecognizer
        detector.processImage(img)
            .addOnSuccessListener { texts ->
                if(texts.textBlocks.size > 0){
                    var str = ""
                    texts.textBlocks.forEach { blocktext ->
                        str = str + blocktext.text
                    }
//                    oryor.find(str)
                }
            }
            .addOnFailureListener {
                    e -> e.printStackTrace()
            }
    }
}
