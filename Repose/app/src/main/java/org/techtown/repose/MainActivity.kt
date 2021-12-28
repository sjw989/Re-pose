package org.techtown.repose

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController

import androidx.navigation.fragment.findNavController
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey


import kotlinx.android.synthetic.main.activity_main.*
import org.techtown.repose.server.APIInterface
import org.techtown.repose.server.RetrofitClient
import org.techtown.repose.server.RetrofitService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



class MainActivity : AppCompatActivity() {

    lateinit var navController:NavController // 네비게이션 컨트롤러

    lateinit var db:AppDatabase
    lateinit var retrofit: Retrofit
    lateinit var supplementService : RetrofitService

    companion object{
        // pose 리스트
        val pose_list = listOf<String>("다리꼬기","한 쪽으로 짐 들기","장시간 앉아 있기","장시간 서 있기",
                                        "장시간 전자기기 사용","장시간 독서", "장시간 필기","장시간 운전",
                                        "팔자걸음","안짱걸음","양반다리", "엎드려자기")

        var user_pose = mutableListOf<Boolean>(false,false,false,false,false,false,false,false,false,false,false,false) // 사이즈 : 12
        var user_timer = mutableListOf<Boolean>(false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false) // 사이즈 : 16
        var user_days = mutableListOf<Boolean>(false,false,false,false,false,false,false) // 사이즈 : 7
        var user_medal = mutableListOf<Boolean>(false,false,false,false,false,false,false, false) // 사이즈 : 8
        var user_confirmNum = 0 // 운동완료 버튼 누른 횟수
    }

    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 임의로 user 객체 하나 만듦
        navController = nav_host_fragment.findNavController()

        //roomDB


//        db = AppDatabase.getInstance(applicationContext)!!
//        mc.db = AppDatabase.getInstance(this)!!
//        val newUserData = UserData(123,"chae","kijung")
//        mc.db.userDao().insertUserData(newUserData)

    }
        //network retrofit



    fun initRetrofit() {
        retrofit = RetrofitClient.getInstance()
        supplementService = retrofit.create(RetrofitService::class.java)
    }

}