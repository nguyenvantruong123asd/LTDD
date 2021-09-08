package com.appveg.farmfamily.ui.send

class BatchQtyDetail {
    var qtyDetailId: Int? = null
    var qtyId: Int? = null
    var vegetableName: String? = ""
    var vegetableQuantity: String? = ""
    var createdBy: String? = ""
    var createdDate: String? = null
    var updatedBy: String? = ""
    var updatedDate: String? = null
    var deletedBy: String? = ""
    var deletedDate: String? = null
    var deletedFlag: Int = 1

    constructor(
        qtyDetailId: Int?,
        qtyId: Int?,
        vegetableName: String?,
        vegetableQuantity: String?,
        createdBy: String?,
        createdDate: String?,
        updatedBy: String?,
        updatedDate: String?
    ) {
        this.qtyDetailId = qtyDetailId
        this.qtyId = qtyId
        this.vegetableName = vegetableName
        this.vegetableQuantity = vegetableQuantity
        this.createdBy = createdBy
        this.createdDate = createdDate
        this.updatedBy = updatedBy
        this.updatedDate = updatedDate
    }

    constructor(
        qtyDetailId: Int?,
        qtyId: Int?,
        vegetableName: String?,
        vegetableQuantity: String?,
        createdBy: String?,
        createdDate: String?,
        updatedBy: String?,
        updatedDate: String?,
        deletedBy: String?,
        deletedDate: String?,
        deletedFlag: Int
    ) {
        this.qtyDetailId = qtyDetailId
        this.qtyId = qtyId
        this.vegetableName = vegetableName
        this.vegetableQuantity = vegetableQuantity
        this.createdBy = createdBy
        this.createdDate = createdDate
        this.updatedBy = updatedBy
        this.updatedDate = updatedDate
        this.deletedBy = deletedBy
        this.deletedDate = deletedDate
        this.deletedFlag = deletedFlag
    }

    constructor(
        qtyDetailId: Int?,
        qtyId: Int?,
        vegetableName: String?,
        vegetableQuantity: String?,
        createdBy: String?,
        createdDate: String?
    ) {
        this.qtyDetailId = qtyDetailId
        this.qtyId = qtyId
        this.vegetableName = vegetableName
        this.vegetableQuantity = vegetableQuantity
        this.createdBy = createdBy
        this.createdDate = createdDate
    }

    constructor(
        qtyDetailId: Int?,
        vegetableName: String?,
        vegetableQuantity: String?,
        updatedBy: String?
    ) {
        this.qtyDetailId = qtyDetailId
        this.qtyId = qtyId
        this.vegetableName = vegetableName
        this.vegetableQuantity = vegetableQuantity
        this.updatedBy = updatedBy
    }
    constructor()

    constructor(
        qtyDetailId: Int?,
        qtyId: Int?,
        vegetableName: String?,
        vegetableQuantity: String?,
        createdBy: String?,
        updatedBy: String?,
        updatedDate: String?
    ) {
        this.qtyDetailId = qtyDetailId
        this.qtyId = qtyId
        this.vegetableName = vegetableName
        this.vegetableQuantity = vegetableQuantity
        this.createdBy = createdBy
        this.updatedBy = updatedBy
        this.updatedDate = updatedDate
    }
}