package com.lx.runwithus

import android.view.View

interface OnRunningClickListener {

    fun onItemClcik(holder: RunningReadyAdapter.ViewHolder, view: View?, position: Int)

}