package com.appveg.farmfamily.ui.garden

class Garden {
    var gardenId: Int? = null
    var gardenCode: String? = null
    var gardenImage:  String? = null
    var gardenName: String? = ""
    var createdBy: String? = "admin"
    var createdDate: String? = null
    var updatedBy: String? = ""
    var updatedDate: String? = null
    var deletedBy: String? = ""
    var deletedDate: String? = null
    var deletedFlag: Int = 1
    constructor()

    constructor(
        gardenId: Int?,
        gardenCode: String?,
        gardenName: String?,
        gardenImage:  String?,
        createdBy: String?,
        createdDate: String?
    ) {
        this.gardenId = gardenId
        this.gardenCode = gardenCode
        this.gardenName = gardenName
        this.gardenImage = gardenImage
        this.createdBy = createdBy
        this.createdDate = createdDate
    }
    constructor(
        gardenId: Int?,
        gardenName: String?,
        gardenImage:  String?,
        updatedDate: String? = null
    ) {
        this.gardenId = gardenId
        this.gardenName = gardenName
        this.gardenImage = gardenImage
        this.updatedDate = updatedDate
    }

    constructor(
        gardenId: Int?,
        gardenImage:  String?,
        gardenName: String?,
        createdBy: String?,
        createdDate: String?,
        updatedBy: String?,
        updatedDate: String?,
        deletedBy: String?,
        deletedDate: String?,
        deletedFlag: Int
    ) {
        this.gardenId = gardenId
        this.gardenImage = gardenImage
        this.gardenName = gardenName
        this.createdBy = createdBy
        this.createdDate = createdDate
        this.updatedBy = updatedBy
        this.updatedDate = updatedDate
        this.deletedBy = deletedBy
        this.deletedDate = deletedDate
        this.deletedFlag = deletedFlag
    }


//    constructor(
//        gardenId: Int?,
//        gardenCode: String,
//        gardenName: String?,
//        gardenImage:  String?
//    ) {
//        this.gardenId = gardenId
//        this.gardenCode = gardenCode
//        this.gardenName = gardenName
//        this.gardenImage = gardenImage
//    }
}