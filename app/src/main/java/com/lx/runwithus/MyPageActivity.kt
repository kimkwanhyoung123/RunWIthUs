package com.lx.runwithus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lx.runwithus.databinding.ActivityMyPageBinding

class MyPageActivity : AppCompatActivity() {

    lateinit var binding: ActivityMyPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getNickName()

        binding.homeButton.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        binding.logout.setOnClickListener {
            var intent = Intent(this, StartActivity::class.java)
            //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

    }

    fun getNickName() {
        val databaseR = Firebase.firestore
        databaseR.collection("users").document(AppData.id.toString()).get().addOnSuccessListener {
            data ->
            binding.username.text = data.get("nickname").toString()
            binding.currentCityAndGu.text = "현재 희망 운동 장소 : " + data.get("city").toString() + " " + data.get("gu").toString()
            var photo = data.get("photo").toString()

            if (photo?.toInt() == 1) {
                Glide.with(binding.firstRankerImage).load(R.drawable.face1).into(binding.firstRankerImage)
            } else if (photo?.toInt() == 2) {
                Glide.with(binding.firstRankerImage).load(R.drawable.face2).into(binding.firstRankerImage)
            } else if (photo?.toInt() == 3) {
                Glide.with(binding.firstRankerImage).load(R.drawable.face3).into(binding.firstRankerImage)
            } else if (photo?.toInt() == 4) {
                Glide.with(binding.firstRankerImage).load(R.drawable.face4).into(binding.firstRankerImage)
            } else if (photo?.toInt() == 5) {
                Glide.with(binding.firstRankerImage).load(R.drawable.face5).into(binding.firstRankerImage)
            } else if (photo?.toInt() == 6) {
                Glide.with(binding.firstRankerImage).load(R.drawable.face6).into(binding.firstRankerImage)
            } else if (photo?.toInt() == 7) {
                Glide.with(binding.firstRankerImage).load(R.drawable.face7).into(binding.firstRankerImage)
            } else if (photo?.toInt() == 8) {
                Glide.with(binding.firstRankerImage).load(R.drawable.face8).into(binding.firstRankerImage)
            } else if (photo?.toInt() == 9) {
                Glide.with(binding.firstRankerImage).load(R.drawable.face9).into(binding.firstRankerImage)
            } else if (photo?.toInt() == 10) {
                Glide.with(binding.firstRankerImage).load(R.drawable.face10).into(binding.firstRankerImage)
            } else if (photo?.toInt() == 11) {
                Glide.with(binding.firstRankerImage).load(R.drawable.face11).into(binding.firstRankerImage)
            } else if (photo?.toInt() == 12) {
                Glide.with(binding.firstRankerImage).load(R.drawable.face12).into(binding.firstRankerImage)
            } else if (photo?.toInt() == 13) {
                Glide.with(binding.firstRankerImage).load(R.drawable.face13).into(binding.firstRankerImage)
            } else if (photo?.toInt() == 14) {
                Glide.with(binding.firstRankerImage).load(R.drawable.face14).into(binding.firstRankerImage)
            } else if (photo?.toInt() == 15) {
                Glide.with(binding.firstRankerImage).load(R.drawable.face15).into(binding.firstRankerImage)
            } else if (photo?.toInt() == 16) {
                Glide.with(binding.firstRankerImage).load(R.drawable.face16).into(binding.firstRankerImage)
            } else if (photo?.toInt() == 17) {
                Glide.with(binding.firstRankerImage).load(R.drawable.face17).into(binding.firstRankerImage)
            } else if (photo?.toInt() == 18) {
                Glide.with(binding.firstRankerImage).load(R.drawable.face18).into(binding.firstRankerImage)
            } else if (photo?.toInt() == 19) {
                Glide.with(binding.firstRankerImage).load(R.drawable.face19).into(binding.firstRankerImage)
            } else if (photo?.toInt() == 20) {
                Glide.with(binding.firstRankerImage).load(R.drawable.face20).into(binding.firstRankerImage)
            } else if (photo?.toInt() == 21) {
                Glide.with(binding.firstRankerImage).load(R.drawable.face21).into(binding.firstRankerImage)
            } else if (photo?.toInt() == 22) {
                Glide.with(binding.firstRankerImage).load(R.drawable.face22).into(binding.firstRankerImage)
            } else if (photo?.toInt() == 23) {
                Glide.with(binding.firstRankerImage).load(R.drawable.face23).into(binding.firstRankerImage)
            } else if (photo?.toInt() == 24) {
                Glide.with(binding.firstRankerImage).load(R.drawable.face24).into(binding.firstRankerImage)
            } else if (photo?.toInt() == 25) {
                Glide.with(binding.firstRankerImage).load(R.drawable.face25).into(binding.firstRankerImage)
            } else if (photo?.toInt() == 26) {
                Glide.with(binding.firstRankerImage).load(R.drawable.crewimage0).into(binding.firstRankerImage)
            } else {

            }

        }
    }

    override fun onBackPressed() {

    }
}