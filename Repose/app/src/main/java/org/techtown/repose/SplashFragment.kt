package org.techtown.repose

import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import androidx.core.animation.doOnEnd
import androidx.navigation.NavController
import androidx.navigation.Navigation
import org.techtown.repose.databinding.FragMainBinding
import org.techtown.repose.databinding.FragSplashBinding


class SplashFragment : Fragment() {
    lateinit var  navController : NavController// 네비게이션 컨트롤러
    private var _binding : FragSplashBinding? = null // 뷰바인딩
    private val binding get() = _binding!! // 뷰바인딩

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragSplashBinding.inflate(inflater,container,false) // 뷰바인딩
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view) // 네비게이션 컨트롤러 view로 부터 가져오기
        init()

    }


    private fun init(){ // 앱 시작할때 애니메이션 효과
        var fadeIn = ObjectAnimator.ofFloat(binding.logoImageView, "alpha", 0f, 1f)
        Handler(Looper.getMainLooper()).postDelayed({
            fadeIn.duration = 1500
            fadeIn.start()

            fadeIn = ObjectAnimator.ofFloat(binding.textView31, "alpha", 0f, 1f)
            fadeIn.duration = 1500
            fadeIn.start()
        }, 1000)

        var fadeOut = ObjectAnimator.ofFloat(binding.textView31, "alpha", 1f, 0f)
        Handler(Looper.getMainLooper()).postDelayed({
            fadeOut.duration = 1500
            fadeOut.start()
            fadeOut.doOnEnd {
                navController.navigate(R.id.action_frag_splash_to_frag_start)
            }
        }, 4000)



    }
    // 뷰바인딩 destroy
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}