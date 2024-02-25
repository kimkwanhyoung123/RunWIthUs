package com.lx.runwithus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.firestore.firestore
import com.lx.runwithus.databinding.ActivityChatBinding

class ChatActivity : AppCompatActivity() {

    lateinit var binding: ActivityChatBinding
    lateinit var chatAdapter: ChatAdapter
    lateinit var chatList: ArrayList<Chat>
    var timer = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initList()

        binding.txtTItle.text = AppData.crewName

        binding.sendButton.setOnClickListener {
            addChat()
            binding.sendMessage.text.clear()
        }


        binding.gobackButton.setOnClickListener {
            val intent = Intent(this, CrewActivity::class.java)
            //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)

        }

        updateList()

    }

    fun initList() {

        // 리싸이클러뷰의 모양 설정 (리스트 모양, 격자 모양, 좌우 스크롤, 상하 스크롤 등을 결졍)
        binding.chatList.layoutManager = LinearLayoutManager(this)

        // 리싸이클러뷰의 어댑터 설정
        chatList = ArrayList()
        chatAdapter = ChatAdapter(this, chatList)
        binding.chatList.adapter = chatAdapter

        //리스너 등록하기
        chatAdapter.listener = object: OnChatClickListener {
            override fun onItemClcik(holder: RecyclerView.ViewHolder, view: View?, position: Int) {
                val item = chatAdapter.chatList.get(position)
            }

        }
        //showMessage()

        // 리스트에 보이는 아이템을 새로고침하는 경우 (화면이 이미 보인 상태에서 버튼을 눌러서 웹서버에 있는 내용을 넣는다고 하면 새로고침이 필요함)


    }

    fun showMessage() {
        val databaseR = Firebase.firestore
        databaseR.collection("chat_room").orderBy("createdAt").get().addOnSuccessListener {
                documents ->
            for (document in documents) {
                if (document.get("crewId").toString() == AppData.crewId) {
                    var time = document.get("createdAt") as Timestamp
                    var hour = time.toDate().hours
                    var noon:String? = null
                    if (hour >= 24) {
                        hour = hour - 24
                    }

                    if (hour > 12) {
                        hour = hour- 12
                        noon = "오후 "
                    } else if (hour == 12) {
                        noon = "오후 "
                    } else {
                        noon = "오전 "
                    }
                    val timeString = noon + (hour).toString() + " 시 " + time.toDate().minutes + " 분 "

                    chatList.add(Chat(message = document.get("message").toString(), sendId = document.get("uid").toString(), time= timeString))
                }
                chatAdapter.notifyDataSetChanged()

            }

        }
    }

    fun addChat() {
        val chat = hashMapOf(
            "createdAt" to Timestamp.now(),
            "crewId" to AppData.crewId,
            "message" to binding.sendMessage.text.toString().trim(),
            "uid" to AppData.id,
        )
        val databaseR = Firebase.firestore
        databaseR.collection("chat_room").add(chat).addOnCompleteListener {

        }
        chatList.clear()
        showMessage()
    }

    fun updateList() {
        chatList.clear()
        showMessage()

        timer.postDelayed(::updateList, 2000)
    }
}