package kolar.simplealarm.Controller.Adapter

import android.animation.ObjectAnimator
import android.app.TimePickerDialog
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.widget.*
import kolar.simplealarm.Model.AlarmClass
import kolar.simplealarm.Model.Controller
import kolar.simplealarm.R
import kotlinx.android.synthetic.main.alarmlist_item.view.*
import java.util.*

/**
 * Created by ondrej.kolar on 22.01.2018.
 */


class AlarmListAdapter : RecyclerView.Adapter<AlarmListAdapter.ViewHolder>() {

    private var list = ArrayList<AlarmClass>()

    private var listener: CompoundButton.OnCheckedChangeListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.alarmlist_item, parent, false)
        return ViewHolder(view, parent.context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val alarmClass = list[position]
        holder.itemView.textView_time.text = String.format("%s : %02d", alarmClass.date.get(Calendar.HOUR_OF_DAY).toString(), alarmClass.date.get(Calendar.MINUTE))
        holder.itemView.checkbox_repeat.isChecked = alarmClass.repeat
        holder.itemView.switch_activate.isChecked = alarmClass.activate
        if (alarmClass.getPostpone() != AlarmClass.POSTPONE_MODE_NONE) {
            holder.itemView.checkbox_defer.isChecked = true
            holder.itemView.linearLayout_expand.visibility = View.VISIBLE
            holder.changePropertiesText(holder.context, holder.itemView.checkbox_repeat, holder.itemView.checkbox_defer, holder.itemView.textView_properties)
            when (alarmClass.getPostpone()) {
                AlarmClass.POSTPONE_MODE_FIVE_MINUTES -> holder.itemView.radioBttn5.isChecked = true
                AlarmClass.POSTPONE_MODE_TEN_MINUTES -> holder.itemView.radioBttn10.isChecked = true
                AlarmClass.POSTPONE_MODE_FIFTEEN_MINUTES -> holder.itemView.radioBttn15.isChecked = true
                AlarmClass.POSTPONE_MODE_THIRTY_MINUTES -> holder.itemView.radioBttn30.isChecked = true
            }
        } else {
            holder.itemView.radioBttn5.isChecked = true
            holder.changePropertiesText(holder.context, holder.itemView.checkbox_repeat, holder.itemView.checkbox_defer, holder.itemView.textView_properties)
        }

        holder.itemView.checkbox_defer.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                holder.itemView.linearLayout_expand.visibility = View.VISIBLE
                holder.changePropertiesText(holder.context, holder.itemView.checkbox_repeat, holder.itemView.checkbox_defer, holder.itemView.textView_properties)
                alarmClass.setPostpone(AlarmClass.POSTPONE_MODE_FIVE_MINUTES)
            } else {
                holder.itemView.linearLayout_expand.startAnimation(AnimationUtils.loadAnimation(holder.context, R.anim.fade_in))
                holder.itemView.linearLayout_expand.visibility = View.GONE
                holder.changePropertiesText(holder.context, holder.itemView.checkbox_repeat, holder.itemView.checkbox_defer, holder.itemView.textView_properties)
                alarmClass.setPostpone(AlarmClass.POSTPONE_MODE_NONE)
            }
        }

        holder.itemView.checkbox_repeat.setOnCheckedChangeListener { buttonView, isChecked ->
            holder.changePropertiesText(holder.context, holder.itemView.checkbox_repeat, holder.itemView.checkbox_defer, holder.itemView.textView_properties)
            alarmClass.repeat = isChecked
        }

        holder.itemView.relativeLayout_expandButton.setOnClickListener { holder.expandLayout(holder.itemView.linearLayout_expandMain, holder.itemView.relativeLayout_expandButton, holder.itemView.textView_properties, holder.itemView.imageView_remove) }

        holder.itemView.switch_activate.setOnCheckedChangeListener { buttonView, isChecked ->
            alarmClass.activate = isChecked
            if (isChecked) {
                val today = Calendar.getInstance()
                today.set(Calendar.SECOND, 0)
                today.set(Calendar.MILLISECOND, 0)
                if(today.timeInMillis > alarmClass.date.timeInMillis){
                    alarmClass.date.add(Calendar.DAY_OF_MONTH,1)
                }
                Controller.startAlarm(alarmClass, holder.context)
            } else
                Controller.stopAlarm(alarmClass, holder.context)
        }

        holder.itemView.textView_time.setOnClickListener {
            try {
                val mTimePicker: TimePickerDialog
                mTimePicker = TimePickerDialog(holder.context, TimePickerDialog.OnTimeSetListener { timePicker, selectedHour, selectedMinute ->
                    try {
                        alarmClass.date.set(Calendar.HOUR_OF_DAY, selectedHour)
                        alarmClass.date.set(Calendar.MINUTE, selectedMinute)
                        holder.itemView.textView_time.text = String.format("%s : %02d", selectedHour, selectedMinute)
                    } catch (ex: Exception) {
                        ex.fillInStackTrace()
                    }
                }, alarmClass.date.get(Calendar.HOUR_OF_DAY), alarmClass.date.get(Calendar.MINUTE), true)//Yes 24 hour time
                mTimePicker.show()
            } catch (ex: Exception) {

            }
        }
        holder.itemView.imageView_remove.setOnClickListener {
            Controller.stopAlarm(alarmClass, holder.context)
            list.removeAt(position)
            notifyDataSetChanged()
        }

        listener = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked)
                when (buttonView) {
                    holder.itemView.radioBttn5 -> alarmClass.setPostpone(AlarmClass.POSTPONE_MODE_FIVE_MINUTES)
                    holder.itemView.radioBttn10 -> alarmClass.setPostpone(AlarmClass.POSTPONE_MODE_TEN_MINUTES)
                    holder.itemView.radioBttn15 -> alarmClass.setPostpone(AlarmClass.POSTPONE_MODE_FIFTEEN_MINUTES)
                    holder.itemView.radioBttn30 -> alarmClass.setPostpone(AlarmClass.POSTPONE_MODE_THIRTY_MINUTES)
                }
        }

        holder.itemView.radioBttn5.setOnCheckedChangeListener(listener)
        holder.itemView.radioBttn10.setOnCheckedChangeListener(listener)
        holder.itemView.radioBttn15.setOnCheckedChangeListener(listener)
        holder.itemView.radioBttn30.setOnCheckedChangeListener(listener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun addData(alarmClass: AlarmClass) {
        var id: Long = -1L
        for (i in 0 until list.size) {
            if (list[i].id > id)
                id = list[i].id
        }
        alarmClass.id = id + 1
        list.add(alarmClass)
        notifyDataSetChanged()
    }

    fun savePref(context: Context) {
        Controller.saveAlarmClasses(list, context)
    }

    fun restoreAlarms(context: Context) {
        val l = Controller.getAlarmClasses(context)
        if (l != null) {
            this.list = l
            notifyDataSetChanged()
        }
    }

    class ViewHolder(itemView: View, var context: Context) : RecyclerView.ViewHolder(itemView) {

        fun expandLayout(linearLayout_expandMain: LinearLayout, expandButton: RelativeLayout, textView_properties: TextView, imageView_remove: ImageView) {
            val animFadeOut = AnimationUtils.loadAnimation(context, R.anim.fade_out)
            val animFadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in)
            if (linearLayout_expandMain.visibility == View.GONE) {
                linearLayout_expandMain.startAnimation(animFadeIn)
                createRotateAnimator(expandButton, 0f, 180f).start()
                textView_properties.visibility = View.GONE
                imageView_remove.visibility = View.VISIBLE
                linearLayout_expandMain.visibility = View.VISIBLE
            } else {
                linearLayout_expandMain.startAnimation(animFadeOut)
                createRotateAnimator(expandButton, 180f, 0f).start()
                textView_properties.visibility = View.VISIBLE
                imageView_remove.visibility = View.GONE
                linearLayout_expandMain.visibility = View.GONE
            }
        }

        fun changePropertiesText(context: Context, checkbox_repeat: CheckBox, checkbox_defer: CheckBox, textView: TextView) {
            val repeat: String
            val defer: String
            if (checkbox_repeat.isChecked) {
                repeat = context.resources.getString(R.string.repeat)
            } else {
                repeat = ""
            }

            if (checkbox_defer.isChecked) {
                if (repeat == "") {
                    defer = context.resources.getString(R.string.defer)
                } else {
                    defer = ", " + context.resources.getString(R.string.defer)
                }
            } else {
                defer = ""
            }
            textView.text = repeat + defer
        }

        private fun createRotateAnimator(target: View, from: Float, to: Float): ObjectAnimator {
            val animator = ObjectAnimator.ofFloat(target, "rotation", from, to)
            animator.duration = 300
            animator.interpolator = LinearInterpolator()
            return animator
        }
    }
}
