package th.co.gis.cdg.checkoryorapplication.service

import com.google.gson.JsonObject
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.*
import th.co.gis.cdg.checkoryorapplication.model.Oryor

interface OryorApi {

    @POST("ajax-check-product.php")
    @Headers("Content-Type: text/plain")
//    Content-Type: x-www-form-urlencoded; charset=utf-8
    fun getDataOryor(@Body body: HashMap<String, Any>) : Single<List<Oryor>>

    @FormUrlEncoded
    @POST("http://pca.fda.moph.go.th/ajax-check-product.php")
    fun getDataOryor(@Field("number_src")number_src: String, @Field("type") type:Int):Observable<JsonObject>

}