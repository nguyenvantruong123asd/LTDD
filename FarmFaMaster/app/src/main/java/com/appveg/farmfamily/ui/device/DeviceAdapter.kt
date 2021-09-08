package com.appveg.farmfamily.ui.device

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.appveg.farmfamily.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import java.io.File

class DeviceAdapter (private var activity: Activity, private var items: ArrayList<Device>) :  BaseAdapter(){

    private class ViewHolder(row: View?) {
        var imgDevice: ImageView
        var deviceName: TextView
        var deviceNum: TextView

        init {
            this.imgDevice = row?.findViewById(R.id.view_device_image) as ImageView
            this.deviceName = row?.findViewById(R.id.view_device_name) as TextView
            this.deviceNum = row?.findViewById(R.id.view_device_num) as TextView
        }
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val viewHolder: ViewHolder
        if (convertView == null) {
            val inflater = activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.layout_device, null)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        var device = items[position]

        viewHolder.deviceName.text = device.deviceName
        viewHolder.deviceNum.text = device.deviceNum

        // chuyển bytearray về bitmap để hiển thị
//        var imageBitmap : String? = device.deviceImg
////        var uri = Uri.parse(imageBitmap)
//        var imageBitmap : ByteArray? = device.deviceImg
//        var bitmap: Bitmap = BitmapFactory.decodeByteArray(imageBitmap,0, imageBitmap!!.size)
 //       viewHolder.imgDevice.setImageBitmap()

        Glide.with(activity)
            .load(Uri.fromFile(File(device.deviceImg)))
            .into(viewHolder.imgDevice)
        //viewHolder.imgDevice.setImageBitmap()

        return view
    }
    override fun getItem(i: Int): Device {
        return items[i]
    }
    override fun getItemId(i: Int): Long {
        return i.toLong()
    }
    override fun getCount(): Int {
        return items.size
    }

}