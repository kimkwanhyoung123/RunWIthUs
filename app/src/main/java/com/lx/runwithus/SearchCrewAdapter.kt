package com.lx.runwithus

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lx.runwithus.databinding.SearchcrewlistItemBinding

class SearchCrewAdapter : RecyclerView.Adapter<SearchCrewAdapter.ViewHolder>() {

    var items = ArrayList<SearchCrew>()

    lateinit var listener: OnSearchCrewClickListener

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchCrewAdapter.ViewHolder {
        val binding = SearchcrewlistItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchCrewAdapter.ViewHolder, position: Int) {
        val item = items[position]
        holder.setItem(item)
    }

    inner class ViewHolder(val binding: SearchcrewlistItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                listener.onItemClcik(this, binding.root, adapterPosition)
            }
        }


        fun setItem(item: SearchCrew) {
            binding.searchCrewName.text = item.name
            binding.searchCrewLocation.text = item.city + " " + item.gu
            binding.searchCrewPop.text = item.memberPop.toString() + "명"

            if (item.crewPhoto?.toString() == "1") {
                Glide.with(binding.searchCrewImage).load(R.drawable.crewimage1).into(binding.searchCrewImage)
            } else if(item.crewPhoto?.toString() == "2") {
                Glide.with(binding.searchCrewImage).load(R.drawable.crewimage2).into(binding.searchCrewImage)
            } else if(item.crewPhoto?.toString() == "3") {
                Glide.with(binding.searchCrewImage).load(R.drawable.crewimage3).into(binding.searchCrewImage)
            } else if(item.crewPhoto?.toString() == "4") {
                Glide.with(binding.searchCrewImage).load(R.drawable.crewimage4).into(binding.searchCrewImage)
            } else if(item.crewPhoto?.toString() == "5") {
                Glide.with(binding.searchCrewImage).load(R.drawable.crewimage5).into(binding.searchCrewImage)
            } else if(item.crewPhoto?.toString() == "6") {
                Glide.with(binding.searchCrewImage).load(R.drawable.crewimage6).into(binding.searchCrewImage)
            } else if(item.crewPhoto?.toString() == "7") {
                Glide.with(binding.searchCrewImage).load(R.drawable.crewimage7).into(binding.searchCrewImage)
            } else if(item.crewPhoto?.toString() == "8") {
                Glide.with(binding.searchCrewImage).load(R.drawable.crewimage8).into(binding.searchCrewImage)
            } else if(item.crewPhoto?.toString() == "9") {
                Glide.with(binding.searchCrewImage).load(R.drawable.crewimage9).into(binding.searchCrewImage)
            } else if(item.crewPhoto?.toString() == "10") {
                Glide.with(binding.searchCrewImage).load(R.drawable.crewimage10).into(binding.searchCrewImage)
            } else if(item.crewPhoto?.toString() == "11") {
                Glide.with(binding.searchCrewImage).load(R.drawable.crewimage11).into(binding.searchCrewImage)
            } else if(item.crewPhoto?.toString() == "12") {
                Glide.with(binding.searchCrewImage).load(R.drawable.crewimage12).into(binding.searchCrewImage)
            } else if(item.crewPhoto?.toString() == "13") {
                Glide.with(binding.searchCrewImage).load(R.drawable.crewimage13).into(binding.searchCrewImage)
            } else if(item.crewPhoto?.toString() == "14") {
                Glide.with(binding.searchCrewImage).load(R.drawable.crewimage14).into(binding.searchCrewImage)
            } else if(item.crewPhoto?.toString() == "15") {
                Glide.with(binding.searchCrewImage).load(R.drawable.crewimage15).into(binding.searchCrewImage)
            } else if(item.crewPhoto?.toString() == "16") {
                Glide.with(binding.searchCrewImage).load(R.drawable.crewimage16).into(binding.searchCrewImage)
            } else if(item.crewPhoto?.toString() == "17") {
                Glide.with(binding.searchCrewImage).load(R.drawable.crewimage17).into(binding.searchCrewImage)
            } else if(item.crewPhoto?.toString() == "18") {
                Glide.with(binding.searchCrewImage).load(R.drawable.crewimage0).into(binding.searchCrewImage)
            } else {

            }

        }
    }
}
