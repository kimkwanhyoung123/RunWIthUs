package com.lx.runwithus

import CarouselAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.lx.runwithus.databinding.ActivityStartBinding
import androidx.viewpager2.widget.ViewPager2

class StartActivity : AppCompatActivity() {

    lateinit var binding: ActivityStartBinding
    private var LastPressed : Long = 1500

    //캐러셀
    private lateinit var viewPager: ViewPager2
    private lateinit var carouselAdapter: CarouselAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        changeViewSignUp()
        changeViewLogin()

        viewPager = binding.viewPager
        carouselAdapter = CarouselAdapter()
        viewPager.adapter = carouselAdapter
    }

    fun changeViewSignUp() {
        binding.signUpButton.setOnClickListener {
            val intent = Intent(this, AgreementActivity::class.java)
            //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    fun changeViewLogin() {
        binding.loginButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    // 뒤로 가기를 눌렀을 때 한번 누르고 1.5초 이내 다시 누르면 꺼짐
    override fun onBackPressed() {
        if(System.currentTimeMillis() - LastPressed >= 1500) {
            LastPressed = System.currentTimeMillis()
            Toast.makeText(this, "이전 버튼을 한 번 더 누르면 앱이 종료됩니다", Toast.LENGTH_SHORT).show()
        }else{
            ActivityCompat.finishAffinity(this)
            System.exit(0)
        }
    }
}