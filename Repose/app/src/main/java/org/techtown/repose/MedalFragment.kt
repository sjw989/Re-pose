package org.techtown.repose

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.NavController
import androidx.navigation.Navigation
import org.techtown.repose.MainActivity.Companion.user_medal
import org.techtown.repose.databinding.FragMedalBinding
import org.techtown.repose.databinding.FragSelectPoseBinding


class MedalFragment : Fragment() {
    lateinit var  navController : NavController// 네비게이션 컨트롤러
    private var _binding : FragMedalBinding? = null // 뷰바인딩
    private val binding get() = _binding!! // 뷰바인딩


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragMedalBinding.inflate(inflater,container,false) // 뷰바인딩
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view) // 네비게이션 컨트롤러 view로 부터 가져오기
        back_pressed() // 뒤로가기 버튼
        set_medal() // 메달 설정
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
    
    // 메달 설정
    fun set_medal(){
        if(user_medal[0]){
            binding.ivMedal1.setImageResource(R.drawable.pose1_on)
        }
        if(user_medal[1]){
            binding.ivMedal2.setImageResource(R.drawable.pose1_on)
        }
        if(user_medal[2]){
            binding.ivMedal3.setImageResource(R.drawable.pose1_on)
        }
        if(user_medal[3]){
            binding.ivMedal4.setImageResource(R.drawable.pose1_on)
        }
        if(user_medal[4]){
            binding.ivMedal5.setImageResource(R.drawable.pose1_on)
        }
        if(user_medal[5]){
            binding.ivMedal6.setImageResource(R.drawable.pose1_on)
        }
        if(user_medal[6]){
            binding.ivMedal7.setImageResource(R.drawable.pose1_on)
        }
        if(user_medal[7]){
            binding.ivMedal8.setImageResource(R.drawable.pose1_on)
        }
        
    }


}