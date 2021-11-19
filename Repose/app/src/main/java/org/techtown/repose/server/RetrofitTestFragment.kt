package org.techtown.repose.server

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.techtown.repose.MainActivity
import org.techtown.repose.R
import org.techtown.repose.databinding.FragAccountSettingBinding
import org.techtown.repose.databinding.FragmentRetrofitTestBinding
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class RetrofitTestFragment : Fragment() {
    lateinit var viewbinding : FragmentRetrofitTestBinding
    var mc : MainActivity = MainActivity()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        viewbinding = FragmentRetrofitTestBinding.inflate(inflater, container, false)
        return viewbinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mc.initRetrofit()

        //버튼눌렀을 때 network api 사용 << coroutine?

        viewbinding.getBtn.setOnClickListener {
            Log.e("server","btn click")
            CoroutineScope(Dispatchers.IO).launch{
                Log.e("server","on coroutine")
                mc.supplementService.userJoin("testid").enqueue(object : retrofit2.Callback<JoinResponse> {
                    override fun onFailure(call: Call<JoinResponse>, t: Throwable) {
                        Log.e("server","fail...")
                        Log.e("server_throwable",t.toString())
                        Log.e("server_call",call.toString())

                    }

                    override fun onResponse(call: Call<JoinResponse>, response: Response<JoinResponse>) {
                        if(response.code() != 200){
                            Log.e("server","response 성공!!")
                        }
                        Log.e("response : ", response?.body().toString())
                    }


                })
            }

        }

    }
}