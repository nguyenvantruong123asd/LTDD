package com.appveg.farmfamily.ui.device_catogory

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
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
import com.baoyz.swipemenulistview.SwipeMenu
import com.baoyz.swipemenulistview.SwipeMenuCreator
import com.baoyz.swipemenulistview.SwipeMenuItem
import com.baoyz.swipemenulistview.SwipeMenuListView
import kotlinx.android.synthetic.main.fragment_device_category.*
import kotlinx.android.synthetic.main.fragment_vegetable.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*
import kotlin.collections.ArrayList

class DeviceCategoryFragment : Fragment() {

    private lateinit var database: Database

    private var deviceCategories: ArrayList<DeviceCategory> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_device_category, container, false)


        var listViewDeviceCategory = root.findViewById(R.id.list_view_device_category) as SwipeMenuListView

        deviceCategories = getListDeviceCategory()

        listViewDeviceCategory.adapter = this.activity?.let {DeviceCategoryAdapter(it, deviceCategories) }

//        swipemenulistview
        val creator = SwipeMenuCreator { menu ->
            // create "open" item
            val editItem = SwipeMenuItem(
                this.context
            )

            editItem.width = 100

//            set icon
            editItem.setIcon(R.drawable.ic_edit)
            // add to menu
            menu.addMenuItem(editItem)

            // create "delete" item
            val deleteItem = SwipeMenuItem(
                this.context
            )
//
            // set item width
            deleteItem.width = 100
            // set a icon
            deleteItem.setIcon(R.drawable.ic_delete)
            // add to menu
            menu.addMenuItem(deleteItem)
        }

        // set swipe
        listViewDeviceCategory.setMenuCreator(creator)
        listViewDeviceCategory.setOnMenuItemClickListener { position, menu, index ->
            when (index) {
                0 -> {
                    getForwardData(position)
                }
                1 -> {
                    // build alert dialog
                    val dialogBuilder = AlertDialog.Builder(activity!!)

                    // set message of alert dialog
                    dialogBuilder.setMessage(getString(R.string.delete_title_all_vi))
                        // if the dialog is cancelable
                        .setCancelable(false)
                        // positive button text and action
                        .setPositiveButton(getString(R.string.yes_vi), DialogInterface.OnClickListener { dialog, id -> removeDeviceCategory(position)
                        })
                        // negative button text and action
                        .setNegativeButton(getString(R.string.quit_vi), DialogInterface.OnClickListener { dialog, id -> dialog.cancel()
                        })

                    // create dialog box
                    val alert = dialogBuilder.create()
                    // set title for alert dialog box
                    alert.setTitle(getString(R.string.delete_device_category))
                    // show alert dialog
                    alert.show()
                }
            }// open
            // delete
            // false : close the menu; true : not close the menu
            false
        }

        //button them rau
        var btn_add_device_category = root.findViewById(R.id.btn_add_device_category) as Button
        btn_add_device_category.setOnClickListener {
            var intent: Intent = Intent(requireContext(), AddDeviceCategory::class.java)
            //listRoom_roomfunction.visibility = View.GONE
            startActivity(intent)
        }

        //button save
        var viewDeviceCategoryPrint = root.findViewById<Button>(R.id.btn_print_device_category)
        viewDeviceCategoryPrint.setOnClickListener {
            var dialog = Dialog(activity)
            // get id by position
            dialog.setContentView(R.layout.custom_dialog_screenhot)
            dialog.setTitle(getString(R.string.choose_mode_for_device))
            var imageView = dialog.findViewById(R.id.imageView) as ImageView
            var button = dialog.findViewById(R.id.detail_mode_btn_screen_ok) as Button

            //handler
            val activityView = device_category_fragment_layout.rootView
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


        return root.rootView

    }

    /**
     * the method to display batch
     */
    private fun getListDeviceCategory() : ArrayList<DeviceCategory>{
        database = Database(activity)
        deviceCategories = database.findAllDeviceCategory()
        if (deviceCategories.isNullOrEmpty()) {
            Toast.makeText(activity, getString(R.string.device_category_is_empty), Toast.LENGTH_LONG).show()
        }
        return deviceCategories
    }
    /**
     * the method to removeBatch
     */
    private fun removeDeviceCategory(position: Int) {
        database = Database(activity)
        var dcategoryId = database.deleteDeviceCategory(deviceCategories[position].dcategoryID!!.toInt())
        deviceCategories.remove(deviceCategories[position])
        if (dcategoryId != null) {
            Toast.makeText(
                activity,
                getString(R.string.deleted_data_success_vi),
                Toast.LENGTH_LONG
            ).show()
        }
        list_view_device_category.adapter =
            activity?.let { DeviceCategoryFragmentAdapter(it, deviceCategories) }
    }

    /**
     * the method to resume ( call when back stack)
     */
    override fun onResume() {
        super.onResume()
        deviceCategories = getListDeviceCategory()
        list_view_device_category.adapter = activity?.let { DeviceCategoryFragmentAdapter(it,deviceCategories) }
    }

    /**
     * the method to itent data for Veg
     */
    fun getForwardData(position: Int){
        var dcategoryID = deviceCategories[position].dcategoryID!!.toInt()
        var intent: Intent = Intent(activity, EditDeviceCategory::class.java)
        intent.putExtra("device_category_id",dcategoryID)
        startActivity(intent)
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