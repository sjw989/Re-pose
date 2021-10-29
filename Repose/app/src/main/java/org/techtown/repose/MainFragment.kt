package org.techtown.repose

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.techtown.repose.databinding.FragMainBinding

class MainFragment : Fragment() {
    lateinit var  navController : NavController// 네비게이션 컨트롤러
    //    private val ft = childFragmentManager.beginTransaction()
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
    }

    // 자세선택 버튼
    fun select_pose(){
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
    fun connect_viewPager(){
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

}