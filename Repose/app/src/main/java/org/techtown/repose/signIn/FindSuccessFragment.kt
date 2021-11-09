package org.techtown.repose.signIn

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.frag_login.*
import org.techtown.repose.R
import org.techtown.repose.databinding.FragFindSuccessBinding

class FindSuccessFragment : Fragment() {
    lateinit var viewbinding: FragFindSuccessBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        viewbinding = FragFindSuccessBinding.inflate(inflater, container, false)
        return viewbinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewbinding.gotoLoginBtn.setOnClickListener{
            findNavController().navigate(R.id.action_frag_find_success_to_frag_login)
        }
    }
}