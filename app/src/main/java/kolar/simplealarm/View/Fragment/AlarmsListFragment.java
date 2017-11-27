package kolar.simplealarm.View.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kolar.simplealarm.Model.AlarmClass;
import kolar.simplealarm.Controller.Adapter.AlarmListAdapter;

/**
 * Created by Kolar on 25.11.2017.
 */

public class AlarmsListFragment extends Fragment {
    private AlarmListAdapter alarmListAdapter;

    public AlarmsListFragment() {
        alarmListAdapter = new AlarmListAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = new RecyclerView(inflater.getContext());
        recyclerView.setAdapter(alarmListAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(inflater.getContext());
        recyclerView.setLayoutManager(layoutManager);
        return recyclerView;
    }

    public void addAlarm(AlarmClass alarmClass) {
        if (alarmListAdapter != null)
            alarmListAdapter.addData(alarmClass);
    }

    public void SaveAlarms(Context context) {
        if (alarmListAdapter != null)
            alarmListAdapter.savePref(context);
    }

    public void RestoreAlarms(Context context) {
        if (alarmListAdapter != null)
            alarmListAdapter.restoreAlarms(context);
    }
}
