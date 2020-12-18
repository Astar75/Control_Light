package com.astar.osterrig.connection.bluetooth

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

class BleService: Service() {

    inner class LocalBinder : Binder() {
        fun getService() = this@BleService
    }

    override fun onBind(intent: Intent?): IBinder? {
        return LocalBinder()
    }

}