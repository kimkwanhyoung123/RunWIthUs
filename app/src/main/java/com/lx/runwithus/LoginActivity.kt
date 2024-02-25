package com.lx.runwithus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lx.runwithus.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding

    var empty = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        successLogin()
        signup()

    }

    fun successLogin() {
        binding.successLoginButton.setOnClickListener {
            var id = binding.inputId.text.toString().trim()
            var password = binding.inputPassword.text.toString().trim()

            login(id,password)

        }
    }

    fun signup() {
        binding.signupButton.setOnClickListener {
            val intent = Intent(this, AgreementActivity::class.java)
            //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    // 입력한 id로 해당하는 user의 password를 조회 후 입력한 것과 대조하기
    fun login(id: String, password: String) {
        val store = Firebase.firestore
        val queryT = store.collection("users").whereEqualTo("id",id)
        empty = true

        queryT.get().addOnSuccessListener {
                documents ->

            for (document in documents) {

                var indexPassword = document.data.get("password")
                if (indexPassword == password) {
                    empty = false
                    AppData.id = document.id
                    val intent = Intent(this, MainActivity::class.java)
                    //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                } else {
                    empty = false
                    Toast.makeText(this,"비밀번호가 일치하지 않습니다", Toast.LENGTH_LONG).show()
                }

            }

        }.addOnFailureListener{
                exception -> Log.w("login_failed", "실패", exception)
        }.addOnCompleteListener{
            if (empty == true) {
                Toast.makeText(this,"입력하신 아이디가 없습니다", Toast.LENGTH_SHORT).show()
            }
        }


    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, StartActivity::class.java)
        //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}