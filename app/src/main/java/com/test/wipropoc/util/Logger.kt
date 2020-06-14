package com.test.wipropoc.util

import android.util.Log
import com.test.wipropoc.application.WiproPocApplication

/**
 *  Logger class to display logs on logcat
 *  <p>
 *    Contains static methods to print error,verbose
 *    </p>
 *
 */
class Logger {

    companion object {
        private var enableLog: Boolean = com.test.wipropoc.BuildConfig.DEBUG

        /**
         * Check logs are enable
         *
         * @return is log enable or disable
         */
        private fun isEnableLog(): Boolean {
            return enableLog
        }

        /**
         * Enable or Disable the logs
         *
         * @param enableLog Boolean to enable/disable logs
         */
        fun setEnableLog(enableLog: Boolean) {
            this.enableLog = enableLog
        }

        /**
         * Print error logs
         *
         * @param tag
         * @param msg Message to be printed
         */
        fun e(tag: String?, msg: String?) {
            if (isEnableLog()) Log.e(tag, msg)
        }

        /**
         * Print error logs
         *
         * @param resourceInt  Resource id to get the tag string
         * @param msg  Message to be printed
         */
        fun e(resourceInt: Int?, msg: String?) {
            val tag = resourceInt?.let { WiproPocApplication.applicationContext().getString(it) }
            if (isEnableLog()) Log.e(tag, msg)
        }

        /**
         * Print info logs
         *
         * @param tag
         * @param msg Message to be printed
         */
        fun i(tag: String?, msg: String?) {
            if (isEnableLog()) Log.i(tag, msg)
        }

        /**
         * Print verbose
         *
         * @param resourceInt Resource id to get the tag string
         * @param msg Message to be printed
         */
        fun v(resourceInt: Int?, msg: String?) {
            val tag = resourceInt?.let { WiproPocApplication.applicationContext().getString(it) }
            if (isEnableLog()) Log.v(tag, msg)
        }

    }

}