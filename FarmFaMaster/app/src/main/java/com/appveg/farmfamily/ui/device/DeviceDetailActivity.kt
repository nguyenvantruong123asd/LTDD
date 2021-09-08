package com.appveg.farmfamily.ui.device

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.appveg.farmfamily.R
import com.appveg.farmfamily.ui.database.Database
import com.baoyz.swipemenulistview.SwipeMenuCreator
import com.baoyz.swipemenulistview.SwipeMenuItem
import kotlinx.android.synthetic.main.activity_device_detail_status.*

class DeviceDetailActivity : AppCompatActivity() {

    private val activity = this@DeviceDetailActivity

    private lateinit var database: Database

    var deviceDetails: ArrayList<DeviceDetail> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_detail_status)

        val listDevice = getListDeviceDetail()

        list_view_device_detail.adapter = DeviceDetailAdapter(activity, listDevice)

        //swipemenulistview
        val creator = SwipeMenuCreator { menu ->
            // create "open" item
            val editItem = SwipeMenuItem(
                this.applicationContext
            )
//
            // set item width
            editItem.width = 100


            // create "delete" item
            val deleteItem = SwipeMenuItem(
                this.applicationContext
            )

            // set item width
            deleteItem.width = 100
            // set a icon
            deleteItem.setIcon(R.drawable.ic_delete)
            // add to menu
            menu.addMenuItem(deleteItem)
        }

        // set swipe
        list_view_device_detail.setMenuCreator(creator)
        list_view_device_detail.setOnMenuItemClickListener { position, menu, index ->
            when (index) {
                0 -> {
                    // build alert dialog
                    val dialogBuilder = AlertDialog.Builder(activity)

                    // set message of alert dialog
                    dialogBuilder.setMessage(getString(R.string.delete_title_all_vi))
                        // if the dialog is cancelable
                        .setCancelable(false)
                        // positive button text and action
                        .setPositiveButton(getString(R.string.yes_vi), DialogInterface.OnClickListener { dialog, id -> removeDeviceAndDeviceDetail(position)
                        })
                        // negative button text and action
                        .setNegativeButton(getString(R.string.quit_vi), DialogInterface.OnClickListener { dialog, id -> dialog.cancel()
                        })

                    // create dialog box
                    val alert = dialogBuilder.create()
                    // set title for alert dialog box
                    alert.setTitle(getString(R.string.delete_detail_device))
                    // show alert dialog
                    alert.show()
                }
            }// open
            // delete
            // false : close the menu; true : not close the menu
            false
        }

    }


    /**
     * the method to get data device
     */
    private fun getListDeviceDetail() : ArrayList<DeviceDetail>{
        database = Database(activity)
        var deviceId: Int = getDataFromItent()
        deviceDetails = database.findAllDeviceDetail(deviceId)
        if (deviceDetails.isNullOrEmpty()) {
            Toast.makeText(activity, getString(R.string.list_device_isEmpty_vi), Toast.LENGTH_LONG).show()
        }else{
            for (i in 0 until deviceDetails.size){
                // convert status
                deviceDetails[i].deviceDetailStatus = convertStatus(deviceDetails[i].deviceDetailStatus!!)

            }
        }
        return deviceDetails
    }

    /**
     * the method to removeBatch
     */
    private fun removeDeviceAndDeviceDetail(position: Int) {
        database = Database(activity)

        var deviceId: Int = getDataFromItent()

        var deviceDetail = database.deleteDetailDevice(deviceDetails[position].deviceDetailID!!.toInt())
        deviceDetails.remove(deviceDetails[position])
        if(deviceDetails.isNullOrEmpty()){
            database.deleteDevice(deviceId)
            database.deleteModeDeviceByDeviceId(deviceId)
            // finish because page no data
            Toast.makeText(
                activity,
                getString(R.string.deleted_data_success_vi),
                Toast.LENGTH_LONG
            ).show()
            activity.finish()
        }else if (deviceDetail != null) {
            Toast.makeText(
                activity,
                getString(R.string.deleted_data_success_vi),
                Toast.LENGTH_LONG
            ).show()
            list_view_device_detail.adapter = DeviceDetailAdapter(activity, deviceDetails)
        }
    }

    /**
     * the method to get data from intent
     */
    private fun getDataFromItent(): Int {
        val bundle: Bundle = intent.extras
        return bundle.get("device_id") as Int
    }
    /**
     * the method to convert status
     */
    private fun convertStatus(status: String): String{
        var result: String = ""
        if("N" == status){
            result = getString(R.string.not_used_yet_status)
        }else if(("Y") == status){
            result = getString(R.string.using_status)
        }else if(("B") == status){
            result = getString(R.string.broken_status)
        }
        return result
    }
}

