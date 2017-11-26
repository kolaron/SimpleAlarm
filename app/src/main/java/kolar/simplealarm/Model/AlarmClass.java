package kolar.simplealarm.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Kolar on 25.11.2017.
 */

public class AlarmClass implements Serializable {
    private boolean activate;
    private Date date;
    private boolean repeat;
    private boolean defer;

    public AlarmClass() {
    }

    public boolean isActivate() {
        return activate;
    }

    public void setActivate(boolean activate) {
        this.activate = activate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    public boolean isDefer() {
        return defer;
    }

    public void setDefer(boolean defer) {
        this.defer = defer;
    }
}
