package com.appveg.farmfamily.ui.vegetable

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
import android.widget.*
import androidx.fragment.app.Fragment
import com.appveg.farmfamily.R
import com.appveg.farmfamily.ui.Screenshot
import com.appveg.farmfamily.ui.database.Database
import com.baoyz.swipemenulistview.SwipeMenuCreator
import com.baoyz.swipemenulistview.SwipeMenuItem
import com.baoyz.swipemenulistview.SwipeMenuListView
import kotlinx.android.synthetic.main.fragment_vegetable.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*
import kotlin.collections.ArrayList

class VegetableFragment  : Fragment() {

    private lateinit var database: Database

    var vegetables: ArrayList<Vegetable> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_vegetable, container, false)

        var listViewVegetable = root.findViewById(R.id.list_view_vegetable) as SwipeMenuListView

        vegetables = getListVeg()

        listViewVegetable.adapter = this.activity?.let { VegetableFragmentAdapter(it, vegetables) }

        //swipemenulistview
        val creator = SwipeMenuCreator { menu ->
            // create "open" item
            val editItem = SwipeMenuItem(
                this.context
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
                this.context
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
        listViewVegetable.setMenuCreator(creator)
        listViewVegetable.setOnMenuItemClickListener { position, menu, index ->
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
                        .setPositiveButton(getString(R.string.yes_vi), DialogInterface.OnClickListener { dialog, id -> removeVegetable(position)
                        })
                        // negative button text and action
                        .setNegativeButton(getString(R.string.quit_vi), DialogInterface.OnClickListener { dialog, id -> dialog.cancel()
                        })

                    // create dialog box
                    val alert = dialogBuilder.create()
                    // set title for alert dialog box
                    alert.setTitle(getString(R.string.delete_veg_detail))
                    // show alert dialog
                    alert.show()
                }
            }// open
            // delete
            // false : close the menu; true : not close the menu
            false
        }

        //button them rau
        var viewVegBtnAdd = root.findViewById(R.id.viewveg_btn_add) as Button
        viewVegBtnAdd.setOnClickListener {
            var intent: Intent = Intent(requireContext(), AddVegetableActivity::class.java)
            startActivity(intent)
        }

        //button save
        var viewVegBtnPrint = root.findViewById(R.id.viewveg_btn_print) as Button
        viewVegBtnPrint.setOnClickListener {
            var dialog = Dialog(activity)
            // get id by position
            dialog.setContentView(R.layout.custom_dialog_screenhot)
            dialog.setTitle(getString(R.string.choose_mode_for_device))
            var imageView = dialog.findViewById(R.id.imageView) as ImageView
            var button = dialog.findViewById(R.id.detail_mode_btn_screen_ok) as Button

            //handler
            val activityView = view_page.rootView
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
    private fun getListVeg() : ArrayList<Vegetable>{
        database = Database(activity)
        vegetables = database.findAllVegetable()
        if (vegetables.isNullOrEmpty()) {
            Toast.makeText(activity, getString(R.string.Veg_Empty_Message_vi), Toast.LENGTH_LONG).show()
        }
        return vegetables
    }
    /**
     * the method to removeBatch
     */
    private fun removeVegetable(position: Int) {
        database = Database(activity)
        var vegId = database.deleteVeg(vegetables[position].vegID!!.toInt())
        // delete param if exits
        if(vegetables[position].paramId != 0){
             database.deleteParam(vegetables[position].paramId)
        }
        vegetables.remove(vegetables[position])
        if (vegId != null) {
            Toast.makeText(
                activity,
                getString(R.string.deleted_data_success_vi),
                Toast.LENGTH_LONG
            ).show()
        }
        list_view_vegetable.adapter = this.activity?.let { VegetableFragmentAdapter(it, vegetables) }
    }

    /**
     * the method to itent data for Veg
     */
    private fun getForwardData(position: Int){
        var vegId = vegetables[position].vegID!!.toInt()
        var intent: Intent = Intent(activity, EditVegetableActivity::class.java)
        intent.putExtra("veg_id",vegId)
        startActivity(intent)
    }

    /**
     * the method to resume ( call when back stack)
     */
    override fun onResume() {
        super.onResume()
        vegetables = getListVeg()
        list_view_vegetable.adapter = activity?.let { VegetableFragmentAdapter(it, vegetables) }
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