package com.kalpeshkundanani.devwatch.battery

import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.kalpeshkundanani.devwatch.R
import kotlinx.android.synthetic.main.battery_fragment.*

class BatteryFragment : Fragment() {

    companion object {
        fun newInstance() = BatteryFragment()
    }

    private lateinit var viewModel: BatteryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.battery_fragment, container, false)
        initViews(view)
        return view
    }

    private fun initViews(view: View?) {
        val batteryStatus: Intent? = IntentFilter(Intent.ACTION_BATTERY_CHANGED).let { ifilter ->
            context?.registerReceiver(null, ifilter)
        }

        val status: Int = batteryStatus?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1
        val isCharging: Boolean = status == BatteryManager.BATTERY_STATUS_CHARGING
                || status == BatteryManager.BATTERY_STATUS_FULL

        // How are we charging?
        val chargePlug: Int = batteryStatus?.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1) ?: -1
        val usbCharge: Boolean = chargePlug == BatteryManager.BATTERY_PLUGGED_USB
        val acCharge: Boolean = chargePlug == BatteryManager.BATTERY_PLUGGED_AC

//        Log.d("newF", "status: $status")
//        Log.d("newF", "isCharging: $isCharging")
//        Log.d("newF", "chargePlug: $chargePlug")
//        Log.d("newF", "usbCharge: $usbCharge")
//        Log.d("newF", "acCharge: $acCharge")

        val batteryPct: Float? = batteryStatus?.let { intent ->
            val level: Int = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            val scale: Int = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
            level / scale.toFloat()
        }
//        Log.d("newF", "batteryPct: $batteryPct")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(BatteryViewModel::class.java)
        viewModel.batteryStatus.observe(this, Observer<BatteryStatusReport> { report ->
//            Log.d("newF", report.toString())
            val percent: Int = (report.level * 100) / report.scale
            val reportString =
                """$report
            percent: $percent 
            status: ${report.statusAsString()}
            plug: ${report.plugTypeAsString()}
            health: ${report.healthAsString()}
            """
            tv_battery_info.text = reportString
        })
    }
}
