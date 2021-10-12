package org.techtown.repose


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import org.techtown.repose.databinding.FragSelectPoseBinding

class SelectPoseFragment : Fragment(), IOnbackPressed {
    private var _binding : FragSelectPoseBinding? = null // 뷰바인딩
    private val binding get() = _binding!! // 뷰바인딩

    companion object{
        var is_click:Boolean = false
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragSelectPoseBinding.inflate(inflater,container,false) // 뷰바인딩
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInit() // 초기 버튼 상태 설정
        setButton() // 버튼으로 자세 선택

    }
    // 버튼으로 자세 선택
    fun setButton(){
        binding.SelectBtnPose1.setOnClickListener(){
            if(MainActivity.user.pose_list.contains("자세1")){
                MainActivity.user.pose_list.remove("자세1")
                binding.SelectBtnPose1.setImageResource(R.drawable.pose1_off)

            }
            else if(!MainActivity.user.pose_list.contains("자세1")){
                (context as MainActivity).setfrag(3)
                if(is_click){
                    MainActivity.user.pose_list.add("자세1")
                    binding.SelectBtnPose1.setImageResource(R.drawable.pose1_on)
                    is_click = false
                }
            }
        }
        binding.SelectBtnPose2.setOnClickListener(){
            if(MainActivity.user.pose_list.contains("자세2")){
                MainActivity.user.pose_list.remove("자세2")
                binding.SelectBtnPose2.setImageResource(R.drawable.pose2_off)
            }
            else if(!MainActivity.user.pose_list.contains("자세2")){
                (context as MainActivity).setfrag(3)
                if(is_click){
                    MainActivity.user.pose_list.add("자세2")
                    binding.SelectBtnPose2.setImageResource(R.drawable.pose2_on)
                    is_click = false
                }
            }
        }
        binding.SelectBtnPose3.setOnClickListener(){
            if(MainActivity.user.pose_list.contains("자세3")){
                MainActivity.user.pose_list.remove("자세3")
                binding.SelectBtnPose3.setImageResource(R.drawable.pose3_off)
            }
            else if(!MainActivity.user.pose_list.contains("자세3")){
                (context as MainActivity).setfrag(3)
                if(is_click){
                    MainActivity.user.pose_list.add("자세3")
                    binding.SelectBtnPose3.setImageResource(R.drawable.pose3_on)
                    is_click = false
                }
            }
        }
    }

    // 초기 버튼 상태
    fun setInit(){
        if(MainActivity.user.pose_list.contains("자세1")){
            binding.SelectBtnPose1.setImageResource(R.drawable.pose1_on)
        }
        else{
            binding.SelectBtnPose1.setImageResource(R.drawable.pose1_off)
        }
        if(MainActivity.user.pose_list.contains("자세2")){
            binding.SelectBtnPose2.setImageResource(R.drawable.pose2_on)
        }
        else{
            binding.SelectBtnPose2.setImageResource(R.drawable.pose2_off)
        }
        if(MainActivity.user.pose_list.contains("자세3")){
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

    // back키
    override fun onBackPressed():Boolean{
        return true
    }
}