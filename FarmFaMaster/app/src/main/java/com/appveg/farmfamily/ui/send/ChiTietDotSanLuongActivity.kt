package com.appveg.farmfamily.ui.send

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.ImageView
import com.appveg.farmfamily.R
import com.baoyz.swipemenulistview.SwipeMenu
import com.baoyz.swipemenulistview.SwipeMenuCreator
import com.baoyz.swipemenulistview.SwipeMenuItem
import com.baoyz.swipemenulistview.SwipeMenuListView
import kotlinx.android.synthetic.main.activity_chi_tiet_san_luong.*
import android.widget.Toast
import com.appveg.farmfamily.ui.Screenshot
import com.appveg.farmfamily.ui.database.Database
import com.appveg.farmfamily.ui.device.DeviceAdapter
import kotlinx.android.synthetic.main.fragment_device.*
import kotlinx.android.synthetic.main.fragment_vegetable.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*
import kotlin.collections.ArrayList


class ChiTietDotSanLuongActivity : AppCompatActivity() {

    private val activity = this@ChiTietDotSanLuongActivity

    private lateinit var database: Database

    var bactchList: ArrayList<BatchCustom> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chi_tiet_san_luong)

        getListBatch()

        list_view.setOnItemClickListener { adapterView, view, i, l ->
            getForwardDataDetail(i)
        }

        //swipemenulistview
        val creator = SwipeMenuCreator { menu ->
            // create "open" item
            val editItem = SwipeMenuItem(
                this.applicationContext
            )
//            // set item background
//            openItem.background = ColorDrawable(
//                Color.rgb(0x00, 0x66,0xff
//                )
//            )
            // set item width
            editItem.width = 100
            // set item title
//            editItem.title = "Open"
//            // set item title fontsize
//            editItem.titleSize = 18
            // set item title font color
//            editItem.titleColor = Color.WHITE

            //set icon
            editItem.setIcon(R.drawable.ic_edit)
            // add to menu
            menu.addMenuItem(editItem)

            // create "delete" item
            val deleteItem = SwipeMenuItem(
                this.applicationContext
            )
//            // set item background
//            deleteItem.background = ColorDrawable(
//                Color.rgb(
//                    0xF9,
//                    0x3F, 0x25
//                )
//            )
            // set item width
            deleteItem.width = 100
            // set a icon
            deleteItem.setIcon(R.drawable.ic_delete)
            // add to menu
            menu.addMenuItem(deleteItem)
        }

        // set swipe
        list_view.setMenuCreator(creator)
        list_view.setOnMenuItemClickListener(object : SwipeMenuListView.OnMenuItemClickListener {
            override fun onMenuItemClick(position: Int, menu: SwipeMenu, index: Int): Boolean {
                when (index) {
                    0 -> {
                        getForwardData(position)
                    }
                    1 -> {
                        // build alert dialog
                        val dialogBuilder = AlertDialog.Builder(activity)

                        // set message of alert dialog
                        dialogBuilder.setMessage(getString(R.string.delete_title_all_vi))
                            // if the dialog is cancelable
                            .setCancelable(false)
                            // positive button text and action
                            .setPositiveButton(getString(R.string.yes_vi), DialogInterface.OnClickListener { dialog, id ->
                                removeBatch(position)
                            })
                            // negative button text and action
                            .setNegativeButton(
                                getString(R.string.quit_vi),
                                DialogInterface.OnClickListener { dialog, id ->
                                    dialog.cancel()
                                })

                        // create dialog box
                        val alert = dialogBuilder.create()
                        // set title for alert dialog box
                        alert.setTitle(getString(R.string.delete_batch))
                        // show alert dialog
                        alert.show()

                    }
                }// open
                // delete
                // false : close the menu; true : not close the menu
                return false
            }
        })


        //su kien them san luong
        themDotSanLuong.setOnClickListener {
            var intent: Intent = Intent(activity, ThemDotSanLuongActivity::class.java)
            intent.putExtra("garden_id", getDataFromItent())
            startActivity(intent)
        }

        //button save
        var viewBatchPrint = findViewById<Button>(R.id.view_batch_btn_print)
        viewBatchPrint.setOnClickListener {
            var dialog = Dialog(activity)
            // get id by position
            dialog.setContentView(R.layout.custom_dialog_screenhot)
            dialog.setTitle(getString(R.string.choose_mode_for_device))
            var imageView = dialog.findViewById(R.id.imageView) as ImageView
            var button = dialog.findViewById(R.id.detail_mode_btn_screen_ok) as Button

            //handler
            val activityView = layoutBatch_screenshot
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
     * the method to get data from intent
     */
    private fun getDataFromItent(): Int {
        val bundle: Bundle = intent.extras
        val id: Int =
            bundle.get("garden_id") as Int
        return id

    }

    /**
     * the method to removeBatch
     */
    private fun removeBatch(position: Int) {
        database = Database(activity)
        var batch_id = database.deleteBatch(bactchList[position].batchId!!.toInt())
        bactchList.remove(bactchList[position])
        if (batch_id != null) {
            Toast.makeText(
                this@ChiTietDotSanLuongActivity,
                getString(R.string.deleted_data_success_vi),
                Toast.LENGTH_LONG
            ).show()
        }
        list_view.adapter = ChiTietAdapter(activity, bactchList)
    }

    /**
     * the method to display batch
     */
    private fun getListBatch() {
        var id = getDataFromItent()
        database = Database(activity)
        bactchList = database.viewBatchByGardenId(id)
        if (bactchList.isNullOrEmpty()) {
            Toast.makeText(activity, getString(R.string.list_batch_is_empty), Toast.LENGTH_LONG).show()
        } else {
            list_view.adapter = ChiTietAdapter(activity, bactchList)
        }
    }


    /**
     * the method to itent data for batch and batch detail
     */
    fun getForwardData(position: Int) {
        var gardenIdForward = getDataFromItent()
        var batch_id = bactchList[position].batchId!!.toInt()
        var intent: Intent = Intent(applicationContext, SuaDotSanLuongActivity::class.java)
        intent.putExtra("garden_id", gardenIdForward)
        intent.putExtra("batch_id", batch_id)
        startActivity(intent)
    }

    /**
     * the method to itent data for batch and batch detail
     */
    fun getForwardDataDetail(position: Int) {
        var intent: Intent = Intent(activity, ChiTietSanLuongRauActivity::class.java)
        intent.putExtra("garden_id", getDataFromItent())
        intent.putExtra("batch_id", bactchList.get(position).batchId)
        startActivity(intent)
    }

    /**
     * the method to resume ( call when back stack)
     */
    override fun onResume() {
        super.onResume()
        var id = getDataFromItent()
        bactchList = database.viewBatchByGardenId(id)
        if (bactchList.isNullOrEmpty()) {
            Toast.makeText(activity, getString(R.string.list_batch_is_empty), Toast.LENGTH_LONG).show()
        } else {
            list_view.adapter = ChiTietAdapter(activity, bactchList)
        }
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


