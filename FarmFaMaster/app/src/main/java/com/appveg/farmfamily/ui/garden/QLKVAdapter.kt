package com.appveg.farmfamily.ui.garden

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
import com.appveg.farmfamily.ui.vegetable.Vegetable
import com.bumptech.glide.Glide
import java.io.File

class QLKVAdapter (private var activity: Activity, private var items: ArrayList<Garden>) :  BaseAdapter(){

    private class ViewHolder(row: View?) {
        var qlkhuvuon_name: TextView? = null
        var qlkhuvuon_photo: ImageView? = null

        init {
            this.qlkhuvuon_name = row?.findViewById(R.id.text_view_garden)
            this.qlkhuvuon_photo = row?.findViewById(R.id.image_view_garden)
        }
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View?
        val viewHolder: ViewHolder
        if (convertView == null) {
            val inflater = activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.layout_ql_khuvuon, null)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        var garden : Garden = getItem(position)
        viewHolder.qlkhuvuon_name!!.text = garden.gardenName

        // chuyển bytearray về bitmap để hiển thị
//        var imageBitmap : ByteArray? = garden.gardenImage
//        var bitmap: Bitmap = BitmapFactory.decodeByteArray(imageBitmap,0, imageBitmap!!.size)
//        viewHolder.qlkhuvuon_photo!!.setImageBitmap(bitmap)
        Glide.with(activity)
            .load(Uri.fromFile(File(garden.gardenImage)))
            .into(viewHolder.qlkhuvuon_photo!!)

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
}