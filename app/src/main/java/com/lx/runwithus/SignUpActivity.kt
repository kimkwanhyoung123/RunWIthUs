package com.lx.runwithus

import android.app.DatePickerDialog
import android.content.Intent
import android.content.res.ColorStateList
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.DatePicker
import androidx.core.content.ContextCompat
import com.lx.fragment.OnFragmentSignUpListener
import com.lx.runwithus.databinding.ActivitySignUpBinding
import java.util.Locale


class SignUpActivity : AppCompatActivity(), OnFragmentSignUpListener {

    lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onFragmentChanged(FragmentType.SIGNUP1)

    }

    override fun onFragmentChanged(type: FragmentType) {
        when(type) {
            FragmentType.SIGNUP1 -> {
                supportFragmentManager.beginTransaction().replace(R.id.signUpContainer, SignUp1Fragment())
                    .commit()
            }

            FragmentType.SIGNUP2 -> {
                supportFragmentManager.beginTransaction().replace(R.id.signUpContainer, SignUp2Fragment())
                    .commit()
            }
            else -> {}
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, StartActivity::class.java)
        //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}