package org.techtown.repose

import androidx.room.*
import com.google.gson.Gson

@Entity
data class UserData(
    @PrimaryKey val id: String,
    @ColumnInfo val pw: String?,
    @ColumnInfo val email: String?,
    @ColumnInfo val pose: List<Boolean>,
    @ColumnInfo val medal: List<Boolean>,
    @ColumnInfo val weekday: List<Boolean>,
    @ColumnInfo val hour: List<Boolean>,
    @ColumnInfo val confirmNum: Int, // 운동 완료 버튼 누른 횟수
    @ColumnInfo val primium: Boolean,
    @ColumnInfo val joinDate: String, // 가입한 날짜
)


