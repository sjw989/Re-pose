package org.techtown.repose

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class EmptyPoseFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_empty_pose, container, false)
    }

    companion object {
        fun newInstance() =
            EmptyPoseFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}