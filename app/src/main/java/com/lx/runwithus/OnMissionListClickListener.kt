package com.lx.runwithus

import android.view.View

interface OnMissionListClickListener {

    fun onItemClick(holder: MissionListAdapter.ViewHolder?, view: View?, position: Int)

}