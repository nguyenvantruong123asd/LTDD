package com.appveg.farmfamily.ui.login

class User {
    var id: Int? = null
    var fullName: String? = ""
    var email: String? = ""
    var password: String? = ""
    var gender: String? = ""
    var status: Int = 0
    var roleId: Int = 1
    var createdBy: String? = ""
    var createdDate: String? = null
    var updatedBy: String? = ""
    var updatedDate: String? = null
    var deletedBy: String? = ""
    var deletedDate: String? = null
    var deletedFlag: Int = 1

    constructor(
        id: Int,
        fullName: String?,
        email: String?,
        password: String?,
        gender: String?,
        status: Int,
        roleId: Int,
        createdBy: String?,
        createdDate: String?,
        updatedBy: String?,
        updatedDate: String?,
        deletedBy: String?,
        deletedDate: String?,
        deletedFlag: Int
    ) {
        this.id = id
        this.fullName = fullName
        this.email = email
        this.password = password
        this.gender = gender
        this.status = status
        this.roleId = roleId
        this.createdBy = createdBy
        this.createdDate = createdDate
        this.updatedBy = updatedBy
        this.updatedDate = updatedDate
        this.deletedBy = deletedBy
        this.deletedDate = deletedDate
        this.deletedFlag = deletedFlag
    }

    constructor(
        id: Int?,
        fullName: String?,
        email: String?,
        password: String?,
        gender: String?,
        status: Int,
        roleId: Int,
        createdBy: String?,
        createdDate: String?
    ) {
        this.id = id
        this.fullName = fullName
        this.email = email
        this.password = password
        this.gender = gender
        this.status = status
        this.roleId = roleId
        this.createdBy = createdBy
        this.createdDate = createdDate
    }

    constructor(
        id: Int?,
        fullName: String?,
        email: String?,
        password: String?,
        status: Int,
        roleId: Int
    ) {
        this.id = id
        this.fullName = fullName
        this.email = email
        this.password = password
        this.status = status
        this.roleId = roleId
    }

    constructor(
        id: Int?,
        password: String?,
        updatedDate: String?
    ) {
        this.id = id
        this.password = password
        this.updatedDate = updatedDate
    }
    constructor()
}