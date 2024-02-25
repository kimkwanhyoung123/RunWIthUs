package com.lx.runwithus

import android.view.View
import androidx.recyclerview.widget.RecyclerView

interface OnChatClickListener {

    fun onItemClcik(holder: RecyclerView.ViewHolder, view: View?, position: Int)

}