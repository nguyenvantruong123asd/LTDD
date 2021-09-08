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
import com.appveg.farmfamily.ui.vegetable.Vegetable
import com.bumptech.glide.Glide
import java.io.File


class SelectVegGardenAdapter (private var activity: Activity, private var items: ArrayList<Vegetable>, private var gardenId: Int,
                              private var countDefault: Int) :  BaseAdapter() {
    private lateinit var database: Database
    private var vegStatus: Boolean = false
    private var count: Int = 0

    private class ViewHolder(row: View?) {
        var imgVegForGarden: ImageView? = null
        var vegForGardenChecked: CheckBox
        init {
            this.imgVegForGarden = row?.findViewById(R.id.img_veg_for_garden)
            this.vegForGardenChecked = row?.findViewById(R.id.veg_for_garden_checked) as CheckBox
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val viewHolder: ViewHolder
        if (convertView == null) {
            val inflater =
                activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.layout_veg_for_garden, null)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        var vegetableForGarden = items[position]

        // chuyển bytearray về bitmap để hiển thị
//        var imageBitmap: ByteArray? = vegetableForGarden.vegImgBlob
//        var bitmap: Bitmap = BitmapFactory.decodeByteArray(imageBitmap, 0, imageBitmap!!.size)
//        viewHolder.imgVegForGarden!!.setImageBitmap(bitmap)
        // load photo
        Glide.with(activity)
            .load(Uri.fromFile(File(vegetableForGarden.vegImgBlob)))
            .into(viewHolder.imgVegForGarden!!)

        var vegCountSelect: TextView = activity.findViewById(R.id.count_select_veg)

        viewHolder.vegForGardenChecked.isChecked =
            checked(vegetableForGarden.gardenId)

        // set count load default
        count = countDefault

        // event checked
        viewHolder.vegForGardenChecked.setOnClickListener {
            vegStatus = viewHolder.vegForGardenChecked.isChecked
            var temp = updateStatus(
                gardenId,
                vegetableForGarden.vegID!!,
                vegStatus
            )
            if (temp != null && temp != 0) {
                notice(temp, vegStatus)
                viewHolder.vegForGardenChecked.isChecked = vegStatus
                vegCountSelect.text = countSelected(vegStatus)
            }else if(temp == 0){
                viewHolder.vegForGardenChecked.isChecked = false
            }
        }

        return view
    }

    override fun getItem(i: Int): Vegetable {
        return items[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getCount(): Int {
        return items.size
    }

    // checked load default
    private fun checked(gardenId: Int): Boolean {
        var result = false
        if (gardenId != 0) {
            result = true
        }
        return result
    }

    // count selected
    private fun countSelected(vegStatus: Boolean): String {
        var result = ""
        if (!vegStatus && count != 0) {
            count--
        } else if (vegStatus) {
            count++
        }
        result = activity!!.resources.getString(R.string.firt_count_veg) + "($count)" + activity!!.resources.getString(R.string.last_count_veg)

        return result
    }


    // display message settings
    private fun notice(temp: Int, status: Boolean) {
        if (temp != null && status) {
            Toast.makeText(activity, activity!!.resources.getString(R.string.add_veg_for_garden) , Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(activity, activity!!.resources.getString(R.string.delete_veg_for_garden) , Toast.LENGTH_SHORT).show()
        }
    }


    // update device for garden
    private fun updateStatus(gardenId: Int, id: Int, checked: Boolean): Int {
        database = Database(activity)
        var vegetable: Vegetable = Vegetable()
        var temps = database.findVegetableForGardenByGardenId(gardenId)

        var result = false
        if (!temps.isNullOrEmpty()) {
            result = true
        }

        if (checked && !result) {
            vegetable.vegID = id
            vegetable.gardenId = gardenId
        } else if(checked && result) {
            Toast.makeText(activity, activity!!.resources.getString(R.string.add_only_veg) , Toast.LENGTH_SHORT).show()
        }else{
            vegetable.vegID = id
            vegetable.gardenId = 0
        }

        return database.updateVegForGarden(vegetable)
    }
}