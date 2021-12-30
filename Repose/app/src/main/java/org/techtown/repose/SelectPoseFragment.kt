package org.techtown.repose


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.techtown.repose.Data.AppDatabase
import org.techtown.repose.Data.BeforeParsingUserData
import org.techtown.repose.Data.UpdateHourOfUserData
import org.techtown.repose.Data.UpdatePoseOfUserData
import org.techtown.repose.MainActivity.Companion.pose_list
import org.techtown.repose.MainActivity.Companion.user_pose
import org.techtown.repose.databinding.FragSelectPoseBinding
import retrofit2.Call
import retrofit2.Response

class SelectPoseFragment : Fragment() {
    lateinit var navController: NavController// 네비게이션 컨트롤러
    private var _binding: FragSelectPoseBinding? = null // 뷰바인딩
    private val binding get() = _binding!! // 뷰바인딩
    var mc: MainActivity = MainActivity()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragSelectPoseBinding.inflate(inflater, container, false) // 뷰바인딩
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mc.initRetrofit()
        mc.db = AppDatabase.getInstance(requireContext())!!
        CoroutineScope(Dispatchers.IO).launch {
            MainActivity.user_days = RoomDBGetWeekDayOfUserData(mc).toMutableList()
        }
        navController = Navigation.findNavController(view) // 네비게이션 컨트롤러 view로 부터 가져오기
        setInit() // 초기 버튼 상태 설정
        setButton() // 버튼으로 자세 선택
        selected() // 사용자가 Disease화면에서 자세를 선택한 경우 처리
        back_pressed() // 뒤로가기 버튼
        btn_back() // 뒤로가기 화살표 버튼
    }

    // Disease화면에서 자세가 선택버튼을 누른경우 처리
    fun selected() {
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<String>("pose")
            ?.observe(viewLifecycleOwner) {
                user_pose[pose_list.indexOf(it)] = true
                when (it) {
                    "다리꼬기" -> binding.SelectBtnPose1.setBackgroundResource(R.drawable.pose_selected)
                    "한 쪽으로 짐 들기" -> binding.SelectBtnPose2.setBackgroundResource(R.drawable.pose_selected)
                    "장시간 앉아 있기" -> binding.SelectBtnPose3.setBackgroundResource(R.drawable.pose_selected)
                    "장시간 서 있기" -> binding.SelectBtnPose4.setBackgroundResource(R.drawable.pose_selected)
                    "장시간 전자기기 사용" -> binding.SelectBtnPose5.setBackgroundResource(R.drawable.pose_selected)
                    "장시간 독서" -> binding.SelectBtnPose6.setBackgroundResource(R.drawable.pose_selected)
                    "장시간 필기" -> binding.SelectBtnPose7.setBackgroundResource(R.drawable.pose_selected)
                    "장시간 운전" -> binding.SelectBtnPose8.setBackgroundResource(R.drawable.pose_selected)
                    "팔자걸음" -> binding.SelectBtnPose9.setBackgroundResource(R.drawable.pose_selected)
                    "안짱걸음" -> binding.SelectBtnPose10.setBackgroundResource(R.drawable.pose_selected)
                    "양반다리" -> binding.SelectBtnPose11.setBackgroundResource(R.drawable.pose_selected)
                    "엎드려자기" -> binding.SelectBtnPose12.setBackgroundResource(R.drawable.pose_selected)
                }
            }
    }

    // 버튼으로 자세 선택
    fun setButton() {
        binding.SelectBtnPose1.setOnClickListener() {
            CoroutineScope(Dispatchers.IO).launch {
                RoomDBUpdatePoseOfUserData(mc, 0, !user_pose[pose_list.indexOf("다리꼬기")])
                val tmpPose :String = ParseDataFromListToString(RoomDBGetPoseOfUserData(mc))
                val updatePoseOfUserData = UpdatePoseOfUserData(
                    tmpPose,
                    RoomDBGetUserIdOfUserData(mc)
                )
                ApiCallUpdatePoseOfUserData(updatePoseOfUserData, mc)
            }
            if (user_pose[0]) {
                user_pose[0] = false
                binding.SelectBtnPose1.setBackgroundResource(R.drawable.pose_not_selected)
            }
            else if (!user_pose[0]) {
                val bundle = Bundle()
                bundle.putString("pose", "다리꼬기")
                findNavController().navigate(
                    R.id.action_frag_select_pose_to_frag_disease,
                    bundle
                )
            }
        }
        binding.SelectBtnPose2.setOnClickListener(){
            CoroutineScope(Dispatchers.IO).launch {
                RoomDBUpdatePoseOfUserData(mc,1, !user_pose[pose_list.indexOf("한 쪽으로 짐 들기")])
                val tmpPose :String = ParseDataFromListToString(RoomDBGetPoseOfUserData(mc))
                val updatePoseOfUserData = UpdatePoseOfUserData(
                    tmpPose,
                    RoomDBGetUserIdOfUserData(mc)
                )
                ApiCallUpdatePoseOfUserData(updatePoseOfUserData, mc)
            }
            if(user_pose[pose_list.indexOf("한 쪽으로 짐 들기")]){
                user_pose[pose_list.indexOf("한 쪽으로 짐 들기")] = false
                binding.SelectBtnPose2.setBackgroundResource(R.drawable.pose_not_selected)
            }
            else if (!user_pose[1]) {
                val bundle = Bundle()
                bundle.putString("pose", "한 쪽으로 짐 들기")
                findNavController().navigate(
                    R.id.action_frag_select_pose_to_frag_disease,
                    bundle
                )
            }
        }

        binding.SelectBtnPose3.setOnClickListener(){
            CoroutineScope(Dispatchers.IO).launch {
                RoomDBUpdatePoseOfUserData(mc,2, !user_pose[pose_list.indexOf("장시간 앉아 있기")])
                val tmpPose :String = ParseDataFromListToString(RoomDBGetPoseOfUserData(mc))
                val updatePoseOfUserData = UpdatePoseOfUserData(
                    tmpPose,
                    RoomDBGetUserIdOfUserData(mc)
                )
                ApiCallUpdatePoseOfUserData(updatePoseOfUserData, mc)
            }
            if(user_pose[pose_list.indexOf("장시간 앉아 있기")]){
                user_pose[pose_list.indexOf("장시간 앉아 있기")] = false
                binding.SelectBtnPose3.setBackgroundResource(R.drawable.pose_not_selected)
            }
            else if (!user_pose[2]) {
                val bundle = Bundle()
                bundle.putString("pose", "장시간 앉아 있기")
                findNavController().navigate(
                    R.id.action_frag_select_pose_to_frag_disease,
                    bundle
                )
            }

        }

        binding.SelectBtnPose4.setOnClickListener() {
            CoroutineScope(Dispatchers.IO).launch {
                RoomDBUpdatePoseOfUserData(mc, 3, !user_pose[pose_list.indexOf("장시간 서 있기")])
                val tmpPose :String = ParseDataFromListToString(RoomDBGetPoseOfUserData(mc))
                val updatePoseOfUserData = UpdatePoseOfUserData(
                    tmpPose,
                    RoomDBGetUserIdOfUserData(mc)
                )
                ApiCallUpdatePoseOfUserData(updatePoseOfUserData, mc)
            }
            if (user_pose[3]) {
                user_pose[3] = false
                binding.SelectBtnPose4.setBackgroundResource(R.drawable.pose_not_selected)
            }
             else if (!user_pose[3]) {
                val bundle = Bundle()
                bundle.putString("pose", "장시간 서 있기")
                findNavController().navigate(
                    R.id.action_frag_select_pose_to_frag_disease,
                    bundle
                )
            }

        }

        binding.SelectBtnPose5.setOnClickListener() {
            CoroutineScope(Dispatchers.IO).launch {
                RoomDBUpdatePoseOfUserData(mc, 4, !user_pose[pose_list.indexOf("장시간 전자기기 사용")])
                val tmpPose :String = ParseDataFromListToString(RoomDBGetPoseOfUserData(mc))
                val updatePoseOfUserData = UpdatePoseOfUserData(
                    tmpPose,
                    RoomDBGetUserIdOfUserData(mc)
                )
                ApiCallUpdatePoseOfUserData(updatePoseOfUserData, mc)
            }
            if (user_pose[4]) {
                user_pose[4] = false
                binding.SelectBtnPose5.setBackgroundResource(R.drawable.pose_not_selected)
            }
             else if (!user_pose[4]) {
                val bundle = Bundle()
                bundle.putString("pose", "장시간 전자기기 사용")
                findNavController().navigate(
                    R.id.action_frag_select_pose_to_frag_disease, bundle
                )
            }

        }

        binding.SelectBtnPose6.setOnClickListener() {
            CoroutineScope(Dispatchers.IO).launch {
                RoomDBUpdatePoseOfUserData(mc, 5, !user_pose[pose_list.indexOf("장시간 독서")])
                val tmpPose :String = ParseDataFromListToString(RoomDBGetPoseOfUserData(mc))
                val updatePoseOfUserData = UpdatePoseOfUserData(
                    tmpPose,
                    RoomDBGetUserIdOfUserData(mc)
                )
                ApiCallUpdatePoseOfUserData(updatePoseOfUserData, mc)
            }
            if (user_pose[5]) {
                user_pose[5] = false
                binding.SelectBtnPose6.setBackgroundResource(R.drawable.pose_not_selected)
            }
           else if (!user_pose[5]) {
                val bundle = Bundle()
                bundle.putString("pose", "장시간 독서")
                findNavController().navigate(
                    R.id.action_frag_select_pose_to_frag_disease, bundle
                )
            }

        }

        binding.SelectBtnPose7.setOnClickListener() {
            CoroutineScope(Dispatchers.IO).launch {
                RoomDBUpdatePoseOfUserData(mc, 6, !user_pose[pose_list.indexOf("장시간 필기")])
                val tmpPose :String = ParseDataFromListToString(RoomDBGetPoseOfUserData(mc))
                val updatePoseOfUserData = UpdatePoseOfUserData(
                    tmpPose,
                    RoomDBGetUserIdOfUserData(mc)
                )
                ApiCallUpdatePoseOfUserData(updatePoseOfUserData, mc)
            }
            if (user_pose[6]) {
                user_pose[6] = false
                binding.SelectBtnPose7.setBackgroundResource(R.drawable.pose_not_selected)
            }
             else if (!user_pose[6]) {
                val bundle = Bundle()
                bundle.putString("pose", "장시간 필기")
                findNavController().navigate(
                    R.id.action_frag_select_pose_to_frag_disease,
                    bundle
                )
            }

        }
        binding.SelectBtnPose8.setOnClickListener() {
            CoroutineScope(Dispatchers.IO).launch {
                RoomDBUpdatePoseOfUserData(mc, 7, !user_pose[pose_list.indexOf("장시간 운전")])
                val tmpPose :String = ParseDataFromListToString(RoomDBGetPoseOfUserData(mc))
                val updatePoseOfUserData = UpdatePoseOfUserData(
                    tmpPose,
                    RoomDBGetUserIdOfUserData(mc)
                )
                ApiCallUpdatePoseOfUserData(updatePoseOfUserData, mc)
            }
            if (user_pose[7]) {
                user_pose[7] = false
                binding.SelectBtnPose8.setBackgroundResource(R.drawable.pose_not_selected)
            }
             else if (!user_pose[7]) {
                val bundle = Bundle()
                bundle.putString("pose", "장시간 운전")
                findNavController().navigate(
                    R.id.action_frag_select_pose_to_frag_disease,
                    bundle
                )
            }

        }
        binding.SelectBtnPose9.setOnClickListener() {
            CoroutineScope(Dispatchers.IO).launch {
                RoomDBUpdatePoseOfUserData(mc, 8, !user_pose[pose_list.indexOf("팔자걸음")])
                val tmpPose :String = ParseDataFromListToString(RoomDBGetPoseOfUserData(mc))
                val updatePoseOfUserData = UpdatePoseOfUserData(
                    tmpPose,
                    RoomDBGetUserIdOfUserData(mc)
                )
                ApiCallUpdatePoseOfUserData(updatePoseOfUserData, mc)
            }
            if (user_pose[8]) {
                user_pose[8] = false
                binding.SelectBtnPose9.setBackgroundResource(R.drawable.pose_not_selected)
            }
            else if (!user_pose[8]) {
                val bundle = Bundle()
                bundle.putString("pose", "팔자걸음")
                findNavController().navigate(
                    R.id.action_frag_select_pose_to_frag_disease,
                    bundle
                )
            }

        }

        binding.SelectBtnPose10.setOnClickListener() {
            CoroutineScope(Dispatchers.IO).launch {
                RoomDBUpdatePoseOfUserData(mc, 9, !user_pose[pose_list.indexOf("안짱걸음")])
                val tmpPose :String = ParseDataFromListToString(RoomDBGetPoseOfUserData(mc))
                val updatePoseOfUserData = UpdatePoseOfUserData(
                    tmpPose,
                    RoomDBGetUserIdOfUserData(mc)
                )
                ApiCallUpdatePoseOfUserData(updatePoseOfUserData, mc)
            }
            if (user_pose[9]) {
                user_pose[9] = false
                binding.SelectBtnPose10.setBackgroundResource(R.drawable.pose_not_selected)
            }
            else if (!user_pose[9]) {
                val bundle = Bundle()
                bundle.putString("pose", "안짱걸음")
                findNavController().navigate(
                    R.id.action_frag_select_pose_to_frag_disease,
                    bundle
                )
            }
        }

        binding.SelectBtnPose11.setOnClickListener() {
            CoroutineScope(Dispatchers.IO).launch {
                RoomDBUpdatePoseOfUserData(mc, 10, !user_pose[pose_list.indexOf("양반다리")])
                val tmpPose :String = ParseDataFromListToString(RoomDBGetPoseOfUserData(mc))
                val updatePoseOfUserData = UpdatePoseOfUserData(
                    tmpPose,
                    RoomDBGetUserIdOfUserData(mc)
                )
                ApiCallUpdatePoseOfUserData(updatePoseOfUserData, mc)
            }
            if (user_pose[10]) {
                user_pose[10] = false
                binding.SelectBtnPose11.setBackgroundResource(R.drawable.pose_not_selected)
            }
             else if (!user_pose[10]) {
                val bundle = Bundle()
                bundle.putString("pose", "양반다리")
                findNavController().navigate(
                    R.id.action_frag_select_pose_to_frag_disease,
                    bundle
                )
            }

        }
        binding.SelectBtnPose12.setOnClickListener() {
            CoroutineScope(Dispatchers.IO).launch {
                RoomDBUpdatePoseOfUserData(mc, 11, !user_pose[pose_list.indexOf("엎드려자기")])
                val tmpPose :String = ParseDataFromListToString(RoomDBGetPoseOfUserData(mc))
                val updatePoseOfUserData = UpdatePoseOfUserData(
                    tmpPose,
                    RoomDBGetUserIdOfUserData(mc)
                )
                ApiCallUpdatePoseOfUserData(updatePoseOfUserData, mc)
            }
            if (user_pose[11]) {
                user_pose[11] = false
                binding.SelectBtnPose12.setBackgroundResource(R.drawable.pose_not_selected)
            }
            else if (!user_pose[11]) {
                val bundle = Bundle()
                bundle.putString("pose", "엎드려자기")
                findNavController().navigate(
                    R.id.action_frag_select_pose_to_frag_disease,
                    bundle
                )
            }
        }
    }


    // 초기 버튼 상태
    fun setInit() {
        if (user_pose[0]) {
            binding.SelectBtnPose1.setBackgroundResource(R.drawable.pose_selected)
        } else {
            binding.SelectBtnPose1.setBackgroundResource(R.drawable.pose_not_selected)
        }
        if (user_pose[1]) {
            binding.SelectBtnPose2.setBackgroundResource(R.drawable.pose_selected)
        } else {
            binding.SelectBtnPose2.setBackgroundResource(R.drawable.pose_not_selected)
        }
        if (user_pose[2]) {
            binding.SelectBtnPose3.setBackgroundResource(R.drawable.pose_selected)
        } else {
            binding.SelectBtnPose3.setBackgroundResource(R.drawable.pose_not_selected)
        }
        if (user_pose[3]) {
            binding.SelectBtnPose4.setBackgroundResource(R.drawable.pose_selected)
        } else {
            binding.SelectBtnPose4.setBackgroundResource(R.drawable.pose_not_selected)
        }
        if (user_pose[4]) {
            binding.SelectBtnPose5.setBackgroundResource(R.drawable.pose_selected)
        } else {
            binding.SelectBtnPose5.setBackgroundResource(R.drawable.pose_not_selected)
        }
        if (user_pose[5]) {
            binding.SelectBtnPose6.setBackgroundResource(R.drawable.pose_selected)
        } else {
            binding.SelectBtnPose6.setBackgroundResource(R.drawable.pose_not_selected)
        }
        if (user_pose[6]) {
            binding.SelectBtnPose7.setBackgroundResource(R.drawable.pose_selected)
        } else {
            binding.SelectBtnPose7.setBackgroundResource(R.drawable.pose_not_selected)
        }
        if (user_pose[7]) {
            binding.SelectBtnPose8.setBackgroundResource(R.drawable.pose_selected)
        } else {
            binding.SelectBtnPose8.setBackgroundResource(R.drawable.pose_not_selected)
        }
        if (user_pose[8]) {
            binding.SelectBtnPose9.setBackgroundResource(R.drawable.pose_selected)
        } else {
            binding.SelectBtnPose9.setBackgroundResource(R.drawable.pose_not_selected)
        }
        if (user_pose[9]) {
            binding.SelectBtnPose10.setBackgroundResource(R.drawable.pose_selected)
        } else {
            binding.SelectBtnPose10.setBackgroundResource(R.drawable.pose_not_selected)
        }
        if (user_pose[10]) {
            binding.SelectBtnPose11.setBackgroundResource(R.drawable.pose_selected)
        } else {
            binding.SelectBtnPose11.setBackgroundResource(R.drawable.pose_not_selected)
        }
        if (user_pose[11]) {
            binding.SelectBtnPose12.setBackgroundResource(R.drawable.pose_selected)
        } else {
            binding.SelectBtnPose12.setBackgroundResource(R.drawable.pose_not_selected)
        }
    }

    // 뷰바인딩 destroy
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    // 뒤로가기 버튼
    fun back_pressed() {
        requireActivity().onBackPressedDispatcher.addCallback(object :
            OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navController.popBackStack()
            }
        })
    }

    fun btn_back() {
        binding.btnBack2.setOnClickListener {
            navController.popBackStack()
        }
    }

    private fun ParseDataFromListToString(list: List<Boolean>): String {
        var str: String = ""
        for(ele in list){
            if(ele) str += "1"
            else str += "0"
        }
        return str
    }

    suspend fun RoomDBUpdatePoseOfUserData(mc: MainActivity, updateIndex: Int, set: Boolean) {
        val tmpId = mc.db.userDao().getUserData()!!.id
        var tmpList = mc.db.userDao().getUserData()!!.pose.toMutableList()
        tmpList[updateIndex] = set
        mc.db.userDao().updateUserDataPose(tmpId, tmpList.toList())
    }

    suspend fun RoomDBGetWeekDayOfUserData(mc: MainActivity):List<Boolean>{
        return mc.db.userDao().getUserData()!!.weekday
    }

    suspend fun RoomDBGetUserIdOfUserData(mc: MainActivity):String{
        return mc.db.userDao().getUserData()!!.id
    }

    suspend fun RoomDBGetPoseOfUserData(mc: MainActivity):List<Boolean>{
        return mc.db.userDao().getUserData()!!.pose
    }

    private fun ApiCallUpdatePoseOfUserData(updatePoseOfUserData: UpdatePoseOfUserData, mc: MainActivity):Int {
        var responseCode: Int = 0
        mc.supplementService.update_pose(updatePoseOfUserData).enqueue(object: retrofit2.Callback<BeforeParsingUserData> {
            override fun onResponse(call: Call<BeforeParsingUserData>, response: Response<BeforeParsingUserData>) {
                if(response.code() == 200){
                    Log.e("server","response 성공!!")
                }
                Log.e("response : ", response.body().toString())
                Log.e("responsecode : ", response.code().toString())
                responseCode = response.code()
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
}