package com.lx.runwithus

import android.view.View

interface OnMissionClickListener {

    fun onItemClick(holder: MissionAdapter.ViewHolder?, view: View?, position: Int)

}