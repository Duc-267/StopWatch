package com.example.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.stopwatch.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        var result: Boolean = false
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val stopwatchFragment = StopWatchFragment()
        val timerGetDataFragment = GetDataTimerFragment()
        val timerFragment = TimerFragment()
        setCurrentFragment(stopwatchFragment)
        val window = getWindow()
        window.statusBarColor = ContextCompat.getColor(this, R.color.teal_700)

        supportFragmentManager.setFragmentResultListener("requestKeyActivity", this){ _, bundle ->
            result = bundle.getBoolean("verify")
            if(result){
                setCurrentFragment(timerFragment)
            } else {
                setCurrentFragment(timerGetDataFragment)
            }
        }

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.bottom_nav_clock -> setCurrentFragment(stopwatchFragment)
                R.id.bottom_nav_timer -> {
                    if(result == false) {
                        setCurrentFragment(timerGetDataFragment)
                    } else {
                        setCurrentFragment(timerFragment)
                    }
                }
            }
            return@setOnItemSelectedListener true
        }


    }
    private fun setCurrentFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            Log.d("Fragment", "Clicked")
            replace(R.id.flFragment, fragment)
            commit()
        }
    }
}

