package com.cashfree.subscription.coresdk.services

import android.content.ContentProvider
import android.content.ContentValues
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import com.cashfree.pg.base.logger.CFLoggerService
import com.cashfree.subscription.coresdk.utils.getMetaData

internal class CFSubscriptionContentProvider : ContentProvider() {

    private val TAG = "CFSubscriptionContentProvider"
    override fun onCreate(): Boolean {
        CFLoggerService.getInstance().d(TAG, "Registered")
        val bundle: Bundle? = context?.getMetaData()
        try {

            val initializationEnabled =
                bundle?.getBoolean("subscription_core_auto_initialize_enabled", true) ?: true
            val loggingLevel = bundle?.getInt("subscription_logging_level", 4) ?: 4
            if (initializationEnabled) {
                CFLoggerService.getInstance().setLoggingLevel(loggingLevel)
                CFLoggerService.getInstance().d(TAG, "initialized")
            } else {
                CFLoggerService.getInstance().d(TAG, "initialization failed")
            }
        } catch (e: PackageManager.NameNotFoundException) {
            CFLoggerService.getInstance().d(TAG, "initialization failed")
        }
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? = null

    override fun getType(uri: Uri): String? = null

    override fun insert(uri: Uri, values: ContentValues?): Uri? = null

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int = 0
    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int = 0
}