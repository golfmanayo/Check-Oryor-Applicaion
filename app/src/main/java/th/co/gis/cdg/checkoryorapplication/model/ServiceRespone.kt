package th.co.gis.cdg.checkoryorapplication.model

import com.google.gson.annotations.SerializedName

data class ServiceRespone(
    @SerializedName("output") val output: String? = null
    )