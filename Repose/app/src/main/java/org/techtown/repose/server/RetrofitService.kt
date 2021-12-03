package org.techtown.repose.server

import org.techtown.repose.Data.BeforeParsingUserData
import org.techtown.repose.Data.LoginUserData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitService {
    @POST("/user")
    fun post_user(@Body user: BeforeParsingUserData): Call<BeforeParsingUserData>

    @POST("/user/login")
    fun confirm_login(@Body user: LoginUserData): Call<BeforeParsingUserData>

    @POST("/user/idcheck")
    fun id_check(@Body user: BeforeParsingUserData): Call<BeforeParsingUserData>

}