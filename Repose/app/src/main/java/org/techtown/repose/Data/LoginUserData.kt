package org.techtown.repose.Data

import com.google.gson.annotations.SerializedName

data class LoginUserData(
    @SerializedName("userId") var userId: String,
    @SerializedName("userPw") var userPw: String,
)
