package org.techtown.repose.signIn

import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.frag_start.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.techtown.repose.*
import org.techtown.repose.databinding.FragStartBinding


class StartFragment : Fragment() {
    private var _binding : FragStartBinding? = null // 뷰바인딩
    private val binding get() = _binding!! // 뷰바인딩
    private var exit_time : Long = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragStartBinding.inflate(inflater,container,false) // 뷰바인딩
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var mc : MainActivity = MainActivity()
//        val db : AppDatabase? = AppDatabase.getInstance(requireContext())
        mc.db = AppDatabase.getInstance(requireContext())!!
        lateinit var dblist:List<UserData>
//        val newUserData = UserData(124,"chae","kijung")
//        mc.db.userDao().insertUserData(newUserData)

        CoroutineScope(Dispatchers.IO).launch{
            // room DB 체크해서 넘어갈지 말지
            dblist = mc.db.userDao().getAll()
            Log.e("dblist",dblist.toString())
        }

//        if(dblist.isEmpty()){
//            findNavController().navigate(R.id.action_frag_start_to_frag_login)
//        }
        init() // 애니메이션 효과
        back_pressed() // 뒤로가기 버튼 금지

        login_btn.setOnClickListener{
            findNavController().navigate(R.id.action_frag_start_to_frag_login)
        }
        sign_up_btn.setOnClickListener{
            findNavController().navigate(R.id.action_frag_start_to_frag_account_setting)
        }
    }

    private fun init(){ // 앱 시작할때 애니메이션 효과
        var fadeIn = ObjectAnimator.ofFloat(binding.loginBtn, "alpha", 0f, 1f)
        fadeIn.duration = 1500
        fadeIn.start()

        fadeIn = ObjectAnimator.ofFloat(binding.messageText, "alpha", 0f, 1f)
        fadeIn.duration = 1500
        fadeIn.start()

        fadeIn = ObjectAnimator.ofFloat(binding.signUpBtn, "alpha", 0f, 1f)
        fadeIn.duration = 1500
        fadeIn.start()
    }

    fun back_pressed(){
        requireActivity().onBackPressedDispatcher.addCallback(object :
            OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                var cur = System.currentTimeMillis()
                println((cur - exit_time).toString())
                if(cur - exit_time < 2000){
                    if(MainFragment.is_countDown){
                        MainFragment.countDown.cancel()
                    }
                    activity?.finish()
                }
                else{
                    exit_time = cur
                    Toast.makeText(context, "뒤로가기를 한 번 더 누르면 종료됩니다", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    // 뷰바인딩 destroy
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}




