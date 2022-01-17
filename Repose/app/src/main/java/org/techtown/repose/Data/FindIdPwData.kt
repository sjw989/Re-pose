package org.techtown.repose.Data

import com.google.gson.annotations.SerializedName

data class FindIdPwData (
    @SerializedName("userEmail") var userEmail: String
)
