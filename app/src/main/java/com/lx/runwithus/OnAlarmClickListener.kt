package com.lx.runwithus

import android.view.View

interface OnAlarmClickListener {

    fun onItemClick(holder: AlarmAdapter.ViewHolder?, view: View?, position: Int)

}