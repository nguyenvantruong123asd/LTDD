package com.appveg.farmfamily.ui.send

import java.io.Serializable

class Batch : Serializable {
    var batchId: Int? = null
    var batchImage: String? = ""
    var batchName: String? = ""
    var theEndDate: String? = ""
    var totalQuantity: String? = ""
    var gardenId: Int? = null
    var createdBy: String? = "admin"
    var createdDate: String? = null
    var updatedBy: String? = ""
    var updatedDate: String? = null
    var deletedBy: String? = ""
    var deletedDate: String? = null
    var deletedFlag: Int = 1

    constructor(
        batchId: Int?,
        batchImage: String?,
        batchName: String?,
        theEndDate: String?,
        totalQuantity: String?,
        gardenId: Int?,
        createdBy: String?,
        createdDate: String?
    ) {
        this.batchId = batchId
        this.batchImage = batchImage
        this.batchName = batchName
        this.theEndDate = theEndDate
        this.totalQuantity = totalQuantity
        this.gardenId = gardenId
        this.createdBy = createdBy
        this.createdDate = createdDate
    }

    constructor(
        batchId: Int?,
        batchName: String?,
        theEndDate: String?,
        totalQuantity: String?,
        createdDate: String?,
        updatedBy: String?,
        updatedDate: String?
    ) {
        this.batchId = batchId
        this.batchName = batchName
        this.theEndDate = theEndDate
        this.totalQuantity = totalQuantity
        this.createdDate = createdDate
        this.updatedBy = updatedBy
        this.updatedDate = updatedDate
    }
    constructor(
        batchId: Int?,
        batchName: String?,
        theEndDate: String?,
        totalQuantity: String?,
        gardenId: Int?,
        createdDate: String?,
        updatedBy: String?,
        updatedDate: String?
    ) {
        this.batchId = batchId
        this.batchName = batchName
        this.theEndDate = theEndDate
        this.totalQuantity = totalQuantity
        this.gardenId = gardenId
        this.createdDate = createdDate
        this.updatedBy = updatedBy
        this.updatedDate = updatedDate
    }

    constructor(
        batchId: Int?,
        batchImage: String?,
        batchName: String?,
        theEndDate: String?,
        totalQuantity: String?,
        gardenId: Int?,
        createdBy: String?,
        createdDate: String?,
        updatedBy: String?,
        updatedDate: String?,
        deletedBy: String?,
        deletedDate: String?,
        deletedFlag: Int
    ) {
        this.batchId = batchId
        this.batchImage = batchImage
        this.batchName = batchName
        this.theEndDate = theEndDate
        this.totalQuantity = totalQuantity
        this.gardenId = gardenId
        this.createdBy = createdBy
        this.createdDate = createdDate
        this.updatedBy = updatedBy
        this.updatedDate = updatedDate
        this.deletedBy = deletedBy
        this.deletedDate = deletedDate
        this.deletedFlag = deletedFlag
    }
    constructor()

}