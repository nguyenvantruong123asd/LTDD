package com.appveg.farmfamily.ui.vegetable

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentActivity
import com.appveg.farmfamily.R
import com.bumptech.glide.Glide
import java.io.File

class VegetableFragmentAdapter(
    private var activity: Activity,
    private val veg: ArrayList<Vegetable>
) : BaseAdapter() {

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
        var veg_Name: TextView
        var veg_Img: ImageView


        init {
            this.veg_Name = row?.findViewById(R.id.viewgarden_nameGarden) as TextView
            this.veg_Img = row?.findViewById(R.id.viewgarden_imageIconGarden) as ImageView

        }

    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var view: View?
        var viewHolder: ViewHolder
        if (convertView == null) {

            val inflater =
                activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.layout_listview_garden, null)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder

        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        var veg: Vegetable = getItem(position) as Vegetable
        viewHolder.veg_Name.text = veg.vegName

        // load photo
        Glide.with(activity).load(Uri.fromFile(File(veg.vegImgBlob))).into(viewHolder.veg_Img)

        return view as View

    }


}