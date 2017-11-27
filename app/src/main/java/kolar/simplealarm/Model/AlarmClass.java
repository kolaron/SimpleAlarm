package kolar.simplealarm.Model;

import android.support.annotation.IntDef;

import java.io.Serializable;
import java.lang.annotation.Retention;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by Kolar on 25.11.2017.
 */

public class AlarmClass implements Serializable {

    @IntDef({POSTPONE_MODE_NONE, POSTPONE_MODE_FIVE_MINUTES, POSTPONE_MODE_TEN_MINUTES, POSTPONE_MODE_FIFTEEN_MINUTES, POSTPONE_MODE_THIRTY_MINUTES})
    @Retention(SOURCE)
    public @interface PostponeMode {
    }

    public static final int POSTPONE_MODE_NONE = -1;
    public static final int POSTPONE_MODE_FIVE_MINUTES = 5;
    public static final int POSTPONE_MODE_TEN_MINUTES = 10;
    public static final int POSTPONE_MODE_FIFTEEN_MINUTES = 15;
    public static final int POSTPONE_MODE_THIRTY_MINUTES = 30;

    private boolean activate = false;
    private Calendar date = GregorianCalendar.getInstance();
    private boolean repeat = false;
    private long id;

    @PostponeMode
    private int postpone;

    public AlarmClass() {
        date.set(Calendar.SECOND, 0);
        this.postpone = POSTPONE_MODE_NONE;
    }

    public void setPostponeMode(@PostponeMode int mode) {
        this.postpone = mode;
    }

    @PostponeMode
    public int getPostponeMode() {
        return this.postpone;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isActivate() {
        return activate;
    }

    public void setActivate(boolean activate) {
        this.activate = activate;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(GregorianCalendar date) {
        this.date = date;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return id + ";" + date.getTimeInMillis() + ";" + postpone + ";" + repeat + ";" + activate;
    }
}
