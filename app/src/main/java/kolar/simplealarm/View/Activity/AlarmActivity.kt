package kolar.simplealarm.View.Activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import kolar.simplealarm.Model.AlarmClass
import kolar.simplealarm.Model.AlarmSoundService
import kolar.simplealarm.Model.Controller
import kolar.simplealarm.Model.Controller.INTENT_ALARMCLASS
import kolar.simplealarm.R
import kotlinx.android.synthetic.main.activity_alarm.*
import java.util.*

/**
 * Created by ondrej.kolar on 22.01.2018.
 */
class AlarmActivity : AppCompatActivity() {

    private lateinit var alarmClass: AlarmClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED)
        window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON)

        alarmClass = intent.getBundleExtra(INTENT_ALARMCLASS).getSerializable(INTENT_ALARMCLASS) as AlarmClass
        initView()
        initListeners()
    }

    private fun initListeners() {
        bttnDefer.setOnClickListener {
            postPoneAlarm()
        }
        bttnOff.setOnClickListener {
            stopAlarm()
        }
    }

    private fun initView() {
        if (alarmClass.getPostpone() != AlarmClass.POSTPONE_MODE_NONE)
            relativeLayout_postpone.visibility = View.VISIBLE
        else
            relativeLayout_postpone.visibility = View.GONE
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN || keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            if (alarmClass.getPostpone() != AlarmClass.POSTPONE_MODE_NONE) {
                postPoneAlarm()
            } else {
                stopAlarm()
            }
        }
        return true
    }

    private fun postPoneAlarm() {
        Controller.postPoneAlarm(alarmClass, applicationContext)
        stopService(Intent(this@AlarmActivity, AlarmSoundService::class.java))
        finish()
    }

    private fun stopAlarm() {
        val list = Controller.getAlarmClasses(applicationContext)
        list?.let {
            if (!alarmClass.repeat) {
                for (i in 0 until list.size) {
                    if (list[i].id == alarmClass.id) {
                        list[i].activate = false
                        Controller.saveAlarmClasses(list, applicationContext)
                        break
                    }
                }
                Controller.stopAlarm(alarmClass, this)
                stopService(Intent(this@AlarmActivity, AlarmSoundService::class.java))
                finish()
            } else {
                stopService(Intent(this@AlarmActivity, AlarmSoundService::class.java))
                finish()
            }
        }
    }
}