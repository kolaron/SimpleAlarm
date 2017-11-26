package kolar.simplealarm.Model;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

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

    public void startAlarm(String time, boolean repeat, AlarmClass alarmClass) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(INTENT_ALARMCLASS, alarmClass);
        myIntent.putExtra(INTENT_ALARMCLASS, bundle);
        myIntent.putExtra("alarmSet", false);
        myIntent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        pendingIntent = PendingIntent.getBroadcast(context, 0, myIntent, 0);
        Calendar calendar = Calendar.getInstance();
        String[] parse = time.split(":");
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(parse[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(parse[1]));
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        if (repeat) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 10 * 1000, pendingIntent);
        } else {
            if (android.os.Build.VERSION.SDK_INT >= 19) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            } else {
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }
        }
    }

    public void stopAlarm() {
        context.stopService(new Intent(context, AlarmSoundService.class));
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
    }
}
