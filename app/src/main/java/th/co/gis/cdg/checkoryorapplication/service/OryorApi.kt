package th.co.gis.cdg.checkoryorapplication.service

import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import th.co.gis.cdg.checkoryorapplication.model.Oryor

interface OryorApi {

    @POST("ajax-check-product.php")
    @Headers("Content-Type: x-www-form-urlencoded; charset=utf-8")
    fun getDataOryor(@Body body: HashMap<String, Any>) : Single<List<Oryor>>

}