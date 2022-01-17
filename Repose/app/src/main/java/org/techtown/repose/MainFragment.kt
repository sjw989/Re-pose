package org.techtown.repose

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.techtown.repose.MainActivity.Companion.user_days
import org.techtown.repose.MainActivity.Companion.user_pose
import org.techtown.repose.MainActivity.Companion.user_timer
import org.techtown.repose.databinding.FragMainBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs
import kotlin.math.max
import androidx.core.view.ViewCompat
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer

import androidx.viewpager2.widget.ViewPager2
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.techtown.repose.Data.AppDatabase
import org.techtown.repose.Data.UserData
import org.techtown.repose.MainActivity.Companion.user_medal


class MainFragment : Fragment(){
    lateinit var  navController : NavController// 네비게이션 컨트롤러
    private var _binding : FragMainBinding? = null // 뷰바인딩
    private val binding get() = _binding!! // 뷰바인딩
    private var exit_time : Long = 0
    var mc:MainActivity = MainActivity()


    companion object{
        var vpAdapter: FragmentStateAdapter? = null // pose Adapter
        lateinit var countDown : CountDownTimer
        val is_exercise_complete : MutableList<Boolean> = mutableListOf(false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false) // 해당 시간에 운동을 했는지
        var is_countDown : Boolean = false // 지금 카운트다운 on/off
        var time_idx : Int = 0
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragMainBinding.inflate(inflater,container,false) // 뷰바인딩
        return binding.root
    }
    @SuppressLint("LongLogTag")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view) // 네비게이션 컨트롤러 view로 부터 가져오기
        mc.initRetrofit()
        mc.db = AppDatabase.getInstance(requireContext())!!
        CoroutineScope(Dispatchers.IO).launch {
            val tempUserData: UserData = mc.db.userDao().getUserData()!!
            Log.e("main_frag", user_pose.toString())
            Log.e("main_frag", user_days.toString())
            Log.e("main_frag", user_timer.toString())
            Log.e("main_frag_medal", user_medal.toString())
            Log.e("main_frag_room", tempUserData.pose.toString())
            Log.e("main_frag_room", tempUserData.weekday.toString())
            Log.e("main_frag_room", tempUserData.hour.toString())
            Log.e("main_frag_room_confirmNum", tempUserData.confirmNum.toString())
        }

        select_pose() // 자세선택 버튼
        show_medal() // 메달 보여주기 화면
        select_time() // 요일, 시간 선택\
        countTimer() // 카운트다운 타이머
        back_pressed() // 뒤로가기 버튼
        connect_viewPager() // 뷰페이저 연결
        start_ad() // 광고시작
    }

    fun start_ad(){
        MobileAds.initialize(context){}
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }
    fun get_time_idx(hour : Int, minute : Int) : Pair<Int,Int>{
        var idx : Int = 0
        var m : Int = 0
        when(hour){
            24-> idx = if(minute<30){ 15 }else{ 0 }
            1-> idx = 0
            2-> idx = 1
            3-> idx = if(minute<30){ 1 }else{ 2 }
            4-> idx = 2
            5-> idx = 3
            6-> idx = if(minute<30){ 3 }else{ 4 }
            7-> idx = 4
            8-> idx = 5
            9-> idx = if(minute<30){ 5 }else{ 6 }
            10-> idx = 6
            11-> idx = 7
            12-> idx = if(minute<30){ 7 }else{ 8 }
            13-> idx = 8
            14-> idx = 9
            15-> idx = if(minute<30){ 9 }else{ 10 }
            16-> idx = 10
            17-> idx = 11
            18-> idx = if(minute<30){ 11 }else{ 12 }
            19-> idx = 12
            20-> idx = 13
            21-> idx = if(minute<30){ 13 }else{ 14 }
            22-> idx = 14
            23-> idx = 15
        }
        when(hour){
            24-> m = if(minute<30){ -1 }else{ 30 }
            1-> m = 30
            2-> m = 120
            3-> m = if(minute<30){ -1 }else{ 210 } // ex) 3시23분에는 알람이 울릴 수 없으니 -1 넣어줌
            4-> m = 210
            5-> m = 300
            6-> m = if(minute<30){ -1 }else{ 390 }
            7-> m = 390
            8-> m = 480
            9-> m = if(minute<30){ -1 }else{ 6 }
            10-> m = 570
            11-> m = 660
            12-> m = if(minute<30){ -1 }else{ 8 }
            13-> m = 750
            14-> m = 840
            15-> m = if(minute<30){ -1 }else{ 10 }
            16-> m = 930
            17-> m = 1020
            18-> m = if(minute<30){ -1 }else{ 12 }
            19-> m = 1110
            20-> m = 1200
            21-> m = if(minute<30){ -1 }else{ 14 }
            22-> m = 1290
            23-> m = 1380
        }
        return Pair(idx,m)
    }

    // 카운트다운 타이머
    private fun countTimer(){
        // 현재시간을 가져오기
        val long_now = System.currentTimeMillis()
        // 현재 시간을 Date 타입으로 변환
        val t_date = Date(long_now)
        val t_dateDay = SimpleDateFormat("E", Locale("ko","KR"))
        val t_dateHour = SimpleDateFormat("kk", Locale("ko","KR"))
        val t_dateMinute = SimpleDateFormat("mm", Locale("ko","KR"))
        val t_dateSeconds = SimpleDateFormat("ss", Locale("ko","KR"))
        println(t_dateDay.format(t_date))
        println(t_dateHour.format(t_date))
        println(t_dateMinute.format(t_date))
        println(t_dateSeconds.format(t_date))


        val time_day : String = t_dateDay.format(t_date).toString()
        var time_hour : Int = t_dateHour.format(t_date).toString().toInt()
        val time_minutes : Int = t_dateMinute.format(t_date).toString().toInt()
        val time_seconds : Int = t_dateSeconds.format(t_date).toString().toInt()

        var p  = get_time_idx(time_hour,time_minutes) // 현재시간이 몇번째 인덱스인지
        time_idx = p.first
        var time_m : Int = p.second
        if(time_hour == 24){ // 24시는 00시로 바꿔줌
            time_hour = 0
            time_idx = 0
        }
        var time_gap = (time_hour*60 + time_minutes) - time_m // 현재시간 - 알람 시작시간
        if(time_day == "월"){
            if (user_days[0]) {
                if (user_timer[time_idx] && user_pose.contains(true)) { // user가 현재 시간을 선택해놨으면 && 선택한 자세가 있으면
                    if(time_gap < 60 ){ // 현재시간 - 알람 시작시간 < 60 이면 운동 할 수 있음
                        if(!is_exercise_complete[time_idx]){ // 해당 시간에 운동을 안헀으면
                            is_countDown = true // 카운트다운 on
                            start_Timer(time_gap, time_seconds.toString()) // 카운트다운 시작
                        }
                    }
                }
            }
        }
        if(time_day == "화"){
            if (user_days[1]) {
                if (user_timer[time_idx] && user_pose.contains(true)) { // user가 현재 시간을 선택해놨으면 && 선택한 자세가 있으면
                    if(time_gap < 60  ){ // 현재시간 - 알람 시작시간 < 60 이면 운동 카운트다운
                        if(!is_exercise_complete[time_idx]){ // 해당 시간에 운동을 안헀으면
                            is_countDown = true // 카운트다운 on
                            start_Timer(time_gap, time_seconds.toString()) // 카운트다운 시작
                        }
                    }
                }
            }
        }
        if(time_day == "수"){
            if (user_days[2]) {
                if (user_timer[time_idx] && user_pose.contains(true)) { // user가 현재 시간을 선택해놨으면 && 선택한 자세가 있으면
                    if(time_gap < 60  ){ // 현재시간 - 알람 시작시간 < 60 이면 운동 카운트다운
                        if(!is_exercise_complete[time_idx]){ // 해당 시간에 운동을 안헀으면
                            is_countDown = true // 카운트다운 on
                            start_Timer(time_gap, time_seconds.toString()) // 카운트다운 시작
                        }
                    }
                }
            }
        }
        if(time_day == "목"){
            if (user_days[3]) {
                if (user_timer[time_idx] && user_pose.contains(true)) { // user가 현재 시간을 선택해놨으면 && 선택한 자세가 있으면
                    if(time_gap < 60  ){ // 현재시간 - 알람 시작시간 < 60 이면 운동 카운트다운
                        if(!is_exercise_complete[time_idx]){ // 해당 시간에 운동을 안헀으면
                            is_countDown = true // 카운트다운 on
                            start_Timer(time_gap, time_seconds.toString()) // 카운트다운 시작
                        }
                    }
                }
            }
        }
        if(time_day == "금"){
            if (user_days[4]) {
                if (user_timer[time_idx] && user_pose.contains(true)) { // user가 현재 시간을 선택해놨으면 && 선택한 자세가 있으면
                    if(time_gap < 60  ){ // 현재시간 - 알람 시작시간 < 60 이면 운동 카운트다운
                        if(!is_exercise_complete[time_idx]){ // 해당 시간에 운동을 안헀으면
                            is_countDown = true // 카운트다운 on
                            start_Timer(time_gap, time_seconds.toString()) // 카운트다운 시작
                        }
                    }
                }
            }
        }
        if(time_day == "토") {
            if (user_days[5]) {
                if (user_timer[time_idx] && user_pose.contains(true)) { // user가 현재 시간을 선택해놨으면 && 선택한 자세가 있으면
                    if(time_gap < 60  ){ // 현재시간 - 알람 시작시간 < 60 이면 운동 카운트다운
                        if(!is_exercise_complete[time_idx]){ // 해당 시간에 운동을 안헀으면
                            is_countDown = true // 카운트다운 on
                            start_Timer(time_gap, time_seconds.toString()) // 카운트다운 시작   
                        }
                    }
                }
            }
        }
        if (time_day == "일") {
            if (user_days[6]) {
                if (user_timer[time_idx] && user_pose.contains(true)) { // user가 현재 시간을 선택해놨으면 && 선택한 자세가 있으면
                    if (time_gap < 60) { // 현재시간 - 알람 시작시간 < 60 이면 운동 카운트다운
                        if (!is_exercise_complete[time_idx]) { // 해당 시간에 운동을 안헀으면
                            is_countDown = true // 카운트다운 on
                            start_Timer(time_gap, time_seconds.toString()) // 카운트다운 시작
                        }
                    }
                }
            }

        }

    }

    // 뒤로가기 버튼
    fun back_pressed(){
        requireActivity().onBackPressedDispatcher.addCallback(object :
            OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                var cur = System.currentTimeMillis()
                println((cur.toLong() - exit_time).toString())
                if(cur - exit_time < 2000){
                    if(is_countDown){
                        countDown.cancel()
                    }
                    activity?.finish()
                }
                else{
                    exit_time = cur.toLong()
                    Toast.makeText(context, "뒤로가기를 한 번 더 누르면 종료됩니다", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    // 메달 화면
    fun show_medal(){
        binding.textView2.setOnClickListener{
            if(is_countDown){
                countDown.cancel()
            }
            navController.navigate(R.id.action_frag_main_to_medalFragment)
        }

        binding.btnMedal.setOnClickListener{
            if(is_countDown){
                countDown.cancel()
            }
            navController.navigate(R.id.action_frag_main_to_medalFragment)
        }
    }

    // 자세선택 버튼
    private fun select_pose(){
        binding.textView3.setOnClickListener{
            if(is_countDown){
                countDown.cancel()
            }
            navController.navigate(R.id.action_frag_main_to_frag_select_pose)
        }
        binding.btnSelectPose.setOnClickListener{
            if(is_countDown){
                countDown.cancel()
            }
            navController.navigate(R.id.action_frag_main_to_frag_select_pose)
        }
    }

    // 시간, 요일 선택 버튼
    private fun select_time(){
        binding.textView.setOnClickListener{
            if(is_countDown){
                countDown.cancel()
            }
            navController.navigate(R.id.action_frag_main_to_selectTimerFragment)
        }
        binding.btnSetTimer.setOnClickListener{
            if(is_countDown){
                countDown.cancel()
            }
            navController.navigate(R.id.action_frag_main_to_selectTimerFragment)
        }
    }

    // 타이머 시작시키기
    private fun start_Timer(time_gap : Int, t_dateSeconds : String){
        val startMinutes : Int = 60 - time_gap - 1
        val startSeconds : Int = 60 - t_dateSeconds.toInt()
        countDown = object : CountDownTimer((1000*startMinutes*60 + 1000 * startSeconds).toLong(), 1000){
            override fun onTick(p0: Long) {
                val second = (p0 / 1000).toString() // 남은 초
                binding.tvMiniutes.text = (second.toInt() / 60).toString() // 남은 분
                binding.tvSeconds.text = (second.toInt() % 60 ).toString() // 남은 초
                if((second.toInt() / 60 ).toString().length == 1){ // 분의 길이가 1이면 : 2 -> 02 이런식으로 바꿔줌
                    binding.tvMiniutes.text = "0" + binding.tvMiniutes.text
                }
                if((second.toInt() % 60 ).toString().length == 1){ // 초의 길이가 1이면 : 2 -> 02 이런식으로 바꿔줌
                    binding.tvSeconds.text = "0" + binding.tvSeconds.text
                }
            }
            override fun onFinish() {
                binding.tvMiniutes.text = "00"
                binding.tvSeconds.text = "00"
            }
        }.start()
    }

    // Pose 뷰페이저 어댑터
    class PosePagerAdapter(private val fmList:ArrayList<Fragment>, fm: MainFragment): FragmentStateAdapter(fm){
        private val fmIds = fmList.map{it.hashCode().toLong()}

        override fun getItemCount(): Int {
            return fmList.size
        }
        override fun createFragment(position: Int): Fragment {
            return fmList[position]
        }

        override fun getItemId(position: Int): Long {
            return fmList[position].hashCode().toLong()
        }
        override fun containsItem(itemId: Long): Boolean {
            return fmIds.contains(itemId)
        }

    }

    // 뷰페이저 연결
    private fun connect_viewPager(){
        val fmList = arrayListOf<Fragment>()
        for(i in 0..user_pose.size - 1){
            if(user_pose[i]){ // 선택되어있는 자세라면
                fmList.add(PoseFragment.newInstance(i,MainActivity.pose_list[i])) // fmList에 추가
            }
        }
        if(fmList.size == 0){ // 선택된 자세가 하나도 없다면
            fmList.add(EmptyPoseFragment.newInstance()) // 자세없음 fragment
        }
        vpAdapter = PosePagerAdapter(fmList,this) // 어댑터 설정
        binding.vpPoseViewPager.adapter = vpAdapter
        binding.poseIndicator.setViewPager2(binding.vpPoseViewPager) // 인디케이터랑 어댑터 동기화
        binding.vpPoseViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.vpPoseViewPager.offscreenPageLimit = 3

        val pageMargin = resources.getDimensionPixelOffset(R.dimen.pageMargin).toFloat()
        val pageOffset = resources.getDimensionPixelOffset(R.dimen.offset).toFloat()


        binding.vpPoseViewPager.setPageTransformer { page, position ->
            val myOffset = position * -(2 * pageOffset + pageMargin)
            if (position < -1) {
                page.translationX = -myOffset
            }
            else if (position <= 1) {
                val scaleFactor = Math.max(0.7f, 1 - Math.abs(position - 0.14285715f))
                page.translationX = myOffset
                //page.scaleY = scaleFactor
                page.alpha = scaleFactor
            }
            else {
                page.alpha = 0f
                page.translationX = myOffset
            }
        }

    }


    // 뷰바인딩 destroy
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }



}
