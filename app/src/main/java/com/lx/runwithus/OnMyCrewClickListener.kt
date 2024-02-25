package com.lx.runwithus

import android.view.View

interface OnMyCrewClickListener {

    fun onItemClcik(holder: MyCrewAdapter.ViewHolder?, view: View?, position: Int)

}