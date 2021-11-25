package org.techtown.repose.server

import retrofit2.Call
import retrofit2.http.*

interface testService {
    @POST("/user")
    fun post_user(@Body user: testUser): Call<testUser>

}