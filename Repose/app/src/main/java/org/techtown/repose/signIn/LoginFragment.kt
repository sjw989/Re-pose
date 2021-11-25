package org.techtown.repose.signIn

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import kotlinx.android.synthetic.main.frag_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.techtown.repose.*
import org.techtown.repose.databinding.FragFindSuccessBinding
import org.techtown.repose.databinding.FragLoginBinding


class LoginFragment : Fragment() {
    lateinit var viewbinding:FragLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewbinding = FragLoginBinding.inflate(inflater, container, false)
        return viewbinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var mc : MainActivity = MainActivity()

        //외부 노드서버랑 연동하기 전 임시 데이터
        val tmpUserId = viewbinding.idEdittext.text.toString()
        val tmpUserPw = viewbinding.pwEdittext.text.toString()
        val tmpUserEmail = "testEmail"
        val tmpJoinDate = "testLocalDate"
        val newUserData = UserData(tmpUserId,
            tmpUserPw,
            tmpUserEmail,
            mutableListOf<Boolean>(false,false,false,false,false,false,false,false,false,false,false,false),
            mutableListOf<Boolean>(false,false,false,false,false,false,false),
            mutableListOf<Boolean>(false,false,false,false,false,false,false,false),
            mutableListOf<Boolean>(false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false),
            0,
            false,
            tmpJoinDate
        )

        mc.db = AppDatabase.getInstance(requireContext())!!
        back_pressed() // 뒤로가기 버튼
        login_btn.setOnClickListener{
            //TO DO: 서버랑 아이디 비밀번호 일치하는지 체크
            //아이디 비밀번호 일치시, 룸디비에 데이터 넣기
            CoroutineScope(Dispatchers.IO).launch {
                // room DB 체크해서 넘어갈지 말지
                RoomDBInsertUserData(mc, newUserData)
                MoveMainFragment()
            }
        }
//        login_btn.setOnClickListener{
//            //로그인을 했을 때 서버에서 아이디랑 비밀번호 일치여부 -> 가져와서 룸DB에 저장한 뒤 main fragment로 이동
//            findNavController().navigate(R.id.action_frag_login_to_fragment_retrofit_test)
//        }
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

    suspend fun RoomDBInsertUserData(mc: MainActivity, newUserData: UserData){
        mc.db.userDao().insertUserData(newUserData)
    }

    private suspend fun MoveMainFragment(){
        CoroutineScope(Dispatchers.Main).launch {
            // room DB 체크해서 넘어갈지 말지
            findNavController().navigate(R.id.action_frag_login_to_frag_main)
        }
    }
}