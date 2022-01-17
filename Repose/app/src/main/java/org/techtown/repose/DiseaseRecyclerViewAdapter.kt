package org.techtown.repose

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginBottom
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView


class DiseaseRecyclerViewAdapter(private val context: Context, private val disease_list: List<String>,
                                 private val view: View, private val pose_name : String?) : RecyclerView.Adapter<DiseaseRecyclerViewAdapter.ViewHolder>(){
    // 질병 1 ~ 14 텍스트 내용
   companion object{
        private val disease_text_list = listOf<String>("앉아서 하는 작업을 할 때 발생하는 질환으로 근육이 긴장하게 되어 일상생활에 큰 피로감을 느끼게 되며 관절염이 발생할 수 있고 바른 자세를 잡는 것이 힘들어집니다.",
            "한 쪽 골반에 오랜시간 체중 실어지면서 발생하는 질환으로 두통과 어깨 눌림현상을 통해 하체의 혈액순환을 방해하고 하복부 및 하체 비만을 초래하기도 합니다.",
            "골반의 비틀림으로 인해 발생하는 통증으로 골반이 교정되지 않으면 지속적인 통증이 발생합니다.",
            "장시간 전자기기를 사용하거나 독서 및 필기를 할 경우 발생하며 어지러움과 단기간 피로를 발생시킵니다.",
            "장시간 전자기기를 사용하거나 잘못된 자세로 수면을 취할 때 발생하는 통증으로 안면비대칭 및 목 비틀림을 유발할 수 있습니다.",
            "잘못된 걸음을 가질 때 발생하는 통증으로 관절에 나쁜 영향을 끼칠 수 있습니다.",
            "장시간 서 있을 경우 발생하는 통증으로 꾸준한 스트레칭 및 휴식이 필요합니다.",
            "손목을 과도하게 사용할 경우 발생하는 통증으로 초기에는 손목이 쑤시거나 아픈 정도지만 방치된다면 손목 신경이 손상되어 정중신경 전체를 절개해야 할 수도 있습니다.",
            "척추와 경추의 균형을 잡지못하는 경우, 안면비대칭을 잠재적으로 가지게 되며 어지러움, 이명을 동반할 수 있습니다.",
            "한 쪽 어깨에 오랜시간 하중이 가해지면서 발생하는 질환으로 양 쪽 어깨의 비대칭이 발생할 수 있습니다.",
            "장시간 앉아 있을 경우 발생하는 통증으로 신체 비대칭을 유발할 수 있습니다.",
            "엎드려자기, 양반다리 등의 잘못된 자세를 통해 발생하는 질환으로 척추가 비틀어져 신체의 모든 부위가 비틀어질 수 있습니다.",
            "잘못된 다리 자세를 취한다면 발생하는 질환으로 색소 침착, 신경 손상, 창상 감염, 피부 반흔, 폐색전증 및 심부정맥 혈전증이 발생할 수 있습니다.",
            "장시간 앉아 있거나 잘못된 걸음을 가질 때 발생하는 통증으로 신체 비대칭을 유발할 수 있습니다.")
   }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val tv_name : TextView = itemView.findViewById<TextView>(R.id.tv_diseaseName)
        val tv_text : TextView = itemView.findViewById<TextView>(R.id.tv_diseaseText)
        val iv : ImageView = itemView.findViewById<ImageView>(R.id.iv_disease)
        fun setData(disease_name : String){
            var image = 0
            var name = ""
            var text = ""
            when(disease_name){
                "disease1"-> {image = R.drawable.disease1; name = "거북목 증후군"; text = disease_text_list[0]}
                "disease2"-> {image = R.drawable.disease2; name = "골반비틀림" ; text = disease_text_list[1]}
                "disease3"-> { image = R.drawable.disease3; name = "골반통증" ; text = disease_text_list[2]}
                "disease4"-> { image = R.drawable.disease4; name = "눈 피로"; text = disease_text_list[3]}
                "disease5"-> { image = R.drawable.disease5; name = "목 통증"; text = disease_text_list[4]}
                "disease6"-> { image = R.drawable.disease6; name = "무릎 통증"; text = disease_text_list[5]}
                "disease7"-> { image = R.drawable.disease7; name = "발 통증"; text = disease_text_list[6]}
                "disease8"-> { image = R.drawable.disease8; name = "손목 터널 증후군"; text = disease_text_list[7]}
                "disease9"-> { image = R.drawable.disease9; name = "안면 비대칭"; text = disease_text_list[8]}
                "disease10"-> { image = R.drawable.disease10; name = "어깨 비틀림"; text = disease_text_list[9]}
                "disease11"-> { image = R.drawable.disease11; name = "엉덩이 통증"; text = disease_text_list[10]}
                "disease12"-> { image = R.drawable.disease12; name = "척추비틀림"; text = disease_text_list[11]}
                "disease13"-> { image = R.drawable.disease13; name = "하지정맥류"; text = disease_text_list[12]}
                "disease14"-> { image = R.drawable.disease14; name = "허리 통증"; text = disease_text_list[13]}
            }
            tv_name.text = name
            tv_text.text = text
            iv.setImageResource(image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // 리사이클러뷰에 사용할 layout Return
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rcv_disease_component,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val disease_name = disease_list[position]
        holder.setData(disease_name)
    }

    override fun getItemCount(): Int {
        return disease_list.size
    }
}
