package com.lx.runwithus

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lx.runwithus.databinding.AlarmListItemBinding
import com.lx.runwithus.databinding.MissionInItemBinding
import com.lx.runwithus.databinding.MycrewlistItemBinding

class AlarmAdapter : RecyclerView.Adapter<AlarmAdapter.ViewHolder>() {

    var items = ArrayList<Alarm>()

    lateinit var listener: OnAlarmClickListener

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmAdapter.ViewHolder {
        val binding = AlarmListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlarmAdapter.ViewHolder, position: Int) {
        val item = items[position]
        holder.setItem(item)
    }

    inner class ViewHolder(val binding: AlarmListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                listener.onItemClick(this, binding.root, adapterPosition)
            }
        }


        fun setItem(item: Alarm) {
            binding.alarmTitle.text = item.title
            binding.alarmInfo.text = item.info
            setImage(item.crewName.toString())
            

            if (item.title == "이런저런 에서 일정을 등록하였습니다.") {
                Glide.with(binding.alarmImage).load(R.drawable.crewimage12).into(binding.alarmImage)
            }

        }

        fun setImage(crewName:String) {
            val databaseR = Firebase.firestore
            databaseR.collection("crews").whereEqualTo("name", crewName).get().addOnSuccessListener {
                data ->
                for (datum in data) {
                    var photo = datum.get("photo").toString()
                    if (photo?.toInt() == 1) {
                        Glide.with(binding.alarmImage).load(R.drawable.crewimage1).into(binding.alarmImage)
                    } else if(photo?.toInt() == 2) {
                        Glide.with(binding.alarmImage).load(R.drawable.crewimage2).into(binding.alarmImage)
                    } else if(photo?.toInt() == 3) {
                        Glide.with(binding.alarmImage).load(R.drawable.crewimage3).into(binding.alarmImage)
                    } else if(photo?.toInt() == 4) {
                        Glide.with(binding.alarmImage).load(R.drawable.crewimage4).into(binding.alarmImage)
                    } else if(photo?.toInt() == 5) {
                        Glide.with(binding.alarmImage).load(R.drawable.crewimage5).into(binding.alarmImage)
                    } else if(photo?.toInt() == 6) {
                        Glide.with(binding.alarmImage).load(R.drawable.crewimage6).into(binding.alarmImage)
                    } else if(photo?.toInt() == 7) {
                        Glide.with(binding.alarmImage).load(R.drawable.crewimage7).into(binding.alarmImage)
                    } else if(photo?.toInt() == 8) {
                        Glide.with(binding.alarmImage).load(R.drawable.crewimage8).into(binding.alarmImage)
                    } else if(photo?.toInt() == 9) {
                        Glide.with(binding.alarmImage).load(R.drawable.crewimage9).into(binding.alarmImage)
                    } else if(photo?.toInt() == 10) {
                        Glide.with(binding.alarmImage).load(R.drawable.crewimage10).into(binding.alarmImage)
                    } else if(photo?.toInt() == 11) {
                        Glide.with(binding.alarmImage).load(R.drawable.crewimage11).into(binding.alarmImage)
                    } else if(photo?.toInt() == 12) {
                        Glide.with(binding.alarmImage).load(R.drawable.crewimage12).into(binding.alarmImage)
                    } else if(photo?.toInt() == 13) {
                        Glide.with(binding.alarmImage).load(R.drawable.crewimage13).into(binding.alarmImage)
                    } else if(photo?.toInt() == 14) {
                        Glide.with(binding.alarmImage).load(R.drawable.crewimage14).into(binding.alarmImage)
                    } else if(photo?.toInt() == 15) {
                        Glide.with(binding.alarmImage).load(R.drawable.crewimage15).into(binding.alarmImage)
                    } else if(photo?.toInt() == 16) {
                        Glide.with(binding.alarmImage).load(R.drawable.crewimage16).into(binding.alarmImage)
                    } else if(photo?.toInt() == 17) {
                        Glide.with(binding.alarmImage).load(R.drawable.crewimage17).into(binding.alarmImage)
                    } else if(photo?.toInt() == 18) {
                        Glide.with(binding.alarmImage).load(R.drawable.crewimage0).into(binding.alarmImage)
                    } else {

                    }
                }
            }
        }
    }
}
