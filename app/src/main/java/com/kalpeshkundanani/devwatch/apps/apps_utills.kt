package com.kalpeshkundanani.devwatch.apps

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.util.Log

internal fun listApps(context: Context?) {
    val pm = context?.packageManager
    val list: List<PackageInfo>? = pm?.getInstalledPackages(0)

    list?.forEach { info: PackageInfo ->
        val ai = pm.getApplicationInfo(info.packageName, 0)
        if (isDebugApp(ai)) {
            Log.d("newF", "DEBUG: " + info.packageName)
        }

        if (isInstalledApp(info)) {
            Log.d("newF", "Installed: " + info.packageName)
        }

        if (!isInstalledApp(info)) {
            Log.d("newF", "System: " + info.packageName)
        }
    }
}

private fun isDebugApp(ai: ApplicationInfo): Boolean {
    return ai.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
}

private fun isInstalledApp(info: PackageInfo): Boolean {
    return info.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM == 0
}