package com.example.kot.modles

class storys {

    var storyBy=""
  var stories=ArrayList<userstories>()


    constructor(storyBy: String, stories:ArrayList<userstories>) {
        this.storyBy = storyBy
        this.stories = stories
    }
    constructor()
}