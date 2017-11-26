package kolar.simplealarm.Model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import kolar.simplealarm.View.Activity.AlarmActivity;

import static kolar.simplealarm.Model.Controller.INTENT_ALARMCLASS;

/**
 * Created by Kolar on 26.11.2017.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, AlarmSoundService.class));
        Intent i = new Intent(context, AlarmActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra(INTENT_ALARMCLASS, intent.getBundleExtra(INTENT_ALARMCLASS));
        context.startActivity(i);
    }
}
