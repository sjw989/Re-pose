package org.techtown.repose

import androidx.room.TypeConverter
import com.google.gson.Gson

class Converters {
    @TypeConverter
    fun listToJson(value: List<Boolean>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String): List<Boolean>? = Gson().fromJson(value, Array<Boolean>::class.java).toList()
}