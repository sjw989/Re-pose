package org.techtown.repose

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.activity.OnBackPressedCallback
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.dialog_medal_condition.*
import kotlinx.android.synthetic.main.dialog_medal_condition.view.*
import org.techtown.repose.MainActivity.Companion.user_medal
import org.techtown.repose.databinding.FragMedalBinding
import org.techtown.repose.databinding.FragSelectPoseBinding


class MedalFragment : Fragment() {
    lateinit var  navController : NavController// 네비게이션 컨트롤러
    private var _binding : FragMedalBinding? = null // 뷰바인딩
    private val binding get() = _binding!! // 뷰바인딩

    private val medal_list : List<String> = listOf("이런, 성한\n곳이 없군!", "건강해지는 소리가\n들리나요?","난 아직도 쌩쌩해!", "이제 시작이야",
                                                    "깃털 같은 몸", "균형 잡힌 몸", "난 완벽해", "든든한 동반자")
    private val medal_condition : List<String> = listOf("XXXX-XX-XX부터\n리포즈와 함께하셨습니다.",
                                                        "리포즈와 함께하신 지\n 100일이 지났습니다.",
                                                        "리포즈와 함께하신 지\n 300일이 지났습니다.",
                                                        "자세 교정 후, \n완료 버튼을 일정 시간 내 \n처음 클릭하셨습니다.",
                                                        "자세 교정 후, \n완료 버튼을 일정 시간 내 \n50회 클릭하셨습니다.",
                                                        "자세 교정 후, \n완료 버튼을 일정 시간 내 \n300회 클릭하셨습니다.",
                                                        "자세 교정 후, \n완료 버튼을 일정 시간 내 \n1000회 클릭하셨습니다.",
                                                        "리포즈의 Pro ver.을 구매했습니다.")


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
        show_condition(); // 메달 조건 보여주기
        btn_back() // 뒤로가기 화살표 버튼
    }
    fun btn_back(){
        binding.btnBack3.setOnClickListener{
            navController.popBackStack()
        }
    }

    fun make_medal_dialog(medal_name : String, medal_condition : String, image : Int){
        val layoutInflater = LayoutInflater.from(context)
        val view  = layoutInflater.inflate(R.layout.dialog_medal_condition,null)

        val alertDialog = AlertDialog.Builder(context).setView(view).create()
        // 터치로 끄는거 잠금 start
        alertDialog.setCanceledOnTouchOutside(false)
        alertDialog.setCancelable(false)
        // end

        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) // 배경 투명하게
        alertDialog.show() // 우선 dialog를 띄우고 text를 설정해야함
        alertDialog.tv_medalName.text = medal_name // 메달이름 설정
        alertDialog.tv_medal_condition.text = medal_condition // 메달 획득조건 설정
        alertDialog.iv_medal_condition.setImageResource(image) // 메달 이미지 설정


        alertDialog.btn_okay.setOnClickListener{
            alertDialog.dismiss()
        }
    }
    fun show_condition( ){
        binding.ivMedal1.setOnClickListener{
            make_medal_dialog(medal_list[0], medal_condition[0],R.drawable.medal1_on)
        }
        binding.ivMedal2.setOnClickListener{
            make_medal_dialog(medal_list[1], medal_condition[1],R.drawable.medal2_on)
        }
        binding.ivMedal3.setOnClickListener{
            make_medal_dialog(medal_list[2], medal_condition[2],R.drawable.medal3_on)
        }
        binding.ivMedal4.setOnClickListener{
            make_medal_dialog(medal_list[3], medal_condition[3],R.drawable.medal4_on)
        }
        binding.ivMedal5.setOnClickListener{
            make_medal_dialog(medal_list[4], medal_condition[4],R.drawable.medal5_on)
        }
        binding.ivMedal6.setOnClickListener{
            make_medal_dialog(medal_list[5], medal_condition[5],R.drawable.medal6_on)
        }
        binding.ivMedal7.setOnClickListener{
            make_medal_dialog(medal_list[6], medal_condition[6],R.drawable.medal7_on)
        }
        binding.ivMedal8.setOnClickListener{
            make_medal_dialog(medal_list[7], medal_condition[7],R.drawable.medal8_on)
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
    
    // 메달 설정
    fun set_medal(){
        if(user_medal[0]){
            binding.ivMedal1.setImageResource(R.drawable.medal1_on)
        }
        if(user_medal[1]){
            binding.ivMedal2.setImageResource(R.drawable.medal2_on)
        }
        if(user_medal[2]){
            binding.ivMedal3.setImageResource(R.drawable.medal3_on)
        }
        if(user_medal[3]){
            binding.ivMedal4.setImageResource(R.drawable.medal4_on)
        }
        if(user_medal[4]){
            binding.ivMedal5.setImageResource(R.drawable.medal5_on)
        }
        if(user_medal[5]){
            binding.ivMedal6.setImageResource(R.drawable.medal6_on)
        }
        if(user_medal[6]){
            binding.ivMedal7.setImageResource(R.drawable.medal7_on)
        }
        if(user_medal[7]){
            binding.ivMedal8.setImageResource(R.drawable.medal8_on)
        }
    }

    // 뷰바인딩 destroy
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }


}