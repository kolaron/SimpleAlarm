package kolar.simplealarm.View.Adapter;

import android.animation.ObjectAnimator;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
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
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import kolar.simplealarm.R;

/**
 * Created by Kolar on 25.11.2017.
 */

public class AlarmListAdapter extends RecyclerView.Adapter<AlarmListAdapter.ViewHolder> {


    public AlarmListAdapter() {

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.alarmlistitem, parent, false);
        return new ViewHolder(view, parent.getContext());
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.buttonExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onExpandButtonClick(holder.context, holder.expandableLayout, holder.buttonExpand, holder.textView_dayRepeat, holder.linearLayout_remove, holder.separator_bott);
            }
        });
        holder.textView_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String[] time = holder.textView_time.getText().toString().split(":");
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(holder.context, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            holder.textView_time.setText(selectedHour + ":" + selectedMinute);
                        }
                    }, Integer.parseInt(time[0]), Integer.parseInt(time[1]), true);//Yes 24 hour time
                    mTimePicker.show();
                } catch (Exception ex) {

                }

            }
        });
        holder.linearLayout_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// TODO: 25.11.2017 odstranit udaj
            }
        });
        holder.button_dayM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDayClick(holder.context, holder.text_dayM);
            }
        });

        holder.button_dayTu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDayClick(holder.context, holder.text_dayTu);
            }
        });
        holder.button_dayF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDayClick(holder.context, holder.text_dayF);
            }
        });
        holder.button_daySa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDayClick(holder.context, holder.text_daySa);
            }
        });
        holder.button_daySu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDayClick(holder.context, holder.text_daySu);
            }
        });
        holder.button_dayTh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDayClick(holder.context, holder.text_dayTh);
            }
        });
        holder.button_dayW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDayClick(holder.context, holder.text_dayW);
            }
        });

        holder.checkbox_repeat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                onCheckBoxChange(holder.context, isChecked, holder.linearLayout_days);
                holder.linearLayout_days.clearAnimation();
                if(isChecked){
                    holder.linearLayout_days.setVisibility(View.VISIBLE);
                }else{
                    holder.linearLayout_days.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView_time, textView_dayRepeat;
        Switch switch_activate;

        LinearLayout expandableLayout, linearLayout_days, linearLayout_remove;
        CheckBox checkbox_repeat;
        TextView text_dayM, text_dayTu, text_dayW, text_dayTh, text_dayF, text_daySa, text_daySu;
        RelativeLayout buttonExpand, button_dayM, button_dayTu, button_dayW, button_dayTh, button_dayF, button_daySa, button_daySu;

        View separator_bott;

        Context context;

        public ViewHolder(View itemView, Context context) {
            super(itemView);
            this.context = context;
            textView_time = itemView.findViewById(R.id.textView_time);
            switch_activate = itemView.findViewById(R.id.switch_activate);

            expandableLayout = itemView.findViewById(R.id.expandableLayout);
            checkbox_repeat = itemView.findViewById(R.id.checkbox_repeat);
            linearLayout_days = itemView.findViewById(R.id.linearLayout_days);

//            days
            button_dayM = itemView.findViewById(R.id.button_dayM);
            button_dayTu = itemView.findViewById(R.id.button_dayTu);
            button_dayW = itemView.findViewById(R.id.button_dayW);
            button_dayTh = itemView.findViewById(R.id.button_dayTh);
            button_dayF = itemView.findViewById(R.id.button_dayF);
            button_daySa = itemView.findViewById(R.id.button_daySa);
            button_daySu = itemView.findViewById(R.id.button_daySu);

            text_dayM = itemView.findViewById(R.id.text_dayM);
            text_dayTu = itemView.findViewById(R.id.text_dayTu);
            text_dayW = itemView.findViewById(R.id.text_dayW);
            text_dayTh = itemView.findViewById(R.id.text_dayTh);
            text_dayF = itemView.findViewById(R.id.text_dayF);
            text_daySa = itemView.findViewById(R.id.text_daySa);
            text_daySu = itemView.findViewById(R.id.text_daySu);

            textView_dayRepeat = itemView.findViewById(R.id.textView_dayRepeat);
            linearLayout_remove = itemView.findViewById(R.id.linearLayout_remove);
            buttonExpand = itemView.findViewById(R.id.buttonExpand);

            separator_bott = itemView.findViewById(R.id.separator_bott);
        }
    }

    private void onExpandButtonClick(Context context, final LinearLayout expandableLayout, final RelativeLayout buttonExpand, final TextView textView_dayRepeat, final LinearLayout linearLayout_remove, final View separator) {
        Animation animFadeOut = AnimationUtils.loadAnimation(context, R.anim.fade_out);
        Animation animFadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in);
        if (expandableLayout.getVisibility() == View.GONE) {
            createRotateAnimator(buttonExpand, 0f, 180f).start();
            expandableLayout.setVisibility(View.VISIBLE);
            textView_dayRepeat.setVisibility(View.GONE);
            linearLayout_remove.setVisibility(View.VISIBLE);
            separator.setVisibility(View.GONE);
            expandableLayout.clearAnimation();
            expandableLayout.startAnimation(animFadeIn);
        } else {
            expandableLayout.setVisibility(View.GONE);
            textView_dayRepeat.setVisibility(View.VISIBLE);
            linearLayout_remove.setVisibility(View.GONE);
            separator.setVisibility(View.VISIBLE);
            createRotateAnimator(buttonExpand, 180f, 0f).start();
            expandableLayout.clearAnimation();
            expandableLayout.startAnimation(animFadeOut);
        }
    }

    private void onCheckBoxChange(Context context, boolean isChecked, LinearLayout linearLayout_days) {
        if (isChecked) {
            linearLayout_days.setVisibility(View.VISIBLE);
        } else {
            linearLayout_days.setVisibility(View.GONE);
        }
    }

    private void onDayClick(Context context, TextView day) {
        Animation animFadeOut = AnimationUtils.loadAnimation(context, android.R.anim.fade_out);
        Animation animFadeIn = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
        if (day.getBackground() == null) {
            day.startAnimation(animFadeOut);
            day.setBackgroundResource(R.drawable.circle_day);
            day.setTextColor(Color.BLACK);
        } else {
            day.startAnimation(animFadeIn);
            day.setBackground(null);
            day.setTextColor(Color.WHITE);
        }
    }

    private ObjectAnimator createRotateAnimator(final View target, final float from, final float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
        animator.setDuration(300);
        animator.setInterpolator(new LinearInterpolator());
        return animator;
    }
}
