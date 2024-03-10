package com.example.kot.modles

import android.view.View

class projects {

    var title:String=""
    var discription:String=""
    var startDate:String=""
    var endDate:String=""
    var link:String=""
    var technologiesUsed:ArrayList<String> = ArrayList()
var Uid:String=""


    constructor()
    constructor(
        Uid:String,
        title: String,
        discription: String,
        startDate: String,
        endDate: String,
        link: String,
        technologiesUsed: ArrayList<String>
    ) {
        this.title = title
        this.discription = discription
        this.startDate = startDate
        this.endDate = endDate
        this.link = link
        this.technologiesUsed = technologiesUsed
        this.Uid= Uid
    }


}