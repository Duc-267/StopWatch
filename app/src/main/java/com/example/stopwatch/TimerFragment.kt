package com.example.stopwatch

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import com.example.stopwatch.databinding.ActivityTimerBinding

class TimerFragment : Fragment(R.layout.activity_timer) {
    private var isRunning = false
    private lateinit var binding : ActivityTimerBinding
    private var time = 10.0
    private lateinit var intentService : Intent

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityTimerBinding.inflate(inflater,container,false)
        setFragmentResultListener("requestKey") { requestKey, bundle ->
            time = bundle.getDouble("second")
            binding.timeViewTimer.text = getStingOfTime(time)
        }
        binding.btnStartStopTimer.setOnClickListener {
            binding.timeViewTimer.text = getStingOfTime(time)

        }
        return binding.root
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