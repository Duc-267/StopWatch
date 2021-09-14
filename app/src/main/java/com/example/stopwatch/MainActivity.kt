package com.example.stopwatch

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.stopwatch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var isRunning = false
    private lateinit var binding : ActivityMainBinding
    private var time = 0.0
    private lateinit var intentService : Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnStartStop.setOnClickListener {
            timerStartStop()
        }
        binding.btnReset.setOnClickListener {
            resetTimer()
        }
        intentService = Intent(this, TimeService::class.java)
        registerReceiver(updateTime, IntentFilter("updateTime") )
    }
    private fun timerStartStop(){
        if(isRunning){
            stopTimer()
        } else {
            startTimer()
        }
    }
    private fun startTimer(){
        intentService.putExtra("EXTRA_TIME", time)
        startService(intentService)
        binding.btnStartStop.text = "Stop"
        binding.btnStartStop.icon =  getDrawable(R.drawable.ic_baseline_stop_24)
        isRunning = true
    }

    private fun stopTimer(){
        stopService(intentService)
        isRunning = false
        binding.btnStartStop.text = "Start"
        binding.btnStartStop.icon =  getDrawable(R.drawable.ic_baseline_play_arrow_24)
    }

    private  val updateTime:BroadcastReceiver = object :BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            time = intent.getDoubleExtra("EXTRA_TIME",0.0)
            binding.timeView.text = getStingOfTime(time)
        }
    }

    private fun resetTimer(){
        stopTimer()
        time = 0.0
        binding.timeView.text = getStingOfTime(time)
    }

    private fun getStingOfTime(time: Double): String{
        val timeToInt = time.toInt()
        val second = timeToInt % 60
        val minute = (timeToInt - second) / 60
        val hour = (timeToInt - second) / 3600
        return makeTimeString(hour, minute, second)
    }
    private fun makeTimeString(hour: Int, min: Int, sec: Int): String =
        String.format("%02d:%02d:%02d", hour, min, sec)
}
