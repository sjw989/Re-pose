package org.techtown.repose

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.techtown.repose.databinding.FragShowExerciseBinding


class ShowExerciseFragment : Fragment(), IOnbackPressed {
    lateinit var  navController : NavController// 네비게이션 컨트롤러
    private var _binding : FragShowExerciseBinding? = null // 뷰바인딩
    private val binding get() = _binding!! // 뷰바인딩

    companion object{
        var vpAdapter: FragmentStateAdapter? = null // exercise viewpager Adapter
        var pose_name : String? = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let{
            pose_name = it.getString("pose_name")
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragShowExerciseBinding.inflate(inflater,container,false) // 뷰바인딩
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view) // 네비게이션 컨트롤러 view로 부터 가져오기
        set_pose_name() // 운동을 보여줄 자세 이름을 PoseFragment로부터 받아옴
        connect_viewPager() // 뷰페이저 연결
    }


    // Exercise 뷰페이저 어댑터
    class ExercisePagerAdapter(private val fmList:ArrayList<Fragment>, fm: ShowExerciseFragment): FragmentStateAdapter(fm){
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
        val idx = MainActivity.pose_list.indexOf(pose_name)// 선택된 pose가 몇번째 pose인지
        val fmList = arrayListOf<Fragment>()
        for(i in MainActivity.exercise_list[idx].indices){ // ll --> 선택된 pose들의 운동들을 담고있는 2차원 list
            pose_name?.let { ExerciseFragment.newInstance(it,i) }?.let { fmList.add(it) }
        }
        vpAdapter = ExercisePagerAdapter(fmList,this) // 어댑터 설정
        binding.vpExerciseViewPager.adapter = vpAdapter // 어댑터 연결
        binding.exerciseIndicator.setViewPager2(binding.vpExerciseViewPager) // 인디케이터랑 어댑터 동기화
    }




    // 자세이름 set
    fun set_pose_name() {
        binding.tvExercisePoseName.text = pose_name
    }

    //뒤로가기버튼
    override fun onBackPressed():Boolean{
        return true
    }
}
