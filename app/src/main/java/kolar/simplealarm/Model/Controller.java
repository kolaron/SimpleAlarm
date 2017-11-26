package kolar.simplealarm.Model;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by Kolar on 26.11.2017.
 */

public class Controller {

    public static final String INTENT_ALARMCLASS = "INTENT_ALARMCLASS";

    private Intent myIntent;
    private PendingIntent pendingIntent;
    private Context context;

    private static Controller instance;

    public static Controller getInstance(Context context) {
        if (instance == null)
            instance = new Controller(context);
        return instance;
    }

    private Controller(Context context) {
        this.context = context;
        myIntent = new Intent(context, AlarmReceiver.class);
    }

    public void startAlarm(AlarmClass alarmClass) {
        AlarmManager alarmManager = initIntents(alarmClass);
        if (alarmClass.isRepeat()) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, alarmClass.getDate().getTimeInMillis(), 10 * 1000, pendingIntent);
        } else {
            if (android.os.Build.VERSION.SDK_INT >= 19) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmClass.getDate().getTimeInMillis(), pendingIntent);
            } else {
                alarmManager.set(AlarmManager.RTC_WAKEUP, alarmClass.getDate().getTimeInMillis(), pendingIntent);
            }
        }
        Log.wtf("POSTPONE DEBUG", String.valueOf(alarmClass.hashCode()));
    }

    public void removeAlarm(AlarmClass alarmClass) {
        context.stopService(new Intent(context, AlarmSoundService.class));
        AlarmManager manager = initIntents(alarmClass);
        manager.cancel(pendingIntent);
    }

    public void postPoneAlarm(AlarmClass alarmClass) {
        AlarmClass alarm = alarmClass;
        alarm.getDate().add(Calendar.MINUTE, alarm.getPostponeMode());
        AlarmManager manager = initIntents(alarm);
        if (android.os.Build.VERSION.SDK_INT >= 19) {
            manager.setExact(AlarmManager.RTC_WAKEUP, alarm.getDate().getTimeInMillis(), pendingIntent);
        } else {
            manager.set(AlarmManager.RTC_WAKEUP, alarm.getDate().getTimeInMillis(), pendingIntent);
        }
    }

    private AlarmManager initIntents(AlarmClass alarm) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(INTENT_ALARMCLASS, alarm);
        myIntent.putExtra(INTENT_ALARMCLASS, bundle);
//        myIntent.putExtra("alarmSet", false);
        myIntent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        pendingIntent = PendingIntent.getBroadcast(context, alarm.hashCode(), myIntent, 0);
        return (AlarmManager) context.getSystemService(ALARM_SERVICE);
    }
}
