package com.appveg.farmfamily.ui.mode

class Mode {
    var modeId: Int? = null
    var code: String? = ""
    var timeOn: String? = ""
    var timeOff: String? = ""
    var on: String? = ""
    var off: String? = ""
    var timeRepeat: String? = ""
    var repeat: String? = ""
    var activeFlag : String? = ""

    var createdBy: String? = "admin"
    var createdDate: String? = null
    var updatedBy: String? = ""
    var updatedDate: String? = null
    var deletedBy: String? = ""
    var deletedDate: String? = null
    var deletedFlag: Int = 1

    constructor(
        modeId: Int?,
        code: String?,
        timeOn: String?,
        timeOff: String?,
        on: String?,
        off: String?,
        timeRepeat: String?,
        repeat: String?,
        activeFlag: String?
    ) {
        this.modeId = modeId
        this.code = code
        this.timeOn = timeOn
        this.timeOff = timeOff
        this.on = on
        this.off = off
        this.timeRepeat = timeRepeat
        this.repeat = repeat
        this.activeFlag = activeFlag
    }

    constructor(
        modeId: Int?,
        timeOn: String?,
        timeOff: String?,
        on: String?,
        off: String?,
        timeRepeat: String?,
        repeat: String?,
        updatedDate: String?
    ) {
        this.modeId = modeId
        this.timeOn = timeOn
        this.timeOff = timeOff
        this.on = on
        this.off = off
        this.timeRepeat = timeRepeat
        this.repeat = repeat
        this.updatedDate = updatedDate
    }

    constructor(
        modeId: Int?,
        code: String?,
        timeOn: String?,
        timeOff: String?,
        on: String?,
        off: String?,
        timeRepeat: String?,
        repeat: String?,
        activeFlag: String?,
        createdBy: String?,
        createdDate: String?,
        updatedBy: String?,
        updatedDate: String?,
        deletedBy: String?,
        deletedDate: String?,
        deletedFlag: Int
    ) {
        this.modeId = modeId
        this.code = code
        this.timeOn = timeOn
        this.timeOff = timeOff
        this.on = on
        this.off = off
        this.timeRepeat = timeRepeat
        this.repeat = repeat
        this.activeFlag = activeFlag
        this.createdBy = createdBy
        this.createdDate = createdDate
        this.updatedBy = updatedBy
        this.updatedDate = updatedDate
        this.deletedBy = deletedBy
        this.deletedDate = deletedDate
        this.deletedFlag = deletedFlag
    }

    constructor(
        modeId: Int?,
        code: String?,
        timeOn: String?,
        timeOff: String?,
        on: String?,
        off: String?,
        timeRepeat: String?,
        repeat: String?,
        activeFlag: String?,
        createdDate: String?
    ) {
        this.modeId = modeId
        this.code = code
        this.timeOn = timeOn
        this.timeOff = timeOff
        this.on = on
        this.off = off
        this.timeRepeat = timeRepeat
        this.repeat = repeat
        this.activeFlag = activeFlag
        this.createdDate = createdDate

    }

    constructor()

}