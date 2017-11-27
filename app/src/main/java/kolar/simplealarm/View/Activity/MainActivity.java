package kolar.simplealarm.View.Activity;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Switch;

import java.util.Calendar;

import kolar.simplealarm.Model.AlarmClass;
import kolar.simplealarm.R;
import kolar.simplealarm.View.Fragment.AlarmsListFragment;

public class MainActivity extends AppCompatActivity {

    private AlarmsListFragment alarmsListFragment;

    private static final int ALARMS_LIST_FRAGMENT = 1;
    private static final int ALARM_DETAIL_FRAGMENT = 2;
    private FloatingActionButton floatingbttn_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        alarmsListFragment = new AlarmsListFragment();
        alarmsListFragment.RestoreAlarms(getApplicationContext());
        setFragment(alarmsListFragment);
    }

    private void initViews() {
        floatingbttn_add = findViewById(R.id.floatingbttn_add);
        floatingbttn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmClass alarmClass = new AlarmClass();
                alarmsListFragment.addAlarm(alarmClass);
            }
        });
    }

    private void switchFragment(int fragment) {
        Fragment newFragment;
        switch (fragment) {
            case ALARMS_LIST_FRAGMENT:
                newFragment = alarmsListFragment;
                break;
            default:
                newFragment = alarmsListFragment;
                break;
        }
        setFragment(newFragment);
    }

    private void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentPlaceHolder, fragment).commit();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (alarmsListFragment != null)
            alarmsListFragment.SaveAlarms(getApplicationContext());
    }
}
