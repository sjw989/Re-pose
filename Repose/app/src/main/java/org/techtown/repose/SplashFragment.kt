package org.techtown.repose

import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.animation.doOnEnd
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.techtown.repose.Data.AppDatabase
import org.techtown.repose.MainActivity.Companion.user_days
import org.techtown.repose.MainActivity.Companion.user_joinData
import org.techtown.repose.MainActivity.Companion.user_medal
import org.techtown.repose.MainActivity.Companion.user_pose
import org.techtown.repose.MainActivity.Companion.user_timer
import org.techtown.repose.databinding.FragSplashBinding
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.*


class SplashFragment : Fragment() {
    lateinit var  navController : NavController// 네비게이션 컨트롤러
    private var _binding : FragSplashBinding? = null // 뷰바인딩
    private val binding get() = _binding!! // 뷰바인딩
    private var ExistUser: Boolean = false
    var mc : MainActivity = MainActivity()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragSplashBinding.inflate(inflater,container,false) // 뷰바인딩
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mc.db = AppDatabase.getInstance(requireContext())!!
        navController = Navigation.findNavController(view) // 네비게이션 컨트롤러 view로 부터 가져오기
        CoroutineScope(Dispatchers.IO).launch {
            Log.e("Exist User",ExistUser.toString())
            ExistUser = mc.db.userDao().getUserData() != null
            Log.e("Exist User",mc.db.userDao().getUserData().toString())
            Log.e("Exist User",ExistUser.toString())
            init()
            Log.e("Exist User",ExistUser.toString())
            //메인액티비티

        }
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun init(){ // 앱 시작할때 애니메이션 효과
        var fadeIn = ObjectAnimator.ofFloat(binding.logoImageView, "alpha", 0f, 1f)
        Handler(Looper.getMainLooper()).postDelayed({
            fadeIn.duration = 1500
            fadeIn.start()

            fadeIn = ObjectAnimator.ofFloat(binding.textView31, "alpha", 0f, 1f)
            fadeIn.duration = 1500
            fadeIn.start()

            fadeIn = ObjectAnimator.ofFloat(binding.textView32, "alpha", 0f, 1f)
            fadeIn.duration = 1500
            fadeIn.start()
        }, 1000)

        var fadeOut = ObjectAnimator.ofFloat(binding.textView31, "alpha", 1f, 0f)
        Handler(Looper.getMainLooper()).postDelayed({
            fadeOut.duration = 1500
            fadeOut.start()
//            navController.navigate(R.id.action_frag_splash_to_frag_start)
            fadeOut.doOnEnd {
                //룸디비 체크
                if(ExistUser) {
                    CoroutineScope(Dispatchers.IO).launch {
                        //룸 db 데이터 가져오기
                        user_days = RoomDBGetWeekdayOfUserData(mc).toMutableList()
                        user_timer = RoomDBGetHourOfUserData(mc).toMutableList()
                        user_pose = RoomDBGetPoseOfUserData(mc).toMutableList()
                        user_medal = RoomDBGetMedalOfUserData(mc).toMutableList()
                        user_joinData = RoomDBGetJoinDateOfUserData(mc)
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            ComposeJoindateForSettingAlarm(mc, user_joinData)
                        }
                    }
                    navController.navigate(R.id.action_frag_splash_to_frag_main)

                    //메인액티비티 변수
                }
                else navController.navigate(R.id.action_frag_splash_to_frag_start)
            }
        }, 4000)
    }

    suspend fun RoomDBGetWeekdayOfUserData(mc: MainActivity):List<Boolean>{
        return mc.db.userDao().getUserData()!!.weekday
    }

    suspend fun RoomDBGetHourOfUserData(mc: MainActivity):List<Boolean>{
        return mc.db.userDao().getUserData()!!.hour
    }

    suspend fun RoomDBGetPoseOfUserData(mc: MainActivity):List<Boolean>{
        return mc.db.userDao().getUserData()!!.pose
    }

    suspend fun RoomDBGetMedalOfUserData(mc: MainActivity):List<Boolean>{
        return mc.db.userDao().getUserData()!!.medal
    }

    suspend fun RoomDBGetJoinDateOfUserData(mc: MainActivity):String{
        return mc.db.userDao().getUserData()!!.joinDate
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun ComposeJoindateForSettingAlarm(mc: MainActivity, tmpJoinDate: String){
        val todayLocalDate = LocalDate.now()
        val joinDate = LocalDate.parse(tmpJoinDate)
        Log.e("todayLocalDate",todayLocalDate.toString())
        Log.e("joinDate",joinDate.toString())
        val dateGap = ChronoUnit.DAYS.between(joinDate, todayLocalDate).toInt()
        if(dateGap == 100 && !user_medal[1]){
            mc.setMedalAlarm(50, requireContext(),1)
        }
        if(dateGap == 300 && !user_medal[2]){
            mc.setMedalAlarm(50, requireContext(),2)
        }
    }


    // 뷰바인딩 destroy
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}