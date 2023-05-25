package com.cashfree.susbcription.coresdk.utils

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.PackageManager.ResolveInfoFlags
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Build

fun Activity.queryIntent(intent: Intent): List<ResolveInfo> {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        packageManager.queryIntentActivities(
            intent, ResolveInfoFlags.of(PackageManager.MATCH_DEFAULT_ONLY.toLong())
        )
    } else {
        packageManager.queryIntentActivities(intent, 0)
    }
}

fun getUPIIntent(url: String): Intent {
    return Intent().apply {
        action = Intent.ACTION_VIEW
        data = Uri.parse(url)
    }
}