package th.co.gis.cdg.checkoryorapplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "UPLOAD")
data class Oryor(
    @SerializedName("Addr") val Addr: String? = null,
    @SerializedName("IDA") val IDA: String? = null,
    @SerializedName("NewCode") val NewCode: String? = null,
    @SerializedName("URLs") val URLs: String? = null,
    @SerializedName("cncnm") val cncnm: String? = null,
    @SerializedName("lcnno") val lcnno: String? = null,
    @SerializedName("licen") val licen: String? = null,
    @SerializedName("produceng") val produceng: String? = null,
    @SerializedName("productha") val productha: String? = null,
    @SerializedName("thanm") val thanm: String? = null,
    @SerializedName("type") val type: String? = null,
    @SerializedName("typeallow") val typeallow: String? = null,
    @SerializedName("typepro") val typepro: String? = null
    ){
    @PrimaryKey(autoGenerate = true) var UPLOAD_ID: Int = 0
}