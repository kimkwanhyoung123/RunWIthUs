package com.lx.runwithus

import android.content.Intent
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.lx.runwithus.databinding.RecordlistItemBinding

class StatisticsRecordAdapter : RecyclerView.Adapter<StatisticsRecordAdapter.ViewHolder>() {

    private var captureLauncher: ActivityResultLauncher<Intent>? = null

    var items = ArrayList<StatisticsRecord>()

    lateinit var listener: OnStatisticsRecordClickListener

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatisticsRecordAdapter.ViewHolder {

        val binding = RecordlistItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: StatisticsRecordAdapter.ViewHolder, position: Int) {
        val item = items[position]
        holder.setItem(item)
    }

    inner class ViewHolder(val binding: RecordlistItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {

            binding.root.setOnClickListener{
                listener.onItemClcik(this,binding.root,adapterPosition)
            }

//            binding.photoButton.setOnClickListener {
//                var captureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//                captureLauncher?.launch(captureIntent)
//            }
        }

        fun setItem(item: StatisticsRecord) {

            binding.recordDate.text = item.time.toString()
            binding.recordTime.text = item.startTime
            if (item.pace == "null") {
                binding.recordpace.text = "기록되지 않음"
            } else {
                binding.recordpace.text = item.pace
            }


            if (item.distance.toString() == "null m") {
                binding.recordDistance.text = "0 m"
            } else {
                binding.recordDistance.text = item.distance.toString()
            }
            val storage = Firebase.storage("gs://fair-kingdom-401602.appspot.com").getReference("runningCapture")
                storage.child(item.photo!!).downloadUrl.addOnFailureListener {
                    println(it)
                    println("실패다잇")
                }.addOnSuccessListener {
                        image ->
                    println("성공이다잇")
                    Glide.with(binding.recordImg).load(image).into(binding.recordImg)
                }

            // 기록 사진
            //Glide.with(binding.recordImg).load(item.photo).into(binding.recordImg)

        }

    }



}