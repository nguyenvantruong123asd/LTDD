package com.appveg.farmfamily.ui.share

class Substance {
    var substanceMassId: Int? = null
    var substanceMassName: String = ""
    var substanceMassImage: String = ""
    var totalSubstanceMass: String = ""
    var substanceCategory: String = ""
    var gardenId: Int = 0

    constructor()

    constructor(
        substanceMassId: Int?,
        substanceMassName: String,
        substanceMassImage: String,
        totalSubstanceMass: String,
        substanceCategory: String,
        gardenId: Int
    ) {
        this.substanceMassId = substanceMassId
        this.substanceMassName = substanceMassName
        this.substanceMassImage = substanceMassImage
        this.totalSubstanceMass = totalSubstanceMass
        this.substanceCategory = substanceCategory
        this.gardenId = gardenId
    }


    constructor(
        substanceMassId: Int?,
        totalSubstanceMass: String
    ) {
        this.substanceMassId = substanceMassId
        this.totalSubstanceMass = totalSubstanceMass

    }


}