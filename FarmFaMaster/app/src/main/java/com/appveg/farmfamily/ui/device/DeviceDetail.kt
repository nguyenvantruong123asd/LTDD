package com.appveg.farmfamily.ui.device

class DeviceDetail {
    var deviceDetailID : Int? = 0
    var deviceDetailCode : String? =""
    var deviceDetailCodeSS : String? =""
    var deviceDetailImg : String? = ""
    var deviceID : Int? = 0
    var deviceDetailStatus :String?=""
    var gardenDetailId: Int? = -1
    var createdBy: String? = "admin"
    var createdDate: String? = null
    var updatedBy: String? = ""
    var updatedDate: String? = null
    var deletedBy: String? = ""
    var deletedDate: String? =""
    var deletedFlag:Int = 1



    constructor()
    constructor(
        deviceDetailID: Int?,
        deviceDetailCode: String?,
        deviceDetailCodeSS: String?,
        deviceDetailImg: String?,
        deviceID: Int?,
        deviceDetailStatus: String?,
        gardenDetailId : Int,
        createdBy: String?,
        createdDate: String?,
        updatedBy: String?,
        updatedDate: String?,
        deletedBy: String?,
        deletedDate: String?,
        deletedFlag: Int
    ) {
        this.deviceDetailID = deviceDetailID
        this.deviceDetailCode = deviceDetailCode
        this.deviceDetailCodeSS = deviceDetailCodeSS
        this.deviceDetailImg = deviceDetailImg
        this.deviceID = deviceID
        this.deviceDetailStatus = deviceDetailStatus
        this.gardenDetailId = gardenDetailId
        this.createdBy = createdBy
        this.createdDate = createdDate
        this.updatedBy = updatedBy
        this.updatedDate = updatedDate
        this.deletedBy = deletedBy
        this.deletedDate = deletedDate
        this.deletedFlag = deletedFlag
    }
    constructor(
        deviceDetailID: Int?,
        deviceDetailCode: String?,
        deviceDetailCodeSS: String?,
        deviceDetailImg: String?,
        deviceDetailStatus: String?,
        deviceID: Int?,
        gardenDetailId: Int?
    ) {
        this.deviceDetailID = deviceDetailID
        this.deviceDetailCode = deviceDetailCode
        this.deviceDetailCodeSS = deviceDetailCodeSS
        this.deviceDetailImg = deviceDetailImg
        this.deviceDetailStatus = deviceDetailStatus
        this.deviceID = deviceID
        this.gardenDetailId = gardenDetailId
    }

    constructor(
        deviceDetailID: Int?,
        deviceDetailCode: String?,
        deviceDetailImg: String?,
        deviceDetailStatus: String?,
        deviceID: Int?,
        gardenDetailId: Int?
    ) {
        this.deviceDetailID = deviceDetailID
        this.deviceDetailCode = deviceDetailCode
        this.deviceDetailImg = deviceDetailImg
        this.deviceDetailStatus = deviceDetailStatus
        this.deviceID = deviceID
        this.gardenDetailId = gardenDetailId
    }

    constructor(
        deviceDetailID: Int?,
        deviceDetailCode: String?,
        deviceDetailImg: String?,
        deviceDetailStatus: String?
    ) {
        this.deviceDetailID = deviceDetailID
        this.deviceDetailCode = deviceDetailCode
        this.deviceDetailImg = deviceDetailImg
        this.deviceDetailStatus = deviceDetailStatus
    }

    constructor(
        deviceDetailID: Int?,
        deviceDetailCode: String?,
        deviceDetailImg: String?,
        deviceID: Int?,
        deviceDetailStatus: String?,
        createdBy: String?,
        createdDate: String?
    ) {
        this.deviceDetailID = deviceDetailID
        this.deviceDetailCode = deviceDetailCode
        this.deviceDetailImg = deviceDetailImg
        this.deviceID = deviceID
        this.deviceDetailStatus = deviceDetailStatus
        this.createdBy = createdBy
        this.createdDate = createdDate
    }

    constructor(
        deviceDetailID: Int?,
        deviceDetailImg: String?,
        updatedDate: String?
    ) {
        this.deviceDetailID = deviceDetailID
        this.deviceDetailImg = deviceDetailImg
        this.updatedDate = updatedDate
    }

    constructor(
        deviceDetailID: Int?,
        deviceDetailStatus: String?
    ) {
        this.deviceDetailID = deviceDetailID
        this.deviceDetailStatus = deviceDetailStatus
    }


}