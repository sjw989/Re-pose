package org.techtown.repose

import androidx.room.*
import com.google.gson.Gson

@Entity
data class UserData(
    @PrimaryKey var id: String,
    @ColumnInfo var pw: String?,
    @ColumnInfo var email: String?,
    @ColumnInfo var pose: List<Boolean>,
    @ColumnInfo var medal: List<Boolean>,
    @ColumnInfo var weekday: List<Boolean>,
    @ColumnInfo var hour: List<Boolean>,
    @ColumnInfo var confirmNum: Int, // 운동 완료 버튼 누른 횟수
    @ColumnInfo var primium: Boolean,
    @ColumnInfo var joinDate: String, // 가입한 날짜
)


