package org.techtown.repose.signIn

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.techtown.repose.*
import org.techtown.repose.Data.AppDatabase
import org.techtown.repose.Data.BeforeParsingUserData
import org.techtown.repose.Data.UserData
import org.techtown.repose.databinding.FragAccountSettingBinding
import retrofit2.Call
import retrofit2.Response
import java.time.LocalDate

class AccountSettingFragment : Fragment() {
    lateinit var viewbinding: FragAccountSettingBinding
    lateinit var navController : NavController// 네비게이션 컨트롤러
    var mc : MainActivity = MainActivity()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        viewbinding = FragAccountSettingBinding.inflate(inflater, container, false)
        return viewbinding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //init
        mc.initRetrofit()
        navController = Navigation.findNavController(view) // 네비게이션 컨트롤러 view로 부터 가져오기
        back_pressed() // 뒤로가기 버튼
        //

        viewbinding.signUpBtn.setOnClickListener{
            val newUserData = setNewUserData()
            val newBeforeParsingUserData = setNewBeforeParsingUserData()
            ApiCallInsertUserData(newBeforeParsingUserData,newUserData,mc)
        }
        viewbinding.btnCheckID.setOnClickListener{
            val newBeforeParsingUserData = setNewBeforeParsingUserData()
            ApiCallExistIdOfUserData(newBeforeParsingUserData,mc)
        }
        //버튼눌렀을 때 network api 사용
        // 뒤로가기 버튼
        viewbinding.btnBack4.setOnClickListener{
            navController.popBackStack()
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
        mc.db = AppDatabase.getInstance(requireContext())!!
        mc.db.userDao().insertUserData(newUserData)
    }

    suspend fun RoomDBGetJoinDateOfUserData(mc: MainActivity):String{
        return mc.db.userDao().getUserData()!!.joinDate
    }

    private suspend fun MoveGuideFragment(){
        CoroutineScope(Dispatchers.Main).launch {
            // room DB 체크해서 넘어갈지 말지
            findNavController().navigate(R.id.action_frag_account_setting_to_guideFragment)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getStringLocalDateNow():String {
        return LocalDate.now().toString()
    }

    private fun ApiCallInsertUserData(beforeParasingUserData: BeforeParsingUserData, newUserData: UserData, mc: MainActivity):Int {
        var responseCode: Int = 0
        mc.supplementService.post_user(beforeParasingUserData).enqueue(object: retrofit2.Callback<BeforeParsingUserData> {
            @RequiresApi(Build.VERSION_CODES.KITKAT)
            override fun onResponse(call: Call<BeforeParsingUserData>, response: Response<BeforeParsingUserData>) {
                if(response.code() == 200){
                    Log.e("server","response 성공!!")
                }
                Log.e("response : ", response.body().toString())
                Log.e("responsecode : ", response.code().toString())
                responseCode = response.code().toInt()

                when(responseCode){
                    200 -> { //유저 생성 성공
                        CoroutineScope(Dispatchers.Main).launch {
                            Toast.makeText(requireContext(),"회원가입 처리 되었습니다!", Toast.LENGTH_SHORT).show()
                        }
                        CoroutineScope(Dispatchers.IO).launch {
                            RoomDBInsertUserData(mc, newUserData)
                            mc.setMedalAlarm(50, requireContext(),0)
                            MainActivity.user_joinData = RoomDBGetJoinDateOfUserData(mc)
                            MoveGuideFragment()
                        }
                    }
                    503 -> { //아이디 중복
                        CoroutineScope(Dispatchers.Main).launch {
                            Toast.makeText(requireContext(),"아이디가 중복되었습니다. 다시 입력해주세요.", Toast.LENGTH_SHORT).show()
                        }
                        viewbinding.idNotRedundancyTextview.visibility = View.INVISIBLE
                        viewbinding.idRedundancyTextview.visibility = View.VISIBLE
                    }
                    else -> { //다른 에러 발생(서버)
                        CoroutineScope(Dispatchers.Main).launch {
                            Toast.makeText(requireContext(),"서버 에러가 발생하였습니다!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            override fun onFailure(call: Call<BeforeParsingUserData>, t: Throwable) {
                Log.e("server","fail...")
                Log.e("server_throwable",t.toString())
                Log.e("server_call",call.toString())
            }
        })

        Log.e("fun code",responseCode.toString())
        return responseCode
    }

    private fun ApiCallExistIdOfUserData(beforeParsingUserData: BeforeParsingUserData, mc: MainActivity):Int {
        var responseCode: Int = 0
        mc.supplementService.id_check(beforeParsingUserData).enqueue(object: retrofit2.Callback<BeforeParsingUserData> {
            override fun onResponse(call: Call<BeforeParsingUserData>, response: Response<BeforeParsingUserData>) {
                if(response.code() == 200){
                    Log.e("server","response 성공!!")
                }
                Log.e("response : ", response.body().toString())
                Log.e("responsecode : ", response.code().toString())
                responseCode = response.code().toInt()

                when(responseCode){
                    200 -> { //유저 생성 성공
                        viewbinding.idNotRedundancyTextview.visibility = View.VISIBLE
                        viewbinding.idRedundancyTextview.visibility = View.INVISIBLE
                    }
                    201 -> { //아이디 중복
                        CoroutineScope(Dispatchers.Main).launch {
                            Toast.makeText(requireContext(),"아이디가 중복되었습니다. 다시 입력해주세요.", Toast.LENGTH_SHORT).show()
                        }
                        viewbinding.idNotRedundancyTextview.visibility = View.INVISIBLE
                        viewbinding.idRedundancyTextview.visibility = View.VISIBLE}
                    else -> { //다른 에러 발생(서버)
                        CoroutineScope(Dispatchers.Main).launch {
                            Toast.makeText(requireContext(),"서버 에러가 발생하였습니다!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            override fun onFailure(call: Call<BeforeParsingUserData>, t: Throwable) {
                Log.e("server","fail...")
                Log.e("server_throwable",t.toString())
                Log.e("server_call",call.toString())
            }
        })

        Log.e("fun code",responseCode.toString())
        return responseCode
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun setNewUserData(): UserData{
        val tmpUserId = viewbinding.idEdittext.text.toString()
        val tmpUserPw = viewbinding.pwEdittext.text.toString()
        val tmpUserEmail = viewbinding.emailEdittext.text.toString()
        val tmpJoinDate = getStringLocalDateNow()
        return UserData(tmpUserId,
            tmpUserPw,
            tmpUserEmail,
            mutableListOf<Boolean>(false,false,false,false,false,false,false,false,false,false,false,false),
            mutableListOf<Boolean>(true,false,false,false,false,false,false,false),
            mutableListOf<Boolean>(false,false,false,false,false,false,false),
            mutableListOf<Boolean>(false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false),
            0,
            false,
            tmpJoinDate
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setNewBeforeParsingUserData(): BeforeParsingUserData{
        val tmpUserId = viewbinding.idEdittext.text.toString()
        val tmpUserPw = viewbinding.pwEdittext.text.toString()
        val tmpUserEmail = viewbinding.emailEdittext.text.toString()
        val tmpJoinDate = getStringLocalDateNow()
        return BeforeParsingUserData(
            tmpUserId,
            tmpUserPw,
            tmpUserEmail,
            "000000000000",
            "10000000",
            "0000000",
            "0000000000000000",
            0,
            0,
            tmpJoinDate
        )
    }


}
