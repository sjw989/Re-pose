package org.techtown.repose

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginBottom
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.frag_show_exercise.view.*
import org.techtown.repose.databinding.FragShowExerciseBinding
import kotlinx.coroutines.delay as delay1

class ExerciseRecyclerViewAdapter(private val context : Context, private val data : List<String>,
                                  private val idx : Int, private val pose_name:String, private val view:View) :
                                    RecyclerView.Adapter<ExerciseRecyclerViewAdapter.ViewHolder>() {
    lateinit var  navController : NavController// 네비게이션 컨트롤러
    private var _binding : FragShowExerciseBinding? = null // 뷰바인딩
    private val binding get() = _binding!! // 뷰바인딩

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv: TextView
        val xml_recyclerview_component: LinearLayout
        init {
            tv = view.findViewById(R.id.tv_exercise_explain)
            xml_recyclerview_component = view.findViewById(R.id.xml_exercise_recyclerview_component)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rcv_exercise_component, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        setText(holder, position) // 운동 text 설명 설정
        //setImage(holder,position) --> 운동 사진 설정
        makeCompleteButton(holder, position) // 운동완료 버튼 동적 생성
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setText(holder: ViewHolder, position: Int) {
        holder.tv.text = data[position]
    }

    fun makeCompleteButton(holder: ViewHolder, position: Int) {
        if (idx == MainActivity.exercise_list[MainActivity.pose_list.indexOf(pose_name)].size - 1
            && position == data.size - 1) {// 지금 운동이 마지막 운동이고, 마지막 설명인 경우
            // 운동완료 버튼 동적 생성
            val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(0,20,0,20)
            var btn = Button(context)
            btn.setText("운동완료")
            btn.layoutParams = params
            btn.marginBottom
            holder.xml_recyclerview_component.addView(btn)
            navController = Navigation.findNavController(view) // 네비게이션 컨트롤러 view로 부터 가져오기
            // 운동완료버튼 이벤트 리스너
            btn.setOnClickListener{
                val dialog = CompleteDialog(context)
                Handler(Looper.getMainLooper()).postDelayed({
                    dialog.show()
                },1000)

                Handler(Looper.getMainLooper()).postDelayed({
                    dialog.dismiss()
                },3000)
                Handler(Looper.getMainLooper()).postDelayed({
                    it.findNavController().navigate(R.id.action_frag_show_exercise_to_frag_main)
                    navController.popBackStack()
                },4000)
            }
        }
    }
    class CompleteDialog constructor(context: Context) : Dialog(context){
        init {
            setCanceledOnTouchOutside(false)
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setContentView(R.layout.dialog_exercise_complete)
        }
    }
}
