package com.appveg.farmfamily.ui.mode

import android.app.Activity
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.appveg.farmfamily.R
import com.appveg.farmfamily.ui.database.Database
import com.appveg.farmfamily.ui.device.DeviceDetail
import com.bumptech.glide.Glide
import java.io.File

class DeviceSettingForGardenAdapter(
    private val activity: Activity,
    private var items: ArrayList<DeviceDetail>
) : BaseAdapter() {
    private lateinit var database: Database
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
        var imgDevice: ImageView
        var deviceName: TextView
        var code: TextView? = null

        init {
            this.imgDevice = row?.findViewById(R.id.view_mode_device_image) as ImageView
            this.deviceName = row?.findViewById(R.id.view_mode_device_name) as TextView
            this.code = row?.findViewById(R.id.view_mode_device_code) as TextView
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var view: View?
        var viewHolder: ViewHolder
        if (convertView == null) {

            var layout = LayoutInflater.from(activity)
            view = layout.inflate(R.layout.layout_mode_device_detail, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder

        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        var deviceDetail = items[position]
        viewHolder.deviceName!!.text = getDeviceName(deviceDetail.deviceID!!)
        viewHolder.code!!.text = deviceDetail.deviceDetailCode

        // load photo
        Glide.with(activity)
            .load(Uri.fromFile(File(deviceDetail.deviceDetailImg)))
            .into(viewHolder.imgDevice)
        return view as View

    }

    private fun getDeviceName(deviceId: Int): String {
        database = Database(activity)
        var device = database.findDeviceById(deviceId)
        return device.deviceName!!
    }
}
