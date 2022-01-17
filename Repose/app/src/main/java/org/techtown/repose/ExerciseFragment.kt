package org.techtown.repose

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.techtown.repose.databinding.FragExerciseBinding


class ExerciseFragment : Fragment() {
    lateinit var mainActivity : MainActivity // MainActivity context 저장할 변수
    private var _binding : FragExerciseBinding? = null // 뷰바인딩
    private val binding get() = _binding!! // 뷰바인딩

    private var pose_name: String? = null // 선택된 pose
    private var idx : Int? = null // 몇번째 운동인지

    override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState)
        arguments?.let{
            idx = it.getInt("idx",0)
            pose_name = it.getString("pose", "")
        } // Fragment생성되었을 때 인자로 받아온 "key(pose)"의 value를 pose_name, idx에 넣어줌에 넣어줌
    }
    // MainActivity로부터 context가져오기위한 onAttach overriding
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is MainActivity) mainActivity = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragExerciseBinding.inflate(inflater,container,false) // 뷰바인딩
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        back_pressed() // 뒤로가기 버튼
        addView(view) // 선택한 pose의 운동 recyclerview에 담기
    }

    // 선택한 pose의 운동 recyclerview에 담기
    fun addView(view : View){
        val layoutManager = LinearLayoutManager(context)
        binding.rcvShowExercise.setLayoutManager(layoutManager)
        var data: List<Pair<String,Int>> = exercise_list[MainActivity.pose_list.indexOf(pose_name)][idx!!]
        val adapter = ExerciseRecyclerViewAdapter(mainActivity, data,idx!!,pose_name!!,view)
        binding.rcvShowExercise.adapter = adapter
    }

    // 뒤로가기 버튼
    fun back_pressed(){
        requireActivity().onBackPressedDispatcher.addCallback(object :
            OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
               if(!ExerciseRecyclerViewAdapter.is_dialog){
                   // 운동완료 다이얼로그를 출력하지 않는 경우에만
                   findNavController().popBackStack()
               }
            }
        })
    }
    
    companion object{
        // ExerciseFragment 객체 생성 메소드
        fun newInstance(pose_name: String, idx : Int)= ExerciseFragment().apply {
                arguments = Bundle().apply {
                    putString("pose", pose_name) // 현재 pose 이름
                    putInt("idx", idx) // 몇 번째 운동인지
                }
        }

        val exercise_set = listOf<Pair<String,Int>>(
            Pair("한 쪽 발목을 반대편 무릎 위로 올려주세요",R.drawable.exercise_a), // 0
            Pair("허리를 쭉 펴고 앞으로 천천히 기울여주세요",R.drawable.exercise_b), // 1
            Pair("양쪽 모두 10회씩 진행해주세요.",0), // 2
            Pair("누운 자세에서 한 쪽 발목을 무릎 위로 올려주세요.",R.drawable.exercise_a), // 3
            Pair("반대쪽 엉덩이 측면에 폼 롤러 및 베개를 두고 앞뒤로 움직여주세요.",R.drawable.exercise_c), // 4
            Pair("허리를 쭉 펴고 앞으로 천천히 기울여주세요",R.drawable.exercise_b), // 5
            Pair("다리를 어깨넓이로 벌려주시고 발이 정면을 향하게 일자로 놓아주세요.",R.drawable.exercise_v), // 6
            Pair("엉덩이 및 고관절이 뒤로 가게 무릎을 모으고 20초간 자세를 유지합니다.", R.drawable.exercise_z), // 7
            Pair("이때, 무릎이 앞쪽을 향하면 안됩니다.",0), // 8
            Pair("총 3번 반복해주세요.",0), // 9
            Pair("먼저 한 손으로 반대쪽 귀를 감싸 쥐세요.",R.drawable.exercise_d), // 10
            Pair("머리를 약 10° 반대쪽으로 회전 시켜주세요.",R.drawable.exercise_d), // 11
            Pair("반대쪽 손끝이 바닥에 닿는다는 느낌으로 올라간 어깨를 아래로 끌어 내려주세요.",R.drawable.exercise_e), // 12
            Pair("이때, 머리를 과하게 당기거나 젖히지 않고 몸은 수직으로 고정시켜주세요.",0), // 13
            Pair("30초간 자세를 유지합니다.",0), // 14
            Pair("의자에 앉은 상태로 허리를 곧게 펴주세요.",R.drawable.exercise_f), // 15
            Pair("한쪽 다리의 무릎을 구부려 가슴으로 끝까지 수축해주세요.",R.drawable.exercise_g), // 16
            Pair("수축 상태에서 버텨주며 10초 동안 천천히 내려주세요.",R.drawable.exercise_h), // 17
            Pair("내려 온 다음에는 가슴까지 수축을 빠르게 10회 진행해주세요.",R.drawable.exercise_g), // 18
            Pair("10회 때 다시 한번 버텨주며 10초 동안 천천히 내려주세요.",R.drawable.exercise_h), // 19
            Pair("한 쪽 다리 당 5회씩 진행해주세요.",0), // 20
            Pair("누운 상태에서 엉덩이를 들어주세요.",R.drawable.exercise_l), // 21
            Pair("옆에서 봤을 때 무릎-골반-가슴이 일자로 이어지게 만들어주세요..",R.drawable.exercise_j), // 22
            Pair("1회에 10초간 10회 진행해주세요.",0), // 23
            Pair("몸을 숙이면서 7초간 바닥에 손끝이 닿게 해주세요. ",R.drawable.exercise_k), // 24
            Pair("천천히 일어나시고 오른손을 위로 펴고 왼손은 골반을 잡아주세요.",R.drawable.exercise_l), // 25
            Pair("옆으로 몸을 7초간 늘여주세요. ",R.drawable.exercise_m), // 26
            Pair("반대쪽도 7초간 진행해주세요.",0), // 27
            Pair("양손을 몸 뒤로 한 상태에서 깍지를 껴주세요.",R.drawable.exercise_n), // 28
            Pair("흉추를 열어주면서 7초간 가슴을 열어주세요.",R.drawable.exercise_o), // 29
            Pair("양손으로 허리를 짚고 7초간 가슴을 열어주세요.",R.drawable.exercise_p), // 30
            Pair("한쪽 팔을 앞으로 쭉 뻗고 손바닥이 바깥을 향하도록 해주세요.",R.drawable.exercise_q), // 31
            Pair("손가락이 있는 부분을 손목이 90° 젖혀질 수 있게 몸 쪽으로 10초간 당겨주세요.",R.drawable.exercise_q), // 32
            Pair("이때, 팔에 당기는 느낌이 들어야 합니다.",0), // 33
            Pair("손바닥이 안쪽을 향하도록 뒤집고 손가락을 몸 쪽으로 10초간 당겨주세요.",R.drawable.exercise_r), // 34
            Pair("반대쪽 손도 진행합니다.",0), // 35
            Pair("손가락 하나를 눈 앞 20cm 거리에 둬 주세요. ",R.drawable.exercise_s), // 36
            Pair("손가락을 10초 응시 후, 먼 곳을 10초 응시해주세요.",0), // 37
            Pair("눈을 감고 눈동자를 상하좌우로 10초 움직여주세요.",R.drawable.exercise_t), // 38
            Pair("양쪽 손을 몸 좌우로 굽혀 올려주세요.(통닭자세)",R.drawable.exercise_u), // 39
            Pair("숨을 들이쉬면서 고개를 포함하여 상반신 전체를 10초간 뒤로 젖혀주세요.",R.drawable.exercise_u), // 40
            Pair("목에 힘을 빼고 고개를 좌우로 부드럽게 1분간 흔들어주세요.", R.drawable.exercise_x) // 41
        )


        val exercise_list =
            listOf(listOf(listOf<Pair<String,Int>>(exercise_set[0],exercise_set[1], exercise_set[2]), // 다리꼬기 1번운동
                            listOf<Pair<String,Int>>(exercise_set[3], exercise_set[4]), // 다리꼬기 2번운동
                            listOf<Pair<String,Int>>(exercise_set[6],exercise_set[7],exercise_set[8],exercise_set[9])), // 다리꼬기 3번운동

                   listOf(listOf<Pair<String,Int>>(exercise_set[10],exercise_set[11],exercise_set[12],exercise_set[13],exercise_set[14])), // 한 쪽으로 짐 들기 1번운동

                   listOf(listOf<Pair<String,Int>>(exercise_set[15],exercise_set[16],exercise_set[17],exercise_set[18],exercise_set[19],exercise_set[20]), // 장시간 앉아 있기 1번운동
                          listOf<Pair<String,Int>>(exercise_set[21],exercise_set[22],exercise_set[23])), // 장시간 앉아있기 2번운동

                   listOf(listOf<Pair<String,Int>>(exercise_set[24],exercise_set[25],exercise_set[26],exercise_set[27]), // 장시간 서 있기 1번운동
                          listOf<Pair<String,Int>>(exercise_set[28],exercise_set[29],exercise_set[30])), // 장시간 서있기 2번운동

                listOf(listOf<Pair<String,Int>>(exercise_set[31],exercise_set[32],exercise_set[33],exercise_set[34],exercise_set[35]), // 장시간 전자기기 사용 1번운동
                       listOf<Pair<String,Int>>(exercise_set[36],exercise_set[37],exercise_set[38]), // 장시간 전자기기 사용 2번운동
                       listOf<Pair<String,Int>>(exercise_set[39],exercise_set[40])), // 장시간 전자기기 사용 3번운동

                listOf(listOf<Pair<String,Int>>(exercise_set[36],exercise_set[37],exercise_set[38]), // 장시간 독서 1번운동
                       listOf<Pair<String,Int>>(exercise_set[39],exercise_set[40])), // 장시간 독서 2번운동

                listOf(listOf<Pair<String,Int>>(exercise_set[31],exercise_set[32],exercise_set[33],exercise_set[34],exercise_set[35]), // 장시간 필기 1번운동
                    listOf<Pair<String,Int>>(exercise_set[36],exercise_set[37],exercise_set[38]), // 장시간 필기 2번운동
                    listOf<Pair<String,Int>>(exercise_set[39],exercise_set[40])), // 장시간 필기 3번운동

                listOf(listOf<Pair<String,Int>>(exercise_set[36],exercise_set[37],exercise_set[38]), // 장시간 운전 1번운동
                    listOf<Pair<String,Int>>(exercise_set[39],exercise_set[40])), // 장시간 운전 2번운동

                listOf(listOf<Pair<String,Int>>(exercise_set[0],exercise_set[1], exercise_set[2]), // 팔자걸음 1번운동
                    listOf<Pair<String,Int>>(exercise_set[3], exercise_set[4]), // 팔자걸음 2번운동
                    listOf<Pair<String,Int>>(exercise_set[6],exercise_set[7],exercise_set[8],exercise_set[9])), // 팔자걸음 3번운동

                listOf(listOf<Pair<String,Int>>(exercise_set[0],exercise_set[1], exercise_set[2]), // 안짱걸음 1번운동
                    listOf<Pair<String,Int>>(exercise_set[3], exercise_set[4]), // 안짱걸음 2번운동
                    listOf<Pair<String,Int>>(exercise_set[6],exercise_set[7],exercise_set[8],exercise_set[9])), // 안짱걸음 3번운동

                listOf(listOf<Pair<String,Int>>(exercise_set[0],exercise_set[1], exercise_set[2]), // 양반다리 1번운동
                    listOf<Pair<String,Int>>(exercise_set[3], exercise_set[4])), // 양반다리 2번운동

                listOf(listOf<Pair<String,Int>>(exercise_set[15],exercise_set[41])) // 엎드려자기 1번운동

        )

    }

    // 뷰바인딩 destroy
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}