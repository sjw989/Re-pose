package org.techtown.repose.server

import org.techtown.repose.Data.*
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

    @POST("/user/update/pose")
    fun update_pose(@Body user: UpdatePoseOfUserData): Call<BeforeParsingUserData>

    @POST("/user/update/medal")
    fun update_medal(@Body user: UpdateMedalOfUserData): Call<BeforeParsingUserData>

    @POST("/user/update/weekday")
    fun update_weekday(@Body user: UpdateWeekdayOfUserData): Call<BeforeParsingUserData>

    @POST("/user/update/hour")
    fun update_hour(@Body user: UpdateHourOfUserData): Call<BeforeParsingUserData>

    @POST("/user/update/confirmNum")
    fun update_confirmNum(@Body user: UpdateConfirmNumOfUserData): Call<BeforeParsingUserData>

}