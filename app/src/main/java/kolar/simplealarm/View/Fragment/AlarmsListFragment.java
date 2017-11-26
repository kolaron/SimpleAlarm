package kolar.simplealarm.View.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kolar.simplealarm.View.Adapter.AlarmListAdapter;

/**
 * Created by Kolar on 25.11.2017.
 */

public class AlarmsListFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = new RecyclerView(inflater.getContext());
        recyclerView.setAdapter(new AlarmListAdapter());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(inflater.getContext());
        recyclerView.setLayoutManager(layoutManager);
        return recyclerView;
    }
}
