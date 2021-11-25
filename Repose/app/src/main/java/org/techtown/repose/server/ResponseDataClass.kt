package org.techtown.repose.server

import retrofit2.Call
import retrofit2.http.*
import com.google.gson.annotations.SerializedName

data class JoinResponse(
    var id:String = "aaa"
)

data class JoinData(
    @SerializedName("id")
    var id:String

)

interface APIInterface {
    @FormUrlEncoded
    @POST("/user")
    fun userJoin(@Body data:JoinData) :Call<JoinResponse>
}
