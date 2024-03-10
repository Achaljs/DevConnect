package com.example.kot.modles

class posts {

    var postImage:String=""
    var caption:String=""
var time:String=""
    var uid:String=""
    var postID:String=""
    var Likedby:ArrayList<String> = ArrayList()

    constructor(postImage: String, caption: String) {

        this.postImage = postImage
        this.caption = caption

    }



    constructor()




    constructor(postImage: String, caption: String, time: String, userName: String) {
        this.postImage = postImage
        this.caption = caption
        this.time = time
        this.uid = userName
    }

    constructor(
        postImage: String,
        caption: String,
        time: String,
        uid: String,
        Likedby: ArrayList<String>,
        postID:String
    ) {
        this.postImage = postImage
        this.caption = caption
        this.time = time
        this.uid = uid
        this.Likedby = Likedby
        this.postID=postID

    }
}