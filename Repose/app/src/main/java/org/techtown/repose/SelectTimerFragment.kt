package org.techtown.repose

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.activity.OnBackPressedCallback
import androidx.navigation.NavController
import androidx.navigation.Navigation
import org.techtown.repose.MainActivity.Companion.user_days
import org.techtown.repose.MainActivity.Companion.user_timer
import org.techtown.repose.databinding.FragSelectTimerBinding


class SelectTimerFragment : Fragment(){
    lateinit var navController: NavController// 네비게이션 컨트롤러
    private var _binding: FragSelectTimerBinding? = null // 뷰바인딩
    private val binding get() = _binding!! // 뷰바인딩

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragSelectTimerBinding.inflate(inflater,container,false) // 뷰바인딩
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view) // 네비게이션 컨트롤러 view로 부터 가져오기
        set_init() // 초기 버튼 설정
        set_days() // 요일 설정
        set_time() // 시간 설정
        back_pressed() // 뒤로가기 버튼
    }
    fun set_init(){
        if(user_days[0]){
            binding.btnMonday.setBackgroundColor(Color.parseColor("#FF6BBAFA"))
        }
        if(user_days[1]){
            binding.btnTuesday.setBackgroundColor(Color.parseColor("#FF6BBAFA"))
        }
        if(user_days[2]){
            binding.btnWednesday.setBackgroundColor(Color.parseColor("#FF6BBAFA"))
        }
        if(user_days[3]){
            binding.btnThursday.setBackgroundColor(Color.parseColor("#FF6BBAFA"))
        }
        if(user_days[4]){
            binding.btnFriday.setBackgroundColor(Color.parseColor("#FF6BBAFA"))
        }
        if(user_days[5]){
            binding.btnSaturday.setBackgroundColor(Color.parseColor("#FF6BBAFA"))
        }
        if(user_days[6]){
            binding.btnSunday.setBackgroundColor(Color.parseColor("#FF6BBAFA"))
        }
        if(user_timer[0]){
            binding.switch1.isChecked = true
        }
        if(user_timer[1]){
            binding.switch2.isChecked = true
        }
        if(user_timer[2]){
            binding.switch3.isChecked = true
        }
        if(user_timer[3]){
            binding.switch4.isChecked = true
        }
        if(user_timer[4]){
            binding.switch5.isChecked = true
        }
        if(user_timer[5]){
            binding.switch6.isChecked = true
        }
        if(user_timer[6]){
            binding.switch7.isChecked = true
        }
        if(user_timer[7]){
            binding.switch8.isChecked = true
        }
        if(user_timer[8]){
            binding.switch9.isChecked = true
        }
        if(user_timer[9]){
            binding.switch10.isChecked = true
        }
        if(user_timer[10]){
            binding.switch11.isChecked = true
        }
        if(user_timer[11]){
            binding.switch12.isChecked = true
        }
    }
    fun set_days(){
        binding.btnMonday.setOnClickListener{
            if(!user_days[0]){
                binding.btnMonday.setBackgroundColor(Color.parseColor("#FF6BBAFA"))
                user_days[0] = true
            }
            else{
                user_days[0] = false
                binding.btnMonday.setBackgroundColor(Color.parseColor("#FF636363"))
            }
        }

        binding.btnTuesday.setOnClickListener{
            if(!user_days[1]){
                binding.btnTuesday.setBackgroundColor(Color.parseColor("#FF6BBAFA"))
                user_days[1] = true
            }
            else{
                binding.btnTuesday.setBackgroundColor(Color.parseColor("#FF636363"))
                user_days[1] = false
            }
        }

        binding.btnWednesday.setOnClickListener{
            if(!user_days[2]){
                binding.btnWednesday.setBackgroundColor(Color.parseColor("#FF6BBAFA"))
                user_days[2] = true
            }
            else{
                user_days[2] = false
                binding.btnWednesday.setBackgroundColor(Color.parseColor("#FF636363"))
            }
        }
        binding.btnThursday.setOnClickListener{
            if(!user_days[3]){
                binding.btnThursday.setBackgroundColor(Color.parseColor("#FF6BBAFA"))
                user_days[3] = true
            }
            else{
                user_days[3] = false
                binding.btnThursday.setBackgroundColor(Color.parseColor("#FF636363"))
            }
        }
        binding.btnFriday.setOnClickListener{
            if(!user_days[4]){
                binding.btnFriday.setBackgroundColor(Color.parseColor("#FF6BBAFA"))
                user_days[4] = true
            }
            else{
                user_days[4] = false
                binding.btnFriday.setBackgroundColor(Color.parseColor("#FF636363"))
            }
        }
        binding.btnSaturday.setOnClickListener{
            if(!user_days[5]){
                binding.btnSaturday.setBackgroundColor(Color.parseColor("#FF6BBAFA"))
                user_days[5] = true
            }
            else{
                user_days[5] = false
                binding.btnSaturday.setBackgroundColor(Color.parseColor("#FF636363"))
            }
        }

        binding.btnSunday.setOnClickListener{
            if(!user_days[6]){
                binding.btnSunday.setBackgroundColor(Color.parseColor("#FF6BBAFA"))
                user_days[6] = true
            }
            else{
                user_days[6] = false
                binding.btnSunday.setBackgroundColor(Color.parseColor("#FF636363"))
            }
        }
    }
    fun set_time(){
        binding.switch1.setOnCheckedChangeListener(timeSwitch_Listener(0))
        binding.switch2.setOnCheckedChangeListener(timeSwitch_Listener(1))
        binding.switch3.setOnCheckedChangeListener(timeSwitch_Listener(2))
        binding.switch4.setOnCheckedChangeListener(timeSwitch_Listener(3))
        binding.switch5.setOnCheckedChangeListener(timeSwitch_Listener(4))
        binding.switch6.setOnCheckedChangeListener(timeSwitch_Listener(5))
        binding.switch7.setOnCheckedChangeListener(timeSwitch_Listener(6))
        binding.switch8.setOnCheckedChangeListener(timeSwitch_Listener(7))
        binding.switch9.setOnCheckedChangeListener(timeSwitch_Listener(8))
        binding.switch10.setOnCheckedChangeListener(timeSwitch_Listener(9))
        binding.switch11.setOnCheckedChangeListener(timeSwitch_Listener(10))
        binding.switch12.setOnCheckedChangeListener(timeSwitch_Listener(11))
    }


    fun back_pressed(){
        requireActivity().onBackPressedDispatcher.addCallback(object :
            OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                navController.popBackStack()
            }
        })
    }


}
class timeSwitch_Listener : CompoundButton.OnCheckedChangeListener{
    var idx : Int = 0
    constructor(idx : Int){
        this.idx = idx
    }
    override fun onCheckedChanged(btn : CompoundButton?, isChecked: Boolean) {
        user_timer[idx] = isChecked
    }

}