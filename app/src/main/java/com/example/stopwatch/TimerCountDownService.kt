package com.example.stopwatch

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import java.util.*

class TimerCountDownService: Service() {
    init {
        Log.d("time", "init")
    }
    private val timer = Timer()
    override fun onBind(p0: Intent?): IBinder? = null


    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val time = intent.getDoubleExtra(EXTRA_TIME, 1.0)
        timer.scheduleAtFixedRate(TimeTask(time), 0, 1000)
        return START_NOT_STICKY
    }

    private inner class TimeTask(private var time: Double):TimerTask(){
        override fun run() {
            val intent = Intent(UPDATE_TIME)
            time--
            intent.putExtra(EXTRA_TIME, time)
            sendBroadcast(intent)
        }

    }
    companion object{
        val EXTRA_TIME = "EXTRA_TIME_TIMER"
        val UPDATE_TIME = "UPDATE_TIME_TIMER"
    }
    override fun onDestroy() {
        timer.cancel()
        super.onDestroy()
    }
}