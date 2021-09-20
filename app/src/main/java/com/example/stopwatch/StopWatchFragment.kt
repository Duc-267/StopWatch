package com.example.stopwatch

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import com.example.stopwatch.databinding.ActivityStopWatchBinding

class StopWatchFragment:Fragment(R.layout.activity_stop_watch) {
    private var isRunning = false
    private lateinit var binding : ActivityStopWatchBinding
    private var time = 10.0
    private lateinit var intentService : Intent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        time = 4.0
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityStopWatchBinding.inflate(inflater,container,false)
        binding.btnStartStop.setOnClickListener {
            Log.d ("time", "$time bf")
            timerStartStop()
            Log.d ("time", "$time at")
        }
        binding.btnReset.setOnClickListener {
            resetTimer()
        }
        intentService = Intent(requireContext(), TimeService::class.java)
        Log.d("time", "intentService - $time")
        requireActivity().registerReceiver(updateTime, IntentFilter("updateTime") )
        return binding.root
    }

    private fun timerStartStop(){
        if(isRunning){
            stopTimer()
        } else {
            startTimer()
        }
    }
    private fun startTimer(){
        Log.d("time", "start 1 - $time")
        intentService.putExtra("EXTRA_TIME", time)
        requireActivity().startService(intentService)
        Log.d("time", "start 2 - $time")
        binding.btnStartStop.text = "Stop"
        binding.btnStartStop.icon =  AppCompatResources.getDrawable(requireContext(), R.drawable.ic_baseline_stop_24)
        isRunning = true
    }

    private fun stopTimer(){
        requireActivity().stopService(intentService)
        isRunning = false
        binding.btnStartStop.text = "Start"
        binding.btnStartStop.icon =  AppCompatResources.getDrawable(requireContext(), R.drawable.ic_baseline_play_arrow_24)
    }

    private  val updateTime: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            time = intent.getDoubleExtra("EXTRA_TIME",-1.0)
            binding.timeView.text = getStingOfTime(time)
        }
    }

    private fun resetTimer(){
        stopTimer()
        time = -1.0
        binding.timeView.text = "00:00:00"
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