package kolar.simplealarm.View.Activity

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import kolar.simplealarm.Model.AlarmClass
import kolar.simplealarm.R
import kolar.simplealarm.View.Fragment.AlarmsListFragment
import java.util.*

/**
 * Created by ondrej.kolar on 22.01.2018.
 */
class MainActivity : AppCompatActivity() {

    lateinit var alarmsListFragment: AlarmsListFragment

    private val ALARMS_LIST_FRAGMENT = 1
    private val ALARM_DETAIL_FRAGMENT = 2
    private var floatingbttn_add: FloatingActionButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        alarmsListFragment = AlarmsListFragment()
        alarmsListFragment.RestoreAlarms(applicationContext)
        setFragment(alarmsListFragment)
    }

    private fun initViews() {
        floatingbttn_add = findViewById(R.id.floatingbttn_add)
        floatingbttn_add!!.setOnClickListener {
            val alarmClass = AlarmClass()
            alarmClass.date.set(Calendar.SECOND, 0)
            alarmClass.date.set(Calendar.MILLISECOND, 0)
            alarmsListFragment.addAlarm(alarmClass)
        }
    }

    private fun switchFragment(fragment: Int) {
        val newFragment: Fragment?
        when (fragment) {
            ALARMS_LIST_FRAGMENT -> newFragment = alarmsListFragment
            else -> newFragment = AlarmsListFragment()
        }
        setFragment(newFragment)
    }

    private fun setFragment(fragment: Fragment?) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentPlaceHolder, fragment).commit()
    }

    override fun onPause() {
        super.onPause()
        alarmsListFragment.SaveAlarms(applicationContext)
    }
}