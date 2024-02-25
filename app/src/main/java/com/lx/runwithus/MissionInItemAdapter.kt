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

class MissionInItemAdapter(private val itemList: List<Mission_in_item>) :
    RecyclerView.Adapter<MissionInItemAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imageView: ImageView = itemView.findViewById(R.id.mission_in_image)
        // 뷰홀더 내의 뷰들을 여기에 참조하세요.

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.mission_in_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = itemList[position]

        currentItem.missionImg?.let { holder.imageView.setImageResource(it) }
        // 데이터를 뷰홀더에 바인딩하는 코드를 작성하세요.
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}