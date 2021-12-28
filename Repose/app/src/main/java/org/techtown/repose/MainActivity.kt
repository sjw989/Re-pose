package org.techtown.repose

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController

import androidx.navigation.fragment.findNavController


import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.techtown.repose.Data.AppDatabase
import org.techtown.repose.Service.AlarmReceiver
import org.techtown.repose.Service.medalAlarmReceiver
import org.techtown.repose.server.RetrofitClient
import org.techtown.repose.server.RetrofitService
import retrofit2.Retrofit
import java.util.*


class MainActivity : AppCompatActivity() {

    lateinit var navController:NavController // 네비게이션 컨트롤러

    lateinit var db: AppDatabase
    lateinit var retrofit: Retrofit
    lateinit var supplementService : RetrofitService
    lateinit var alarmManager: AlarmManager
    //알림 설정

    companion object{
        // pose 리스트
        val pose_list = listOf<String>("다리꼬기","한 쪽으로 짐 들기","장시간 앉아 있기","장시간 서 있기",
                                        "장시간 전자기기 사용","장시간 독서", "장시간 필기","장시간 운전",
                                        "팔자걸음","안짱걸음","양반다리", "엎드려자기")

        var user_pose = mutableListOf<Boolean>(false,false,false,false,false,false,false,false,false,false,false,false) // 사이즈 : 12
        var user_timer = mutableListOf<Boolean>(false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false) // 사이즈 : 16
        var user_days = mutableListOf<Boolean>(false,false,false,false,false,false,false) // 사이즈 : 7
        var user_medal = mutableListOf<Boolean>(false,false,false,false,false,false,false,false) // 사이즈 : 8
        var user_confirmNum = 0 // 운동완료 버튼 누른 횟수
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 임의로 user 객체 하나 만듦
        navController = nav_host_fragment.findNavController()
        Log.e("mainActivity","_oncreated!!")
    }

    //network retrofit
    fun initRetrofit() {
        retrofit = RetrofitClient.getInstance()
        supplementService = retrofit.create(RetrofitService::class.java)
    }

    fun initRoomDB(context: Context) {
        db = AppDatabase.getInstance(context)!!
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun setPosingAlarm(ischecked: Boolean, notificationId:Int, listenerContext: Context) {
        alarmManager = listenerContext.getSystemService(ALARM_SERVICE) as AlarmManager
        val alarmIntent = Intent(listenerContext, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            listenerContext, notificationId, alarmIntent,
            PendingIntent.FLAG_UPDATE_CURRENT)
        if(ischecked){
            val repeatInterval: Long = 24* 60 * 60 * 1000   // 24h
            val calendar: Calendar = Calendar.getInstance().apply { // 1
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, notificationId * 2)
                set(Calendar.MINUTE, 0)
            }
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, // 2
                calendar.timeInMillis,
                repeatInterval,
                pendingIntent)
            Log.e("alarm is setting $notificationId",alarmManager.toString())
        }else{
            alarmManager.cancel(pendingIntent)
            Log.e("alarm is canceled $notificationId",alarmManager.toString())
        }
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun setMedalAlarm(notificationId:Int, listenerContext: Context, medalIdx: Int) {
        alarmManager = listenerContext.getSystemService(ALARM_SERVICE) as AlarmManager
        val alarmIntent = Intent(listenerContext, medalAlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            listenerContext, notificationId, alarmIntent,
            PendingIntent.FLAG_UPDATE_CURRENT)

        CoroutineScope(Dispatchers.IO).launch {
            initRoomDB(listenerContext)
            RoomDBUpdateMedalOfUserData(medalIdx, true)
        }

        val calendar = Calendar.getInstance()
        alarmManager.set(AlarmManager.RTC_WAKEUP, // 2
            calendar.timeInMillis+2000,
            pendingIntent)

        Log.e("alarm is setting $notificationId",alarmManager.toString())
    }


    suspend fun RoomDBUpdateMedalOfUserData( updateIndex: Int, set: Boolean){
        val tmpId = db.userDao().getUserData()!!.id
        var tmpList = db.userDao().getUserData()!!.medal.toMutableList()
        tmpList[updateIndex] = set
        user_medal = tmpList
        db.userDao().updateUserDataMedal(tmpId, tmpList.toList())
    }
}