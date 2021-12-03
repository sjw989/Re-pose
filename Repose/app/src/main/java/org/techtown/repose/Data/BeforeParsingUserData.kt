package org.techtown.repose.Data

import com.google.gson.annotations.SerializedName

data class BeforeParsingUserData(
    @SerializedName("userId") var userId: String,
    @SerializedName("userPw") var userPw: String?,
    @SerializedName("userEmail") var userEmail: String?,
    @SerializedName("pose") var pose: String, //12
    @SerializedName("medal") var medal: String, // 8
    @SerializedName("weekday") var weekday: String, // 7
    @SerializedName("hour") var hour: String, //16
    @SerializedName("confirmNum") var confirmNum: Int, // 운동 완료 버튼 누른 횟수
    @SerializedName("premium") var premium: Int,
    @SerializedName("JoinDate") var joinDate: String, // 가입한 날짜
)
