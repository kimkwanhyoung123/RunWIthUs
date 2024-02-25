package com.lx.runwithus

import android.view.View

interface OnRecommendCrewClickListener {

    fun onItemClcik(holder: RecommendCrewAdapter.ViewHolder?, view: View?, position: Int)

}