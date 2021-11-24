package org.techtown.repose.signIn

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import org.techtown.repose.R
import org.techtown.repose.databinding.FragAccountSettingBinding

class AccountSettingFragment : Fragment() {
    lateinit var viewbinding: FragAccountSettingBinding
    lateinit var  navController : NavController// 네비게이션 컨트롤러

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        viewbinding = FragAccountSettingBinding.inflate(inflater, container, false)
        return viewbinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        back_pressed() // 뒤로가기 버튼
        //버튼눌렀을 때 network api 사용
        navController = Navigation.findNavController(view) // 네비게이션 컨트롤러 view로 부터 가져오기

        viewbinding.signUpBtn.setOnClickListener{
            navController.navigate(R.id.action_frag_account_setting_to_guideFragment)
        }

    }

    fun back_pressed(){
        requireActivity().onBackPressedDispatcher.addCallback(object :
            OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        })
    }
}
