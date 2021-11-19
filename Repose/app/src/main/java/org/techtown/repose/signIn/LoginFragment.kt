package org.techtown.repose.signIn

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.frag_login.*
import org.techtown.repose.MainFragment
import org.techtown.repose.R


class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frag_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        back_pressed() // 뒤로가기 버튼
        login_btn.setOnClickListener{
            //로그인을 했을 때 서버에서 아이디랑 비밀번호 일치여부 -> 가져와서 룸DB에 저장한 뒤 main fragment로 이동
            findNavController().navigate(R.id.action_frag_login_to_frag_main)
        }
        find_id_pw_btn.setOnClickListener{
            findNavController().navigate(R.id.action_frag_login_to_frag_find_id)
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