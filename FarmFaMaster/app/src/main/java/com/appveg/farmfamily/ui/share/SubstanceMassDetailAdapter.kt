package com.appveg.farmfamily.ui.share

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.appveg.farmfamily.R
import com.bumptech.glide.Glide
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.ceil


class SubstanceMassDetailAdapter (private var activity: Activity, private var items: ArrayList<SubstanceDetail>) :  BaseAdapter(){

    private class ViewHolder(row: View?) {
        var substanceDetailImage: ImageView
        var substanceDetailName: TextView
        var substanceDetailNum: TextView
        var substanceDetailDate: TextView

        init {
            this.substanceDetailImage = row?.findViewById(R.id.view_substance_detail_image) as ImageView
            this.substanceDetailName = row?.findViewById(R.id.view_substance_detail_name) as TextView
            this.substanceDetailDate = row?.findViewById(R.id.view_substance_detail_date) as TextView
            this.substanceDetailNum = row?.findViewById(R.id.view_substance_detail_num) as TextView
        }
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val viewHolder: ViewHolder
        if (convertView == null) {
            val inflater = activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.layout_substance_mass_detail, null)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        var substanceDetail = items[position]

        viewHolder.substanceDetailName.text = substanceDetail.substanceMassDetailName
        viewHolder.substanceDetailDate.text = substanceDetail.createdDate
        viewHolder.substanceDetailNum.text = (ceil(substanceDetail.substanceMassDetailNum.toDouble()*10) /10).toString() + " L"

        // chuyển bytearray về bitmap để hiển thị
        //var imageBitmap : String? = substanceDetail.substanceMassDetailImage
        //var uri = Uri.parse(imageBitmap)

        Glide.with(activity)
            .load(Uri.fromFile(File(substanceDetail.substanceMassDetailImage)))
            .into(viewHolder.substanceDetailImage)

        return view
    }
    override fun getItem(i: Int): SubstanceDetail {
        return items[i]
    }
    override fun getItemId(i: Int): Long {
        return i.toLong()
    }
    override fun getCount(): Int {
        return items.size
    }

}