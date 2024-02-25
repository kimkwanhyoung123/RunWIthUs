package com.lx.runwithus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lx.fragment.OnFragmentRunningListener
import com.lx.miniproject.RunningCountDownFragment
import com.lx.runwithus.databinding.ActivityRunningBinding

class RunningActivity : AppCompatActivity(), OnFragmentRunningListener {

    lateinit var binding: ActivityRunningBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRunningBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onFragmentChanged(RunningFragmentType.RUNNINGCOUNTDOWN)


    }

    override fun onFragmentChanged(type: RunningFragmentType) {
        when (type) {
            RunningFragmentType.RUNNINGCOUNT -> {
                supportFragmentManager.beginTransaction().replace(R.id.runningContainer, RunningCountFragment()).addToBackStack(null).commit()
            }
            RunningFragmentType.RUNNINGCOUNT2 -> {
                supportFragmentManager.beginTransaction().replace(R.id.runningContainer, RunningStopFragment()).addToBackStack(null).commit()
            }
            RunningFragmentType.RUNNINGHOME -> {
                supportFragmentManager.beginTransaction().replace(R.id.runningContainer, RunningStopFragment()).addToBackStack(null).commit()
            }
            RunningFragmentType.RUNNINGCOUNTDOWN -> {
                supportFragmentManager.beginTransaction().replace(R.id.runningContainer, RunningCountDownFragment()).addToBackStack(null).commit()
            }
            RunningFragmentType.AED -> {
                supportFragmentManager.beginTransaction().replace(R.id.runningContainer, AedFragment()).addToBackStack(null).commit()
            }

            else -> {}
        }
    }

    override fun onBackPressed() {

    }
}