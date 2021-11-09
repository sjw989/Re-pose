package org.techtown.repose.signIn

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import kotlinx.android.synthetic.main.frag_start.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.techtown.repose.AppDatabase
import org.techtown.repose.MainActivity
import org.techtown.repose.R
import org.techtown.repose.UserData


class StartFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frag_start, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var mc : MainActivity = MainActivity()
//        val db : AppDatabase? = AppDatabase.getInstance(requireContext())
        mc.db = AppDatabase.getInstance(requireContext())!!
        lateinit var dblist:List<UserData>
//        val newUserData = UserData(124,"chae","kijung")
//        mc.db.userDao().insertUserData(newUserData)

        CoroutineScope(Dispatchers.IO).launch{
            // room DB 체크해서 넘어갈지 말지
            dblist = mc.db.userDao().getAll()
            Log.e("dblist",dblist.toString())
        }

//        if(dblist.isEmpty()){
//            findNavController().navigate(R.id.action_frag_start_to_frag_login)
//        }


        login_btn.setOnClickListener{
            findNavController().navigate(R.id.action_frag_start_to_frag_login)
        }
        sign_up_btn.setOnClickListener{
            findNavController().navigate(R.id.action_frag_start_to_frag_account_setting)
        }
    }
}