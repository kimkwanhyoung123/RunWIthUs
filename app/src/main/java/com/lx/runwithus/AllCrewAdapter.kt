package com.lx.runwithus

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lx.runwithus.databinding.AllcrewlistItemBinding

class AllCrewAdapter : RecyclerView.Adapter<AllCrewAdapter.ViewHolder>() {

    var items = ArrayList<Crew>()

    lateinit var listener: OnAllCrewClickListener

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllCrewAdapter.ViewHolder {
        val binding = AllcrewlistItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AllCrewAdapter.ViewHolder, position: Int) {
        val item = items[position]
        holder.setItem(item)
    }

    inner class ViewHolder(val binding: AllcrewlistItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                listener.onItemClcik(this, binding.root, adapterPosition)
            }
        }


        fun setItem(item: Crew) {
            binding.allCrewName.text = item.name
            binding.allCrewLocation.text = item.city + " " + item.gu
            binding.allCrewPop.text = item.memberPop.toString() + "명"

            if (item.crewPhoto?.toString() == "1" || item.crewPhoto?.toString() == "16") {
                Glide.with(binding.allCrewImage).load(R.drawable.crewimage1).into(binding.allCrewImage)
            } else if(item.crewPhoto?.toString() == "2" || item.crewPhoto?.toString() == "17") {
                Glide.with(binding.allCrewImage).load(R.drawable.crewimage2).into(binding.allCrewImage)
            } else if(item.crewPhoto?.toString() == "3" || item.crewPhoto?.toString() == "18") {
                Glide.with(binding.allCrewImage).load(R.drawable.crewimage3).into(binding.allCrewImage)
            } else if(item.crewPhoto?.toString() == "4" || item.crewPhoto?.toString() == "19") {
                Glide.with(binding.allCrewImage).load(R.drawable.crewimage4).into(binding.allCrewImage)
            } else if(item.crewPhoto?.toString() == "5" || item.crewPhoto?.toString() == "20") {
                Glide.with(binding.allCrewImage).load(R.drawable.crewimage5).into(binding.allCrewImage)
            } else if(item.crewPhoto?.toString() == "6" || item.crewPhoto?.toString() == "21") {
                Glide.with(binding.allCrewImage).load(R.drawable.crewimage6).into(binding.allCrewImage)
            } else if(item.crewPhoto?.toString() == "7" || item.crewPhoto?.toString() == "22") {
                Glide.with(binding.allCrewImage).load(R.drawable.crewimage7).into(binding.allCrewImage)
            } else if(item.crewPhoto?.toString() == "8" || item.crewPhoto?.toString() == "23") {
                Glide.with(binding.allCrewImage).load(R.drawable.crewimage8).into(binding.allCrewImage)
            } else if(item.crewPhoto?.toString() == "9" || item.crewPhoto?.toString() == "24") {
                Glide.with(binding.allCrewImage).load(R.drawable.crewimage9).into(binding.allCrewImage)
            } else if(item.crewPhoto?.toString() == "10" || item.crewPhoto?.toString() == "25") {
                Glide.with(binding.allCrewImage).load(R.drawable.crewimage10).into(binding.allCrewImage)
            } else if(item.crewPhoto?.toString() == "11" || item.crewPhoto?.toString() == "26") {
                Glide.with(binding.allCrewImage).load(R.drawable.crewimage11).into(binding.allCrewImage)
            } else if(item.crewPhoto?.toString() == "12" || item.crewPhoto?.toString() == "27") {
                Glide.with(binding.allCrewImage).load(R.drawable.crewimage12).into(binding.allCrewImage)
            } else if(item.crewPhoto?.toString() == "13" || item.crewPhoto?.toString() == "28") {
                Glide.with(binding.allCrewImage).load(R.drawable.crewimage13).into(binding.allCrewImage)
            } else if(item.crewPhoto?.toString() == "14" || item.crewPhoto?.toString() == "29") {
                Glide.with(binding.allCrewImage).load(R.drawable.crewimage14).into(binding.allCrewImage)
            } else {
                Glide.with(binding.allCrewImage).load(R.drawable.crewimage0).into(binding.allCrewImage)
            }



        }
    }
}
