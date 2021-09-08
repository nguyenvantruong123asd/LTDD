package com.appveg.farmfamily.ui.mode

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.TextView
import com.appveg.farmfamily.R

class ModeDetailAdapter(
    private val activity: Activity,
    private var items: ArrayList<Mode>
) : BaseAdapter() {

    //1
    override fun getCount(): Int {
        return items.size
    }

    //2
    override fun getItem(position: Int): Any {
        return items[position]
    }

    //3
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    //4
    private class ViewHolder(row: View?) {
        var modeCode: TextView? = null
        var modeTimeRepeat: TextView? = null
        var modeTimeInfo: TextView? = null
        var modeChecked: CheckBox

        init {
            this.modeCode = row?.findViewById(R.id.view_mode_code) as TextView
            this.modeTimeRepeat = row?.findViewById(R.id.view_mode_time_repeat) as TextView
            this.modeTimeInfo = row?.findViewById(R.id.view_mode_time_info) as TextView
            this.modeChecked = row?.findViewById(R.id.mode_checked) as CheckBox
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var view: View?
        var viewHolder: ViewHolder
        if (convertView == null) {

            var layout = LayoutInflater.from(activity)
            view = layout.inflate(R.layout.layout_modes, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder

        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        var mode = items[position]
        viewHolder.modeCode!!.text = mode.code
        viewHolder.modeTimeRepeat!!.text = mode.timeRepeat
        viewHolder.modeTimeInfo!!.text =
            activity!!.resources.getString(R.string.time_slot_vi)+ ": " + mode.timeOn + " - " + mode.timeOff + ", " + activity!!.resources.getString(R.string.ON_vi) + ": " + mode.on + "p, " + activity!!.resources.getString(
                R.string.OFF_vi
            ) + ": " + mode.off + "p"


        return view as View

    }

}