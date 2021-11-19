package org.techtown.repose.server

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RetrofitService {
    @POST("/")
    fun userJoin(@Body data:String) : Call<JoinResponse>
}