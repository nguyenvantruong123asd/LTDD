package com.appveg.farmfamily.ui.home

class DetailGardenFirebase {
    var timestamp : Long? = null
    var value : String? = null

    constructor()

    constructor(timestamp: Long?, value: String?) {
        this.timestamp = timestamp
        this.value = value
    }

    constructor(value: String?) {
        this.value = value
    }


}