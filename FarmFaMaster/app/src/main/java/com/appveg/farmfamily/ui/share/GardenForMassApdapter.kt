package com.appveg.farmfamily.ui.share

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.appveg.farmfamily.R
import com.appveg.farmfamily.ui.database.Database
import com.appveg.farmfamily.ui.garden.Garden
import com.bumptech.glide.Glide
import java.io.File

class GardenForMassApdapter(var activity: Activity, var items: ArrayList<Garden>) :  BaseAdapter() {
    private lateinit var database: Database

    private class ViewHolder(row: View?) {
        var kvName: TextView? = null
        var kvCamera: ImageView? = null
        var deviceSetting: TextView? = null

        init {
            this.kvName = row?.findViewById(R.id.name_kv)
            this.kvCamera = row?.findViewById(R.id.img_kv)
            this.deviceSetting = row?.findViewById(R.id.deviceSettingNum)
        }
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val viewHolder: ViewHolder
        if (convertView == null) {
            val inflater = activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.layout_khuvuon, null)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        var garden = items[position]
        viewHolder.kvName?.text = garden.gardenName
        viewHolder.deviceSetting!!.text = returnStatus(garden.gardenId!!)

        // chuyển bytearray về bitmap để hiển thị
//        var imageBitmap : ByteArray? = garden.gardenImage
//        var bitmap: Bitmap = BitmapFactory.decodeByteArray(imageBitmap,0, imageBitmap!!.size)
//        viewHolder.kvCamera!!.setImageBitmap(bitmap)
        Glide.with(activity)
            .load(Uri.fromFile(File(garden.gardenImage)))
            .into(viewHolder.kvCamera!!)

        return view as View
    }
    override fun getItem(i: Int): Garden {
        return items[i]
    }
    override fun getItemId(i: Int): Long {
        return i.toLong()
    }
    override fun getCount(): Int {
        return items.size
    }

    private fun returnStatus(gardenId: Int): String {
        database = Database(activity)
        var content = database.findAllDeviceByGardenId(gardenId)
        var result =  activity.resources.getString(R.string.da_cai_dat_vi)+ " 0 " + activity.resources.getString(R.string.device_status_vi)
        if (!content.isNullOrEmpty()) {
            result =  activity.resources.getString(R.string.da_cai_dat_vi) + " " + content.size + " " + activity.resources.getString(R.string.device_status_vi)
        }
        return result
    }
}