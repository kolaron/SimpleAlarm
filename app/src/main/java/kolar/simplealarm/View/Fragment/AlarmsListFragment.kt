package kolar.simplealarm.View.Fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kolar.simplealarm.Controller.Adapter.AlarmListAdapter
import kolar.simplealarm.Model.AlarmClass

/**
 * Created by ondrej.kolar on 22.01.2018.
 */
class AlarmsListFragment : Fragment() {

    private val alarmListAdapter: AlarmListAdapter = AlarmListAdapter()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        inflater?.let {
            val recyclerView = RecyclerView(inflater.context)
            recyclerView.adapter = alarmListAdapter
            val layoutManager = LinearLayoutManager(inflater.context)
            recyclerView.layoutManager = layoutManager
            return recyclerView
        }
        return null
    }

    fun addAlarm(alarmClass: AlarmClass) {
        alarmListAdapter.addData(alarmClass)
    }

    fun saveAlarms(context: Context) {
        alarmListAdapter.savePref(context)
    }

    fun restoreAlarms(context: Context) {
        alarmListAdapter.restoreAlarms(context)
    }
}