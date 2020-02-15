package com.apero.task.activity.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.apero.task.activity.activity.HomeActivity

class Receiver :BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        var intent = Intent(p0, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        p0!!.startActivity(intent)

    }
}