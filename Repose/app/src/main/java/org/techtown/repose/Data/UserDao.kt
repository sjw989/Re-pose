package org.techtown.repose.Data

import androidx.room.*

@Dao
interface UserDao {
    @Query("SELECT * FROM userdata")
    fun getUserData(): UserData?

    @Query("UPDATE userdata SET pose=:pose WHERE id=:id")
    fun updateUserDataPose(id: String, pose: List<Boolean>)

    @Query("UPDATE userdata SET medal=:medal WHERE id=:id")
    fun updateUserDataMedal(id: String, medal: List<Boolean>)

    @Query("UPDATE userdata SET weekday=:weekday WHERE id=:id")
    fun updateUserDataWeekday(id: String, weekday: List<Boolean>)

    @Query("UPDATE userdata SET hour=:hour WHERE id=:id")
    fun updateUserDataHour(id: String, hour: List<Boolean>)

    @Insert
    fun insertUserData(user: UserData)

    @Delete
    fun delete(user: UserData)

//    @Query("SELECT * FROM userdata WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<UserData>
//
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): UserData
//
//    @Insert
//    fun insertAll(vararg users: UserData)
}
