package org.techtown.repose

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MainActivity : AppCompatActivity() {
    companion object{
        // 우리가 미리 선정해놓은 pose 리스트
        val pose_list = listOf<String>("자세1","자세2","자세3")

        // 미리 선정해놓은 pose 리스트의 가이드 --> 우선은 텍스트만
        val pose_guide = listOf(listOf<String>("가이드1-1","가이드1-2","가이드1-3"),
                                listOf<String>("가이드2-1","가이드2-2","가이드2-3"),
                                listOf<String>("가이드3-1","가이드3-2","가이드3-3"))
        var user_pose = arrayListOf<String>()
        var user = User("sjw989","1q2w3e4r",user_pose)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 임의로 user 객체 하나 만듦
        user.pose_list.add("자세1")
        user.pose_list.add("자세2")
        user.pose_list.add("자세3")
        setfrag(0)
    }

    fun setfrag(num : Int){
        val ft = supportFragmentManager.beginTransaction()
        when(num){
            0 -> {
                ft.replace(R.id.main_fragment, MainFragment(), "MainFrag").commit()
            } // 처음에 앱이 실행됐을때 mainFragment가 나옴
            1 -> {
                ft.addToBackStack(null)
                ft.replace(R.id.main_fragment, GuideFragment(), "GuideFrag").commit()
            }
            2->{
                ft.addToBackStack(null)
                ft.replace(R.id.main_fragment, SelectPoseFragment(), "SelectPoseFrag").commit()
            }
            3->{
                ft.addToBackStack(null)
                ft.replace(R.id.main_fragment, DiseaseFragment.newInstance("자세1")).commit()
            }

        }
    }

    // back버튼
    override fun onBackPressed() {
        val frag = this.supportFragmentManager.findFragmentById(R.id.main_fragment)
        (frag as? IOnbackPressed)?.onBackPressed()?.not()?.let{
            super.onBackPressed()
        }
    }


}