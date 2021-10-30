package org.techtown.repose

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import org.techtown.repose.databinding.FragDiseaseBinding
import org.techtown.repose.databinding.FragPoseBinding


class DiseaseFragment : Fragment(), IOnbackPressed {
    lateinit var  navController : NavController// 네비게이션 컨트롤러
    private var _binding : FragDiseaseBinding? = null // 뷰바인딩
    private val binding get() = _binding!! // 뷰바인딩

    private var pose_name: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pose_name = arguments?.getString("pose")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragDiseaseBinding.inflate(inflater,container,false) // 뷰바인딩
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navController = Navigation.findNavController(view) // 네비게이션 컨트롤러 view로 부터 가져오기
        binding.tvPoseName.text = pose_name // 선택된 자세 이름 view에 띄우기
        binding.btnGoExercise.setOnClickListener(){
            navController.previousBackStackEntry?.savedStateHandle?.set("pose", pose_name) // 자세가 선택되었음을 return 해줌
            navController.popBackStack()
        }
    }

    // 뷰바인딩 destroy
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    // 뒤로가기 버튼
    override fun onBackPressed():Boolean{
        navController.popBackStack()
        return true
    }
}