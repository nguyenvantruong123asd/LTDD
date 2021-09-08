package com.appveg.farmfamily.ui.garden

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.appveg.farmfamily.R
import com.appveg.farmfamily.ui.database.Database
import com.appveg.farmfamily.ui.device.DeviceDetail
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.io.File
import java.text.Normalizer
import java.util.regex.Pattern


class DeviceForGardenAdapter(
    private var activity: Activity,
    private var items: ArrayList<DeviceDetail>

) : BaseAdapter() {
    private lateinit var database: Database

    private class ViewHolder(row: View?) {
        var imgDeviceForGarden: ImageView? = null
        var deviceForGardenChecked: CheckBox

        init {
            this.imgDeviceForGarden = row?.findViewById(R.id.img_device_for_garden_1)
            this.deviceForGardenChecked =
                row?.findViewById(R.id.device_for_garden_checked_1) as CheckBox
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val viewHolder: ViewHolder
        if (convertView == null) {
            val inflater =
                activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.layout_device_for_garden_1, null)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        var deviceDetailForGarden = getItem(position)

        // chuyển bytearray về bitmap để hiển thị
//        var imageBitmap: ByteArray? = deviceDetailForGarden.deviceDetailImg
//        var bitmap: Bitmap = BitmapFactory.decodeByteArray(imageBitmap, 0, imageBitmap!!.size)
//        viewHolder.imgDeviceForGarden!!.setImageBitmap(bitmap)

        Glide.with(activity)
            .load(Uri.fromFile(File(deviceDetailForGarden.deviceDetailImg)))
            .into(viewHolder.imgDeviceForGarden!!)

        viewHolder.deviceForGardenChecked.isChecked =
            checked(deviceDetailForGarden.deviceDetailStatus!!)


        return view
    }

    override fun getItem(i: Int): DeviceDetail {
        return items[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getCount(): Int {
        return items.size
    }

    // checked load default (case broken)
    private fun checked(status: String): Boolean {
        var result = false
        if ("Y" == status.trim()) {
            result = true
        }
        return result
    }

}