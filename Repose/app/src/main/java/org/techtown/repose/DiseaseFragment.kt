package org.techtown.repose

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.OnBackPressedCallback
import androidx.core.view.marginBottom
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.techtown.repose.databinding.FragDiseaseBinding


class DiseaseFragment : Fragment(){
    lateinit var mainActivity : MainActivity // MainActivity context 저장할 변수
    lateinit var  navController : NavController// 네비게이션 컨트롤러
    private var _binding : FragDiseaseBinding? = null // 뷰바인딩
    private val binding get() = _binding!! // 뷰바인딩

    private val disease_list = listOf(listOf<String>("disease2","disease11","disease13"), // pose1
                                      listOf<String>("disease10","disease14"), // pose2
                                      listOf<String>("disease14","disease11"), // pose3
                                      listOf<String>("disease7","disease6","disease13"), // pose4
                                      listOf<String>("disease4","disease1","disease8"), // pose5
                                      listOf<String>("disease4","disease1"), // pose6
                                      listOf<String>("disease4","disease1","disease8"), // pose7
                                      listOf<String>("disease4","disease1","disease14"), // pose8
                                      listOf<String>("disease6","disease14"), // pose9
                                      listOf<String>("disease6","disease14"), // pose10
                                      listOf<String>("disease3","disease12"), // pose11
                                      listOf<String>("disease9","disease12","disease5"), // pose12
                                    )
    private var pose_name: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pose_name = arguments?.getString("pose")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is MainActivity) mainActivity = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragDiseaseBinding.inflate(inflater,container,false) // 뷰바인딩
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navController = Navigation.findNavController(view) // 네비게이션 컨트롤러 view로 부터 가져오기
        btn_back() // 화살표 뒤로가기
        back_pressed() // 뒤로가기 버튼
        select_pose() // 자세를 선택한 경우
        set_recyclerViewAdapter(view) // 질병리스트 리사이클러뷰 연결
        
    }

    // 뷰바인딩 destroy
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
    fun set_recyclerViewAdapter(view : View){
        val layoutManager = LinearLayoutManager(context)
        binding.rcvDiseaseList.setLayoutManager(layoutManager)
        val idx = MainActivity.pose_list.indexOf(pose_name)
        val adapter = DiseaseRecyclerViewAdapter(mainActivity, disease_list[idx], view, pose_name)
        binding.rcvDiseaseList.adapter=adapter
    }

    fun select_pose(){
        binding.btnGoExercise.setOnClickListener(){
            navController.previousBackStackEntry?.savedStateHandle?.set("pose", pose_name) // 자세가 선택되었음을 return 해줌
            navController.popBackStack()
        }
    }
    // 뒤로가기 버튼
    fun back_pressed(){
        requireActivity().onBackPressedDispatcher.addCallback(object :
            OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                navController.popBackStack()
            }
        })
    }

    fun btn_back(){
        binding.btnBack8.setOnClickListener{
            navController.popBackStack()
        }
    }
}