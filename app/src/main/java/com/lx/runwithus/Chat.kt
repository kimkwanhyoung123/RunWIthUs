package com.lx.runwithus

import java.sql.Timestamp
import java.util.Date

data class Chat(

    var message:String? = null,
    var sendId:String? = null,
    var crewId:String? = null,
    var time: String? = null,


) {
    constructor() : this("","","", "")
}