package com.lx.runwithus

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

class ChatAdapter(val context: Context, val chatList: ArrayList<Chat>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val receive = 1 // 받는 타입
    val send = 2 // 보내는 타입

    lateinit var listener: OnChatClickListener

    override fun getItemCount() = chatList.size

    override fun getItemViewType(position: Int): Int {

        // 메시지 값
        val currentMessage = chatList[position]

        return if (AppData.id.equals(currentMessage.sendId)) {
            send
        } else {
            receive
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if(viewType == 1) { // 받는 화면
            val view: View = LayoutInflater.from(context).inflate(R.layout.receive_item, parent, false)
            ReceiveViewHolder(view)
        } else { // 보내는 화면
            val view:View = LayoutInflater.from(context).inflate(R.layout.send_item, parent, false)
            SendViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // 현재 메시지
        val currentMessage = chatList[position]

        // 보내는 데이터
        if(holder.javaClass == SendViewHolder::class.java) {
            val viewHolder = holder as SendViewHolder
            viewHolder.sendMessage.text = currentMessage.message
            viewHolder.sendTime.text = currentMessage.time
        } else { // 받는 데이터
            val viewHolder = holder as ReceiveViewHolder
            val databaseR = Firebase.firestore
            databaseR.collection("users").document(currentMessage.sendId.toString()).get().addOnSuccessListener{
                data ->
                viewHolder.receiveName.text = data.get("nickname").toString()
            }
            viewHolder.receiveMessage.text = currentMessage.message
            viewHolder.receiveTime.text = currentMessage.time

        }
    }

    // 보낸 쪽
    class SendViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val sendMessage: TextView = itemView.findViewById(R.id.send_txt_message)
        val sendTime: TextView = itemView.findViewById(R.id.send_txt_date)
    }

    // 받는 쪽
    class ReceiveViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val receiveMessage: TextView = itemView.findViewById(R.id.receive_txt_message)
        val receiveTime: TextView = itemView.findViewById(R.id.receive_txt_date)
        val receiveName: TextView = itemView.findViewById(R.id.txt_isShown)
    }

}