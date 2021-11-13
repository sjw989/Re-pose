package org.techtown.repose

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvPoseName.text  = pose_name
        binding.btnGoGuide.setOnClickListener(View.OnClickListener {
            (context as MainActivity).setfrag(1)
        })
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