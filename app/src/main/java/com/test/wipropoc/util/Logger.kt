package com.test.wipropoc.util

import android.util.Log
import com.test.wipropoc.WiproPocApplication

class Logger {

    companion object {
        private var enableLog: Boolean = com.test.wipropoc.BuildConfig.DEBUG
        fun isEnableLog(): Boolean {
            return enableLog
        }

        fun setEnableLog(enableLog: Boolean) {
            this.enableLog = enableLog
        }

        fun e(tag: String?, msg: String?) {
            if (isEnableLog()) Log.e(tag, msg)
        }

        fun e(tagInt: Int?, msg: String?) {
            val tag = tagInt?.let { WiproPocApplication.applicationContext().getString(it) }
            if (isEnableLog()) Log.e(tag, msg)
        }

        fun i(tag: String?, msg: String?) {
            if (isEnableLog()) Log.i(tag, msg)
        }

        fun v(tag: String?, msg: String?) {
            if (isEnableLog()) Log.v(tag, msg)
        }

        fun v(tagInt: Int?, msg: String?) {
            val tag = tagInt?.let { WiproPocApplication.applicationContext().getString(it) }
            if (isEnableLog()) Log.v(tag, msg)
        }

    }

}