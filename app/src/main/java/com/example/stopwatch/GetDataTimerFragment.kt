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
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.example.stopwatch.databinding.ActivityTimerGetDataBinding

class GetDataTimerFragment:Fragment(R.layout.activity_timer_get_data) {
    lateinit var binding: ActivityTimerGetDataBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityTimerGetDataBinding.inflate(inflater,container,false)
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
        val listMinute = listMinuteSecond
        listMinute.add(0, "Select Minute")
        val minuteAdapter:ArrayAdapter<String> = object: ArrayAdapter<String>(requireContext().applicationContext
            ,R.layout.support_simple_spinner_dropdown_item,listMinute){
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
        val listSecond= listMinuteSecond
        listSecond.add(0, "Select Second")
        val secondAdapter:ArrayAdapter<String> = object: ArrayAdapter<String>(requireContext().applicationContext
            ,R.layout.support_simple_spinner_dropdown_item,listSecond){
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
        var getMinute = listMinute[0]
        var getSecond = listSecond[0]
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
            var verify = false
            if(getHour != listHour[0] && getMinute != listMinute[0] && getSecond != listSecond[0]){
                //setFragmentResult("requestKey", bundleOf("hour" to getHour.toDouble()))
                //setFragmentResult("requestKey", bundleOf("minute" to getMinute.toDouble()))
                setFragmentResult("requestKey", bundleOf("second" to getSecond.toDouble()))
                verify = true
                setFragmentResult("requestKeyActivity", bundleOf("verify" to verify))
            } else {
                Toast.makeText(requireContext().applicationContext,"Please specify time!", Toast.LENGTH_SHORT ).show()
                setFragmentResult("requestKeyActivity", bundleOf("verify" to verify))
            }
        }
        return binding.root
    }


}