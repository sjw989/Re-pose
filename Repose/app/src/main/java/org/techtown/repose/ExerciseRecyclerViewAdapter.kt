package org.techtown.repose

import android.app.AlertDialog
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
import android.widget.Toast
import androidx.core.view.marginBottom
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView

class ExerciseRecyclerViewAdapter(private val context : Context, private val data : List<String>,
                                  private val idx : Int, private val pose_name:String, private val view:View) :
                                    RecyclerView.Adapter<ExerciseRecyclerViewAdapter.ViewHolder>() {
    lateinit var  navController : NavController// 네비게이션 컨트롤러

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
                val dialog = make_dialog(context)

                val nav = it
                dialog.show()
                Log.e("a","0")
                dialog.setOnDismissListener {
                    Handler(Looper.getMainLooper()).postDelayed({
                        nav.findNavController().popBackStack()
                    },1000)
                }
            }
        }
    }
    fun make_dialog(context :Context) : AlertDialog{
        val layoutInflater = LayoutInflater.from(context)
        val view  = layoutInflater.inflate(R.layout.dialog_exercise_complete,null)
        val alertDialog = AlertDialog.Builder(context).setView(view).create()
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.window!!.setWindowAnimations(R.style.ani_dialog)
        return alertDialog
    }
}
