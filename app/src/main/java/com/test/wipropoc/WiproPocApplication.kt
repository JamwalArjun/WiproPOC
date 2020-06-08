package com.test.wipropoc

import android.app.Application
import android.content.Context

class WiproPocApplication : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: WiproPocApplication? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
    }
}