package com.lx.runwithus

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lx.runwithus.databinding.MissionInItemBinding
import com.lx.runwithus.databinding.MissionListItemBinding
import com.lx.runwithus.databinding.MycrewlistItemBinding

class MissionListAdapter : RecyclerView.Adapter<MissionListAdapter.ViewHolder>() {

    var items = ArrayList<Mission_list>()

    var selectedMissionPosition: Int = RecyclerView.NO_POSITION

    lateinit var listener: OnMissionListClickListener

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MissionListAdapter.ViewHolder {
        val binding = MissionListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MissionListAdapter.ViewHolder, position: Int) {
        val item = items[position]
        holder.setItem(item)

        holder.itemView.setOnClickListener{
            //selectedMissionPosition = position
            listener.onItemClick(holder, it, position)
        }
    }

    inner class ViewHolder(val binding: MissionListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                listener.onItemClick(this, binding.root, adapterPosition)
            }
        }


        fun setItem(item: Mission_list) {
            binding.missionTitle.text = item.title
            binding.missionInfo.text = item.info
            binding.missionDate.text = item.date
            Glide.with(binding.missionImage).load(item.missionImg).into(binding.missionImage)
        }



    }

}
