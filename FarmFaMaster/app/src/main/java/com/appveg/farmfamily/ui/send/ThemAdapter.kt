package com.appveg.farmfamily.ui.send

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import com.appveg.farmfamily.R
import com.appveg.farmfamily.ui.database.Database
import com.appveg.farmfamily.ui.vegetable.VegetableTemp

class ThemAdapter(private val context: Context, private val dotRau: ArrayList<VegetableTemp>) : BaseAdapter() {

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
        var rau_name: TextView
        var rau_soluong: TextView

        init {
            this.rau_name = row?.findViewById(R.id.txt_rau) as TextView
            this.rau_soluong = row?.findViewById(R.id.txtsoluong) as TextView
        }

    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var view: View?
        var viewHolder : ViewHolder
        if( convertView == null){

            var layout = LayoutInflater.from(context)
            view = layout.inflate(R.layout.list_itemrau_themsl,parent,false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder

        }else{
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        var vegetableTemp : VegetableTemp = getItem(position) as VegetableTemp
        viewHolder.rau_name.text = vegetableTemp.vegName
        viewHolder.rau_soluong.text = vegetableTemp.vegQty.toString()

        return view as View

    }
    /*remove in dotRau and remove item in arraylist*/
    fun removeItemPosition(position: Int) : ArrayList<VegetableTemp>{
        var database = Database(context)
        var vegetableTemp : VegetableTemp = getItem(position) as VegetableTemp
        dotRau.remove(vegetableTemp)
        return dotRau
    }
    /*remove in dotRau and remove item in database*/
    fun removeItemPositionDb(batch_id: Int) : ArrayList<VegetableTemp>{
        var database = Database(context)
        if(batch_id != null){
            database.deleteBatchDetail(batch_id)
        }
        return dotRau
    }

}

