package kolar.simplealarm.Model

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import java.util.*

/**
 * Created by ondrej.kolar on 22.01.2018.
 */
object Controller {

    const val INTENT_ALARMCLASS = "INTENT_ALARMCLASS"
    const val SHARED_KEY_SIZE = "SIZE"

    fun saveAlarmClasses(alarmClasses: ArrayList<AlarmClass>, context: Context) {
        val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
        editor.putInt(SHARED_KEY_SIZE, alarmClasses.size)
        for (i in alarmClasses.indices) {
            editor.putString(i.toString(), alarmClasses[i].toString())
        }
        editor.apply()
    }

    fun getAlarmClasses(context: Context): ArrayList<AlarmClass>? {
        try {
            val size = PreferenceManager.getDefaultSharedPreferences(context).getInt(SHARED_KEY_SIZE, -1)
            if (size > 0) {
                val resultList = ArrayList<AlarmClass>()
                for (i in 0 until size) {
                    val item = PreferenceManager.getDefaultSharedPreferences(context).getString(i.toString(), null)
                    if (item != null) {
                        val para = item.split(";".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
                        val alarmClass = AlarmClass()
                        val cal = Calendar.getInstance()
                        alarmClass.id = java.lang.Long.parseLong(para[0])
                        cal.timeInMillis = java.lang.Long.parseLong(para[1])
                        alarmClass.date = cal
                        alarmClass.setPostpone(Integer.parseInt(para[2]).toLong())
                        alarmClass.repeat = java.lang.Boolean.parseBoolean(para[3])
                        alarmClass.activate = java.lang.Boolean.parseBoolean(para[4])
                        resultList.add(alarmClass)
                    }
                }
                return resultList
            }
            return null
        } catch (e: Exception) {
            return null
        }
    }

    fun postPoneAlarm(alarmClass: AlarmClass, context: Context) {
        alarmClass.date.add(Calendar.MINUTE, alarmClass.getPostpone().toInt())
        val pi = PendingIntent.getBroadcast(context, 1, initIntents(alarmClass, context), PendingIntent.FLAG_UPDATE_CURRENT)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        if (android.os.Build.VERSION.SDK_INT >= 19) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmClass.date.timeInMillis, pi)
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, alarmClass.date.timeInMillis, pi)
        }
    }

    private fun initIntents(alarm: AlarmClass, context: Context): Intent {
        val bundle = Bundle()
        bundle.putSerializable(INTENT_ALARMCLASS, alarm)
        val myIntent = Intent(context, AlarmReceiver::class.java)
        myIntent.putExtra(INTENT_ALARMCLASS, bundle)
        //        myIntent.putExtra("alarmSet", false);
        myIntent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
        return myIntent
    }

    fun startAlarm(alarm: AlarmClass, context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pi = PendingIntent.getBroadcast(context, 1, initIntents(alarm, context), PendingIntent.FLAG_UPDATE_CURRENT)
        if (alarm.repeat) {
            //        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime(), startTime, pi)
        } else {
            if (android.os.Build.VERSION.SDK_INT >= 19) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarm.date.timeInMillis, pi)
            } else {
                alarmManager.set(AlarmManager.RTC_WAKEUP, alarm.date.timeInMillis, pi)
            }
        }
    }

    fun stopAlarm(alarm: AlarmClass, context: Context) {
        val pi = PendingIntent.getBroadcast(context, 1, initIntents(alarm, context), PendingIntent.FLAG_UPDATE_CURRENT)
        val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pi)
    }
}