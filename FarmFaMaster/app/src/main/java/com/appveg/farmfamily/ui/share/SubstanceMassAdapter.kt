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
import com.appveg.farmfamily.ui.device.Device
import com.bumptech.glide.Glide
import java.io.File
import kotlin.math.ceil


class SubstanceMassAdapter (private var activity: Activity, private var items: ArrayList<Substance>) :  BaseAdapter(){

    private class ViewHolder(row: View?) {
        var substanceImage: ImageView
        var substanceName: TextView
        var substanceTotal: TextView

        init {
            this.substanceImage = row?.findViewById(R.id.view_substance_image) as ImageView
            this.substanceName = row?.findViewById(R.id.view_substance_name) as TextView
            this.substanceTotal = row?.findViewById(R.id.view_substance_total) as TextView
        }
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val viewHolder: ViewHolder
        if (convertView == null) {
            val inflater = activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.layout_substance_mass, null)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        var substance = items[position]

        viewHolder.substanceName.text = substance.substanceMassName
        viewHolder.substanceTotal.text =  (ceil(substance.totalSubstanceMass.toDouble()*10) /10).toString() + " L"

        // chuyển bytearray về bitmap để hiển thị
        var imageBitmap : String? = substance.substanceMassImage
        var uri = Uri.parse(imageBitmap)

        Glide.with(activity)
            .load(Uri.fromFile(File(substance.substanceMassImage)))
            .into(viewHolder.substanceImage)
        return view
    }
    override fun getItem(i: Int): Substance {
        return items[i]
    }
    override fun getItemId(i: Int): Long {
        return i.toLong()
    }
    override fun getCount(): Int {
        return items.size
    }

}