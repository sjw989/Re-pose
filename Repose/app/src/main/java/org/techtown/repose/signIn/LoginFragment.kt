package org.techtown.repose.signIn

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.frag_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.techtown.repose.*
import org.techtown.repose.Data.AppDatabase
import org.techtown.repose.Data.BeforeParsingUserData
import org.techtown.repose.Data.LoginUserData
import org.techtown.repose.Data.UserData
import org.techtown.repose.databinding.FragLoginBinding
import retrofit2.Call
import retrofit2.Response

class LoginFragment : Fragment() {
    lateinit var viewbinding:FragLoginBinding
    var mc : MainActivity = MainActivity()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewbinding = FragLoginBinding.inflate(inflater, container, false)
        return viewbinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mc.initRetrofit()
        mc.db = AppDatabase.getInstance(requireContext())!!

        back_pressed() // 뒤로가기 버튼
        btn_back() // 뒤로가기 화살표 버튼

//지우님 클릭리스너
//        viewbinding.loginBtn.setOnClickListener{
//            findNavController().navigate(R.id.action_frag_login_to_frag_main)
//        }


        viewbinding.loginBtn.setOnClickListener{
            //외부 노드서버랑 연동하기 전 임시 데이터
            val tmpUserId = viewbinding.idEdittext.text.toString()
            val tmpUserPw = viewbinding.pwEdittext.text.toString()
            val newLoginUserData = LoginUserData(tmpUserId, tmpUserPw)

            Log.e("loginUserData Id: ", newLoginUserData.userId)
            Log.e("loginUserData Pw: ", newLoginUserData.userPw)
            ApiCallConfirmUserData(newLoginUserData, mc)
            //TO DO: 서버랑 아이디 비밀번호 일치하는지 체크
            //아이디 비밀번호 일치시, 룸디비에 데이터 넣기
//            CoroutineScope(Dispatchers.IO).launch {
//                // room DB 체크해서 넘어갈지 말지
//                RoomDBInsertUserData(mc, newUserData)
//                MoveMainFragment()
//            }
        }


//        login_btn.setOnClickListener{
//            //로그인을 했을 때 서버에서 아이디랑 비밀번호 일치여부 -> 가져와서 룸DB에 저장한 뒤 main fragment로 이동
//            findNavController().navigate(R.id.action_frag_login_to_fragment_retrofit_test)
//        }
        find_id_pw_btn.setOnClickListener {
            findNavController().navigate(R.id.action_frag_login_to_frag_find_id)
        }
    }

    fun btn_back(){
        viewbinding.btnBack5.setOnClickListener{
            findNavController().popBackStack()
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

    private fun ApiCallConfirmUserData(loginUserData: LoginUserData, mc: MainActivity):Int {
        var responseCode: Int = 0
        mc.supplementService.confirm_login(loginUserData).enqueue(object: retrofit2.Callback<BeforeParsingUserData> {
            override fun onResponse(call: Call<BeforeParsingUserData>, response: Response<BeforeParsingUserData>) {
                if(response.code() == 200){
                    Log.e("server","response 성공!!")
                }
                Log.e("response : ", response.body().toString())
                Log.e("responsecode : ", response.code().toString())
                responseCode = response.code().toInt()

                when(responseCode){
                    200 -> { //유효한 유저
                        Log.e("server","exist userId")
                        CoroutineScope(Dispatchers.Main).launch {
                            Toast.makeText(requireContext(),"로그인 되었습니다!", Toast.LENGTH_SHORT).show()
                        }
                        CoroutineScope(Dispatchers.IO).launch {
                            RoomDBInsertUserData(mc, ParseUserData(response.body()!!))
                            MainActivity.user_days = RoomDBGetWeekdayOfUserData(mc).toMutableList()
                            MainActivity.user_timer = RoomDBGetHourOfUserData(mc).toMutableList()
                            MainActivity.user_pose = RoomDBGetPoseOfUserData(mc).toMutableList()
                            MainActivity.user_medal = RoomDBGetMedalOfUserData(mc).toMutableList()
                            MainActivity.user_joinData = RoomDBGetJoinDateOfUserData(mc)
                            MoveMainFragment()
                        }
                    }
                    201 -> { //없는 유저
                        Log.e("server","not exist userId")

                        CoroutineScope(Dispatchers.Main).launch {
                            Toast.makeText(requireContext(),"일치하는 아이디와 패스워드가 없습니다.\n아이디와 패스워드를 확인해주세요.", Toast.LENGTH_SHORT).show()
                        }
                        viewbinding.idNotionTextview.visibility = View.VISIBLE
                        viewbinding.pwNotionTextview.visibility = View.VISIBLE
                    }
                    else -> { //다른 에러 발생(서버)
                        Log.e("server","server error")
//                        CoroutineScope(Dispatchers.Main).launch {
//                            Toast.makeText(requireContext(),"서버 에러가 발생하였습니다!", Toast.LENGTH_SHORT).show()
//                        }
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

    private fun ParseUserData(beforeParsingUserData: BeforeParsingUserData): UserData {
        val parseUserData: UserData = UserData(
            beforeParsingUserData.userId,
            beforeParsingUserData.userPw,
            beforeParsingUserData.userEmail,
            ParseDataFromStringToList(beforeParsingUserData.pose),
            ParseDataFromStringToList(beforeParsingUserData.medal),
            ParseDataFromStringToList(beforeParsingUserData.weekday),
            ParseDataFromStringToList(beforeParsingUserData.hour),
            beforeParsingUserData.confirmNum,
            ParseDataFromIntToBoolean(beforeParsingUserData.premium),
            beforeParsingUserData.joinDate
        )
        return  parseUserData
    }

    private fun ParseDataFromStringToList(str: String):List<Boolean>{
        var newList: MutableList<Boolean> = mutableListOf()
        for(char in str){
            if(char == '0') newList.add(false)
            else newList.add(true)
        }
        return newList.toList()
    }

    private fun ParseDataFromIntToBoolean(int: Int):Boolean{
        return int != 0
    }

    suspend fun RoomDBGetWeekdayOfUserData(mc: MainActivity):List<Boolean>{
        return mc.db.userDao().getUserData()!!.weekday
    }

    suspend fun RoomDBGetHourOfUserData(mc: MainActivity):List<Boolean>{
        return mc.db.userDao().getUserData()!!.hour
    }

    suspend fun RoomDBGetPoseOfUserData(mc: MainActivity):List<Boolean>{
        return mc.db.userDao().getUserData()!!.pose
    }

    suspend fun RoomDBGetMedalOfUserData(mc: MainActivity):List<Boolean>{
        return mc.db.userDao().getUserData()!!.medal
    }

    suspend fun RoomDBGetJoinDateOfUserData(mc: MainActivity):String{
        return mc.db.userDao().getUserData()!!.joinDate
    }
}