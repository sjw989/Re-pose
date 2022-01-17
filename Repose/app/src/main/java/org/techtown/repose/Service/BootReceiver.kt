package org.techtown.repose.Service

import android.content.Intent

import android.content.BroadcastReceiver
import android.content.Context
import org.techtown.repose.MainActivity
import org.techtown.repose.MainActivity.Companion.user_timer


class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent) {
        val action = intent.action // intent로 전달받은 Action을 얻는다.
//        if (action == Intent.ACTION_BOOT_COMPLETED) {   // 시스템 부팅이 완료되었으면
//            Alarms.disableExpiredAlarms(context) // 이미 지난 알람은 취소한다.
//        }
//        Alarms.setNextAlert(context) // 다음번 알람을 설정한다.
//        MainActivity().db.userDao().
    }
    fun CancelAllAlarm(){
        for(i in user_timer.indices){

        }
    }
}