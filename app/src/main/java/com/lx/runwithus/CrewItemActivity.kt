package com.lx.runwithus

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.lx.runwithus.databinding.ActivityCrewItemBinding


class CrewItemActivity : AppCompatActivity() {

    lateinit var binding: ActivityCrewItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrewItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val message = intent.getStringExtra("BUTTON_TYPE")

        binding.goBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        changeFragment(message)

    }

    fun changeFragment(message: String?) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        when (message) {
            "addCrew" -> {
                val addCrewFragment = AddCrewFragment()
                fragmentTransaction.replace(R.id.crewItemContainer, addCrewFragment)
                updateTitle("크루 추가")
            }
            "searchCrew" -> {
                val searchCrewFragment = SearchCrewFragment()
                fragmentTransaction.replace(R.id.crewItemContainer, searchCrewFragment)
                updateTitle("크루 검색")
            }
            "allCrewList" -> {
                val allCrewFragment = AllCrewFragment()
                fragmentTransaction.replace(R.id.crewItemContainer, allCrewFragment)
                updateTitle("모든 크루 항목")
            }
            "alarmList" -> {
                val alarmFragment = AlarmFragment()
                fragmentTransaction.replace(R.id.crewItemContainer, alarmFragment)
                updateTitle("모든 알림")
            }
            else -> {}
        }

        fragmentTransaction.commit()
    }

    fun updateTitle(title: String) {
        binding.crewItemTitle.text = title
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

}