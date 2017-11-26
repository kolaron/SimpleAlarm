package kolar.simplealarm.View.Adapter;

import android.animation.ObjectAnimator;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
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

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import kolar.simplealarm.Model.AlarmClass;
import kolar.simplealarm.Model.Controller;
import kolar.simplealarm.R;

/**
 * Created by Kolar on 25.11.2017.
 */

public class AlarmListAdapter extends RecyclerView.Adapter<AlarmListAdapter.ViewHolder> {

    private ArrayList<AlarmClass> list = new ArrayList<>();

    public AlarmListAdapter() {
        list.add(new AlarmClass());
        list.add(new AlarmClass());
        list.add(new AlarmClass());
        list.add(new AlarmClass());
        list.add(new AlarmClass());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.alarmlist_item, parent, false);
        return new ViewHolder(view, parent.getContext());
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final AlarmClass alarmClass = list.get(position);
        holder.checkbox_defer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    holder.linearLayout_expand.setVisibility(View.VISIBLE);
                    holder.changePropertiesText(holder.context, holder.checkbox_repeat, holder.checkbox_defer, holder.textView_properties);
                    alarmClass.setPostpone(AlarmClass.postPone.FIVE_MINUTES);
                } else {
                    holder.linearLayout_expand.startAnimation(AnimationUtils.loadAnimation(holder.context, R.anim.fade_in));
                    holder.linearLayout_expand.setVisibility(View.GONE);
                    holder.changePropertiesText(holder.context, holder.checkbox_repeat, holder.checkbox_defer, holder.textView_properties);
                    alarmClass.setPostpone(AlarmClass.postPone.NONE);
                }
            }
        });

        holder.checkbox_repeat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                holder.changePropertiesText(holder.context, holder.checkbox_repeat, holder.checkbox_defer, holder.textView_properties);
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
                if (isChecked) {
                    Controller.getInstance(holder.context).startAlarm(alarmClass);
                } else {
                    Controller.getInstance(holder.context).removeAlarm(alarmClass);
                }
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
        holder.radioBttn5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    alarmClass.setPostpone(AlarmClass.postPone.FIVE_MINUTES);
            }
        });
        holder.radioBttn10.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    alarmClass.setPostpone(AlarmClass.postPone.TEN_MINUTES);
            }
        });
        holder.radioBttn15.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    alarmClass.setPostpone(AlarmClass.postPone.FIFTEEN_MINUTES);
            }
        });
        holder.radioBttn30.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    alarmClass.setPostpone(AlarmClass.postPone.THIRTY_MINUTES);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addData(AlarmClass alarmClass) {
        list.add(alarmClass);
        notifyDataSetChanged();
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
