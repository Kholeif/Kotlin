package com.example.mynotesusingfirebase

class note {
    var id: String? = null
    var title: String? = null
    var des: String? = null
    var timestamp: String? = null

    constructor(){}

    constructor(id: String, title: String, des: String, timestamp: String) {
        this.id = id
        this.title = title
        this.des = des
        this.timestamp = timestamp
    }

}