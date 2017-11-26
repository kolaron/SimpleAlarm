package kolar.simplealarm.View.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import kolar.simplealarm.Model.AlarmClass;
import kolar.simplealarm.Model.AlarmSoundService;
import kolar.simplealarm.Model.Controller;
import kolar.simplealarm.R;

import static kolar.simplealarm.Model.Controller.INTENT_ALARMCLASS;

/**
 * Created by Kolar on 26.11.2017.
 */

public class AlarmActivity extends Activity implements View.OnClickListener {

    private RelativeLayout bttnDefer, bttnOff, relativeLayout_postpone;

    private AlarmClass alarmClass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        Intent intent = getIntent();
        alarmClass = (AlarmClass) intent.getBundleExtra(INTENT_ALARMCLASS).getSerializable(INTENT_ALARMCLASS);
        initViews();
        initListeners();
    }

    private void initViews() {
        bttnDefer = findViewById(R.id.bttnDefer);
        bttnOff = findViewById(R.id.bttnOff);
        relativeLayout_postpone = findViewById(R.id.relativeLayout_postpone);

        if (alarmClass.getPostpone() != AlarmClass.postPone.NONE)
            relativeLayout_postpone.setVisibility(View.VISIBLE);
        else
            relativeLayout_postpone.setVisibility(View.GONE);
    }

    private void initListeners() {
        bttnDefer.setOnClickListener(this);
        bttnOff.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(bttnDefer)) {
            Controller.getInstance(AlarmActivity.this).postPoneAlarm(alarmClass);
            stopService(new Intent(AlarmActivity.this, AlarmSoundService.class));
            finish();
        } else if (v.equals(bttnOff)) {
            stopService(new Intent(AlarmActivity.this, AlarmSoundService.class));
            finish();
        }
    }
}
