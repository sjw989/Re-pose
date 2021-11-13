package org.techtown.repose

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import org.techtown.repose.databinding.FragMainBinding
import org.techtown.repose.databinding.FragPoseBinding

class MainFragment : Fragment() {
    //    private val ft = childFragmentManager.beginTransaction()
    private var _binding : FragMainBinding? = null // 뷰바인딩
    private val binding get() = _binding!! // 뷰바인딩


    companion object{
        var vpAdapter: FragmentStateAdapter? = null // pose Adapter
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragMainBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        // 뷰페이저 관련
        val fmList = arrayListOf<Fragment>()
        for(i in 0..MainActivity.user.pose_list.size-1){
            fmList.add(PoseFragment.newInstance(MainActivity.pose_list.indexOf(MainActivity.user.pose_list[i]),MainActivity.user.pose_list[i]))
        }
        vpAdapter = PosePagerAdapter(fmList,this) // 어댑터 설정
        binding.vpMainViewPager.adapter = vpAdapter // 어댑터 연결
        binding.poseIndicator.setViewPager2(binding.vpMainViewPager) // 인디케이터랑 어댑터 동기화
        // 뷰페이저 end

        // 자세선택 버튼
        binding.btnSelectPose.setOnClickListener(View.OnClickListener {
            (context as MainActivity).setfrag(2)
        })

    }

    // Pose 보여주는 뷰페이저 어댑터
    class PosePagerAdapter(private val fmList:ArrayList<Fragment>, fm: MainFragment): FragmentStateAdapter(fm){
        private val fmIds = fmList.map{it.hashCode().toLong()}

        override fun getItemCount(): Int {
            return fmList.size
        }
        override fun createFragment(position: Int): Fragment {
            return fmList[position]
        }

        override fun getItemId(position: Int): Long {
            return fmList[position].hashCode().toLong()
        }

        override fun containsItem(itemId: Long): Boolean {
            return fmIds.contains(itemId)
        }

    }

    // 뷰바인딩 destroy
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}