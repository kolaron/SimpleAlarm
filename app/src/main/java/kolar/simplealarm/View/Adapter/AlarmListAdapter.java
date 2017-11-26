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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;

import kolar.simplealarm.Model.AlarmClass;
import kolar.simplealarm.R;

/**
 * Created by Kolar on 25.11.2017.
 */

public class AlarmListAdapter extends RecyclerView.Adapter<AlarmListAdapter.ViewHolder> {

    private ArrayList<AlarmClass> list = new ArrayList<>();

    public AlarmListAdapter() {
        list.add(null);
        list.add(null);
        list.add(null);
        list.add(null);
        list.add(null);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.alarmlist_item, parent, false);
        return new ViewHolder(view, parent.getContext());
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.checkbox_defer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    holder.linearLayout_expand.setVisibility(View.VISIBLE);
                } else {
                    holder.linearLayout_expand.startAnimation(AnimationUtils.loadAnimation(holder.context, R.anim.fade_in));
                    holder.linearLayout_expand.setVisibility(View.GONE);
                }
            }
        });

        holder.relativeLayout_expandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.expandeLayout(holder.linearLayout_expandMain, holder.relativeLayout_expandButton);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
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

        private void expandeLayout(LinearLayout linearLayout_expandMain, RelativeLayout expandButton) {
            Animation animFadeOut = AnimationUtils.loadAnimation(context, R.anim.fade_out);
            Animation animFadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in);
            if (linearLayout_expandMain.getVisibility() == View.GONE) {
                linearLayout_expandMain.startAnimation(animFadeIn);
                createRotateAnimator(expandButton, 0f, 180f).start();
                linearLayout_expandMain.setVisibility(View.VISIBLE);
            } else {
                linearLayout_expandMain.startAnimation(animFadeOut);
                createRotateAnimator(expandButton, 180f, 0f).start();
                linearLayout_expandMain.setVisibility(View.GONE);
            }
        }

        private ObjectAnimator createRotateAnimator(final View target, final float from, final float to) {
            ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
            animator.setDuration(300);
            animator.setInterpolator(new LinearInterpolator());
            return animator;
        }
    }
}
