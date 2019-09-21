package th.co.gis.cdg.checkoryorapplication

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener

import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.cloud.FirebaseVisionCloudDetectorOptions
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.text.FirebaseVisionCloudTextRecognizerOptions
import com.google.firebase.ml.vision.text.FirebaseVisionText
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer
import com.google.gson.Gson
import com.google.gson.JsonObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_result.*
import th.co.gis.cdg.checkoryorapplication.service.OryorService
import th.co.gis.cdg.checkoryorapplication.model.Oryor
import th.co.gis.cdg.checkoryorapplication.model.ServiceRespone

class ResultActivity : AppCompatActivity() {

    private var oryorString : String? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        getIntent().getBundleExtra("code")

        val str:String? = intent.getStringExtra("code")
        str?.let {
            getResult(it)
        }

        buttonTest.setOnClickListener {
//            getResult("20-2-04858-2-0016")
            getResult("13-1-22135-2-0003")
        }

    }

    fun getResult(str :String){
        val service = OryorService()
        service.getOryor(str)
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
                    if(it["output"].toString() != "null"){
                        val data = Gson().fromJson(it["output"],Oryor::class.java)
                        Log.i("Success","test")
                        tvlcnno.text = data.lcnno
                        tvAddr.text = data.Addr
                        tvIDA.text = data.IDA
                        tvNewCode.text = data.NewCode
                        tvcncnm.text = data.cncnm
                        tvLicen.text = data.licen
                        tvProducEng.text = data.produceng
                        tvProducTh.text = data.productha
                        tvthanm.text = data.thanm
                        tvType.text = data.type
                        tvTypeAllow.text = data.typeallow
                        tvTypePro.text = data.typepro
                        linearResult.visibility = View.VISIBLE

                        noneData.visibility = View.GONE
                    } else {
                        noneData.visibility = View.VISIBLE
                        noneData.text = "${noneData.text} หมายเลข ${str}"
                        linearResult.visibility = View.GONE
                    }

                },
                {
                    Log.i("Error",it.message)
                }
            )
    }
}
