package com.cashfree.susbcription.coresdk.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.PackageManager.ResolveInfoFlags
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Build
import android.os.Bundle

fun Activity.queryIntent(intent: Intent): List<ResolveInfo> {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        packageManager.queryIntentActivities(
            intent, ResolveInfoFlags.of(PackageManager.MATCH_DEFAULT_ONLY.toLong())
        )
    } else {
        packageManager.queryIntentActivities(intent, 0)
    }
}

fun Context.getMetaData(): Bundle? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        packageManager?.getApplicationInfo(
            packageName,
            PackageManager.ApplicationInfoFlags.of(PackageManager.GET_META_DATA.toLong())
        )?.metaData
    } else {
        packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA).metaData
    }
}

fun getUPIIntent(url: String): Intent {
    return Intent().apply {
        action = Intent.ACTION_VIEW
        data = Uri.parse(url)
    }
}