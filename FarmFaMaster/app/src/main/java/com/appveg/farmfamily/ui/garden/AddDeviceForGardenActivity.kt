package com.appveg.farmfamily.ui.garden

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import com.appveg.farmfamily.R
import com.appveg.farmfamily.ui.database.Database
import com.appveg.farmfamily.ui.device.DeviceDetail
import com.appveg.farmfamily.ui.vegetable.Vegetable

import kotlinx.android.synthetic.main.activity_add_device_for_garden.*

class AddDeviceForGardenActivity : AppCompatActivity() {
    private val activity = this@AddDeviceForGardenActivity

    var deviceForGardens: ArrayList<DeviceDetail> = ArrayList()
    var vegetables: ArrayList<Vegetable> = ArrayList()

    private lateinit var database: Database
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_device_for_garden)

        getlistVeg()
        getListDevice()

//        grid.setOnItemClickListener { adapterView, view, i, l ->
//        }

        /*action button*/
        actionButton()

    }

    private fun actionButton() {
        var gardenId = getDataFromItent()
        /*event add veg*/
        btn_add_veg_garden_forward.setOnClickListener {
            var intent: Intent = Intent(activity,SelectVegGardenActivity::class.java)
            intent.putExtra("garden_id",gardenId)
            startActivity(intent)
        }
        btn_add_device_garden_forward.setOnClickListener {
            var gardenCode = getGardenCode()
            var intent: Intent = Intent(activity,SelectDeviceGardenActivity::class.java)
            intent.putExtra("garden_id",gardenId)
            intent.putExtra("garden_code",gardenCode)
            startActivity(intent)
        }

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
    /**
     * the method to display vegetable for gaden
     */
    private fun getlistVeg() :ArrayList<Vegetable>  {
        var gardenId = getDataFromItent()
        database = Database(activity)
        vegetables = database.findVegetableByGardenId(gardenId)
        if (vegetables.isNullOrEmpty()) {
            layout_no_data_veg.visibility = View.VISIBLE
            veg_no_data.text = getString(R.string.no_data_vi)
        } else {
            layout_no_data_veg.visibility = View.GONE
            list_veg_for_garden.adapter = VegForGardenAdapter(activity, vegetables)
        }
        return vegetables
    }

    /**
     * the method to display device for gaden
     */
    private fun getListDevice():ArrayList<DeviceDetail> {
        var gardenId = getDataFromItent()
        database = Database(activity)
        deviceForGardens = database.findAllDeviceByGardenId(gardenId)
        if (deviceForGardens.isNullOrEmpty()) {
            layout_no_data_device.visibility = View.VISIBLE
            device_no_data.text = getString(R.string.no_data_vi)
        } else {
            layout_no_data_device.visibility = View.GONE
            gird_device_for_garden.adapter = DeviceForGardenAdapter(activity, deviceForGardens)
        }
        return deviceForGardens
    }

    /**
     * the method to resume ( call when back stack)
     */
    override fun onResume() {
        super.onResume()
        deviceForGardens = getListDevice()
        vegetables = getlistVeg()
        list_veg_for_garden.adapter = VegForGardenAdapter(activity, vegetables)
        gird_device_for_garden.adapter = DeviceForGardenAdapter(activity, deviceForGardens)
    }
}
