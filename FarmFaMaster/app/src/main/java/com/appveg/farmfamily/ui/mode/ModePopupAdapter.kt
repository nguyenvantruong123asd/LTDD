package com.appveg.farmfamily.ui.mode

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import com.appveg.farmfamily.R
import com.appveg.farmfamily.ui.database.Database
import com.appveg.farmfamily.ui.device.DeviceDetail
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ModePopupAdapter(
    private val activity: Activity,
    private var items: ArrayList<Mode>,
    private var deviceID: Int,
    private var gardenCode: String,
    private var gardenId: Int
) : BaseAdapter() {

    private lateinit var database: Database
    private lateinit var database1: DatabaseReference
    //1
    override fun getCount(): Int {
        return items.size
    }

    //2
    override fun getItem(position: Int): Any {
        return items[position]
    }

    //3
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    //4
    private class ViewHolder(row: View?) {
        var modeCode: TextView? = null
        var modeTimeRepeat: TextView? = null
        var modeTimeInfo: TextView? = null
        var modeChecked: CheckBox

        init {
            this.modeCode = row?.findViewById(R.id.view_mode_code_popup) as TextView
            this.modeTimeRepeat = row?.findViewById(R.id.view_mode_time_repeat_popup) as TextView
            this.modeTimeInfo = row?.findViewById(R.id.view_mode_time_info_popup) as TextView
            this.modeChecked = row?.findViewById(R.id.mode_checked_popup) as CheckBox
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var view: View?
        var viewHolder: ViewHolder
        if (convertView == null) {

            var layout = LayoutInflater.from(activity)
            view = layout.inflate(R.layout.layout_modes_popup, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder

        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        var mode = items[position]
        viewHolder.modeCode!!.text = mode.code
        viewHolder.modeTimeRepeat!!.text = mode.timeRepeat
        viewHolder.modeTimeInfo!!.text =
            activity!!.resources.getString(R.string.time_slot_vi)+ ": " + mode.timeOn + " - " + mode.timeOff + ", " + activity!!.resources.getString(R.string.ON_vi) + ": " + mode.on + "p, " + activity!!.resources.getString(
                R.string.OFF_vi
            ) + ": " + mode.off + "p"
        viewHolder.modeChecked.setOnClickListener {
            var deviceStatus = viewHolder.modeChecked.isChecked
            updateModeDevice(deviceStatus, mode,deviceID,gardenId)
            viewHolder.modeChecked.isChecked = deviceStatus
        }
        viewHolder.modeChecked.isChecked = checked(mode.modeId!!,deviceID)


        return view as View

    }


    private fun updateModeDevice(checked: Boolean,mode: Mode,deviceID: Int,gardenId: Int){
        database = Database(activity)
        database1 = FirebaseDatabase.getInstance().reference
        var deviceDetail = database.findAllDeviceDetailForMode(deviceID,gardenId)
        var modeDevice : ModeDevice = ModeDevice()
        if(checked){
            modeDevice.modeId = mode.modeId
            modeDevice.deviceId = deviceID
            database.addModeDevice(modeDevice)
            // add mode to firebase
            database1.child(gardenCode).child(deviceDetail.deviceDetailCodeSS!!).child("MODE").child(mode.code!!).push().setValue(mode)
            Toast.makeText(activity, activity.getString(R.string.setting_mode_for_device_vi), Toast.LENGTH_SHORT).show()
        }else{
            database1.child(gardenCode).child(deviceDetail.deviceDetailCodeSS!!).child("MODE").child(mode.code!!).removeValue()
            database.deleteModeDevice(mode.modeId!!,deviceID)
            Toast.makeText(activity, activity.getString(R.string.un_setting_mode_for_device_vi), Toast.LENGTH_SHORT).show()
        }
    }

    private fun checked(modeId: Int,deviceID: Int) : Boolean{
        database = Database(activity)
        var modeDevice = database.findModeDeviceByModeIdAndDeviceId(modeId,deviceID)
        if(modeDevice.modeDeviceId != null){
            return true
        }
        return false
    }

}