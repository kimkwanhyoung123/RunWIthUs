package com.lx.runwithus

import android.os.Build.VERSION_CODES.P
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lx.runwithus.databinding.MycrewlistItemBinding

class MyCrewAdapter : RecyclerView.Adapter<MyCrewAdapter.ViewHolder>() {

    var items = ArrayList<MyCrew>()

    lateinit var listener: OnMyCrewClickListener

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCrewAdapter.ViewHolder {
        val binding = MycrewlistItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyCrewAdapter.ViewHolder, position: Int) {
        val item = items[position]
        holder.setItem(item)
    }

    inner class ViewHolder(val binding: MycrewlistItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                listener.onItemClcik(this, binding.root, adapterPosition)
            }
        }


        fun setItem(item: MyCrew) {

            var photo = item.crewPhoto

            if (photo == null) {
                photo = 1
            }

            if(photo == 1) {
                Glide.with(binding.myCrewImage).load(R.drawable.crewimage1).into(binding.myCrewImage)
            } else if(photo == 2) {
                Glide.with(binding.myCrewImage).load(R.drawable.crewimage2).into(binding.myCrewImage)
            } else if(photo == 3) {
                Glide.with(binding.myCrewImage).load(R.drawable.crewimage3).into(binding.myCrewImage)
            } else if(photo == 4) {
                Glide.with(binding.myCrewImage).load(R.drawable.crewimage4).into(binding.myCrewImage)
            } else if(photo== 5) {
                Glide.with(binding.myCrewImage).load(R.drawable.crewimage5).into(binding.myCrewImage)
            } else if(photo== 6) {
                Glide.with(binding.myCrewImage).load(R.drawable.crewimage6).into(binding.myCrewImage)
            } else if(photo== 7) {
                Glide.with(binding.myCrewImage).load(R.drawable.crewimage7).into(binding.myCrewImage)
            } else if(photo== 8) {
                Glide.with(binding.myCrewImage).load(R.drawable.crewimage8).into(binding.myCrewImage)
            } else if(photo== 9) {
                Glide.with(binding.myCrewImage).load(R.drawable.crewimage9).into(binding.myCrewImage)
            } else if(photo== 10) {
                Glide.with(binding.myCrewImage).load(R.drawable.crewimage10).into(binding.myCrewImage)
            } else if(photo== 11) {
                Glide.with(binding.myCrewImage).load(R.drawable.crewimage11).into(binding.myCrewImage)
            } else if(photo== 12) {
                Glide.with(binding.myCrewImage).load(R.drawable.crewimage12).into(binding.myCrewImage)
            } else if(photo== 13) {
                Glide.with(binding.myCrewImage).load(R.drawable.crewimage13).into(binding.myCrewImage)
            } else if(photo== 14) {
                Glide.with(binding.myCrewImage).load(R.drawable.crewimage14).into(binding.myCrewImage)
            } else if(photo== 15) {
                Glide.with(binding.myCrewImage).load(R.drawable.crewimage15).into(binding.myCrewImage)
            } else if(photo== 16) {
                Glide.with(binding.myCrewImage).load(R.drawable.crewimage16).into(binding.myCrewImage)
            } else if(photo== 17) {
                Glide.with(binding.myCrewImage).load(R.drawable.crewimage17).into(binding.myCrewImage)
            }  else {
                Glide.with(binding.myCrewImage).load(R.drawable.crewimage0).into(binding.myCrewImage)
            }





            indexLeader(item.id.toString())
            binding.myCrewName.text = item.name
            //Glide.with(binding.myCrewImage).load(item.crewPhoto).into(binding.myCrewImage)
        }

        fun indexLeader(crewId: String) {
            val databaseR = Firebase.firestore
            databaseR.collection("records").whereEqualTo("userId", AppData.id).whereEqualTo("crewId",crewId).get().addOnSuccessListener {
                    documents ->
                for (document in documents) {
                    if(document.get("grade") != "크루 리더") {
                        binding.reader.visibility = View.INVISIBLE
                    }
                }
            }
        }
    }
}