package com.cashfree.susbcription.demo.helper

import android.view.View

fun View.visibility(show: Boolean) {
    if (show) this.visibility = View.VISIBLE
    else this.visibility = View.GONE
}