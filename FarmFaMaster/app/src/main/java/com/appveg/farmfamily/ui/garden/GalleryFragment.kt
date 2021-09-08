package com.appveg.farmfamily.ui.garden

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.*
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
import kotlinx.android.synthetic.main.fragment_gallery.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*
import kotlin.collections.ArrayList

class GalleryFragment : Fragment() {
    private lateinit var database: Database

    var gardens: ArrayList<Garden> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_gallery, container, false)

        var listViewGarden = root.findViewById(R.id.list_view_garden) as SwipeMenuListView

        gardens = getListGarden()

        listViewGarden.adapter = this.activity?.let { QLKVAdapter(it, gardens) }

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
        listViewGarden.setMenuCreator(creator)
        listViewGarden.setOnMenuItemClickListener { position, menu, index ->
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
                        .setPositiveButton(getString(R.string.yes_vi), DialogInterface.OnClickListener { dialog, id -> removeGarden(position)
                        })
                        // negative button text and action
                        .setNegativeButton(getString(R.string.quit_vi), DialogInterface.OnClickListener { dialog, id -> dialog.cancel()
                        })

                    // create dialog box
                    val alert = dialogBuilder.create()
                    // set title for alert dialog box
                    alert.setTitle(getString(R.string.delete_kv_vi))
                    // show alert dialog
                    alert.show()
                }
            }// open
            // delete
            // false : close the menu; true : not close the menu
            false
        }

        //button them khu vườn
        var btnGardenAdd = root.findViewById(R.id.garden_btn_add) as Button
        btnGardenAdd.setOnClickListener {
            var intent: Intent = Intent(requireContext(), ThemKhuVuonActivity::class.java)
            startActivity(intent)
        }

        listViewGarden.setOnItemClickListener { adapterView, view, i, l ->
            var gardenId = gardens[i].gardenId!!
            var gardenCode = gardens[i].gardenCode
            var intent: Intent = Intent(requireContext(), AddDeviceForGardenActivity::class.java)
            intent.putExtra("garden_id",gardenId)
            intent.putExtra("garden_code",gardenCode)
            startActivity(intent)
        }
//        button save
        var viewGardenPrint = root.findViewById<Button>(R.id.garden_btn_print)
        viewGardenPrint.setOnClickListener {
            var dialog = Dialog(activity)
            // get id by position
            dialog.setContentView(R.layout.custom_dialog_screenhot)
            dialog.setTitle(getString(R.string.choose_mode_for_device))
            var imageView = dialog.findViewById(R.id.imageView) as ImageView
            var button = dialog.findViewById(R.id.detail_mode_btn_screen_ok) as Button

            //handler
            val activityView = layout_garden_screenshot.rootView
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
    private fun getListGarden() : ArrayList<Garden>{
        database = Database(activity)
        gardens = database.findAllGarden()
        if (gardens.isNullOrEmpty()) {
            Toast.makeText(activity, getString(R.string.list_garden_is_empty), Toast.LENGTH_LONG).show()
        }
        return gardens
    }
    /**
     * the method to removeBatch
     */
    private fun removeGarden(position: Int) {
        database = Database(activity)
        var gardenId = database.deleteGarden(gardens[position].gardenId!!.toInt())
        gardens.remove(gardens[position])
        if (gardenId != null) {
            Toast.makeText(
                activity,
                getString(R.string.deleted_data_success_vi),
                Toast.LENGTH_LONG
            ).show()
        }
        list_view_garden.adapter = activity?.let { QLKVAdapter(it, gardens) }
    }

    /**
     * the method to itent data for Veg
     */
    private fun getForwardData(position: Int){
        var gardenId = gardens[position].gardenId!!.toInt()
        var intent: Intent = Intent(activity, SuaKhuVuonActivity::class.java)
        intent.putExtra("garden_id",gardenId)
        startActivity(intent)
    }

    /**
     * the method to resume ( call when back stack)
     */
    override fun onResume() {
        super.onResume()
        gardens = getListGarden()
        list_view_garden.adapter = activity?.let { QLKVAdapter(it,gardens) }
    }

    private fun getScreenShot(view: View): Bitmap {
        val returnedBitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(returnedBitmap)
        val bgDrawable = view.background
        if (bgDrawable != null) bgDrawable.draw(canvas)
        else canvas.drawColor(Color.WHITE)
        view.draw(canvas)
        return returnedBitmap
    }

    private fun saveImageSDcard(bitmap: Bitmap) : Uri? {

        var filePath = Environment.getExternalStorageDirectory()

        filePath = File(filePath, "${UUID.randomUUID()}.png")

        var outputStream : OutputStream? = null

        try {
            outputStream = FileOutputStream(filePath)
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream)
            outputStream.flush()
            outputStream.close()
        }catch (e: IOException){
            e.printStackTrace()
        }
        return Uri.parse(filePath.absoluteFile.toString())
    }
}