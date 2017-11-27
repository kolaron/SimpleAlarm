package kolar.simplealarm.Controller.Adapter;

import android.animation.ObjectAnimator;
import android.app.TimePickerDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;

import kolar.simplealarm.Model.AlarmClass;
import kolar.simplealarm.Model.Controller;
import kolar.simplealarm.R;

/**
 * Created by Kolar on 25.11.2017.
 */

public class AlarmListAdapter extends RecyclerView.Adapter<AlarmListAdapter.ViewHolder> {

    private ArrayList<AlarmClass> list = new ArrayList<>();

    public AlarmListAdapter() {
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.alarmlist_item, parent, false);
        return new ViewHolder(view, parent.getContext());
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final AlarmClass alarmClass = list.get(position);

        holder.textView_time.setText(alarmClass.getDate().get(Calendar.HOUR_OF_DAY) + ":" + alarmClass.getDate().get(Calendar.MINUTE));
        holder.checkbox_repeat.setChecked(alarmClass.isRepeat());
        holder.switch_activate.setChecked(alarmClass.isActivate());
        if (alarmClass.getPostponeMode() != AlarmClass.POSTPONE_MODE_NONE) {
            holder.checkbox_defer.setChecked(true);
            holder.linearLayout_expand.setVisibility(View.VISIBLE);
            holder.changePropertiesText(holder.context, holder.checkbox_repeat, holder.checkbox_defer, holder.textView_properties);
            if (alarmClass.getPostponeMode() == AlarmClass.POSTPONE_MODE_FIVE_MINUTES)
                holder.radioBttn5.setChecked(true);
            else if (alarmClass.getPostponeMode() == AlarmClass.POSTPONE_MODE_TEN_MINUTES)
                holder.radioBttn10.setChecked(true);
            else if (alarmClass.getPostponeMode() == AlarmClass.POSTPONE_MODE_FIFTEEN_MINUTES)
                holder.radioBttn15.setChecked(true);
            else if (alarmClass.getPostponeMode() == AlarmClass.POSTPONE_MODE_THIRTY_MINUTES)
                holder.radioBttn30.setChecked(true);
        } else{
            holder.radioBttn5.setChecked(true);
            holder.changePropertiesText(holder.context, holder.checkbox_repeat, holder.checkbox_defer, holder.textView_properties);
        }

        holder.checkbox_defer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    holder.linearLayout_expand.setVisibility(View.VISIBLE);
                    holder.changePropertiesText(holder.context, holder.checkbox_repeat, holder.checkbox_defer, holder.textView_properties);
                    alarmClass.setPostponeMode(AlarmClass.POSTPONE_MODE_FIVE_MINUTES);
                } else {
                    holder.linearLayout_expand.startAnimation(AnimationUtils.loadAnimation(holder.context, R.anim.fade_in));
                    holder.linearLayout_expand.setVisibility(View.GONE);
                    holder.changePropertiesText(holder.context, holder.checkbox_repeat, holder.checkbox_defer, holder.textView_properties);
                    alarmClass.setPostponeMode(AlarmClass.POSTPONE_MODE_NONE);
                }
            }
        });

        holder.checkbox_repeat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                holder.changePropertiesText(holder.context, holder.checkbox_repeat, holder.checkbox_defer, holder.textView_properties);
                alarmClass.setRepeat(isChecked);
            }
        });

        holder.relativeLayout_expandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.expandeLayout(holder.linearLayout_expandMain, holder.relativeLayout_expandButton, holder.textView_properties, holder.imageView_remove);
            }
        });

        holder.switch_activate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                alarmClass.setActivate(isChecked);
                if (isChecked)
                    Controller.getInstance(holder.context).startAlarm(alarmClass);
                else
                    Controller.getInstance(holder.context).removeAlarm(alarmClass);
            }
        });

        holder.textView_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(holder.context, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            try {
                                alarmClass.getDate().set(Calendar.HOUR_OF_DAY, selectedHour);
                                alarmClass.getDate().set(Calendar.MINUTE, selectedMinute);
                                holder.textView_time.setText(selectedHour + ":" + selectedMinute);
                            } catch (Exception ex) {
                                ex.fillInStackTrace();
                            }
                        }
                    }, alarmClass.getDate().get(Calendar.HOUR_OF_DAY), alarmClass.getDate().get(Calendar.MINUTE), true);//Yes 24 hour time
                    mTimePicker.show();
                } catch (Exception ex) {

                }

            }
        });
        holder.imageView_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Controller.getInstance(holder.context).removeAlarm(alarmClass);
                list.remove(position);
                notifyDataSetChanged();
            }
        });

        listener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    if (buttonView.equals(holder.radioBttn5))
                        alarmClass.setPostponeMode(AlarmClass.POSTPONE_MODE_FIVE_MINUTES);
                    else if (buttonView.equals(holder.radioBttn10))
                        alarmClass.setPostponeMode(AlarmClass.POSTPONE_MODE_TEN_MINUTES);
                    else if (buttonView.equals(holder.radioBttn15))
                        alarmClass.setPostponeMode(AlarmClass.POSTPONE_MODE_FIFTEEN_MINUTES);
                    else if (buttonView.equals(holder.radioBttn30))
                        alarmClass.setPostponeMode(AlarmClass.POSTPONE_MODE_THIRTY_MINUTES);
            }
        };

        holder.radioBttn5.setOnCheckedChangeListener(listener);
        holder.radioBttn10.setOnCheckedChangeListener(listener);
        holder.radioBttn15.setOnCheckedChangeListener(listener);
        holder.radioBttn30.setOnCheckedChangeListener(listener);
    }

    private CompoundButton.OnCheckedChangeListener listener;

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addData(AlarmClass alarmClass) {
        list.add(alarmClass);
        notifyDataSetChanged();
    }

    public void savePref(Context context) {
        Controller.getInstance(context).saveAlarmClasses(list);
    }

    public void restoreAlarms(Context context) {
        ArrayList<AlarmClass> l = Controller.getInstance(context).getAlarmClasses();
        if (l != null) {
            this.list = l;
            notifyDataSetChanged();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        Context context;
        TextView textView_time, textView_properties;
        Switch switch_activate;
        CheckBox checkbox_repeat, checkbox_defer;
        RadioGroup radioGroup_defer;
        RadioButton radioBttn5, radioBttn10, radioBttn15, radioBttn30;
        LinearLayout linearLayout_expand, linearLayout_expandMain;

        RelativeLayout relativeLayout_expandButton;
        ImageView imageView_remove;

        public ViewHolder(View itemView, Context context) {
            super(itemView);
            this.context = context;
            textView_time = itemView.findViewById(R.id.textView_time);
            switch_activate = itemView.findViewById(R.id.switch_activate);
            checkbox_repeat = itemView.findViewById(R.id.checkbox_repeat);
            checkbox_defer = itemView.findViewById(R.id.checkbox_defer);
            radioGroup_defer = itemView.findViewById(R.id.radioGroup_defer);
            radioBttn5 = itemView.findViewById(R.id.radioBttn5);
            radioBttn10 = itemView.findViewById(R.id.radioBttn10);
            radioBttn15 = itemView.findViewById(R.id.radioBttn15);
            radioBttn30 = itemView.findViewById(R.id.radioBttn30);
            linearLayout_expand = itemView.findViewById(R.id.linearLayout_expand);
            relativeLayout_expandButton = itemView.findViewById(R.id.relativeLayout_expandButton);
            textView_properties = itemView.findViewById(R.id.textView_properties);
            imageView_remove = itemView.findViewById(R.id.imageView_remove);
            linearLayout_expandMain = itemView.findViewById(R.id.linearLayout_expandMain);
        }

        private void expandeLayout(LinearLayout linearLayout_expandMain, RelativeLayout expandButton, TextView textView_properties, ImageView imageView_remove) {
            Animation animFadeOut = AnimationUtils.loadAnimation(context, R.anim.fade_out);
            Animation animFadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in);
            if (linearLayout_expandMain.getVisibility() == View.GONE) {
                linearLayout_expandMain.startAnimation(animFadeIn);
                createRotateAnimator(expandButton, 0f, 180f).start();
                textView_properties.setVisibility(View.GONE);
                imageView_remove.setVisibility(View.VISIBLE);
                linearLayout_expandMain.setVisibility(View.VISIBLE);
            } else {
                linearLayout_expandMain.startAnimation(animFadeOut);
                createRotateAnimator(expandButton, 180f, 0f).start();
                textView_properties.setVisibility(View.VISIBLE);
                imageView_remove.setVisibility(View.GONE);
                linearLayout_expandMain.setVisibility(View.GONE);
            }
        }

        private void changePropertiesText(Context context, CheckBox checkbox_repeat, CheckBox checkbox_defer, TextView textView) {
            String repeat;
            String defer;
            if (checkbox_repeat.isChecked()) {
                repeat = context.getResources().getString(R.string.repeat);
            } else {
                repeat = "";
            }

            if (checkbox_defer.isChecked()) {
                if (repeat.equals("")) {
                    defer = context.getResources().getString(R.string.defer);
                } else {
                    defer = ", " + context.getResources().getString(R.string.defer);
                }
            } else {
                defer = "";
            }
            textView.setText(repeat + defer);
        }

        private ObjectAnimator createRotateAnimator(final View target, final float from, final float to) {
            ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
            animator.setDuration(300);
            animator.setInterpolator(new LinearInterpolator());
            return animator;
        }
    }
}
