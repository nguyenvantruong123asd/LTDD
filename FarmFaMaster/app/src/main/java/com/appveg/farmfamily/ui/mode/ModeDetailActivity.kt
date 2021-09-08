package com.appveg.farmfamily.ui.mode

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.appveg.farmfamily.R
import com.appveg.farmfamily.ui.database.Database
import com.baoyz.swipemenulistview.SwipeMenuCreator
import com.baoyz.swipemenulistview.SwipeMenuItem
import kotlinx.android.synthetic.main.activity_mode_detail.*

class ModeDetailActivity : AppCompatActivity() {

    private val activity = this@ModeDetailActivity
    private lateinit var database: Database
    var modes: ArrayList<Mode> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mode_detail)

        modes = getListMode()

        list_view_mode_detail.adapter = ModeDetailAdapter(activity, modes)

        mode_detail_btn_add.setOnClickListener {
            var intent: Intent = Intent(activity, AddModeForDeviceActivity::class.java)
            startActivity(intent)
        }
        mode_btn_cancel.setOnClickListener {
            activity.finish()
        }

        //swipemenulistview
        val creator = SwipeMenuCreator { menu ->
            // create "open" item
            val editItem = SwipeMenuItem(
                this.activity
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
                this.activity
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
        list_view_mode_detail.setMenuCreator(creator)
        list_view_mode_detail.setOnMenuItemClickListener { position, menu, index ->
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
                            removeMode(position)
                        })
                        // negative button text and action
                        .setNegativeButton(getString(R.string.quit_vi), DialogInterface.OnClickListener { dialog, id -> dialog.cancel()
                        })

                    // create dialog box
                    val alert = dialogBuilder.create()
                    // set title for alert dialog box
                    alert.setTitle(getString(R.string.delete_mode_title))
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
    private fun getListMode() : ArrayList<Mode>{
        database = Database(activity)
        var listMode = database.findAllMode()
        if (listMode.isNullOrEmpty()) {
            Toast.makeText(activity, getString(R.string.list_mode_isEmpty_vi), Toast.LENGTH_LONG).show()
        }
        return listMode
    }

    /**
     * the method to removeBatch
     */
    private fun removeMode(position: Int) {
        database = Database(activity)

        var modeId = modes[position].modeId!!.toInt()
        modes.remove(modes[position])
        database.deleteMode(modeId)
        database.deleteModeDeviceByModeId(modeId)
        // finish because page no data
        Toast.makeText(
            activity,
            getString(R.string.deleted_data_success_vi),
            Toast.LENGTH_LONG
        ).show()
        list_view_mode_detail.adapter = ModeDetailAdapter(activity, modes)
    }

    /**
     * the method to itent data for Veg
     */
    private fun getForwardData(position: Int){
        var modeId = modes[position].modeId!!.toInt()
        var intent: Intent = Intent(activity, EditModeForDeviceActivity::class.java)
        intent.putExtra("mode_id",modeId)
        startActivity(intent)
    }

    /**
     * the method to resume ( call when back stack)
     */
    override fun onResume() {
        super.onResume()
        modes = getListMode()
        list_view_mode_detail.adapter = ModeDetailAdapter(activity, modes)
    }
}
