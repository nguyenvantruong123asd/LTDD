package com.appveg.farmfamily.ui.device_catogory

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
import androidx.fragment.app.FragmentActivity
import com.appveg.farmfamily.R
import com.bumptech.glide.Glide
import java.io.File

class DeviceCategoryAdapter (private val activity: Activity, private val veg: ArrayList<DeviceCategory>) : BaseAdapter() {

    //1
    override fun getCount(): Int {
        return veg.size
    }

    //2
    override fun getItem(position: Int): Any {
        return veg[position]
    }

    //3
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    //4
    private class ViewHolder(row: View?) {
        var dcategory_name: TextView
        var dcategory_img: ImageView



        init {
            this.dcategory_name = row?.findViewById(R.id.name_device_category) as TextView
            this.dcategory_img = row?.findViewById(R.id.img_device_category) as ImageView

        }

    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var view: View?
        var viewHolder : ViewHolder
        if( convertView == null){

            val inflater = activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.layout_list_device_category,null)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder

        }else{
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        var dcategory : DeviceCategory = getItem(position) as DeviceCategory
        viewHolder.dcategory_name.text = dcategory.dcategoryName

        // chuyển bytearray về bitmap để hiển thị
        Glide.with(activity)
            .load(Uri.fromFile(File(dcategory.dcategoryImg)))
            .into(viewHolder.dcategory_img)

        return view as View

    }
}