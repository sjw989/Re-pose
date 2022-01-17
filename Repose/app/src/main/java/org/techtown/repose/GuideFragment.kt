package org.techtown.repose

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

class GuideFragment : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frag_guide, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val guide_dialog1 = GuideFragment1()
        val guide_dialog2 = GuideFragment2()
        val guide_dialog3 = GuideFragment3()
        val guide_dialog4 = GuideFragment4()
        
        // guide 1번 보여주기
        guide_dialog1.show(requireActivity().supportFragmentManager, null)

        requireActivity().supportFragmentManager.executePendingTransactions() // 얘 없으면 NullPointerError 뜸


        // guide 1번이 dismiss하면 guide 2 보여주기
        guide_dialog1.dialog!!.setOnDismissListener{

            guide_dialog2.show(requireActivity().supportFragmentManager,null)

            requireActivity().supportFragmentManager.executePendingTransactions()
            // guide 2번이 dismiss 하면 guide 3 보여주기
            guide_dialog2.dialog!!.setOnDismissListener{
                guide_dialog3.show(requireActivity().supportFragmentManager,null)

                requireActivity().supportFragmentManager.executePendingTransactions()
                //guide 3번 dismiss하면 guide 4 보여주기
                guide_dialog3.dialog!!.setOnDismissListener{
                    guide_dialog4.show(requireActivity().supportFragmentManager,null)

                    requireActivity().supportFragmentManager.executePendingTransactions()
                    guide_dialog4.dialog!!.setOnDismissListener{
                        findNavController().navigate(R.id.action_frag_guide_to_frag_main)
                    }
                }

            }
        }





    }

}