package com.appveg.farmfamily.ui.share

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.math.MathUtils
import com.appveg.farmfamily.R
import com.appveg.farmfamily.ui.database.Database
import com.appveg.farmfamily.ui.device.Device
import com.appveg.farmfamily.ui.device.DeviceDetail
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_edit_device.*
import kotlinx.android.synthetic.main.activity_edit_device.positionSpinner_edit
import kotlinx.android.synthetic.main.activity_edit_substance_mass.*
import kotlinx.android.synthetic.main.activity_edit_vegetable.*
import kotlinx.android.synthetic.main.activity_them_dot_san_luong.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.ceil

class EditSubstanceMassDetailActivity : AppCompatActivity() {
    var activity = this@EditSubstanceMassDetailActivity
    private lateinit var database: Database

    private var selected: String? = ""
    private var substanceDetailCategoryId: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_substance_mass)
        //init
        initDataEdit()
        //action button
        actionButton()
        //spinner hien thi danh sach rau
        val listSensor = getListCategory()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listSensor)
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1)
        positionSpinner_substance_edit.adapter = adapter

        positionSpinner_substance_edit.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                // either one will work as well
                //val item = parent.getItemAtPosition(position) as String
                selected = adapter.getItem(position)
                substanceDetailCategoryId = position

            }
        }
    }

    private fun actionButton() {
        /*event add veg*/
        btn_substance_edit.setOnClickListener {
            updateSubstanceAndSubstanceDetail()
        }

//        /*event call camera*/
//        add_camera_device_edit.setOnClickListener {
//            getImageFromCamera()
//        }
//        /*event call image*/
//        add_image_device_edit.setOnClickListener {
//            getImageFromGallery()
//        }

        /*event cancel*/
        cancel_action_substance_edit.setOnClickListener {
            activity.finish()
        }

    }

    /**
     * This method is to remove data
     */
    private fun initDataEdit() {
        database = Database(activity)

        val substanceDetailId: Int = getDataFromItent()

        // gán lại id để tý update data
        var substanceDetail: SubstanceDetail = database.findAllSubstanceDetailBySubstanceId(substanceDetailId)

//        var imageBitmap: ByteArray? = device.deviceImg
////        var bitmap: Bitmap = BitmapFactory.decodeByteArray(imageBitmap, 0, imageBitmap!!.size)

        Glide.with(activity)
            .load(Uri.fromFile(File(substanceDetail.substanceMassDetailImage)))
            .into(selected_image_substance_edit)

        this.substance_detail_num_edit.setText(substanceDetail.substanceMassDetailNum)
        this.substance_detail_num_edit.setSelection(substance_detail_num_edit.text.length)
        this.positionSpinner_substance_edit.setSelection(substanceDetail.substanceMassDetailCategory.toInt())
    }
    /**
     * This method is to remove data
     */
    private fun updateSubstanceAndSubstanceDetail() {
        database = Database(activity)
        var substanceDetailNum = substance_detail_num_edit.text.toString().trim()

        val substanceDetailId = getDataFromItent()

        var substanceDetail: SubstanceDetail = database.findAllSubstanceDetailBySubstanceId(substanceDetailId)
        var substance = database.findAllSubstanceBySubstanceId(substanceDetail.substanceMassId)

        var totalTemp = substance.totalSubstanceMass.toDouble() - substanceDetail.substanceMassDetailNum.toDouble()
        /*format date*/
        val current = Calendar.getInstance().time
        val formatter: SimpleDateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
        val formatted: String = formatter.format(current)

        val checkSubstanceName = checkSubstanceName(substanceDetailNum)

        if (checkSubstanceName) {
            totalTemp += substanceDetailNum.toDouble()
            var substance = Substance(substanceDetail.substanceMassId, (ceil(totalTemp*10) /10).toString())
            database.updateSubstance(substance)
            var temp = SubstanceDetail(substanceDetailId,(ceil(substanceDetailNum.toDouble()*10) /10).toString(),formatted)
            database.updateSubstanceDetail(temp)
            Toast.makeText(this, getString(R.string.update_data_success_vi), Toast.LENGTH_LONG).show()
            activity.finish()
        } else {
            Toast.makeText(this, getString(R.string.update_data_fail_vi), Toast.LENGTH_LONG).show()
        }

    }


    /**
     * the method to get data from intent
     */
    private fun getDataFromItent(): Int {
        val bundle: Bundle = intent.extras
        return bundle.get("substance_mass_detail_id") as Int
    }

    /**
     * This method is to get list category
     */
    private fun getListCategory(): ArrayList<String> {
        var substanceCategorys: ArrayList<String> = ArrayList()
        substanceCategorys.add("DD dinh dưỡng")
        substanceCategorys.add("DD giảm pH")
        substanceCategorys.add("DD tăng pH")
        return substanceCategorys
    }


    /**
     * This method is to batch name
     */
    private fun checkSubstanceName(check: String): Boolean {
        if (check.isEmpty()) {
            substance_detail_num_edit.error = getString(R.string.error_empty_common)
            return false
        }
        return true
    }


}
