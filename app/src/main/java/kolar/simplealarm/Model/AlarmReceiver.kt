package kolar.simplealarm.Model

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import kolar.simplealarm.Model.Controller.INTENT_ALARMCLASS
import kolar.simplealarm.View.Activity.AlarmActivity

/**
 * Created by ondrej.kolar on 22.01.2018.
 */
class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            intent?.let {
//                context.startService(Intent(context, AlarmSoundService::class.java))
                val i = Intent(context, AlarmActivity::class.java)
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                i.putExtra(INTENT_ALARMCLASS, intent.getBundleExtra(INTENT_ALARMCLASS))
                context.startActivity(i)
            }
        }
    }
}