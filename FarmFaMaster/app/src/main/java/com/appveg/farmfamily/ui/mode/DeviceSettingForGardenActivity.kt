package com.appveg.farmfamily.ui.mode

import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.Gravity
import android.view.View
import android.widget.*
import com.appveg.farmfamily.R
import com.appveg.farmfamily.ui.Screenshot
import com.appveg.farmfamily.ui.database.Database
import com.appveg.farmfamily.ui.device.DeviceDetail
import com.baoyz.swipemenulistview.SwipeMenuCreator
import com.baoyz.swipemenulistview.SwipeMenuItem
import kotlinx.android.synthetic.main.activity_device_setting_for_garden.*
import kotlinx.android.synthetic.main.activity_device_setting_for_garden.detail_mode_btn
import kotlinx.android.synthetic.main.custom_dialog_mode.*
import kotlinx.android.synthetic.main.fragment_vegetable.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*
import kotlin.collections.ArrayList

class DeviceSettingForGardenActivity : AppCompatActivity() {
    private val activity = this@DeviceSettingForGardenActivity
    private lateinit var database: Database

    var deviceDetails: ArrayList<DeviceDetail> = ArrayList()
    var listModes: ArrayList<Mode> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_setting_for_garden)



        val listDeviceDetail = getListDeviceDetail()

        list_view_mode_device.adapter = DeviceSettingForGardenAdapter(activity, listDeviceDetail)

        //swipemenulistview
        val creator = SwipeMenuCreator { menu ->

            val addItem = SwipeMenuItem(
                this.activity
            )
//            // set item background
//            openItem.background = ColorDrawable(
//                Color.rgb(0x00, 0x66,0xff
//                )
//            )
            // set item width
            addItem.width = 100
            // set item title
//            editItem.title = "Open"
//            // set item title fontsize
//            editItem.titleSize = 18
            // set item title font color
//            editItem.titleColor = Color.WHITE

            //set icon
            addItem.setIcon(R.drawable.ic_add_param)
            // add to menu
            menu.addMenuItem(addItem)

        }

        // set swipe
        list_view_mode_device.setMenuCreator(creator)
        list_view_mode_device.setOnMenuItemClickListener { position, menu, index ->
            when (index) {
                0 -> {
                    var dialog = Dialog(activity)
                    // get id by position
                    var deviceId = deviceDetails[position].deviceID!!.toInt()
                    //var modeDevices = database.findAllModeDeviceByDeviceId(deviceId)
                    var gardenCode = getGardenCode()
                    var gardenId = getDataFromItent()
                    dialog.setContentView(R.layout.custom_dialog_mode)
                    var myNames = dialog.findViewById(R.id.list_mode) as ListView
                    var layoutListViewMode = dialog.findViewById(R.id.layout_list_view_mode) as LinearLayout
                    var layoutNoDataMode = dialog.findViewById(R.id.layout_no_data_mode) as LinearLayout
                    var layoutChooseModePopup = dialog.findViewById(R.id.layout_choose_mode_popup) as LinearLayout
                    var deviceNoDataMode = dialog.findViewById(R.id.device_no_data_mode) as TextView
                    var button = dialog.findViewById(R.id.detail_mode_btn_ok) as Button
                    //get list mode
                    listModes = getListMode()
                    if (listModes.isNullOrEmpty()) {
                        layoutListViewMode.visibility = View.GONE
                        layoutNoDataMode.visibility = View.VISIBLE
                        deviceNoDataMode.text = getString(R.string.no_data_vi)
                        layoutChooseModePopup.gravity = Gravity.CENTER
                    } else {
                        layoutListViewMode.visibility = View.VISIBLE
                        layoutNoDataMode.visibility = View.GONE
                        myNames.adapter = ModePopupAdapter(activity, listModes,deviceId,gardenCode,gardenId)
                    }
                    button.setOnClickListener {
                        dialog.dismiss()
                    }
                    dialog.show()
                }
            }// open
            // delete
            // false : close the menu; true : not close the menu
            false
        }
        detail_mode_btn.setOnClickListener {
            var intent: Intent = Intent(activity, ModeDetailActivity::class.java)
            startActivity(intent)
        }

        //button save\
        var viewDeviceSettingForGarden = findViewById<Button>(R.id.view_mode_btn_print)
        viewDeviceSettingForGarden.setOnClickListener {
            var dialog = Dialog(activity)
            // get id by position
            dialog.setContentView(R.layout.custom_dialog_screenhot)
            dialog.setTitle(getString(R.string.choose_mode_for_device))
            var imageView = dialog.findViewById(R.id.imageView) as ImageView
            var button = dialog.findViewById(R.id.detail_mode_btn_screen_ok) as Button

            //handler
            val activityView = layoutDeviceSettingForGarden_screenshot.rootView
            var screenshot = Screenshot()
            val b = screenshot.takescreenshot(activityView)
            imageView.setImageBitmap(b)
            saveImageSDcard(b)
            //get list mode
            button.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }
    }


    /**
     * the method to display device
     */
    private fun getListDeviceDetail() : ArrayList<DeviceDetail>{
        database = Database(activity)
        var gardenId = getDataFromItent()
        deviceDetails = database.findAllDeviceByGardenId(gardenId)
        if (deviceDetails.isNullOrEmpty()) {
            Toast.makeText(activity, getString(R.string.list_device_isEmpty_vi), Toast.LENGTH_LONG).show()
        }
        return deviceDetails
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
     * the method to display device for gaden
     */
    private fun getListMode():ArrayList<Mode> {
        database = Database(activity)
        return database.findAllMode()
    }

    /**
     * save picture
     */
    private fun saveImageSDcard(bitmap: Bitmap) : Uri? {

        val path = Environment.getExternalStorageDirectory().toString()
        val file = File(path, "${UUID.randomUUID()}.png")

        var outputStream : OutputStream? = null

        try {
            outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream)
            outputStream.flush()
            outputStream.close()
        }catch (e: IOException){
            e.printStackTrace()
        }
        return Uri.parse(file.absoluteFile.toString())
    }
}
