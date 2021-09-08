package com.appveg.farmfamily.ui.vegetable

class Vegetable {

    var vegID: Int? = 0
    var vegName: String? = ""
    var vegImgBlob: String? = null
    var gardenId: Int = -1
    var paramId: Int = -1
    var createdBy: String? = "admin"
    var createdDate: String? = null
    var updatedBy: String? = ""
    var updatedDate: String? = null
    var deletedBy: String? = ""
    var deletedDate: String? = ""
    var deletedFlag: Int = 1
    constructor(
        vegID: Int?,
        vegName: String?,
        vegImgBlob: String?,
        createdBy: String?,
        createdDate: String?
    ) {
        this.vegID = vegID
        this.vegName = vegName
        this.vegImgBlob = vegImgBlob
        this.createdBy = createdBy
        this.createdDate = createdDate
    }
    constructor(
        vegID: Int?,
        vegName: String?,
        vegImgBlob: String?,
        gardenId: Int,
        paramId: Int,
        createdBy: String?,
        createdDate: String?,
        updatedBy: String?,
        updatedDate: String?,
        deletedBy: String?,
        deletedDate: String?,
        deletedFlag: Int
    ) {
        this.vegID = vegID
        this.vegName = vegName
        this.vegImgBlob = vegImgBlob
        this.gardenId = gardenId
        this.paramId = paramId
        this.createdBy = createdBy
        this.createdDate = createdDate
        this.updatedBy = updatedBy
        this.updatedDate = updatedDate
        this.deletedBy = deletedBy
        this.deletedDate = deletedDate
        this.deletedFlag = deletedFlag
    }

    constructor(
        vegID: Int?,
        vegName: String?,
        createdBy: String?,
        createdDate: String?
    ) {
        this.vegID = vegID
        this.vegName = vegName
        this.createdBy = createdBy
        this.createdDate = createdDate
    }

    constructor()

    //get ALl Room
    constructor(vegID: Int?, vegName: String?, vegImgBlob: String) {
        this.vegID = vegID
        this.vegName = vegName
        this.vegImgBlob = vegImgBlob
    }

    constructor(vegID: Int?, vegName: String?, vegImgBlob: String,gardenId: Int) {
        this.vegID = vegID
        this.vegName = vegName
        this.vegImgBlob = vegImgBlob
        this.gardenId = gardenId
    }
    constructor(vegID: Int?, vegName: String?, vegImgBlob: String,gardenId: Int,paramId: Int) {
        this.vegID = vegID
        this.vegName = vegName
        this.vegImgBlob = vegImgBlob
        this.gardenId = gardenId
        this.paramId = paramId
    }


}