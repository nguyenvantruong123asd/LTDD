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
import com.appveg.farmfamily.R
import com.appveg.farmfamily.ui.database.Database
import com.google.android.material.math.MathUtils
import kotlinx.android.synthetic.main.activity_add_substance_mass.*
import kotlinx.android.synthetic.main.activity_them_dot_san_luong.positionSpinner
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.ceil

class AddSubstanceMassActivity : AppCompatActivity() {

    var activity = this@AddSubstanceMassActivity
    private var selected: String? = ""
    private var substanceCategoryId: Int = -1

    private lateinit var database: Database


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_substance_mass)
        // action button
        actionButtonForImageView()
        actionButton()

        //spinner hien thi danh sach rau
        val listSensor = getListCategory()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listSensor)
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1)
        positionSpinner.adapter = adapter

        positionSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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
                substanceCategoryId = position

            }
        }
    }
    /**
     * This method to select image default
     */
    private fun actionButtonForImageView() {
        add_image_substance_1.setOnClickListener {
            var substanceImg = R.drawable.dungichdd
            this.selected_image_substance.setImageResource(substanceImg)
            this.positionSpinner.setSelection(0)
        }
        add_image_substance_2.setOnClickListener {
            var substanceImg = R.drawable.giamph
            this.selected_image_substance.setImageResource(substanceImg)
            this.positionSpinner.setSelection(1)
        }
        add_image_substance_3.setOnClickListener {
            var substanceImg = R.drawable.tangph
            this.selected_image_substance.setImageResource(substanceImg)
            this.positionSpinner.setSelection(2)
        }

    }

    private fun actionButton() {
        /*event add veg*/
        btn_substance_add.setOnClickListener {
            addSubtanceAndSubstanceDetail()
        }

        /*event cancel*/
        cancel_action_substance.setOnClickListener {
            activity.finish()
        }


    }
    /**
     * This method is to get list category
     */
    private fun addSubtanceAndSubstanceDetail() {
        database = Database(activity)
        var substanceNum = substance_num.text.toString().trim()

        var substanceCategoryId = substanceCategoryId
        var substanceName = selected.toString()

        var checkSubstanceNum = checkSubstanceNum(substanceNum)

        var gardenId = getDataFromItent()
        /*format date*/
        val current = Calendar.getInstance().time
        val formatter: SimpleDateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
        val formatted: String = formatter.format(current)

        var bitmap: Bitmap = (selected_image_substance.drawable as BitmapDrawable).bitmap

        var uri = saveImageSDcard(bitmap)

        if (checkSubstanceNum) {
            var substance: Substance
            var listSubstance = getListSubstance()
            var exits = false
            var substanceTempId: Int = -1
            var totalTempSubstance = 0.0
            if (!listSubstance.isNullOrEmpty()) {
                for (i in 0 until listSubstance.size) {
                    if (listSubstance[i].substanceMassName == substanceName) {
                        exits = true
                        substanceTempId = listSubstance[i].substanceMassId!!
                        totalTempSubstance = listSubstance[i].totalSubstanceMass.toDouble()
                    }
                }
            }
            // have two case
            if (!exits) {
                totalTempSubstance += substanceNum.toDouble()
                substance = Substance(null,substanceName,uri.toString(),(ceil(totalTempSubstance*10) /10).toString(),substanceCategoryId.toString(),gardenId)
                var tempId = database.addSubstance(substance)
                if (tempId != null) {
                    var substanceDetail = SubstanceDetail(
                        null,
                        substanceName,
                        uri.toString(),
                        (ceil(substanceNum.toDouble()*10) /10).toString(),
                        substanceCategoryId.toString(),
                        tempId.toInt(),
                        gardenId,
                        "admin",
                        formatted
                    )
                    database.addSubstanceDetail(substanceDetail)
                }
                Toast.makeText(this, getString(R.string.insert_data_success_vi), Toast.LENGTH_LONG)
                    .show()
                activity.finish()
            } else {
                totalTempSubstance += substanceNum.toDouble()
                substance = Substance(substanceTempId,(ceil(totalTempSubstance*10) /10).toString())
                database.updateSubstance1(substance)
                var substanceDetail = SubstanceDetail(
                    null,
                    substanceName,
                    uri.toString(),
                    (ceil(substanceNum.toDouble()*10) /10).toString(),
                    substanceCategoryId.toString(),
                    substanceTempId,
                    gardenId,
                    "admin",
                    formatted
                )
                database.addSubstanceDetail(substanceDetail)

                Toast.makeText(this, getString(R.string.insert_data_success_vi), Toast.LENGTH_SHORT)
                    .show()
                activity.finish()
            }

        } else {
            Toast.makeText(this, getString(R.string.insert_data_fail_vi), Toast.LENGTH_SHORT).show()
        }

    }

    /**
     * This method is to batch name
     */
    private fun checkSubstanceNum(check: String): Boolean {
        if (check.isEmpty()) {
            substance_num.error = getString(R.string.error_empty_common)
            return false
        }
        return true
    }


    /**
     * save image to sd card
     */
    private fun saveImageSDcard(bitmap: Bitmap) : Uri? {
        // path to sd card

        var file = File(filesDir, "Images" )
        // create a folder
        if(!file.exists()){
            file.mkdir()
        }


        file = File(file, "${UUID.randomUUID()}.png")

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

    /**
     * This method is to get list category
     */
    private fun getListSubstance(): ArrayList<Substance> {
        database = Database(activity)
        var gardenId = getDataFromItent()
        return database.findAllSubstanceByGardenId(gardenId)
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
     * the method to get data from intent
     */
    private fun getDataFromItent(): Int {
        val bundle: Bundle = intent.extras
        return bundle.get("garden_id") as Int
    }

}
