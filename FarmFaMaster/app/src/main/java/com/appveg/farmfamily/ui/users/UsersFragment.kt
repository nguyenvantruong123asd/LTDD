package com.appveg.farmfamily.ui.users

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
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
import com.appveg.farmfamily.ui.login.User
import com.baoyz.swipemenulistview.SwipeMenuCreator
import com.baoyz.swipemenulistview.SwipeMenuItem
import com.baoyz.swipemenulistview.SwipeMenuListView
import kotlinx.android.synthetic.main.fragment_users.*
import kotlinx.android.synthetic.main.fragment_vegetable.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*
import kotlin.collections.ArrayList

class UsersFragment : Fragment() {

    private lateinit var database: Database

    var users: ArrayList<User> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_users, container, false)

        var listViewUser = root.findViewById(R.id.list_view_users) as SwipeMenuListView

        users = getListUser()

        listViewUser.adapter = this.activity?.let { UsersAdapter(it, users) }

        //swipemenulistview
        val creator = SwipeMenuCreator { menu ->
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
        listViewUser.setMenuCreator(creator)
        listViewUser.setOnMenuItemClickListener { position, menu, index ->
            when (index) {
                0 -> {
                    // build alert dialog
                    val dialogBuilder = AlertDialog.Builder(activity)

                    // set message of alert dialog
                    dialogBuilder.setMessage(getString(R.string.delete_title_all_vi))
                        // if the dialog is cancelable
                        .setCancelable(false)
                        // positive button text and action
                        .setPositiveButton(getString(R.string.yes_vi), DialogInterface.OnClickListener { dialog, id ->
                            removeUser(position)
                        })
                        // negative button text and action
                        .setNegativeButton(getString(R.string.quit_vi), DialogInterface.OnClickListener { dialog, id ->
                            dialog.cancel()
                        })

                    // create dialog box
                    val alert = dialogBuilder.create()
                    // set title for alert dialog box
                    alert.setTitle(getString(R.string.delete_user_name_manager))
                    // show alert dialog
                    alert.show()
                }
            }// open
            // delete
            // false : close the menu; true : not close the menu
            false
        }

        //button save
        //button save
        var viewUsersBtnPrint = root.findViewById(R.id.users_btn_print) as Button
        viewUsersBtnPrint.setOnClickListener {
            var dialog = Dialog(activity)
            // get id by position
            dialog.setContentView(R.layout.custom_dialog_screenhot)
            dialog.setTitle(getString(R.string.choose_mode_for_device))
            var imageView = dialog.findViewById(R.id.imageView) as ImageView
            var button = dialog.findViewById(R.id.detail_mode_btn_screen_ok) as Button

            //handler
            val activityView = layoutUsers_screenshot.rootView
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
        return root
    }

    /**
     * the method to display batch
     */
    private fun getListUser(): ArrayList<User> {
        database = Database(activity)
        users = database.getAllUser()
        var listUser = ArrayList<User>()
        if (users.isNullOrEmpty() || users.size == 1) {
            Toast.makeText(activity, getString(R.string.list_user_is_empty), Toast.LENGTH_LONG).show()
        }
        for (item in 0 until users.size){
            if(2 == users[item].roleId){
                listUser.add(users[item])
            }
        }
        return listUser
    }

    /**
     * the method to removeBatch
     */
    private fun removeUser(position: Int) {
        database = Database(activity)
        var vegId = database.deleteUser(users[position])
        users.remove(users[position])
        if (vegId != null) {
            Toast.makeText(
                activity,
                getString(R.string.deleted_data_success_vi),
                Toast.LENGTH_LONG
            ).show()
        }
        list_view_users.adapter = UsersAdapter(activity, users)
    }

    /**
     * the method to resume ( call when back stack)
     */
    override fun onResume() {
        super.onResume()
        users = getListUser()
        list_view_users.adapter = activity?.let { UsersAdapter(it, users) }
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