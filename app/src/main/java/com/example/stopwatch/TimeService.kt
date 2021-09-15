package com.example.stopwatch

import android.app.Service
import android.content.Intent
import android.os.IBinder
import java.util.*

class TimeService: Service() {
    private val timer = Timer()
    override fun onBind(p0: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val time = intent.getDoubleExtra("EXTRA_TIME", -1.0)
        timer.scheduleAtFixedRate(TimeTask(time), 0, 1000)
        return START_NOT_STICKY
    }

    private inner class TimeTask(private var time: Double):TimerTask(){
        override fun run() {
            val intent = Intent("updateTime")
            time++
            intent.putExtra("EXTRA_TIME", time)
            sendBroadcast(intent)
        }

    }
    override fun onDestroy() {
        timer.cancel()
        super.onDestroy()
    }
}