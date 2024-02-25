package com.lx.runwithus

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lx.runwithus.databinding.MissionInItemBinding
import com.lx.runwithus.databinding.MycrewlistItemBinding

class MissionAdapter : RecyclerView.Adapter<MissionAdapter.ViewHolder>() {

    var items = ArrayList<Mission>()

    lateinit var listener: OnMissionClickListener

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MissionAdapter.ViewHolder {
        val binding = MissionInItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MissionAdapter.ViewHolder, position: Int) {
        val item = items[position]
        holder.setItem(item)
    }

    inner class ViewHolder(val binding: MissionInItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                listener.onItemClick(this, binding.root, adapterPosition)
            }
        }


        fun setItem(item: Mission) {
            Glide.with(binding.missionInImage).load(item.missionImg).into(binding.missionInImage)
        }
    }
}
