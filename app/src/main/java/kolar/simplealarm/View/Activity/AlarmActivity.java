package kolar.simplealarm.View.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import kolar.simplealarm.Model.AlarmClass;
import kolar.simplealarm.Model.AlarmSoundService;
import kolar.simplealarm.R;

import static kolar.simplealarm.Model.Controller.INTENT_ALARMCLASS;

/**
 * Created by Kolar on 26.11.2017.
 */

public class AlarmActivity extends Activity implements View.OnClickListener {

    private Button bttnDefer, bttnOff;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        Intent intent = getIntent();
        AlarmClass alarmClass = (AlarmClass) intent.getBundleExtra(INTENT_ALARMCLASS).getSerializable(INTENT_ALARMCLASS);
        initViews();
        initListeners();
        if (alarmClass.isDefer()) {

        } else {

        }
    }

    private void initViews() {
        bttnDefer = findViewById(R.id.bttnDefer);
        bttnOff = findViewById(R.id.bttnOff);
    }

    private void initListeners() {
        bttnDefer.setOnClickListener(this);
        bttnOff.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(bttnDefer)) {

        } else if (v.equals(bttnOff)) {
            stopService(new Intent(AlarmActivity.this, AlarmSoundService.class));
        }
    }
}
