package com.astar.osterrig.connection.bluetooth

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic
import android.content.Context
import android.graphics.Color
import android.util.Log
import androidx.annotation.IntRange
import no.nordicsemi.android.ble.BleManager
import no.nordicsemi.android.ble.PhyRequest
import no.nordicsemi.android.ble.data.Data
import java.util.*

class SaberBleManager(context: Context) : BleManager(context) {

    private var lightnessGattChar: BluetoothGattCharacteristic? = null
    private var colorGattChar: BluetoothGattCharacteristic? = null
    private var functionGattChar: BluetoothGattCharacteristic? = null
    private var speedGattChar: BluetoothGattCharacteristic? = null
    private var batteryGattChar: BluetoothGattCharacteristic? = null

    fun sendLightness(@IntRange(from = 0, to = 255) lightness: Int) {
        writeCharacteristic(lightnessGattChar, String.format("%d", lightness).toByteArray())
            .split()
            .enqueue()
    }

    fun sendColor(
        @IntRange(from = 0, to = 255) red: Int,
        @IntRange(from = 0, to = 255) green: Int,
        @IntRange(from = 0, to = 255) blue: Int
    ) {
        writeCharacteristic(
            lightnessGattChar,
            String.format("r%dg%db%d", red, green, blue).toByteArray()
        )
            .split()
            .enqueue()
    }

    fun sendColor(
        @IntRange(from = 0, to = 255) red: Int,
        @IntRange(from = 0, to = 255) green: Int,
        @IntRange(from = 0, to = 255) blue: Int,
        @IntRange(from = 0, to = 255) white: Int
    ) {
        writeCharacteristic(
            lightnessGattChar,
            String.format("r%dg%db%dw%d", red, green, blue, white).toByteArray()
        )
            .split()
            .enqueue()
    }

    fun sendGradient(isSmooth: Boolean, colors: IntArray) {
        var gradientCommand = ""
        when (colors.size) {
            2 -> gradientCommand = String.format(
                Locale.getDefault(), "%s:r%dg%db%d;r%dg%db%d;",
                if (isSmooth) "gsh" else "gsm",
                Color.red(colors[0]), Color.green(colors[0]), Color.blue(
                    colors[0]
                ),
                Color.red(colors[1]), Color.green(colors[1]), Color.blue(
                    colors[1]
                )
            )
            3 -> gradientCommand = String.format(
                Locale.getDefault(), "%s:r%dg%db%d;r%dg%db%d;r%dg%db%d;",
                if (isSmooth) "gsh" else "gsm",
                Color.red(colors[0]), Color.green(colors[0]), Color.blue(
                    colors[0]
                ),
                Color.red(colors[1]), Color.green(colors[1]), Color.blue(
                    colors[1]
                ),
                Color.red(colors[2]), Color.green(colors[2]), Color.blue(
                    colors[2]
                )
            )
            4 -> gradientCommand = String.format(
                Locale.getDefault(), "%s:r%dg%db%d;r%dg%db%d;r%dg%db%d;r%dg%db%d;",
                if (isSmooth) "gsh" else "gsm",
                Color.red(colors[0]), Color.green(colors[0]), Color.blue(
                    colors[0]
                ),
                Color.red(colors[1]), Color.green(colors[1]), Color.blue(
                    colors[1]
                ),
                Color.red(colors[2]), Color.green(colors[2]), Color.blue(
                    colors[2]
                ),
                Color.red(colors[3]), Color.green(colors[3]), Color.blue(
                    colors[3]
                )
            )
        }
        writeCharacteristic(colorGattChar, gradientCommand.toByteArray())
            .split()
            .enqueue()
    }

    fun sendFunction(function: String) {
        writeCharacteristic(functionGattChar, function.toByteArray())
            .split()
            .enqueue()
    }

    fun sendSpeed(speed: Int) {
        writeCharacteristic(speedGattChar, speed.toString().toByteArray())
            .split()
            .enqueue()
    }

    fun setNotification() {
        enableNotifications(batteryGattChar)
        setNotificationCallback(batteryGattChar).with { device: BluetoothDevice?, data: Data ->
            Log.e(
                "Notification",
                "Device: ${device?.address}, Data: $data"
            )
        }
    }

    override fun log(priority: Int, message: String) {
        Log.println(priority, "SaberBleManager", message)
    }

    override fun getGattCallback(): BleManagerGattCallback {
        return SaberManagerCallback()
    }


    private inner class SaberManagerCallback : BleManagerGattCallback() {

        override fun initialize() {
            beginAtomicRequestQueue()
                .add(requestMtu(MTU_VALUE)
                    .with { device, mtu -> Log.i(TAG, "MTU set: $mtu for ${device.address}") }
                    .fail { device, status ->
                        Log.i(
                            TAG,
                            "Request MTU not supported: $status for ${device.address}"
                        )
                    })
                .add(setPreferredPhy(
                    PhyRequest.PHY_LE_2M_MASK,
                    PhyRequest.PHY_LE_2M_MASK,
                    PhyRequest.PHY_OPTION_NO_PREFERRED
                )
                    .fail { device, status ->
                        Log.i(
                            TAG,
                            "Phy not supported: $status for ${device.address}"
                        )
                    }
                )
                .done { device -> Log.d(TAG, "Target initialized ${device.address}") }
                .enqueue()

            setNotificationCallback(batteryGattChar)
                .with { device, data ->
                    Log.e(
                        TAG,
                        "Initialize: Device - ${device.address}, data = ${data.value} "
                    )
                }

            enableNotifications(batteryGattChar)
                .done { device ->
                    Log.d(TAG, "initialize: notification enable success: ${device.address}")
                }
                .fail { device, status ->
                    Log.e(
                        TAG,
                        "initialize: notification enable failed: ${device.address}, status = $status"
                    )
                }
                .enqueue()
        }

        override fun isRequiredServiceSupported(gatt: BluetoothGatt): Boolean {
            val serviceBattery = gatt.getService(SERVICE_BATTERY_UUID)
            val serviceSaber = gatt.getService(SERVICE_SABER_UUID)

            if (serviceBattery != null && serviceSaber != null) {
                batteryGattChar = serviceBattery.getCharacteristic(BATTERY_CHAR_UUID)
                lightnessGattChar = serviceSaber.getCharacteristic(LIGHTNESS_CHAR_UUID)
                colorGattChar = serviceSaber.getCharacteristic(COLOR_CHAR_UUID)
                functionGattChar = serviceSaber.getCharacteristic(FUNCTION_CHAR_UUID)
                speedGattChar = serviceSaber.getCharacteristic(SPEED_CHAR_UUID)
            }
            return batteryGattChar != null && lightnessGattChar != null && colorGattChar != null && functionGattChar != null && speedGattChar != null
        }

        override fun onDeviceDisconnected() {
            batteryGattChar = null
            lightnessGattChar = null
            colorGattChar = null
            functionGattChar = null
            speedGattChar = null
        }
    }

    private val batteryGattCallback = object : BatteryLevelDataCallback() {
        override fun onDataReceived(device: BluetoothDevice, data: Data) {

        }

        override fun onInvalidDataReceived(device: BluetoothDevice, data: Data) {

        }
    }

    companion object {
        const val MTU_VALUE = 507

        val TAG: String? = SaberBleManager::class.java.simpleName

        val SERVICE_BATTERY_UUID: UUID = UUID.fromString("7b20eb75-30bf-40fd-9580-59770fcd0a53")
        val SERVICE_SABER_UUID: UUID = UUID.fromString("d4d4dc12-0493-44fa-bc55-477388a6565c")

        val BATTERY_CHAR_UUID: UUID = UUID.fromString("b0a53ff0-3f99-4ce6-9d6d-a1cc3277251a")
        val LIGHTNESS_CHAR_UUID: UUID = UUID.fromString("879928f7-7b16-4c85-bf73-48f141198c83")
        val COLOR_CHAR_UUID: UUID = UUID.fromString("879928f7-6a26-4c85-bf73-48f141198c83")
        val FUNCTION_CHAR_UUID: UUID = UUID.fromString("879928f7-6a36-4c85-bf73-48f141198c83")
        val SPEED_CHAR_UUID: UUID = UUID.fromString("879928f7-6a46-4c85-bf73-48f141198c83")
    }
}