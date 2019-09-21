package th.co.gis.cdg.checkoryorapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_result.*
import th.co.gis.cdg.checkoryorapplication.database.DatabaseManager
import th.co.gis.cdg.checkoryorapplication.model.Constants
import th.co.gis.cdg.checkoryorapplication.model.Oryor
import th.co.gis.cdg.checkoryorapplication.service.OryorService

class ResultActivity : AppCompatActivity() {

    private var oryorString : String? =null
    private var oryorList = arrayListOf<Oryor>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        val str:String? = intent.getStringExtra("code")
        val id : Int = intent.getIntExtra("ID_ORYOR",-1)
        progress.visibility = View.VISIBLE
        str?.let {
            getResult(it)
        }
        if (id != -1){
            getOryorFromDB(id)
        }



    }

    @SuppressLint("CheckResult")
    private fun getOryorFromDB(id: Int) {
        DatabaseManager.getInstance(this).getOryorByID(id)
            .subscribe({
                progress.visibility = View.GONE
                setView(it)
            },{

            })
    }

    private fun setView(data: Oryor){
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
    }

    @SuppressLint("CheckResult")
    fun getResult(str :String){
        val service = OryorService()
        service.getOryor(str)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    progress.visibility = View.GONE
                    if(it["output"].toString() != "null"){
                        val data = Gson().fromJson(it["output"],Oryor::class.java)
                        Log.i("Success","search")
                        setView(data)
                        Constants.list.add(data)

                        DatabaseManager.getInstance(this).insertUpload(arrayOf(data))
                            .subscribe({
                                Log.i("Success","save")
                            },{
                                Log.i("Error",it.message)
                            })

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
