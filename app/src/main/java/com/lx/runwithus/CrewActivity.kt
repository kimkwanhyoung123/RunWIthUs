package com.lx.runwithus

import CalendarFragment
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.lx.fragment.OnFragmentSignUpListener
import com.lx.runwithus.databinding.ActivityCrewBinding

class CrewActivity : AppCompatActivity(), OnFragmentSignUpListener {

    lateinit var binding: ActivityCrewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        bottomChange()
        binding.bottomNavigationView.selectedItemId = R.id.detailed
        onFragmentChanged(FragmentType.STATISTICS)

    }

    fun bottomChange() {
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.home -> {
                    onFragmentChanged(FragmentType.MAIN)
                }
                R.id.calendar -> {
                    onFragmentChanged(FragmentType.CALENDAR)
                }
                R.id.running -> {
                    onFragmentChanged(FragmentType.RUNNING)
                }
                R.id.chatting -> {
                    onFragmentChanged(FragmentType.CHATTING)
                }
                R.id.detailed -> {
                    onFragmentChanged(FragmentType.DETAILED)
                }
            }

            true
        }
    }

    override fun onFragmentChanged(type: FragmentType) {
        when (type) {
            FragmentType.MAIN-> {
                val intent = Intent(this, MainActivity::class.java)
                //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }

            FragmentType.CALENDAR -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, CalendarFragment()).addToBackStack(null).commit()
            }

            FragmentType.RUNNING -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, RunningFragment()).addToBackStack(null).commit()
            }

            FragmentType.CHATTING -> {
                goChatting()
            }

            FragmentType.DETAILED -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, DetailedFragment()).addToBackStack(null).commit()
            }

            FragmentType.STATISTICS -> {
                supportFragmentManager.beginTransaction().replace(R.id.detailedCrewContainer, StatisticsFragment()).addToBackStack(null).commit()
            }

            FragmentType.MISSION -> {
                supportFragmentManager.beginTransaction().replace(R.id.detailedCrewContainer, MissionFragment()).addToBackStack(null).commit()
            }

            else -> {}
        }

    }

    fun goChatting() {
        val intent = Intent(this, ChatActivity::class.java)
        //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun onBackPressed() {
        if (AppData.where == "AllCrewFragment") {
            val intent = Intent(this, CrewItemActivity::class.java)
            //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.putExtra("BUTTON_TYPE", "allCrewList")
            startActivity(intent)
        } else {
            val intent = Intent(this, MainActivity::class.java)
            //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

    }
}