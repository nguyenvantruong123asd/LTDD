package com.appveg.farmfamily.ui.param

class Param {

    var paramId : Int? = -1

    var tempToNight : String? =null
    var tempFromNight : String? = null

    var tempToDay: String? = null
    var tempFromDay : String? = null

    var phTo : String? = null
    var phFrom : String? = null

    var ppmTo :String? = null
    var ppmFrom :String? = null

    var tdsLevelTo :String? = null
    var tdsLevelFrom :String? = null

    var createdBy: String? = "admin"
    var createdDate: String? = null
    var updatedBy: String? = ""
    var updatedDate: String? = null
    var deletedBy: String? = ""
    var deletedDate: String? = ""
    var deletedFlag: Int = 1


    constructor(
        paramId : Int?,
        tempToNight : String?,
        tempFromNight : String?,
        tempToDay: String?,
        tempFromDay : String?,
        phTo : String?,
        phFrom : String?,
        ppmTo :String?,
        ppmFrom :String?,
        tdsLevelTo: String?,
        tdsLevelFrom: String?,
        createdBy: String?,
        createdDate: String?,
        updatedBy: String?,
        updatedDate: String?,
        deletedBy: String?,
        deletedDate: String?,
        deletedFlag: Int

    ){
        this.paramId = paramId
        this.tempToNight = tempToNight
        this.tempFromNight = tempFromNight
        this.tempToDay = tempToDay
        this.tempFromDay = tempFromDay
        this.phTo = phTo
        this.phFrom = phFrom
        this.ppmTo = ppmTo
        this.ppmFrom = ppmFrom
        this.tdsLevelTo = tdsLevelTo
        this.tdsLevelFrom = tdsLevelFrom
        this.createdBy = createdBy
        this.createdDate = createdDate
        this.updatedBy = updatedBy
        this.updatedDate = updatedDate
        this.deletedBy = deletedBy
        this.deletedDate = deletedDate
        this.deletedFlag = deletedFlag
    }



    //update
    constructor(
        paramId : Int?,
        tempToNight : String?,
        tempFromNight : String?,
        tempToDay: String?,
        tempFromDay : String?,
        phTo : String?,
        phFrom : String?,
        ppmTo :String?,
        ppmFrom :String?,
        tdsLevelTo: String?,
        tdsLevelFrom: String?,
        updatedDate: String?

    ){
        this.paramId = paramId
        this.tempToNight = tempToNight
        this.tempFromNight = tempFromNight
        this.tempToDay = tempToDay
        this.tempFromDay = tempFromDay
        this.phTo = phTo
        this.phFrom = phFrom
        this.ppmTo = ppmTo
        this.ppmFrom = ppmFrom
        this.tdsLevelTo = tdsLevelTo
        this.tdsLevelFrom = tdsLevelFrom
        this.updatedDate = updatedDate
    }

    //add
    constructor(
        paramId: Int?,
        tempToNight : String?,
        tempFromNight : String?,
        tempToDay: String?,
        tempFromDay : String?,
        phTo : String?,
        phFrom : String?,
        ppmTo :String?,
        ppmFrom :String?,
        tdsLevelTo: String?,
        tdsLevelFrom: String?,
        createdBy: String?,
        createdDate: String?

    ){
        this.paramId = paramId
        this.tempToNight = tempToNight
        this.tempFromNight = tempFromNight
        this.tempToDay = tempToDay
        this.tempFromDay = tempFromDay
        this.phTo = phTo
        this.phFrom = phFrom
        this.ppmTo = ppmTo
        this.ppmFrom = ppmFrom
        this.tdsLevelTo = tdsLevelTo
        this.tdsLevelFrom = tdsLevelFrom
        this.createdBy = createdBy
        this.createdDate = createdDate
    }

    //find by id
    constructor(
        paramId: Int?,
        tempToNight : String?,
        tempFromNight : String?,
        tempToDay: String?,
        tempFromDay : String?,
        phTo : String?,
        phFrom : String?,
        ppmTo :String?,
        ppmFrom :String?,
        tdsLevelTo: String?,
        tdsLevelFrom: String?
    ){
        this.paramId = paramId
        this.tempToNight = tempToNight
        this.tempFromNight = tempFromNight
        this.tempToDay = tempToDay
        this.tempFromDay = tempFromDay
        this.phTo = phTo
        this.phFrom = phFrom
        this.ppmTo = ppmTo
        this.ppmFrom = ppmFrom
        this.tdsLevelTo = tdsLevelTo
        this.tdsLevelFrom = tdsLevelFrom
    }

    constructor()


}