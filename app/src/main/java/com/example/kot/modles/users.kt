package com.example.kot.modles

class users {
     var image: String? =null
    var names: String? =null
    var email: String? =null
    var pass: String? =null

    constructor(names: String?, email: String?, pass: String?) {
        this.names = names
        this.email = email
        this.pass = pass
    }

    constructor(image: String?, names: String?, email: String?, pass: String?) {
        this.image = image
        this.names = names
        this.email = email
        this.pass = pass
    }
    constructor()


}