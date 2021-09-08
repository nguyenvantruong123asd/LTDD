package com.appveg.farmfamily.ui.share

import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.appveg.farmfamily.R
import com.appveg.farmfamily.ui.Screenshot
import com.appveg.farmfamily.ui.database.Database
import com.baoyz.swipemenulistview.SwipeMenuCreator
import com.baoyz.swipemenulistview.SwipeMenuItem
import kotlinx.android.synthetic.main.activity_substance_mass.*
import kotlinx.android.synthetic.main.fragment_vegetable.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*
import kotlin.collections.ArrayList

class SubstanceMassActivity : AppCompatActivity() {
    var activity = this@SubstanceMassActivity

    private lateinit var database: Database

    var substances: ArrayList<Substance> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_substance_mass)
        substances = getListSubstance()
        list_view_substance.adapter = SubstanceMassAdapter(activity, substances)


        list_view_substance.setOnItemClickListener { adapterView, view, i, l ->
            var gardenId = substances[i].gardenId!!
            var substanceMassId = substances[i].substanceMassId
            var intent: Intent = Intent(activity, SubstanceMassDetailActivity::class.java)
            intent.putExtra("garden_id",gardenId)
            intent.putExtra("substance_mass_id",substanceMassId)
            startActivity(intent)
        }

        view_substance_btn_add.setOnClickListener {
            var gardenId = getDataFromItent()
            var intent: Intent = Intent(activity, AddSubstanceMassActivity::class.java)
            intent.putExtra("garden_id",gardenId)
            startActivity(intent)
        }

        //button save
        var viewSubstancePrint = findViewById<Button>(R.id.view_substance_btn_print)
        viewSubstancePrint.setOnClickListener {
            var dialog = Dialog(activity)
            // get id by position
            dialog.setContentView(R.layout.custom_dialog_screenhot)
            dialog.setTitle(getString(R.string.choose_mode_for_device))
            var imageView = dialog.findViewById(R.id.imageView) as ImageView
            var button = dialog.findViewById(R.id.detail_mode_btn_screen_ok) as Button

            //handler
            val activityView = layoutDevice_screenshot
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
        return bundle.get("garden_id") as Int
    }


    /**
     * This method is to get list category
     */
    private fun getListSubstance(): ArrayList<Substance> {
        database = Database(activity)
        var gardenId = getDataFromItent()
        var substanceList = database.findAllSubstanceByGardenId(gardenId)
        if (substanceList.isNullOrEmpty()) {
            Toast.makeText(activity, getString(R.string.substance_empty_vi), Toast.LENGTH_LONG).show()
        }
        return substanceList
    }

    /**
     * the method to resume ( call when back stack)
     */
    override fun onResume() {
        super.onResume()
        substances = getListSubstance()
        list_view_substance.adapter = SubstanceMassAdapter(activity, substances)
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
