package org.techtown.repose.server

import org.techtown.repose.BeforeParsingUserData
import org.techtown.repose.UserData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RetrofitService {
    @POST("/user")
    fun post_user(@Body user: BeforeParsingUserData): Call<BeforeParsingUserData>
}