package com.appveg.farmfamily.ui.send

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.appveg.farmfamily.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.io.File
import kotlin.math.ceil

class ChiTietAdapter(private val context: ChiTietDotSanLuongActivity, private val dotRau: ArrayList<BatchCustom>) : BaseAdapter() {

    fun remove(position: Int) {
        dotRau.removeAt(position)
        notifyDataSetChanged()
    }

    //1
    override fun getCount(): Int {
        return dotRau.size
    }

    //2
    override fun getItem(position: Int): Any {
        return dotRau[position]
    }

    //3
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    //4
    private class ViewHolder(row: View?) {
        var dotRau_name: TextView
        var img_dotRau: ImageView
        var tongSanLuong: TextView



        init {
            this.dotRau_name = row?.findViewById(R.id.dotRau_name) as TextView
            this.img_dotRau = row?.findViewById(R.id.img_dotRau) as ImageView
            this.tongSanLuong = row?.findViewById(R.id.tong_san_luong) as TextView


        }

    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var view: View?
        var viewHolder : ViewHolder
        if( convertView == null){

            var layout = LayoutInflater.from(context)
            view = layout.inflate(R.layout.layoutlistview_chitietdot_sanluong,parent,false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder

        }else{
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        var dotRau : BatchCustom = getItem(position) as BatchCustom
        viewHolder.dotRau_name.text = dotRau.batchName

        Glide.with(context)
            .load(Uri.fromFile(File(dotRau.batchImage)))
            .into(viewHolder.img_dotRau)

        viewHolder.tongSanLuong.text = context!!.resources.getString(R.string.batch_sum) + " " + (ceil(dotRau.totalQuantity!!.toDouble()*10) /10).toString() + "/kg"

        return view as View

    }





}