package com.lx.runwithus

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lx.runwithus.databinding.AllRankinglistItemBinding

class RankAdapter : RecyclerView.Adapter<RankAdapter.ViewHolder>() {

    var items = ArrayList<RankCrew>()

    lateinit var listener: OnRankCrewClickListener

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankAdapter.ViewHolder {
        val binding = AllRankinglistItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RankAdapter.ViewHolder, position: Int) {
        val item = items[position]
        holder.setItem(item)
    }

    inner class ViewHolder(val binding: AllRankinglistItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                listener.onItemClcik(this, binding.root, adapterPosition)
            }
        }


        fun setItem(item: RankCrew) {
            binding.rank.text = item.rank.toString()
            getName(item.name.toString())
            binding.distance.text = item.distance.toString() + " km"


        }

        fun getName(userId: String) {
            val databaseR = Firebase.firestore
            databaseR.collection("users").document(userId).get().addOnSuccessListener {
                    data ->
                binding.rankname.text = data.get("nickname").toString()
                var photo = data.get("photo").toString()
                if (photo?.toInt() == 1) {
                    Glide.with(binding.image).load(R.drawable.face1).into(binding.image)
                } else if(photo?.toInt() == 2) {
                    Glide.with(binding.image).load(R.drawable.face2).into(binding.image)
                } else if(photo?.toInt() == 3) {
                    Glide.with(binding.image).load(R.drawable.face3).into(binding.image)
                } else if(photo?.toInt() == 4) {
                    Glide.with(binding.image).load(R.drawable.face4).into(binding.image)
                } else if(photo?.toInt() == 5) {
                    Glide.with(binding.image).load(R.drawable.face5).into(binding.image)
                } else if(photo?.toInt() == 6) {
                    Glide.with(binding.image).load(R.drawable.face6).into(binding.image)
                } else if(photo?.toInt() == 7) {
                    Glide.with(binding.image).load(R.drawable.face7).into(binding.image)
                } else if(photo?.toInt() == 8) {
                    Glide.with(binding.image).load(R.drawable.face8).into(binding.image)
                } else if(photo?.toInt() == 9) {
                    Glide.with(binding.image).load(R.drawable.face9).into(binding.image)
                } else if(photo?.toInt() == 10) {
                    Glide.with(binding.image).load(R.drawable.face10).into(binding.image)
                } else if(photo?.toInt() == 11) {
                    Glide.with(binding.image).load(R.drawable.face11).into(binding.image)
                } else if(photo?.toInt() == 12) {
                    Glide.with(binding.image).load(R.drawable.face12).into(binding.image)
                } else if(photo?.toInt() == 13) {
                    Glide.with(binding.image).load(R.drawable.face13).into(binding.image)
                } else if(photo?.toInt() == 14) {
                    Glide.with(binding.image).load(R.drawable.face14).into(binding.image)
                } else if(photo?.toInt() == 15) {
                    Glide.with(binding.image).load(R.drawable.face15).into(binding.image)
                } else if(photo?.toInt() == 16) {
                    Glide.with(binding.image).load(R.drawable.face16).into(binding.image)
                } else if(photo?.toInt() == 17) {
                    Glide.with(binding.image).load(R.drawable.face17).into(binding.image)
                } else if(photo?.toInt() == 18) {
                    Glide.with(binding.image).load(R.drawable.face18).into(binding.image)
                } else if(photo?.toInt() == 19) {
                    Glide.with(binding.image).load(R.drawable.face19).into(binding.image)
                } else if(photo?.toInt() == 20) {
                    Glide.with(binding.image).load(R.drawable.face20).into(binding.image)
                } else if(photo?.toInt() == 21) {
                    Glide.with(binding.image).load(R.drawable.face21).into(binding.image)
                } else if(photo?.toInt() == 22) {
                    Glide.with(binding.image).load(R.drawable.face22).into(binding.image)
                } else if(photo?.toInt() == 23) {
                    Glide.with(binding.image).load(R.drawable.face23).into(binding.image)
                } else if(photo?.toInt() == 24) {
                    Glide.with(binding.image).load(R.drawable.face24).into(binding.image)
                } else if(photo?.toInt() == 25) {
                    Glide.with(binding.image).load(R.drawable.face25).into(binding.image)
                } else if(photo?.toInt() == 26) {
                    Glide.with(binding.image).load(R.drawable.crewimage0).into(binding.image)
                } else {

                }
            }


        }
    }
}
