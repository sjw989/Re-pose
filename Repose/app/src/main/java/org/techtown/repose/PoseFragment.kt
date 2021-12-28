package org.techtown.repose

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.techtown.repose.Data.UserData
import org.techtown.repose.databinding.FragPoseBinding

class PoseFragment : Fragment() {

    private var _binding : FragPoseBinding? = null // 뷰바인딩
    private val binding get() = _binding!! // 뷰바인딩

    private var pose_num : Int? = null
    private var pose_name: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let{
            pose_num = it.getInt("num",0)
            pose_name = it.getString("pose", "")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragPoseBinding.inflate(inflater,container,false) // 뷰바인딩
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvPoseName.text  = pose_name
        when(pose_name){
            "다리꼬기" -> binding.btnGoGuide.setImageResource(R.drawable.pose1)
            "한 쪽으로 짐 들기" -> binding.btnGoGuide.setImageResource(R.drawable.pose2)
            "장시간 앉아 있기" -> binding.btnGoGuide.setImageResource(R.drawable.pose3)
            "장시간 서 있기" -> binding.btnGoGuide.setImageResource(R.drawable.pose4)
            "장시간 전자기기 사용" -> binding.btnGoGuide.setImageResource(R.drawable.pose5)
            "장시간 독서" -> binding.btnGoGuide.setImageResource(R.drawable.pose6)
            "장시간 필기" -> binding.btnGoGuide.setImageResource(R.drawable.pose7)
            "장시간 운전" -> binding.btnGoGuide.setImageResource(R.drawable.pose8)
            "팔자걸음" -> binding.btnGoGuide.setImageResource(R.drawable.pose9)
            "안짱걸음" -> binding.btnGoGuide.setImageResource(R.drawable.pose10)
            "양반다리" -> binding.btnGoGuide.setImageResource(R.drawable.pose11)
            "엎드려자기" -> binding.btnGoGuide.setImageResource(R.drawable.pose12)
        }
        binding.btnGoGuide.setOnClickListener{
            var bundle = Bundle()
            bundle.putString("pose_name",pose_name)
            if(MainFragment.is_countDown){
                MainFragment.countDown.cancel()
            }
            findNavController().navigate(R.id.action_frag_main_to_frag_show_exercise,bundle)
        }
    }

    // 뷰바인딩 destroy
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    // PoseFragment 객체 생성 메소드
    companion object{
        fun newInstance(pose_num: Int, pose_name: String)=
            PoseFragment().apply {
                arguments = Bundle().apply {
                    putInt("num",pose_num)
                    putString("pose", pose_name)
                }
            }
    }
}