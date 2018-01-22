package kolar.simplealarm.Model

import android.support.annotation.IntDef
import java.io.Serializable
import java.util.*


/**
 * Created by ondrej.kolar on 22.01.2018.
 */
class AlarmClass : Serializable {

    @IntDef(POSTPONE_MODE_NONE, POSTPONE_MODE_FIVE_MINUTES, POSTPONE_MODE_TEN_MINUTES, POSTPONE_MODE_FIFTEEN_MINUTES, POSTPONE_MODE_THIRTY_MINUTES)
    @Retention(AnnotationRetention.SOURCE)
    annotation class PostponeMode

    companion object {
        const val POSTPONE_MODE_NONE = 0L
        const val POSTPONE_MODE_FIVE_MINUTES = 5L
        const val POSTPONE_MODE_TEN_MINUTES = 10L
        const val POSTPONE_MODE_FIFTEEN_MINUTES = 15L
        const val POSTPONE_MODE_THIRTY_MINUTES = 30L
    }

    var activate = false
    var date = GregorianCalendar.getInstance()
    var repeat = false
    var id: Long = 0
    private var postpone: Long = POSTPONE_MODE_NONE

    fun setPostpone(@PostponeMode postpone: Long) {
        this.postpone = postpone
    }

    fun getPostpone(): Long {
        return this.postpone
    }

    override fun toString(): String {
        return id.toString() + ";" + date.timeInMillis + ";" + postpone + ";" + repeat + ";" + activate
    }
}