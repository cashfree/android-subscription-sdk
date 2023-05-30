package com.cashfree.subscription.coresdk.ui

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.cashfree.subscription.coresdk.databinding.CfDialogExitBinding

internal class ExitDialog constructor(
    context: Context,
    private val action: () -> Unit
) : AlertDialog(context) {


    private lateinit var binding: CfDialogExitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CfDialogExitBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        handleActionClick()
    }

    private fun initView() {
        val primaryTextColor = Color.parseColor("#000000")
        val states = arrayOf(
            intArrayOf(android.R.attr.state_enabled),
            intArrayOf(-android.R.attr.state_enabled)
        )
        val buttonColors = intArrayOf(primaryTextColor, Color.GRAY)
        val okTextButtonColor = ColorStateList(states, buttonColors)
        val cancelTextButtonColor = ColorStateList(states, buttonColors)

        with(binding) {
            btnYes.setTextColor(okTextButtonColor)
            btnNo.setTextColor(cancelTextButtonColor)
            tvTitle.setTextColor(primaryTextColor)
            tvMessage.setTextColor(primaryTextColor)
        }
    }

    private fun handleActionClick() {
        with(binding) {
            btnYes.setOnClickListener { action.invoke() }
            btnNo.setOnClickListener { dismiss() }
        }
    }
}