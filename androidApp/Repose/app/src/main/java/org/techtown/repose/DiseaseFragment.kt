package org.techtown.repose

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.techtown.repose.databinding.FragDiseaseBinding
import org.techtown.repose.databinding.FragPoseBinding


class DiseaseFragment : Fragment(), IOnbackPressed {
    private var _binding : FragDiseaseBinding? = null // 뷰바인딩
    private val binding get() = _binding!! // 뷰바인딩

    private var pose_name: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            pose_name = it.getString("pose", "")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragDiseaseBinding.inflate(inflater,container,false) // 뷰바인딩
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.tvPoseName.text = pose_name
        binding.btnGoExercise.setOnClickListener(){
            SelectPoseFragment.is_click = true
            onBackPressed()
        }
    }

    companion object {
        fun newInstance(pose_name: String) =
            DiseaseFragment().apply {
                arguments = Bundle().apply {
                    putString("pose", pose_name)
                }
            }
    }

    // 뷰바인딩 destroy
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onBackPressed():Boolean{
        return true
    }
}