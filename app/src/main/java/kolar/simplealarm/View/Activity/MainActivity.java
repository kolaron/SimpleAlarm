package kolar.simplealarm.View.Activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Switch;

import kolar.simplealarm.R;
import kolar.simplealarm.View.Fragment.AlarmsListFragment;

public class MainActivity extends AppCompatActivity {

    private static final int ALARMS_LIST_FRAGMENT = 1;
    private static final int ALARM_DETAIL_FRAGMENT = 2;

    private FrameLayout fragmentPlaceHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setFragment(new AlarmsListFragment());
    }

    private void initViews() {
        fragmentPlaceHolder = findViewById(R.id.fragmentPlaceHolder);
    }

    private void switchFragment(int fragment) {
        Fragment newFragment;
        switch (fragment) {
            case ALARMS_LIST_FRAGMENT:
                newFragment = new AlarmsListFragment();
                break;
            default:
                newFragment = new AlarmsListFragment();
                break;
        }
        setFragment(newFragment);
    }

    private void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentPlaceHolder, fragment).commit();
    }
}
