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
import android.view.View.INVISIBLE
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.core.view.marginBottom
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import org.techtown.repose.ExerciseFragment.Companion.exercise_list
import org.techtown.repose.MainActivity.Companion.user_confirmNum
import java.text.SimpleDateFormat
import java.util.*

class ExerciseRecyclerViewAdapter(private val context : Context, private val data : List<Pair<String,Int>>,
                                  private val idx : Int, private val pose_name:String, private val view:View) :
                                    RecyclerView.Adapter<ExerciseRecyclerViewAdapter.ViewHolder>() {
    lateinit var  navController : NavController// 네비게이션 컨트롤러

    companion object{
        var is_dialog : Boolean = false // 다이얼로그가 활성화중인지 저장하는 변수 -> Handler + 뒤로가기버튼 때문에 앱터지는거 방지
        // ExerciseFragment의 뒤로가기 버튼을 막을 거라서 전역변수로 선언해줌.
        // 미리 선정해놓은 pose 리스트의 가이드 --> 우선은 텍스트만

    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv: TextView = view.findViewById(R.id.tv_exercise_explain)
        val iv : ImageView = view.findViewById(R.id.iv_exercise)
        val xml_recyclerview_component: LinearLayout = view.findViewById(R.id.xml_exercise_recyclerview_component)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rcv_exercise_component, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        setText(holder, position) // 운동 text 설명 설정
        setImage(holder,position) // 운동 이미지 설정
        makeCompleteButton(holder, position) // 운동완료 버튼 동적 생성
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setText(holder: ViewHolder, position: Int) {
        holder.tv.text = data[position].first
    }
    fun setImage(holder: ViewHolder, position: Int){
        if(data[position].second == 0){
            val params : LinearLayout.LayoutParams = LinearLayout.LayoutParams(0,200)
            holder.iv.layoutParams = params
            holder.iv.visibility = INVISIBLE
        }
        else{
            holder.iv.setImageResource(data[position].second)
        }
    }

    fun makeCompleteButton(holder: ViewHolder, position: Int) {
        if (idx == exercise_list[MainActivity.pose_list.indexOf(pose_name)].size - 1
            && position == data.size - 1) {// 지금 운동이 마지막 운동이고, 마지막 설명인 경우
            // 운동완료 버튼 동적 생성
            val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(600,150)
            params.setMargins(0,20,0,20)
            var btn = Button(context)
            btn.setText("운동완료")
            btn.setTextColor(Color.parseColor("#ffffff"))
            btn.setBackgroundResource(R.drawable.green_border)
            btn.layoutParams = params
            btn.marginBottom
            holder.xml_recyclerview_component.addView(btn)
            navController = Navigation.findNavController(view) // 네비게이션 컨트롤러 view로 부터 가져오기

            // 운동완료버튼 이벤트 리스너
            btn.setOnClickListener{
                if(MainFragment.is_countDown){ // 버튼을 눌렀는데 현재 카운트다운 중이라면
                    MainFragment.is_countDown = false
                    MainFragment.is_exercise_complete[MainFragment.time_idx] = true // 해당 시간에 운동 헀음을 표시 -> Timer 안뜨게 하기위함
                    user_confirmNum++ // 운동완료 버튼 누른 횟수 ++
                }

                val dialog = make_dialog(context)

                val nav = it
                dialog.show()
                dialog.setOnDismissListener {
                    Handler(Looper.getMainLooper()).postDelayed({
                        is_dialog = true
                        nav.findNavController().popBackStack()
                    },1500)
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
