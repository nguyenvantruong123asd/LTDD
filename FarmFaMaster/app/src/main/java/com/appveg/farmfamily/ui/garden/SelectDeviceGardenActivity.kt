package com.appveg.farmfamily.ui.garden


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.*

import com.appveg.farmfamily.R
import com.appveg.farmfamily.ui.database.Database
import com.appveg.farmfamily.ui.device.DeviceDetail
import kotlinx.android.synthetic.main.activity_select_device_garden.*

class SelectDeviceGardenActivity : AppCompatActivity() {
    private val activity = this@SelectDeviceGardenActivity
    var deviceForGardens: ArrayList<DeviceDetail> = ArrayList()

    private lateinit var database: Database
    private lateinit var grid : GridView
    private lateinit var deviceCountSelect : TextView
    private var count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_device_garden)
        deviceCountSelect = activity.findViewById(R.id.count_select_device)
        grid = activity.findViewById(R.id.gird_device_for_garden_select)
        deviceForGardens = getListDeviceForGarden()
        var gardenId = getDataFromItent()
        var gardenCode = getGardenCode()
        grid.adapter = this.activity?.let { SelectDeviceGardenAdapter (it, deviceForGardens,gardenId,count,gardenCode) }

        /*action button*/
        actionButton()
    }
    private fun actionButton() {
        /*event cancel*/
        btn_device_cancel.setOnClickListener {
            activity.finish()
        }

    }

    /**
     * the method to display device for garden
     */
    private fun getListDeviceForGarden() : ArrayList<DeviceDetail>{
        database = Database(activity)
        var gardenId: Int = getDataFromItent()
        deviceForGardens = database.findAllDeviceDetailForGarden(gardenId)
        if (deviceForGardens.isNullOrEmpty()) {
            Toast.makeText(activity, getString(R.string.list_device_isEmpty_vi), Toast.LENGTH_LONG).show()
        }else{
            for (item in 0 until deviceForGardens.size){
                if("Y" == deviceForGardens[item].deviceDetailStatus){
                    count++
                }
            }
            deviceCountSelect.text = getString(R.string.firt_count_veg) + "($count)" + getString(R.string.last_count_device)
        }
        return deviceForGardens
    }

    /**
     * the method to get data from intent
     */
    private fun getDataFromItent(): Int {
        val bundle: Bundle = intent.extras
        return bundle.get("garden_id") as Int
    }

    /**
     * the method to get data from intent
     */
    private fun getGardenCode(): String {
        val bundle: Bundle = intent.extras
        return bundle.get("garden_code") as String
    }

}
