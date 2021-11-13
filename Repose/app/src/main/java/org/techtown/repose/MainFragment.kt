package org.techtown.repose

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.techtown.repose.databinding.FragMainBinding

class MainFragment : Fragment(), IOnbackPressed{
    lateinit var  navController : NavController// 네비게이션 컨트롤러
    private var _binding : FragMainBinding? = null // 뷰바인딩
    private val binding get() = _binding!! // 뷰바인딩

    companion object{
        var vpAdapter: FragmentStateAdapter? = null // pose Adapter
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragMainBinding.inflate(inflater,container,false) // 뷰바인딩
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view) // 네비게이션 컨트롤러 view로 부터 가져오기
        select_pose() // 자세선택 버튼
        connect_viewPager() // 뷰페이저 연결
        countTimer() // 카운트다운 타이머
    }

    // 카운트다운 타이머
    private fun countTimer(){
        binding.btnStartTimer.setOnClickListener{
            val countDown = object : CountDownTimer(1000*120, 1000){
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
                    binding.tvMiniutes.text = "02"
                    binding.tvSeconds.text = "00"
                }
            }.start()
        }
    }

    // 자세선택 버튼
    private fun select_pose(){
        binding.btnSelectPose.setOnClickListener{
            findNavController().navigate(R.id.action_frag_main_to_frag_select_pose)
        }
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
        if(!MainActivity.user.pose_list.isEmpty()){
            for(i in 0 until MainActivity.user.pose_list.size){ // user의 pose_list를 가져와서 newInstance 만들어줌
                fmList.add(PoseFragment.newInstance(MainActivity.pose_list.indexOf(MainActivity.user.pose_list[i]),MainActivity.user.pose_list[i]))
            }
        }
        else{
            fmList.add(EmptyPoseFragment.newInstance())
        }
        vpAdapter = PosePagerAdapter(fmList,this) // 어댑터 설정
        binding.vpPoseViewPager.adapter = vpAdapter // 어댑터 연결
        binding.poseIndicator.setViewPager2(binding.vpPoseViewPager) // 인디케이터랑 어댑터 동기화
    }

    // 뷰바인딩 destroy
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

      // back키
    override fun onBackPressed():Boolean{
          return true
    }

}
