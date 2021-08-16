package com.subi.likeanh.model

data class User(
    var uid:String,
    var phone: String,
    var name: String,
    var token:String,
    var status:Boolean,
    var bank:String,
    var stk:Int,
    var sdtGt:String,
    var idPhone:String,
    var userBank: String

){
    constructor():this("","","","",true, "",0,"","","")
}