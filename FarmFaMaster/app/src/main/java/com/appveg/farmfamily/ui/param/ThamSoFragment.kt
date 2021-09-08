package com.appveg.farmfamily.ui.param


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
import com.appveg.farmfamily.ui.device.DeviceAdapter
import com.appveg.farmfamily.ui.vegetable.EditVegetableActivity
import com.appveg.farmfamily.ui.vegetable.Vegetable
import com.appveg.farmfamily.ui.vegetable.VegetableFragmentAdapter
import com.baoyz.swipemenulistview.SwipeMenuCreator
import com.baoyz.swipemenulistview.SwipeMenuItem
import com.baoyz.swipemenulistview.SwipeMenuListView
import kotlinx.android.synthetic.main.fragment_device.*
import kotlinx.android.synthetic.main.fragment_thamso.*
import kotlinx.android.synthetic.main.fragment_vegetable.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*
import kotlin.collections.ArrayList

class ThamSoFragment : Fragment() {

    private lateinit var database: Database

    var vegetables: ArrayList<Vegetable> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_thamso, container, false)

        var listViewVegetable = root.findViewById(R.id.list_view_param) as SwipeMenuListView

        vegetables = getListVeg()

        listViewVegetable.adapter = this.activity?.let { ParamFragmentAdapter(it, vegetables) }

        listViewVegetable.setOnItemClickListener { adapterView, view, i, l ->
            checkedBeforeForwardData1(i)
        }

        //swipemenulistview
        val creator = SwipeMenuCreator { menu ->

            val addItem = SwipeMenuItem(
                this.context
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
                    checkedBeforeForwardData(position)
                }
                1 -> {
                    getForwardData(position)
                }
                2 -> {
                    // build alert dialog
                    val dialogBuilder = AlertDialog.Builder(activity)

                    // set message of alert dialog
                    dialogBuilder.setMessage(getString(R.string.delete_title_all_vi))
                        // if the dialog is cancelable
                        .setCancelable(false)
                        // positive button text and action
                        .setPositiveButton(getString(R.string.yes_vi), DialogInterface.OnClickListener { dialog, id ->
                            removeParam(position)
                        })
                        // negative button text and action
                        .setNegativeButton(getString(R.string.quit_vi), DialogInterface.OnClickListener { dialog, id -> dialog.cancel()
                        })

                    // create dialog box
                    val alert = dialogBuilder.create()
                    // set title for alert dialog box
                    alert.setTitle(getString(R.string.delete_param))
                    // show alert dialog
                    alert.show()
                }
            }// open
            // delete
            // false : close the menu; true : not close the menu
            false
        }

        //button save
        var viewParamPrint = root.findViewById<Button>(R.id.view_param_btn_print)
        viewParamPrint.setOnClickListener {
            var dialog = Dialog(activity)
            // get id by position
            dialog.setContentView(R.layout.custom_dialog_screenhot)
            dialog.setTitle(getString(R.string.choose_mode_for_device))
            var imageView = dialog.findViewById(R.id.imageView) as ImageView
            var button = dialog.findViewById(R.id.detail_mode_btn_screen_ok) as Button

            //handler
            val activityView = layoutParam_screenshot.rootView
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
        vegetables = database.findAllVegetableForParam()
        if (vegetables.isNullOrEmpty()) {
            Toast.makeText(activity, getString(R.string.Veg_Empty_Message_vi), Toast.LENGTH_LONG).show()
        }
        return vegetables
    }

    /**
     * the method to itent data for Veg
     */
    private fun checkedBeforeForwardData(position: Int){
        var vegId = vegetables[position].vegID!!.toInt()
        var vegetable : Vegetable = database.findVegetableById(vegId)
        if(vegetable.paramId != 0 ){
            Toast.makeText(activity,getString(R.string.message_setting_param),Toast.LENGTH_SHORT).show()
        }else{
            var intent: Intent = Intent(requireContext(), AddParamActivity::class.java)
            intent.putExtra("veg_id",vegId)
            startActivity(intent)
        }
    }

    /**
     * the method to itent data for Veg
     */
    private fun checkedBeforeForwardData1(position: Int){
        var vegId = vegetables[position].vegID!!.toInt()
        var vegetable : Vegetable = database.findVegetableById(vegId)
        if(vegetable.paramId != 0 ){
            var intent: Intent = Intent(requireContext(), ParamDetailActivity::class.java)
            intent.putExtra("param_id",vegetables[position].paramId)
            startActivity(intent)
        }else{
            Toast.makeText(activity,getString(R.string.message_no_setting_param),Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * the method to resume ( call when back stack)
     */
    override fun onResume() {
        super.onResume()
        vegetables = getListVeg()
        list_view_param.adapter = activity?.let { ParamFragmentAdapter(it,vegetables) }
    }

    /**
     * the method to itent data for param
     */
    private fun getForwardData(position: Int){
        var paramId = vegetables[position].paramId!!.toInt()
        if(paramId != 0 ){
            var intent: Intent = Intent(activity, EditParamActivity::class.java)
            intent.putExtra("param_id",paramId)
            startActivity(intent)
        }else{
            Toast.makeText(activity,getString(R.string.message_no_setting_param),Toast.LENGTH_SHORT).show()
        }

    }

    /**
     * the method to removeBatch
     */
    private fun removeParam(position: Int) {
        database = Database(activity)
        var paramId = database.deleteParam(vegetables[position].paramId)
        if (paramId != null) {
            database.updateParamIdForVeg(0, vegetables[position].vegID!!)
            Toast.makeText(
                activity,
                getString(R.string.deleted_data_success_vi),
                Toast.LENGTH_LONG
            ).show()
        }
        vegetables = getListVeg()
        list_view_param.adapter = ParamFragmentAdapter(activity, vegetables)
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