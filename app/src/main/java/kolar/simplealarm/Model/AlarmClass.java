package kolar.simplealarm.Model;

import android.os.NetworkOnMainThreadException;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Kolar on 25.11.2017.
 */

public class AlarmClass implements Serializable, Cloneable {
    private boolean activate = false;
    private Calendar date = GregorianCalendar.getInstance();
    private boolean repeat = false;
    private postPone postpone = postPone.NONE;

    public AlarmClass() {
        date.set(Calendar.SECOND, 0);
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

    public postPone getPostpone() {
        return postpone;
    }

    public void setPostpone(postPone postpone) {
        this.postpone = postpone;
    }

    public enum postPone {
        NONE, FIVE_MINUTES, TEN_MINUTES, FIFTEEN_MINUTES, THIRTY_MINUTES
    }

}
