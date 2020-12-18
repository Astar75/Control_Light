package com.astar.osterrig.connection.bluetooth

import android.bluetooth.BluetoothDevice
import no.nordicsemi.android.ble.BleManagerCallbacks

interface SaberBleManagerCallback : BleManagerCallbacks {

    fun onReadBatteryLevel(device: BluetoothDevice, batteryLevelValue: Int)

}