package com.example.stopwatch

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import com.example.stopwatch.databinding.ActivityTimerBinding

class TimerFragment : Fragment(R.layout.activity_timer) {
    private var isRunning = true
    private lateinit var binding : ActivityTimerBinding
    private var time:Double = 0.0
    private lateinit var intentService : Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener("requestKey") { _, bundle ->
            time = bundle.getDouble("time")
            binding.timeViewTimer.text = getStingOfTime(time)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityTimerBinding.inflate(inflater,container,false)
        binding.btnPauseResume.setOnClickListener {
            timerPauseResume()
        }
        binding.btnCancel.setOnClickListener {
            isRunning = false
            setFragmentResult("requestKeyActivity", bundleOf("verify" to false))
        }
        intentService = Intent(requireContext(), TimerCountDownService::class.java)
        requireContext().registerReceiver(updateTime, IntentFilter(TimerCountDownService.UPDATE_TIME))
        return binding.root
    }


    override fun onResume() {
        super.onResume()
        intentService.putExtra(TimerCountDownService.EXTRA_TIME, time)
        requireActivity().startService(intentService)
    }

    private fun timerPauseResume(){
        if(isRunning){
            pauseTimer()
        } else {
            resumeTimer()
        }
    }

    private fun resumeTimer(){
        if(time <= -1.0){
            Toast.makeText(requireContext().applicationContext, "Time's up!!!", Toast.LENGTH_SHORT).show()
        } else {
            intentService.putExtra(TimerCountDownService.EXTRA_TIME, time)
            requireActivity().startService(intentService)
            binding.btnPauseResume.text = "Pause"
            binding.btnPauseResume.icon = AppCompatResources.getDrawable(requireContext(), R.drawable.ic_baseline_stop_24)
            isRunning = true
        }
    }

    private fun pauseTimer(){
        requireActivity().stopService(intentService)
        isRunning = false
        binding.btnPauseResume.text = "Resume"
        binding.btnPauseResume.icon =  AppCompatResources.getDrawable(requireContext(), R.drawable.ic_baseline_play_arrow_24)
    }

    private fun getStingOfTime(time: Double): String{
        val timeToInt = time.toInt()
        val second = timeToInt % 60
        val minute = ((timeToInt - second) / 60) % 60
        val hour = (timeToInt - second) / 3600
        return makeTimeString(hour, minute, second)
    }
    private val updateTime = object:BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent) {
            time = intent.getDoubleExtra(TimerCountDownService.EXTRA_TIME, -1.0)
            if(time == -1.0){
                pauseTimer()
                Toast.makeText(requireContext().applicationContext, "Time's up!!!", Toast.LENGTH_SHORT).show()
            } else {
                binding.timeViewTimer.text = getStingOfTime(time)
            }
        }
    }
    private fun makeTimeString(hour: Int, min: Int, sec: Int): String =
        String.format("%02d:%02d:%02d", hour, min, sec)


}