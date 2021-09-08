package com.appveg.farmfamily.ui.device_catogory

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.appveg.farmfamily.R
import com.appveg.farmfamily.ui.database.Database
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_edit_device_category.*
import kotlinx.android.synthetic.main.activity_edit_device_category.cancel_action_device_edit
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

class EditDeviceCategory : AppCompatActivity() {

    private val activity = this@EditDeviceCategory
    private lateinit var database: Database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_device_category)

        // function init data for edit
        initDataEdit()
        // action for button and image
        actionButton()
        // action for image default
        actionButtonForImageView()
    }

    /**
     * This method to select image default
     */
    private fun actionButtonForImageView() {
        device_category_image1_edit.setOnClickListener {
            var dcategory_img= R.drawable.cambienmua
            var dcatrgory_name1 = "Cảm biến "
            this.selected_device_category_image_edit.setImageResource(dcategory_img)
            this.device_category_name_edit.setText(dcatrgory_name1)
            this.device_category_name_edit.setSelection(device_category_name_edit.text.length)
        }
//        device_category_image2_edit.setOnClickListener {
//            var dcategory_img= R.drawable.quat
//            var dcatrgory_name2 = "Quạt"
//            this.selected_device_category_image_edit.setImageResource(dcategory_img)
//            this.device_category_name_edit.setText(dcatrgory_name2)
//            this.device_category_name_edit.setSelection(device_category_name_edit.text.length)
//        }
        device_category_image3_edit.setOnClickListener {
            var dcategory_img = "Máy bơm"
            var dcatrgory_name3= R.drawable.maybo2
            this.selected_device_category_image_edit.setImageResource(dcatrgory_name3)
            this.device_category_name_edit.setText(dcategory_img)
            this.device_category_name_edit.setSelection(device_category_name_edit.text.length)
        }
        device_category_image4_edit.setOnClickListener {
            var dcategory_img = "Bóng đèn"
            var dcatrgory_name4= R.drawable.bongden
            this.selected_device_category_image_edit.setImageResource(dcatrgory_name4)
            this.device_category_name_edit.setText(dcategory_img)
            this.device_category_name_edit.setSelection(device_category_name_edit.text.length)
        }
    }

    private fun actionButton() {
        /*event add device category*/
        add_device_category_edit.setOnClickListener{
            editDeviceCategory()
        }
        /*event cancel*/
        cancel_action_device_edit.setOnClickListener {
            activity.finish()
        }

    }

    private fun editDeviceCategory() {
        database = Database(activity)
        var dcategory_name = device_category_name_edit.text.toString().trim()

        var checkDCategoryName = checkDCategoryName(dcategory_name)
        /*format date*/
        val current = Calendar.getInstance().time
        val formatter: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
        val formatted: String = formatter.format(current)

        val dcategory_id = getDataFromItent()

//        var bitmapDrawable: BitmapDrawable = selected_device_category_image_edit.drawable as BitmapDrawable
//        var bitmap: Bitmap = bitmapDrawable.bitmap
//        var byteArray: ByteArrayOutputStream = ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.PNG,0,byteArray)
        var bitmap: Bitmap = (selected_device_category_image_edit.drawable as BitmapDrawable).bitmap

        var uri = saveImageSDcard(bitmap)

//        var image: ByteArray = byteArray.toByteArray()
//        var checkDCategoryImg = checkDCategoryImage(image)

        if(checkDCategoryName){
            var dcategory = DeviceCategory(dcategory_id, dcategory_name,uri.toString(),formatted)
            database.updateDeviceCategoryImageDefault(dcategory)
            Toast.makeText(this,getString(R.string.update_data_success_vi),Toast.LENGTH_LONG).show()
            activity.finish()
        }else{
            Toast.makeText(this,getString(R.string.update_data_fail_vi), Toast.LENGTH_LONG).show()
        }

    }

    /**
     * This method is to batch name
     */
    private fun checkDCategoryName(check: String): Boolean {
        if (check.isEmpty()) {
            device_category_name_edit.error = getString(R.string.error_empty_common)
            return false
        }
        return true
    }

    /**
     * the method to get data from intent
     */
    private fun getDataFromItent(): Int {
        val bundle: Bundle = intent.extras
        return bundle.get("device_category_id") as Int
    }


    /**
     * This method is to remove data
     */
    private fun initDataEdit() {
        val dcategoryId: Int = getDataFromItent()

        // gán lại id để tý update data
        var dcategory: DeviceCategory = getDCategoryById(dcategoryId)

        Glide.with(activity)
            .load(Uri.fromFile(File(dcategory.dcategoryImg)))
            .into(selected_device_category_image_edit)

        this.device_category_name_edit.setText(dcategory.dcategoryName)
        this.device_category_name_edit.setSelection(device_category_name_edit.text.length)
    }

    /**
     * the method to get batch by id
     */
    private fun getDCategoryById(dcategory_id: Int) : DeviceCategory{
        database = Database(activity)
        var dcategory : DeviceCategory = DeviceCategory()
        if(dcategory_id != null){
            dcategory = database.findDeviceCategoryId(dcategory_id)
        }
        return dcategory
    }

    private fun saveImageSDcard(bitmap: Bitmap): Uri? {
        // path to sd card
        var file = File(filesDir, "Images")
        // create a folder
        if (!file.exists()) {
            file.mkdir()
        }

        file = File(file, "${UUID.randomUUID()}.png")

        var outputStream: OutputStream? = null

        try {
            outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream)
            outputStream.flush()
            outputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return Uri.parse(file.absoluteFile.toString())
    }

}