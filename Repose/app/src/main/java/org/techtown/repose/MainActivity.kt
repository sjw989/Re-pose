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
        val pose_list = listOf<String>("다리꼬기","한 쪽으로 짐 들기","장시간 앉아 있기","장시간 서있기",
                                        "장시간 전자기기 사용","장시간 독서", "장시간 필기","장시간 운전",
                                        "팔자걸음","안짱걸음","양반다리", "엎드려 자기")

        // 미리 선정해놓은 pose 리스트의 가이드 --> 우선은 텍스트만
        val exercise_list = listOf(listOf(listOf<String>("가슴 높이의 벽에 양쪽 손바닥을 고정하고 허리를 쭉 펴주세요.","팔과 다리 또한 쭉 펴주세요.","한쪽 무릎을 가슴까지 끌어당겼다가 뒤로 최대한 뻗어 올리고 다시 가운데로 위치시켜주세요.", "엉덩이를 쭉 뒤로 빼면서 허벅지 뒤쪽 근육을 늘여주세요. 이때, 시선은 정면에 고정시켜주세요.","양쪽 무릎 각각 10회 진행해주세요."), listOf<String>("골반이 높은 쪽 다리를 뒤로 펴고 반대편 다리는 굽혀 런지 자세를 해주세요.","무릎을 굽히는 방향으로 깍지 낀 팔을 머리 위로 쭉 뻗어주세요.","골반이 올라간 쪽만 10회 진행해주세요."), listOf<String>("날개뼈 밑에 폼롤러 및 베개를 놓고 바로 누워주세요","골반이 높은 쪽 팔을 머리 위로 올렸다 내렸다 반복해주세요.","골반이 올라간 쪽만 10회 진행해주세요.")),
                                listOf(listOf<String>("2번 팔올려","2번 팔내려","2번 팔주먹"), listOf<String>("2번 발올려","2번 발내려","2번 발주먹"), listOf<String>("2번 눈올려","2번 눈내려","2번 눈주먹")),
                                listOf(listOf<String>("3번 팔올려","3번 팔내려","3번 팔주먹"), listOf<String>("3번 발올려","3번 발내려","3번 발주먹"), listOf<String>("3번 눈올려","3번 눈내려","3번 눈주먹"))
                                )

        var user_pose = mutableListOf<Boolean>(false,false,false,false,false,false,false,false,false,false,false,false) // 사이즈 : 12
        var user_timer = mutableListOf<Boolean>(false,false,false,false,false,false,false,false,false,false,false,false) // 사이즈 : 12
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