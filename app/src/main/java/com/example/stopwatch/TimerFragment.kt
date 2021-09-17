package com.example.stopwatch

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.stopwatch.databinding.ActivityTimerBinding

class TimerFragment:Fragment(R.layout.activity_timer) {
    private lateinit var binding: ActivityTimerBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityTimerBinding.inflate(inflater,container,false)
        val listHour = requireActivity().resources.getStringArray(R.array.hours).toMutableList()
        listHour.add(0, "Select Hour")
        val hourAdapter:ArrayAdapter<String> = object: ArrayAdapter<String>(requireContext().applicationContext
            ,R.layout.support_simple_spinner_dropdown_item,listHour){
            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val view: TextView = super.getDropDownView(position,
                    convertView,
                    parent) as TextView
                view.setTypeface(view.typeface,Typeface.BOLD)
                // set selected item style
                if (position == binding.spHour.selectedItemPosition && position !=0 ){
                    view.background = ColorDrawable(Color.parseColor("#F7E7CE"))
                    view.setTextColor(Color.parseColor("#333399"))
                }

                // make hint item color gray
                if(position == 0){
                    view.setTextColor(Color.LTGRAY)
                }

                return view
            }
            override fun isEnabled(position: Int): Boolean {
                // disable first item
                // first item is display as hint
                return position != 0
            }
        }
        binding.spHour.adapter = hourAdapter
        val listMinuteSecond = requireActivity().resources.getStringArray(R.array.minutes).toMutableList()
        listMinuteSecond.add(0, "Select Minute")
        val minuteAdapter:ArrayAdapter<String> = object: ArrayAdapter<String>(requireContext().applicationContext
            ,R.layout.support_simple_spinner_dropdown_item,listMinuteSecond){
            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val view: TextView = super.getDropDownView(position,
                    convertView,
                    parent) as TextView
                view.setTypeface(view.typeface,Typeface.BOLD)
                // set selected item style
                if (position == binding.spMinute.selectedItemPosition && position !=0 ){
                    view.background = ColorDrawable(Color.parseColor("#F7E7CE"))
                    view.setTextColor(Color.parseColor("#333399"))
                }

                // make hint item color gray
                if(position == 0){
                    view.setTextColor(Color.LTGRAY)
                }

                return view
            }
            override fun isEnabled(position: Int): Boolean {
                // disable first item
                // first item is display as hint
                return position != 0
            }
        }
        binding.spMinute.adapter = minuteAdapter
        listMinuteSecond.removeAt(0)
        listMinuteSecond.add(0, "Select Second")
        val secondAdapter:ArrayAdapter<String> = object: ArrayAdapter<String>(requireContext().applicationContext
            ,R.layout.support_simple_spinner_dropdown_item,listMinuteSecond){
            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val view: TextView = super.getDropDownView(position,
                    convertView,
                    parent) as TextView
                view.setTypeface(view.typeface,Typeface.BOLD)
                // set selected item style
                if (position == binding.spSecond.selectedItemPosition && position !=0 ){
                    view.background = ColorDrawable(Color.parseColor("#F7E7CE"))
                    view.setTextColor(Color.parseColor("#333399"))
                }

                // make hint item color gray
                if(position == 0){
                    view.setTextColor(Color.LTGRAY)
                }

                return view
            }
            override fun isEnabled(position: Int): Boolean {
                // disable first item
                // first item is display as hint
                return position != 0
            }
        }
        binding.spSecond.adapter = secondAdapter
        var getHour = listHour[0]
        var getMinute = listMinuteSecond[0]
        var getSecond = listMinuteSecond[0]
        binding.spHour.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(position != 0){
                    binding.textView.visibility = View.VISIBLE
                }
                getHour = adapterView?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        binding.spMinute.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(position != 0){
                    binding.textView2.visibility = View.VISIBLE
                }
                getMinute = adapterView?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        binding.spSecond.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(position != 0){
                    binding.textView3.visibility = View.VISIBLE
                }
                getSecond = adapterView?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        binding.btnStartTimer.setOnClickListener {
            if(getHour != listHour[0] && getMinute != listMinuteSecond[0] && getSecond != listMinuteSecond[0]){
                Log.d("Test", "ok")
            } else {
                Log.d("Test", "no")
            }
        }
        return binding.root
    }

}