package com.lx.runwithus

import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance

class AppData {
    var count: Int = 0
    var progress: Int = 0

    companion object {
        var id:String? = null
        var name:String? = null
        var email:String? = null
        var password:String? = null
        var nickname:String? = null
        var birth:Int? = null
        var weight:Int? = null
        var height:Int? = null
        var address:String? = null
        var crewId:String? = null
        var grade:String? = null
        var crewName:String? = null
        var noticeId:String? = null
        var ready:Boolean = false
        var distance:String? = null
        var gender:String? = null
        var kcal:String? = null
        var velocity:String? = null
        var resultPicture:String? = null
        var backDistance:Double? = null
        var time:Long? = null
        var where:String? = null

        private var instance: AppData? = null

        fun getInstance(): AppData {
            if (instance == null) {
                instance = AppData()
            }
            return instance!!
        }


    }

}