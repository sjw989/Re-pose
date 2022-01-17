package org.techtown.repose.Data

import com.google.gson.annotations.SerializedName

data class UpdatePoseOfUserData(
    @SerializedName("pose") var pose: String,
    @SerializedName("userId") var userId: String,
)
data class UpdateMedalOfUserData(
    @SerializedName("medal") var medal: String,
    @SerializedName("userId") var userId: String,
)
data class UpdateWeekdayOfUserData(
    @SerializedName("weekday") var weekday: String,
    @SerializedName("userId") var userId: String,
)
data class UpdateHourOfUserData(
    @SerializedName("hour") var hour: String,
    @SerializedName("userId") var userId: String,
)
data class UpdateConfirmNumOfUserData(
    @SerializedName("confirmNum") var confirmNum: Int,
    @SerializedName("userId") var userId: String,
)

