package th.co.gis.cdg.checkoryorapplication.service

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import th.co.gis.cdg.checkoryorapplication.model.Oryor

class OryorService {

    companion object {
        const val BASE_UEL = "http://pca.fda.moph.go.th/ajax-check-product.php"
    }

    var api: OryorApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_UEL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        api = retrofit.create(OryorApi::class.java)
    }

    fun getOryor(cade : String): Single<List<Oryor>> {
        val parameter = hashMapOf(
            "number_src" to cade,
            "type" to 0
        )
        return api.getDataOryor(parameter)
    }

}