package com.appveg.farmfamily.ui.device

import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.appveg.farmfamily.R
import com.appveg.farmfamily.ui.Screenshot
import com.appveg.farmfamily.ui.database.Database
import com.baoyz.swipemenulistview.SwipeMenuCreator
import com.baoyz.swipemenulistview.SwipeMenuItem
import com.baoyz.swipemenulistview.SwipeMenuListView
import kotlinx.android.synthetic.main.fragment_device.*
import kotlinx.android.synthetic.main.fragment_vegetable.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*
import kotlin.collections.ArrayList

class DeviceFragment : Fragment() {
    private lateinit var database: Database

    var devices: ArrayList<Device> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_device, container, false)

        var listViewDevice = root.findViewById(R.id.list_view_device) as SwipeMenuListView

        val devices = getListDevice()

        listViewDevice.adapter = this.activity?.let { DeviceAdapter(it, devices) }


        listViewDevice.setOnItemClickListener { adapterView, view, i, l ->
                getForwardDataDetail(i)
        }

        val creator = SwipeMenuCreator { menu ->
            // create "open" item
            val editItem = SwipeMenuItem(
                this.context
            )
//
            // set item width
            editItem.width = 100

//            editItem.titleColor = Color.WHITE

            //set icon
            editItem.setIcon(R.drawable.ic_edit)
            // add to menu
            menu.addMenuItem(editItem)

            // create "delete" item
//            val deleteItem = SwipeMenuItem(
//                this.context
//            )

//            // set item width
//            deleteItem.width = 100
//            // set a icon
//            deleteItem.setIcon(R.drawable.ic_delete)
//            // add to menu
//            menu.addMenuItem(deleteItem)
        }

        // set swipe
        listViewDevice.setMenuCreator(creator)
        listViewDevice.setOnMenuItemClickListener { position, menu, index ->
            when (index) {
                0 -> {
                    getForwardData(position)
                }
            }// open
            // delete
            // false : close the menu; true : not close the menu
            false
        }

        var deviceBtnAdd = root.findViewById(R.id.view_device_btn_add) as Button
        deviceBtnAdd.setOnClickListener {
            var intent: Intent = Intent(requireContext(), AddDeviceActivity::class.java)
            startActivity(intent)
        }

        //button save
        var viewDeviceBtnPrint = root.findViewById<Button>(R.id.view_device_btn_print)

        viewDeviceBtnPrint.setOnClickListener {
            var dialog = Dialog(activity)
            // get id by position
            dialog.setContentView(R.layout.custom_dialog_screenhot)
            dialog.setTitle(getString(R.string.choose_mode_for_device))
            var imageView = dialog.findViewById(R.id.imageView) as ImageView
            var button = dialog.findViewById(R.id.detail_mode_btn_screen_ok) as Button

            //handler
            val activityView = layoutDevice_screenshot.rootView
            var screenshot = Screenshot()
            val b = screenshot.takescreenshot(activityView)
            imageView.setImageBitmap(b)
            saveImageSDcard(b)
            //get list mode
            button.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
//            Toast.makeText(activity, "ahihi!", Toast.LENGTH_LONG).show()

        }

        return root.rootView


    }
    /**
     * the method to display device
     */
    private fun getListDevice() : ArrayList<Device>{
        database = Database(activity)
        devices = database.findAllDevice()
        if (devices.isNullOrEmpty()) {
            Toast.makeText(activity, getString(R.string.list_device_isEmpty_vi), Toast.LENGTH_LONG).show()
        }else{
            for (i in 0 until devices.size){
                var deviceDetails = database.findAllDeviceDetail(devices[i].deviceID!!.toInt())
                devices[i].deviceNum = "(" + deviceDetails.size.toString() + ")"
            }
        }
        return devices
    }

    /**
     * the method to itent data for Veg
     */
    private fun getForwardData(position: Int){
        var device_id = devices[position].deviceID!!.toInt()
        var intent: Intent = Intent(activity, EditDeviceActivity::class.java)
        intent.putExtra("device_id",device_id)
        startActivity(intent)
    }

    /**
     * the method to resume ( call when back stack)
     */
    override fun onResume() {
        super.onResume()
        devices = getListDevice()
        list_view_device.adapter = activity?.let { DeviceAdapter(it,devices) }
    }

    /**
     * the method to itent device detail
     */
    private fun getForwardDataDetail(i: Int) {
        var device_id = devices[i].deviceID!!.toInt()
        var intent: Intent = Intent(activity, DeviceDetailActivity::class.java)
        intent.putExtra("device_id",device_id)
        startActivity(intent)
    }

    /**
     * save picture
     */
    private fun  saveImageSDcard(bitmap: Bitmap) : Uri? {

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