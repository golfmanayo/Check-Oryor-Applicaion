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
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_result.*
import th.co.gis.cdg.checkoryorapplication.service.OryorService
import th.co.gis.cdg.checkoryorapplication.model.Oryor
import th.co.gis.cdg.checkoryorapplication.model.ServiceRespone

class ResultActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val service = OryorService()

        buttonTest.setOnClickListener {
            service.getOryor("20-2-04858-2-001")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(object : DisposableSingleObserver<List<ServiceRespone>>() {
//                    override fun onSuccess(value: List<ServiceRespone>?) {
//                        val i=0
//                    }
//
//                    override fun onError(e: Throwable?) {
//                        val i=0
//                    }
//
//                })
                .subscribe(
                    {
                        val data = Gson().fromJson(it["output"],Oryor::class.java)
                        val i=0
                    },
                    {
                        val i=0
                    }
                )

            val  oryortext = th.co.gis.cdg.checkoryorapplication.Oryor.find("asdf0A2562/23dsads")
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
