package org.techtown.repose

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.techtown.repose.ExerciseFragment.Companion.exercise_list
import org.techtown.repose.databinding.FragShowExerciseBinding


class ShowExerciseFragment : Fragment(){
    lateinit var  navController : NavController// 네비게이션 컨트롤러
    private var _binding : FragShowExerciseBinding? = null // 뷰바인딩
    private val binding get() = _binding!! // 뷰바인딩

    companion object{
        var vpAdapter: FragmentStateAdapter? = null // exercise viewpager Adapter
        var pose_name : String? = ""
        val exercise_name_list = listOf(listOf<String>("틈틈이 골반운동!","자기 전에 골반운동!", "이제 거의 다 왔어요!"), // 다리꼬기
            listOf<String>("어깨가 잠시 아플 때!"), // 한 쪽으로 짐 들기
            listOf<String>("너무 오래 앉아 있었나요?","침대에서 잠깐 허리 운동!"), // 장시간 앉아 있기
            listOf<String>("바쁘시겠지만 잠깐 운동 하나 합시다!","일하러 가기 전에 하나만 더!"), // 장시간 서 있기
            listOf<String>("손목 한 번 꺾어볼까요!","눈이 피로하다면 지금입니다!", "건강해진다면 통닭 포즈 쯤이야.."), // 장시간 전자기기 사용
            listOf<String>("눈이 피로하다면 지금입니다!","건강해진다면 통닭 포즈쯤이야.."), // 장시간 독서
            listOf<String>("손목 한 번 꺾어볼까요!","눈이 피로하다면 지금입니다!", "건강해진다면 통닭 포즈 쯤이야.."), // 장시간 필기
            listOf<String>("눈이 피로하다면 지금입니다!", "건강해진다면 통닭 포즈 쯤이야.."), // 장시간 운전
            listOf<String>("틈틈이 골반운동!","자기 전에 골반운동!", "이제 거의 다 왔어요!"), // 팔자걸음
            listOf<String>("틈틈이 골반운동!","자기 전에 골반운동!", "이제 거의 다 왔어요!"), // 안짱걸음
            listOf<String>("틈틈이 골반운동!","자기 전에 골반운동!"), // 양반다리
            listOf<String>("틈틈이 골바운동!","자기 전에 골바운동!", "이제 거의 다 왔어요!"), // 엎드려자기
        )
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
        back_pressed() // 뒤로가기 버튼
        btn_back() // 화살표 뒤로가기
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
        for(i in exercise_list[idx].indices){ // ll --> 선택된 pose들의 운동들을 담고있는 2차원 list
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

    fun back_pressed(){
        requireActivity().onBackPressedDispatcher.addCallback(object :
            OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                navController.popBackStack()
            }
        })
    }

    fun btn_back(){
        binding.btnBack9.setOnClickListener{
            navController.popBackStack()
        }
    }
}
