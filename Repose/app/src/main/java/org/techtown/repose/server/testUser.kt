package org.techtown.repose.server

import com.google.gson.annotations.SerializedName

data class testUser(
    @SerializedName("userId")
    val userId: String,
    @SerializedName("userPw")
    val userPw: String,
    @SerializedName("userEmail")
    val userEmail: String
)
