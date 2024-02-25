package com.lx.runwithus

import android.app.DatePickerDialog
import android.content.Intent
import android.content.res.ColorStateList
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.DatePicker
import android.widget.RadioButton
import android.widget.Spinner
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.lx.runwithus.databinding.ActivityAgreementBinding
import java.util.Locale

class AgreementActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAgreementBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgreementBinding.inflate(layoutInflater)
        setContentView(binding.root)

        changeViewAgreement()
        gobackStart()

        val checkBoxAll = findViewById<CheckBox>(R.id.checkBoxAll)
        val checkBoxTerms = findViewById<CheckBox>(R.id.checkBoxTerms)
        val checkBoxPrivacy = findViewById<CheckBox>(R.id.checkBoxPrivacy)
        val checkBoxLocation = findViewById<CheckBox>(R.id.checkBoxLocation)
        val checkBoxAds = findViewById<CheckBox>(R.id.checkBoxAds)
        val btnAgreeAndStart = findViewById<Button>(R.id.btnAgreeAndStart)

        var index = true

        btnAgreeAndStart.isEnabled = false
        btnAgreeAndStart.alpha = 0.5f

        // 전체 동의 체크박스 클릭 시 나머지 체크박스 자동 선택
        checkBoxAll.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                index = true
                checkBoxTerms.isChecked = isChecked
                checkBoxPrivacy.isChecked = isChecked
                checkBoxLocation.isChecked = isChecked
                checkBoxAds.isChecked = isChecked

                // 동의하고 시작하기 버튼의 활성화 상태 조절
                btnAgreeAndStart.isEnabled = isChecked
                // 버튼의 투명도 조절 (비활성화 시 투명하게)
                btnAgreeAndStart.alpha = if (isChecked) 1.0f else 0.5f

                index = false
            }

        }


        checkBoxTerms.setOnCheckedChangeListener { _, isChecked ->
            if (checkBoxTerms.isChecked && checkBoxPrivacy.isChecked && checkBoxLocation.isChecked && checkBoxAds.isChecked) {
                checkBoxAll.isChecked = true
            }
            if (checkBoxTerms.isChecked && checkBoxPrivacy.isChecked && checkBoxLocation.isChecked) {
                btnAgreeAndStart.isEnabled = true
                btnAgreeAndStart.alpha = 1.0f
            } else {
                btnAgreeAndStart.isEnabled = false
                btnAgreeAndStart.alpha = 0.5f
                if (!index) {
                    checkBoxAll.isChecked = false
                }

            }

        }

        checkBoxPrivacy.setOnCheckedChangeListener { _, isChecked ->
            if (checkBoxTerms.isChecked && checkBoxPrivacy.isChecked && checkBoxLocation.isChecked && checkBoxAds.isChecked) {
                checkBoxAll.isChecked = true
            }
            if (checkBoxTerms.isChecked && checkBoxPrivacy.isChecked && checkBoxLocation.isChecked) {
                btnAgreeAndStart.isEnabled = true
                btnAgreeAndStart.alpha = 1.0f
            } else {
                btnAgreeAndStart.isEnabled = false
                btnAgreeAndStart.alpha = 0.5f
                if (!index) {
                    checkBoxAll.isChecked = false
                }
            }

        }

        checkBoxLocation.setOnCheckedChangeListener { _, isChecked ->
            if (checkBoxTerms.isChecked && checkBoxPrivacy.isChecked && checkBoxLocation.isChecked && checkBoxAds.isChecked) {
                checkBoxAll.isChecked = true
            }
            if (checkBoxTerms.isChecked && checkBoxPrivacy.isChecked && checkBoxLocation.isChecked) {
                btnAgreeAndStart.isEnabled = true
                btnAgreeAndStart.alpha = 1.0f
            } else {
                btnAgreeAndStart.isEnabled = false
                btnAgreeAndStart.alpha = 0.5f
                if (!index) {
                    checkBoxAll.isChecked = false
                }
            }

        }

        checkBoxAds.setOnCheckedChangeListener { _, isChecked ->
            if (checkBoxTerms.isChecked && checkBoxPrivacy.isChecked && checkBoxLocation.isChecked && checkBoxAds.isChecked) {
                checkBoxAll.isChecked = true
            } else {
                if (!index) {
                    checkBoxAll.isChecked = false
                }
            }

        }


    }

    fun changeViewAgreement() {
        binding.btnAgreeAndStart.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    fun gobackStart() {
        binding.gobackStartButton.setOnClickListener {
            val intent = Intent(this, StartActivity::class.java)
            //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}
