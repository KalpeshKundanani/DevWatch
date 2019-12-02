package com.kalpeshkundanani.devwatch.battery

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData


class BatteryViewModel(application: Application) : AndroidViewModel(application) {
    val batteryStatus: MutableLiveData<BatteryStatusReport> = MutableLiveData()
    private val intentFilter: IntentFilter = IntentFilter()
    private val batteryStatusReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val bundle = intent?.extras
            if(bundle != null){
                batteryStatus.value = BatteryStatusReport(bundle)
            }
        }
    }

    init {
        intentFilter.addAction(Intent.ACTION_POWER_CONNECTED)
        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED)
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED)
        application.registerReceiver(batteryStatusReceiver, intentFilter)
    }
}

class BatteryStatusReport(bundle: Bundle){
    val iconResId:Int = bundle.getInt(BatteryManager.EXTRA_ICON_SMALL)
    val technology:String? = bundle.getString(BatteryManager.EXTRA_TECHNOLOGY)
    val health:Int = bundle.getInt(BatteryManager.EXTRA_HEALTH)
    val status:Int = bundle.getInt(BatteryManager.EXTRA_STATUS)
    val plugged:Int = bundle.getInt(BatteryManager.EXTRA_PLUGGED)
    val present:Boolean = bundle.getBoolean(BatteryManager.EXTRA_PRESENT)
    val seq:Int = bundle.getInt("seq")
    val level:Int = bundle.getInt(BatteryManager.EXTRA_LEVEL)
    val scale:Int = bundle.getInt(BatteryManager.EXTRA_SCALE)
    val temperature:Int = bundle.getInt(BatteryManager.EXTRA_TEMPERATURE)
    val voltage:Int = bundle.getInt(BatteryManager.EXTRA_VOLTAGE)
//    val maxVoltage:Int = bundle.getInt("max_charging_voltage")
//    val max_charging_current:Int = bundle.getInt("max_charging_current")
//    val fastcharge_status:Int = bundle.getInt("fastcharge_status")
//    val charge_counter:Int = bundle.getInt("charge_counter")
//    val invalid_charger:Int = bundle.getInt("invalid_charger")
//    val battery_low = bundle.getBoolean(BatteryManager.EXTRA_BATTERY_LOW)

    override fun toString(): String {
        return """
            iconResId=$iconResId, 
            technology=$technology, 
            health=$health, 
            status=$status, 
            plugged=$plugged, 
            present=$present, 
            seq=$seq, 
            level=$level, 
            scale=$scale, 
            temperature=$temperature, 
            voltage=$voltage, 
        """
    }

    fun plugTypeAsString(): String = when (plugged) {
        BatteryManager.BATTERY_PLUGGED_AC -> "AC"
        BatteryManager.BATTERY_PLUGGED_USB -> "USB"
        else -> "Unknown"
    }

    fun healthAsString(): String = when (health) {
        BatteryManager.BATTERY_HEALTH_DEAD -> "Dead"
        BatteryManager.BATTERY_HEALTH_GOOD -> "Good"
        BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE -> "Over Voltage"
        BatteryManager.BATTERY_HEALTH_OVERHEAT -> "Over Heat"
        BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE -> "Failure"
        else -> "Unknown"
    }

    fun statusAsString(): String = when (status) {
        BatteryManager.BATTERY_STATUS_CHARGING -> "Charging"
        BatteryManager.BATTERY_STATUS_DISCHARGING -> "Discharging"
        BatteryManager.BATTERY_STATUS_FULL -> "Full"
        BatteryManager.BATTERY_STATUS_NOT_CHARGING -> "Not Charging"
        else ->"Unknown"
    }
}

