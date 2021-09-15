package com.example.stopwatch

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.stopwatch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val firstFragment = StopWatchFragment()
        val secondFragment = TimerFragment()
        setCurrentFragment(firstFragment)
        val window = getWindow()
        window.statusBarColor = ContextCompat.getColor(this, R.color.teal_700)


        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.bottom_nav_clock -> setCurrentFragment(firstFragment)
                R.id.bottom_nav_timer -> setCurrentFragment(secondFragment)
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

