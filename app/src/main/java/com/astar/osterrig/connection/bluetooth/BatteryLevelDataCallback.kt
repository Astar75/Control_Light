package com.astar.osterrig.connection.bluetooth

import android.bluetooth.BluetoothDevice
import no.nordicsemi.android.ble.callback.profile.ProfileDataCallback
import no.nordicsemi.android.ble.data.Data

abstract class BatteryLevelDataCallback: ProfileDataCallback {
    override fun onInvalidDataReceived(device: BluetoothDevice, data: Data) {
        super.onInvalidDataReceived(device, data)
    }
}