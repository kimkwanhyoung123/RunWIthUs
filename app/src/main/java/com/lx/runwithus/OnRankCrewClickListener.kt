package com.lx.runwithus

import android.view.View

interface OnRankCrewClickListener {

    fun onItemClcik(holder: RankAdapter.ViewHolder?, view: View?, position: Int)

}