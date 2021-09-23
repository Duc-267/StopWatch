package com.example.stopwatch

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.content.IntentFilter
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import com.example.stopwatch.databinding.ActivityTimerBinding
import java.lang.Exception

class TimerFragment : Fragment(R.layout.activity_timer) {
    private val CHANNEL_ID = "channelID"
    private val CHANNEL_NAME = "channelName"
    private val NOTIFICATION_ID = 0
    private var fullProportion = 100.0
    private var proportion:Double = 0.0
    private var progr = 0.0
    private var isRunning = true
    private lateinit var binding : ActivityTimerBinding
    private var time:Double = 0.0
    private lateinit var intentService : Intent

    private val notification = NotificationCompat.Builder(requireContext().applicationContext, CHANNEL_ID)
        .setContentTitle("Timer")
        .setContentText("Time's up!!!")
        .setSmallIcon(R.drawable.ic_baseline_alarm_on_24)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .build()
    private val notificationManager = NotificationManagerCompat.from(requireContext().applicationContext)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener("requestKey") { _, bundle ->
            time = bundle.getDouble("time")
            binding.timeViewTimer.text = getStingOfTime(time)
            proportion = fullProportion / time
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityTimerBinding.inflate(inflater,container,false)
        binding.progressBar.progress = progr.toInt()
        binding.btnPauseResume.setOnClickListener {
            timerPauseResume()
        }
        binding.btnCancel.setOnClickListener {
            pauseTimer()
            isRunning = false
            setFragmentResult("requestKeyActivity", bundleOf("verify" to false))
            binding.progressBar.progress = 0
            progr = 0.0
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
        if(time <= 0.0){
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
            progr += proportion
            Log.d("time", "$progr $proportion")
            binding.progressBar.progress = progr.toInt()
            if(time == 0.0){
                notificationManager.notify(NOTIFICATION_ID, notification)
                binding.timeViewTimer.text = getStingOfTime(time)
                binding.progressBar.progress = 0
                progr = 0.0
                pauseTimer()
                Toast.makeText(requireContext().applicationContext, "Time's up!!!", Toast.LENGTH_SHORT).show()
            } else {
                binding.timeViewTimer.text = getStingOfTime(time)
            }
        }
    }
    private fun makeTimeString(hour: Int, min: Int, sec: Int): String =
        String.format("%02d:%02d:%02d", hour, min, sec)


    private fun createNotification(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            val manager = requireActivity().getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
        playNotificationSound(requireContext().applicationContext)
    }
    private fun playNotificationSound(context: Context){
        try {
            val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val ring = RingtoneManager.getRingtone(context, defaultSoundUri)
            ring.play()
        } catch (e:Exception){
            e.printStackTrace()
        }
    }
}