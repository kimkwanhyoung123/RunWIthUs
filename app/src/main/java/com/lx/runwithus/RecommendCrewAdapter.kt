package com.lx.runwithus

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lx.runwithus.databinding.RecommendcrewListBinding

class RecommendCrewAdapter : RecyclerView.Adapter<RecommendCrewAdapter.ViewHolder>() {

    var items = ArrayList<RecommendCrew>()

    lateinit var listener: OnRecommendCrewClickListener

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendCrewAdapter.ViewHolder {

        val binding = RecommendcrewListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: RecommendCrewAdapter.ViewHolder, position: Int) {
        val item = items[position]
        holder.setItem(item)
    }

    inner class ViewHolder(val binding: RecommendcrewListBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener{
                listener.onItemClcik(this,binding.root,adapterPosition)
            }
        }

        fun setItem(item: RecommendCrew) {


            if(item.crewPhoto == 1) {
                Glide.with(binding.recommendCrewImage).load(R.drawable.crewimage1).into(binding.recommendCrewImage)
            } else if(item.crewPhoto == 2) {
                Glide.with(binding.recommendCrewImage).load(R.drawable.crewimage2).into(binding.recommendCrewImage)
            } else if(item.crewPhoto == 3) {
                Glide.with(binding.recommendCrewImage).load(R.drawable.crewimage3).into(binding.recommendCrewImage)
            } else if(item.crewPhoto == 4) {
                Glide.with(binding.recommendCrewImage).load(R.drawable.crewimage4).into(binding.recommendCrewImage)
            } else if(item.crewPhoto == 5) {
                Glide.with(binding.recommendCrewImage).load(R.drawable.crewimage5).into(binding.recommendCrewImage)
            } else if(item.crewPhoto == 6) {
                Glide.with(binding.recommendCrewImage).load(R.drawable.crewimage6)
                    .into(binding.recommendCrewImage)
            } else if(item.crewPhoto == 7) {
                Glide.with(binding.recommendCrewImage).load(R.drawable.crewimage7)
                    .into(binding.recommendCrewImage)
            } else if(item.crewPhoto == 8) {
                Glide.with(binding.recommendCrewImage).load(R.drawable.crewimage8)
                    .into(binding.recommendCrewImage)
            } else if(item.crewPhoto == 9) {
                Glide.with(binding.recommendCrewImage).load(R.drawable.crewimage9)
                    .into(binding.recommendCrewImage)
            } else if(item.crewPhoto == 10) {
                Glide.with(binding.recommendCrewImage).load(R.drawable.crewimage10)
                    .into(binding.recommendCrewImage)
            } else if(item.crewPhoto == 11) {
                Glide.with(binding.recommendCrewImage).load(R.drawable.crewimage11)
                    .into(binding.recommendCrewImage)
            } else if(item.crewPhoto == 12) {
                Glide.with(binding.recommendCrewImage).load(R.drawable.crewimage12)
                    .into(binding.recommendCrewImage)
            } else if(item.crewPhoto == 13) {
                Glide.with(binding.recommendCrewImage).load(R.drawable.crewimage13)
                    .into(binding.recommendCrewImage)
            } else if(item.crewPhoto == 14) {
                Glide.with(binding.recommendCrewImage).load(R.drawable.crewimage14)
                    .into(binding.recommendCrewImage)
            } else if(item.crewPhoto == 15) {
                Glide.with(binding.recommendCrewImage).load(R.drawable.crewimage15)
                    .into(binding.recommendCrewImage)
            } else if(item.crewPhoto == 16) {
                Glide.with(binding.recommendCrewImage).load(R.drawable.crewimage16)
                    .into(binding.recommendCrewImage)
            } else if(item.crewPhoto == 17) {
                Glide.with(binding.recommendCrewImage).load(R.drawable.crewimage17)
                    .into(binding.recommendCrewImage)
            }else {
                Glide.with(binding.recommendCrewImage).load(R.drawable.crewimage0)
                    .into(binding.recommendCrewImage)
            }



            binding.recommendCrewName.text = item.name

            binding.recommendCrewLocation.text = item.city + " " + item.gu

            binding.recommendCrewPop.text = item.memberPop.toString() + "명"



        }

    }

}