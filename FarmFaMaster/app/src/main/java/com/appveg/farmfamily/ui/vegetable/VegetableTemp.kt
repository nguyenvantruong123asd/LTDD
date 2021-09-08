package com.appveg.farmfamily.ui.vegetable

class VegetableTemp {
    var vegId: Int? = -1
    var vegName: String? = ""
    var vegQty: Double? = null

    constructor(vegName: String?, vegQty: Double?) {
        this.vegName = vegName
        this.vegQty = vegQty
    }

    constructor()

    constructor(vegId: Int?, vegName: String?, vegQty: Double?) {
        this.vegId = vegId
        this.vegName = vegName
        this.vegQty = vegQty
    }

}