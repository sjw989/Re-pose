package org.techtown.repose

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import org.techtown.repose.databinding.FragExerciseBinding


class ExerciseFragment : Fragment() {
    lateinit var mainActivity : MainActivity // MainActivity context 저장할 변수
    private var _binding : FragExerciseBinding? = null // 뷰바인딩
    private val binding get() = _binding!! // 뷰바인딩

    private var pose_name: String? = null // 선택된 pose
    private var idx : Int? = null // 몇번째 운동인지

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        addView(view) // 선택한 pose의 운동 recyclerview에 담기
    }

    // 선택한 pose의 운동 recyclerview에 담기
    fun addView(view : View){
        val layoutManager = LinearLayoutManager(context)
        binding.rcvShowExercise.setLayoutManager(layoutManager)
        var data: List<String> = MainActivity.exercise_list[MainActivity.pose_list.indexOf(pose_name)][idx!!]
        val adapter = ExerciseRecyclerViewAdapter(mainActivity, data,idx!!,pose_name!!,view)
        binding.rcvShowExercise.adapter = adapter
    }

    companion object{
        // ExerciseFragment 객체 생성 메소드
        fun newInstance(pose_name: String, idx : Int)= ExerciseFragment().apply {
                arguments = Bundle().apply {
                    putString("pose", pose_name) // 현재 pose 이름
                    putInt("idx", idx) // 몇 번째 운동인지
                }
            }
    }

    // 뷰바인딩 destroy
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}