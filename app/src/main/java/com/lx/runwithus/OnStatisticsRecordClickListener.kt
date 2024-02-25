package com.lx.runwithus

import android.view.View

interface OnStatisticsRecordClickListener {

    fun onItemClcik(holder: StatisticsRecordAdapter.ViewHolder, view: View?, position: Int)

}