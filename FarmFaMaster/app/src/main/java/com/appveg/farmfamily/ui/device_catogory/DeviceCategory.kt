package com.appveg.farmfamily.ui.device_catogory

class DeviceCategory {
    var dcategoryID: Int? = 0
    var dcategoryName: String? = ""
    var dcategoryImg: String? = null
    var createdBy: String? = "admin"
    var createdDate: String? = null
    var updatedBy: String? = ""
    var updatedDate: String? = null
    var deletedBy: String? = ""
    var deletedDate: String? = ""
    var deletedFlag: Int = 1
    constructor(
        dcategoryID: Int?,
        dcategoryName: String?,
        dcategoryImg: String,
        createdBy: String?,
        createdDate: String?
    ) {
        this.dcategoryID = dcategoryID
        this.dcategoryName = dcategoryName
        this.dcategoryImg = dcategoryImg
        this.createdBy = createdBy
        this.createdDate = createdDate
    }

    constructor(
        dcategoryID: Int?,
        dcategoryName: String?,
        dcategoryImg: String,
        updatedDate: String?
    ) {
        this.dcategoryID = dcategoryID
        this.dcategoryName = dcategoryName
        this.dcategoryImg = dcategoryImg
        this.updatedDate = updatedDate
    }

    constructor(
        dcategoryID: Int?,
        dcategoryName: String?,
        dcategoryImg: String

    ) {
        this.dcategoryID = dcategoryID
        this.dcategoryName = dcategoryName
        this.dcategoryImg = dcategoryImg

    }

    constructor()
}