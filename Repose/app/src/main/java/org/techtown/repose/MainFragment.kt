package org.techtown.repose

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




class MainFragment : Fragment(){
    lateinit var  navController : NavController// 네비게이션 컨트롤러
    private var _binding : FragMainBinding? = null // 뷰바인딩
    private val binding get() = _binding!! // 뷰바인딩
    private var exit_time : Long = 0

    companion object{
        var vpAdapter: FragmentStateAdapter? = null // pose Adapter
        lateinit var countDown : CountDownTimer
        val is_exercise_complete : MutableList<Boolean> = mutableListOf(false,false,false,false,false,false,false,false,false,false,false,false) // 해당 시간에 운동을 했는지
        var is_countDown : Boolean = false // 지금 카운트다운 on/off
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragMainBinding.inflate(inflater,container,false) // 뷰바인딩
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view) // 네비게이션 컨트롤러 view로 부터 가져오기
        select_pose() // 자세선택 버튼
        show_medal() // 메달 보여주기 화면
        select_time() // 요일, 시간 선택
        connect_viewPager() // 뷰페이저 연결
        countTimer() // 카운트다운 타이머
        back_pressed() // 뒤로가기 버튼
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

        var time_hour : Int = t_dateHour.format(t_date).toString().toInt()
        var time_idx: Int = time_hour / 2 // 현재시간이 몇번째 인덱스인지
        if(time_hour == 24){ // 24시는 00시로 바꿔줌
            time_hour = 0
            time_idx = 0
        }
        val time_day : String = t_dateDay.format(t_date).toString()
        val time_minutes : Int = t_dateMinute.format(t_date).toString().toInt()
        val time_seconds : Int = t_dateSeconds.format(t_date).toString().toInt()
        
        if(time_day == "월"){
            if (user_days[0]) {
                if (user_timer[time_idx] && user_pose.contains(true)) { // user가 현재 시간을 선택해놨으면 && 선택한 자세가 있으면

                    if(time_hour < time_idx * 2 + 1  ){ // 2시간 단위니까 예를 들어 2~4시면 3시 이전에만 카운트다운이 돌아야함.
                        if(!is_exercise_complete[time_idx]){ // 해당 시간에 운동을 안헀으면
                            is_countDown = true // 카운트다운 on
                            /*
                            binding.tvMiniutes.visibility = View.VISIBLE
                            binding.tvSeconds.visibility = View.VISIBLE
                            binding.tvTime.visibility = View.VISIBLE
                             */
                            start_Timer(time_minutes.toString(), time_seconds.toString()) // 카운트다운 시작
                        }
                        else{
                            /*
                            binding.tvMiniutes.visibility = View.INVISIBLE // 해당시간에 운동을 했으면 숨김
                            binding.tvSeconds.visibility = View.INVISIBLE
                            binding.tvTime.visibility = View.INVISIBLE
                             */
                        }
                    }
                }
                else{
                    /*
                    binding.tvMiniutes.visibility = View.INVISIBLE // 그 시간 선택 안해놨거나 선택한 자세가 없으면 숨김
                    binding.tvSeconds.visibility = View.INVISIBLE
                    binding.tvTime.visibility = View.INVISIBLE
                     */
                }
            }
        }
        if(time_day == "화"){
            if (user_days[1]) {
                if (user_timer[time_idx] && user_pose.contains(true)) { // user가 현재 시간을 선택해놨으면 && 선택한 자세가 있으면
                    if(time_hour < time_idx * 2 + 1  ){ // 2시간 단위니까 예를 들어 2~4시면 3시 이전에만 카운트다운이 돌아야함.
                        if(!is_exercise_complete[time_idx]){ // 해당 시간에 운동을 안헀으면
                            is_countDown = true // 카운트다운 on
                            /*
                            binding.tvMiniutes.visibility = View.VISIBLE
                            binding.tvSeconds.visibility = View.VISIBLE
                            binding.tvTime.visibility = View.VISIBLE
                             */
                            start_Timer(time_minutes.toString(), time_seconds.toString()) // 카운트다운 시작
                        }
                        else{
                            /*
                            binding.tvMiniutes.visibility = View.INVISIBLE // 해당시간에 운동을 했으면 숨김
                            binding.tvSeconds.visibility = View.INVISIBLE
                            binding.tvTime.visibility = View.INVISIBLE
                             */
                        }
                    }
                }
                else{
                    /*
                    binding.tvMiniutes.visibility = View.INVISIBLE // 그 시간 선택 안해놨거나 선택한 자세가 없으면 숨김
                    binding.tvSeconds.visibility = View.INVISIBLE
                    binding.tvTime.visibility = View.INVISIBLE
                     */
                }
            }
        }
        if(time_day == "수"){
            if (user_days[2]) {
                if (user_timer[time_idx] && user_pose.contains(true)) { // user가 현재 시간을 선택해놨으면 && 선택한 자세가 있으면
                    if(time_hour < time_idx * 2 + 1  ){ // 2시간 단위니까 예를 들어 2~4시면 3시 이전에만 카운트다운이 돌아야함.
                        if(!is_exercise_complete[time_idx]){ // 해당 시간에 운동을 안헀으면
                            is_countDown = true // 카운트다운 on
                            /*
                            binding.tvMiniutes.visibility = View.VISIBLE
                            binding.tvSeconds.visibility = View.VISIBLE
                            binding.tvTime.visibility = View.VISIBLE
                             */
                            start_Timer(time_minutes.toString(), time_seconds.toString()) // 카운트다운 시작
                        }
                        else{
                            /*
                            binding.tvMiniutes.visibility = View.INVISIBLE // 해당시간에 운동을 했으면 숨김
                            binding.tvSeconds.visibility = View.INVISIBLE
                            binding.tvTime.visibility = View.INVISIBLE
                             */
                        }
                    }
                }
                else{
                    /*
                    binding.tvMiniutes.visibility = View.INVISIBLE // 그 시간 선택 안해놨거나 선택한 자세가 없으면 숨김
                    binding.tvSeconds.visibility = View.INVISIBLE
                    binding.tvTime.visibility = View.INVISIBLE
                     */
                }
            }
        }
        if(time_day == "목"){
            if (user_days[3]) {
                if (user_timer[time_idx] && user_pose.contains(true)) { // user가 현재 시간을 선택해놨으면 && 선택한 자세가 있으면
                    if(time_hour < time_idx * 2 + 1  ){ // 2시간 단위니까 예를 들어 2~4시면 3시 이전에만 카운트다운이 돌아야함.
                        if(!is_exercise_complete[time_idx]){ // 해당 시간에 운동을 안헀으면
                            is_countDown = true // 카운트다운 on
                            /*
                            binding.tvMiniutes.visibility = View.VISIBLE
                            binding.tvSeconds.visibility = View.VISIBLE
                            binding.tvTime.visibility = View.VISIBLE
                             */
                            start_Timer(time_minutes.toString(), time_seconds.toString()) // 카운트다운 시작
                        }
                        else{
                            /*
                            binding.tvMiniutes.visibility = View.INVISIBLE // 해당시간에 운동을 했으면 숨김
                            binding.tvSeconds.visibility = View.INVISIBLE
                            binding.tvTime.visibility = View.INVISIBLE
                             */
                        }
                    }
                }
                else{
                    /*
                    binding.tvMiniutes.visibility = View.INVISIBLE // 그 시간 선택 안해놨거나 선택한 자세가 없으면 숨김
                    binding.tvSeconds.visibility = View.INVISIBLE
                    binding.tvTime.visibility = View.INVISIBLE
                     */
                }
            }
        }
        if(time_day == "금"){
            if (user_days[4]) {
                if (user_timer[time_idx] && user_pose.contains(true)) { // user가 현재 시간을 선택해놨으면 && 선택한 자세가 있으면
                    if(time_hour < time_idx * 2 + 1  ){ // 2시간 단위니까 예를 들어 2~4시면 3시 이전에만 카운트다운이 돌아야함.
                        if(!is_exercise_complete[time_idx]){ // 해당 시간에 운동을 안헀으면
                            is_countDown = true // 카운트다운 on
                            /*
                    binding.tvMiniutes.visibility = View.INVISIBLE // 그 시간 선택 안해놨거나 선택한 자세가 없으면 숨김
                    binding.tvSeconds.visibility = View.INVISIBLE
                    binding.tvTime.visibility = View.INVISIBLE
                     */
                            start_Timer(time_minutes.toString(), time_seconds.toString()) // 카운트다운 시작
                        }
                        else{
                            /*
                     binding.tvMiniutes.visibility = View.INVISIBLE // 그 시간 선택 안해놨거나 선택한 자세가 없으면 숨김
                     binding.tvSeconds.visibility = View.INVISIBLE
                     binding.tvTime.visibility = View.INVISIBLE
                      */
                        }
                    }
                }
                else{
                    /*
                     binding.tvMiniutes.visibility = View.INVISIBLE // 그 시간 선택 안해놨거나 선택한 자세가 없으면 숨김
                     binding.tvSeconds.visibility = View.INVISIBLE
                     binding.tvTime.visibility = View.INVISIBLE
                      */
                }
            }
        }
        if(time_day == "토") {
            if (user_days[5]) {
                if (user_timer[time_idx] && user_pose.contains(true)) { // user가 현재 시간을 선택해놨으면 && 선택한 자세가 있으면
                    if(time_hour < time_idx * 2 + 1  ){ // 2시간 단위니까 예를 들어 2~4시면 3시 이전에만 카운트다운이 돌아야함.
                        if(!is_exercise_complete[time_idx]){ // 해당 시간에 운동을 안헀으면
                            is_countDown = true // 카운트다운 on
                            /*
                     binding.tvMiniutes.visibility = View.INVISIBLE // 그 시간 선택 안해놨거나 선택한 자세가 없으면 숨김
                     binding.tvSeconds.visibility = View.INVISIBLE
                     binding.tvTime.visibility = View.INVISIBLE
                      */
                            start_Timer(time_minutes.toString(), time_seconds.toString()) // 카운트다운 시작   
                        }
                        else{
                            /*
                    binding.tvMiniutes.visibility = View.INVISIBLE // 그 시간 선택 안해놨거나 선택한 자세가 없으면 숨김
                    binding.tvSeconds.visibility = View.INVISIBLE
                    binding.tvTime.visibility = View.INVISIBLE
                     */
                        }
                    }
                }
                else{
                    /*
                     binding.tvMiniutes.visibility = View.INVISIBLE // 그 시간 선택 안해놨거나 선택한 자세가 없으면 숨김
                     binding.tvSeconds.visibility = View.INVISIBLE
                     binding.tvTime.visibility = View.INVISIBLE
                      */
                }
            }
        }
        if (time_day == "일") {
            if (user_days[6]) {
                if (user_timer[time_idx] && user_pose.contains(true)) { // user가 현재 시간을 선택해놨으면 && 선택한 자세가 있으면
                    if (time_hour < time_idx * 2 + 1) { // 2시간 단위니까 예를 들어 2~4시면 3시 이전에만 카운트다운이 돌아야함.
                        if (!is_exercise_complete[time_idx]) { // 해당 시간에 운동을 안헀으면
                            is_countDown = true // 카운트다운 on
                            /*
                     binding.tvMiniutes.visibility = View.INVISIBLE // 그 시간 선택 안해놨거나 선택한 자세가 없으면 숨김
                     binding.tvSeconds.visibility = View.INVISIBLE
                     binding.tvTime.visibility = View.INVISIBLE
                      */
                            start_Timer(
                                time_minutes.toString(),
                                time_seconds.toString()
                            ) // 카운트다운 시작
                        } else {
                            /*
                     binding.tvMiniutes.visibility = View.INVISIBLE // 그 시간 선택 안해놨거나 선택한 자세가 없으면 숨김
                     binding.tvSeconds.visibility = View.INVISIBLE
                     binding.tvTime.visibility = View.INVISIBLE
                      */
                        }
                    }
                } else {
                    /*
                     binding.tvMiniutes.visibility = View.INVISIBLE // 그 시간 선택 안해놨거나 선택한 자세가 없으면 숨김
                     binding.tvSeconds.visibility = View.INVISIBLE
                     binding.tvTime.visibility = View.INVISIBLE
                      */
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
        binding.btnMedal.setOnClickListener{
            navController.navigate(R.id.action_frag_main_to_medalFragment)
        }
    }

    // 자세선택 버튼
    private fun select_pose(){
        binding.btnSelectPose.setOnClickListener{
            if(is_countDown){
                countDown.cancel()
            }
            navController.navigate(R.id.action_frag_main_to_frag_select_pose)
        }
    }

    // 시간, 요일 선택 버튼
    private fun select_time(){
        binding.btnSetTimer.setOnClickListener{
            if(is_countDown){
                countDown.cancel()
            }
            findNavController().navigate(R.id.action_frag_main_to_selectTimerFragment)
        }
    }

    // 타이머 시작시키기
    private fun start_Timer(t_dateMinute : String, t_dateSeconds : String){
        val startMinutes : Int = 60 - t_dateMinute.toInt() - 1
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
                binding.tvMiniutes.text = "60"
                binding.tvSeconds.text = "00"
                /*
                binding.tvTime.visibility = View.INVISIBLE
                binding.tvMiniutes.visibility = View.INVISIBLE // 카운트다운 종료되면 숨김
                binding.tvSeconds.visibility = View.INVISIBLE
                 */
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
    }


    // 뷰바인딩 destroy
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }



}
