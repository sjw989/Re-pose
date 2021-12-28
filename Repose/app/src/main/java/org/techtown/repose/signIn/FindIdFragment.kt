package org.techtown.repose.signIn

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.frag_find_id.*
import kotlinx.android.synthetic.main.frag_login.*
import kotlinx.android.synthetic.main.frag_login.login_btn
import org.techtown.repose.R
import org.techtown.repose.databinding.FragFindIdBinding

class FindIdFragment : Fragment() {
    lateinit var viewbinding: FragFindIdBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        viewbinding = FragFindIdBinding.inflate(inflater, container, false)
        return  viewbinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        find_btn.setOnClickListener{
            if(viewbinding.idEdittext.text.toString() == "a"){

                //viewbinding.logoImageView.visibility = View.VISIBLE
                //viewbinding.failLogoImageView.visibility = View.INVISIBLE
                //viewbinding.failMessageTextview.visibility = View.INVISIBLE
                findNavController().navigate(R.id.action_frag_find_id_to_frag_find_success)
            }
            else{
                //viewbinding.logoImageView.visibility = View.INVISIBLE
                //viewbinding.failLogoImageView.visibility = View.VISIBLE
                //viewbinding.failMessageTextview.visibility = View.VISIBLE

            }
        }

    }

}