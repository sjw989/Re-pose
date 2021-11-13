package org.techtown.repose


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController

import org.techtown.repose.databinding.FragSelectPoseBinding

class SelectPoseFragment : Fragment(){
    lateinit var  navController : NavController// 네비게이션 컨트롤러
    private var _binding : FragSelectPoseBinding? = null // 뷰바인딩
    private val binding get() = _binding!! // 뷰바인딩


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragSelectPoseBinding.inflate(inflater,container,false) // 뷰바인딩
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view) // 네비게이션 컨트롤러 view로 부터 가져오기
        setInit() // 초기 버튼 상태 설정
        setButton() // 버튼으로 자세 선택
        selected() // 사용자가 Disease화면에서 자세를 선택한 경우 처리
        back_pressed() // 뒤로가기 버튼
    }

    // Disease화면에서 자세가 선택버튼을 누른경우 처리
    fun selected(){
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<String>("pose")?.observe(viewLifecycleOwner){
            MainActivity.user.pose_list.add(it)
            when(it){
                "다리꼬기"->binding.SelectBtnPose1.setImageResource(R.drawable.pose1_on)
                "양반다리"->binding.SelectBtnPose2.setImageResource(R.drawable.pose2_on)
                "한 쪽으로 짐 들기"->binding.SelectBtnPose3.setImageResource(R.drawable.pose3_on)
            }
        }
    }

    // 버튼으로 자세 선택
    fun setButton(){
        binding.SelectBtnPose1.setOnClickListener(){
            if(MainActivity.user.pose_list.contains("다리꼬기")){
                MainActivity.user.pose_list.remove("다리꼬기")
                binding.SelectBtnPose1.setImageResource(R.drawable.pose1_off)
            }
            else if(!MainActivity.user.pose_list.contains("다리꼬기")){
                val bundle = Bundle()
                bundle.putString("pose","다리꼬기")
                findNavController().navigate(R.id.action_frag_select_pose_to_frag_disease,bundle)
            }
        }
        binding.SelectBtnPose2.setOnClickListener(){
            if(MainActivity.user.pose_list.contains("양반다리")){
                MainActivity.user.pose_list.remove("양반다리")
                binding.SelectBtnPose2.setImageResource(R.drawable.pose2_off)
            }
            else if(!MainActivity.user.pose_list.contains("양반다리")){
                val bundle = Bundle()
                bundle.putString("pose","양반다리")
                findNavController().navigate(R.id.action_frag_select_pose_to_frag_disease,bundle)
            }
        }
        binding.SelectBtnPose3.setOnClickListener(){
            if(MainActivity.user.pose_list.contains("한 쪽으로 짐 들기")){
                MainActivity.user.pose_list.remove("한 쪽으로 짐 들기")
                binding.SelectBtnPose3.setImageResource(R.drawable.pose3_off)
            }
            else if(!MainActivity.user.pose_list.contains("한 쪽으로 짐 들기")){
                val bundle = Bundle()
                bundle.putString("pose","한 쪽으로 짐 들기")
                findNavController().navigate(R.id.action_frag_select_pose_to_frag_disease,bundle)
            }
        }
    }

    // 초기 버튼 상태
    fun setInit(){
        if(MainActivity.user.pose_list.contains("다리꼬기")){
            binding.SelectBtnPose1.setImageResource(R.drawable.pose1_on)
        }
        else{
            binding.SelectBtnPose1.setImageResource(R.drawable.pose1_off)
        }
        if(MainActivity.user.pose_list.contains("양반다리")){
            binding.SelectBtnPose2.setImageResource(R.drawable.pose2_on)
        }
        else{
            binding.SelectBtnPose2.setImageResource(R.drawable.pose2_off)
        }
        if(MainActivity.user.pose_list.contains("한 쪽으로 짐 들기")){
            binding.SelectBtnPose3.setImageResource(R.drawable.pose3_on)
        }
        else{
            binding.SelectBtnPose3.setImageResource(R.drawable.pose3_off)
        }
    }

    // 뷰바인딩 destroy
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
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
}