package org.techtown.repose.Data

import androidx.room.*
import com.google.gson.Gson

@Entity
data class UserData(
    @PrimaryKey var id: String,
    @ColumnInfo var pw: String?,
    @ColumnInfo var email: String?,
    @ColumnInfo var pose: List<Boolean>, //12
    @ColumnInfo var medal: List<Boolean>, // 8
    @ColumnInfo var weekday: List<Boolean>, // 7
    @ColumnInfo var hour: List<Boolean>, //16
    @ColumnInfo var confirmNum: Int, // 운동 완료 버튼 누른 횟수
    @ColumnInfo var primium: Boolean,
    @ColumnInfo var joinDate: String, // 가입한 날짜
)


