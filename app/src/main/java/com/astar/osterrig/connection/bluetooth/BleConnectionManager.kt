package com.astar.osterrig.connection.bluetooth

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.content.Context

class BleConnectionManager(private val context: Context): SaberBleManagerCallback {

    enum class State {
        DISCONNECTED,
        CONNECTING,
        CONNECTED
    }

    private val mBleManagers: HashMap<BluetoothDevice, SaberBleManager> = HashMap()
    private val mManagedDevices: MutableList<BluetoothDevice> = mutableListOf()

    fun connect(device: BluetoothDevice) {
        if (mManagedDevices.contains(device)) {
            return
        }

        mManagedDevices.add(device)

        var manager = mBleManagers[device]
        if (manager == null) {
            manager = SaberBleManager(context).apply {
                setGattCallbacks(this@BleConnectionManager)
            }
            mBleManagers[device] = manager
        }
        manager.connect(device)
            .retry(2, 100)
            .useAutoConnect(true)
            .timeout(10000)
            .fail { d, _ ->
                mManagedDevices.remove(d)
                mBleManagers.remove(d)
            }
            .enqueue()
    }

    fun disconnect(device: BluetoothDevice) {
        val manager = mBleManagers[device]
        if (manager != null && manager.isConnected) {
            manager.disconnect().enqueue()
        }
        mManagedDevices.remove(device)
    }

    fun disconnectAll() {
        for (manager in mBleManagers.values) {
            manager.disconnect().enqueue()
            mManagedDevices.remove(manager.bluetoothDevice)
        }
    }

    fun isConnected(device: BluetoothDevice): Boolean {
        val manager = mBleManagers[device]
        return manager != null && manager.isReady
    }

    fun isReady(device: BluetoothDevice): Boolean {
        val manager = mBleManagers[device]
        return manager != null && manager.isReady
    }

    fun getConnectionState(device: BluetoothDevice): Int {
        val manager = mBleManagers[device]
        return manager?.connectionState ?: BluetoothGatt.STATE_DISCONNECTED
    }

    fun sendLightness(device: BluetoothDevice, lightness: Int) {
        val manager = mBleManagers[device]
        if (manager != null && manager.isConnected) {
            manager.sendLightness(lightness)
        }
    }

    fun sendColor(device: BluetoothDevice, red: Int, green: Int, blue: Int) {
        val manager = mBleManagers[device]
        if (manager != null && manager.isConnected) {
            manager.sendColor(red, green, blue)
        }
    }

    fun sendColor(device: BluetoothDevice, red: Int, green: Int, blue: Int, white: Int) {
        val manager = mBleManagers[device]
        if (manager != null && manager.isConnected) {
            manager.sendColor(red, green, blue, white)
        }
    }

    fun sendGradient(device: BluetoothDevice, isSmooth: Boolean, colors: IntArray) {
        val manager = mBleManagers[device]
        if (manager != null && manager.isConnected) {
            manager.sendGradient(isSmooth, colors)
        }
    }

    fun sendFunction(device: BluetoothDevice, function: String) {
        val manager = mBleManagers[device]
        if (manager != null && manager.isConnected) {
            manager.sendFunction(function)
        }
    }

    fun sendSpeed(device: BluetoothDevice, speed: Int) {
        val manager = mBleManagers[device]
        if (manager != null && manager.isConnected) {
            manager.sendSpeed(speed)
        }
    }

    fun setNotification(device: BluetoothDevice) {
        val manager = mBleManagers[device]
        if (manager != null && manager.isConnected) {
            manager.setNotification()
        }
    }

    override fun onReadBatteryLevel(device: BluetoothDevice, batteryLevelValue: Int) {

    }

    override fun onDeviceConnecting(device: BluetoothDevice) {

    }

    override fun onDeviceConnected(device: BluetoothDevice) {

    }

    override fun onDeviceDisconnecting(device: BluetoothDevice) {

    }

    override fun onDeviceDisconnected(device: BluetoothDevice) {

    }

    override fun onLinkLossOccurred(device: BluetoothDevice) {

    }

    override fun onServicesDiscovered(device: BluetoothDevice, optionalServicesFound: Boolean) {

    }

    override fun onDeviceReady(device: BluetoothDevice) {

    }

    override fun onBondingRequired(device: BluetoothDevice) {

    }

    override fun onBonded(device: BluetoothDevice) {

    }

    override fun onBondingFailed(device: BluetoothDevice) {

    }

    override fun onError(device: BluetoothDevice, message: String, errorCode: Int) {

    }

    override fun onDeviceNotSupported(device: BluetoothDevice) {

    }
}