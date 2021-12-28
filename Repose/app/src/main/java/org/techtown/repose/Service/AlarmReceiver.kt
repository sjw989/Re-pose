package org.techtown.repose.Service

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import org.techtown.repose.MainActivity
import org.techtown.repose.R
import java.util.*

class AlarmReceiver : BroadcastReceiver() {

    companion object {
        const val TAG = "AlarmReceiver"
        const val NOTIFICATION_ID = 0
        const val PRIMARY_CHANNEL_ID = "primary_notification_channel"
    }

    lateinit var notificationManager: NotificationManager

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "Received intent : $intent")
        // 요일 체킹
        val cal = Calendar.getInstance()
        if (!MainActivity.user_days?.get(AdjustWeekDay(cal[Calendar.DAY_OF_WEEK]))!!) {
            Log.e( "Received intent","not checked weekday")
            return
        }
        notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel()
        deliverNotification(context)
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun deliverNotification(context: Context) {
        val contentIntent = Intent(context, MainActivity::class.java)
        val contentPendingIntent = PendingIntent.getActivity(
            context,
            NOTIFICATION_ID,
            contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val builder =
            NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID)
                .setSmallIcon(R.drawable.notice)
                .setContentTitle("자세 교정!")
                .setContentText(SelectRandomText())
                .setContentIntent(contentPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)

        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }

    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                PRIMARY_CHANNEL_ID,
                "Stand up notification",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = "AlarmManager Tests"
            notificationManager.createNotificationChannel(
                notificationChannel)
        }
    }

    fun SelectRandomText():String{
        return when(Random().nextInt(7)) {
            0 -> "근육들이 일도 안하고 놀고 있네요. 한번 혼내주러 가시죠!"
            1 -> "좀 더 젊어질 시간입니다!"
            2 -> "스트레칭 한번 하고 가시죠!"
            3 -> "느슨해진 몸에 긴장감을 더해줄 시간입니다!"
            4 -> "어디선가 우두둑 소리가 들리는 것 같은데.. 착각이겠죠?"
            5 -> "스트레칭 하고 메달 받자! 앗 광고 아닙니다!"
            6 -> "저와 한 약속.. 잊지 않았죠? 자세 고치러 갑시다!"
            else -> "저와 한 약속.. 잊지 않았죠? 자세 고치러 갑시다!"
        }
    }

    fun AdjustWeekDay(day_of_week: Int): Int{
        //일 월 ... 토 -> 월 화 ... 일
        return if(day_of_week == 1) 6
        else day_of_week-2
    }
}