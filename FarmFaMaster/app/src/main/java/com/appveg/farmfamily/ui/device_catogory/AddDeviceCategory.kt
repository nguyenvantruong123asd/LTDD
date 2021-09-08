package com.appveg.farmfamily.ui.device_catogory

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.appveg.farmfamily.R
import com.appveg.farmfamily.ui.database.Database
import kotlinx.android.synthetic.main.activity_add_device.*
import kotlinx.android.synthetic.main.activity_add_device_category.*
import kotlinx.android.synthetic.main.activity_add_vegetable.*
import kotlinx.android.synthetic.main.activity_edit_device_category.*
import kotlinx.android.synthetic.main.fragment_device_category.*
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

class AddDeviceCategory : AppCompatActivity() {

    private val activity = this@AddDeviceCategory


    private lateinit var database: Database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_device_category)


        actionButton()
        actionButtonForImageView()
    }

    private fun actionButton() {
        /*event add veg*/
        btn_add_category.setOnClickListener {
            addDeviceCategory()
        }
        /*event cancel*/
        cancel_action_device_category.setOnClickListener {
            activity.finish()
        }

    }

    /**
     * This method to select image default
     */
    private fun actionButtonForImageView() {
        device_category_image1.setOnClickListener {
            var dcategory_img = R.drawable.cambienmua
            var dcatrgory_name1 = "Cảm biến "
            this.selected_device_category_image.setImageResource(dcategory_img)
            this.device_category_name.setText(dcatrgory_name1)
            this.device_category_name.setSelection(device_category_name.text.length)
        }
        device_category_image2.setOnClickListener {
            var dcategory_img = R.drawable.quat
            var dcatrgory_name2 = "Quạt"
            this.selected_device_category_image.setImageResource(dcategory_img)
            this.device_category_name.setText(dcatrgory_name2)
            this.device_category_name.setSelection(device_category_name.text.length)
        }
        device_category_image3.setOnClickListener {
            var dcategory_img = "Máy bơm"
            var dcatrgory_name3 = R.drawable.maybo2
            this.selected_device_category_image.setImageResource(dcatrgory_name3)
            this.device_category_name.setText(dcategory_img)
            this.device_category_name.setSelection(device_category_name.text.length)
        }
        device_category_image4.setOnClickListener {
            var dcategory_img = "Bóng đèn"
            var dcatrgory_name4 = R.drawable.bongden
            this.selected_device_category_image.setImageResource(dcatrgory_name4)
            this.device_category_name.setText(dcategory_img)
            this.device_category_name.setSelection(device_category_name.text.length)
        }
    }

    /**
     * This method is to batch name
     */
    private fun checkDeviceCategoryName(check: String): Boolean {
        database = Database(activity)
        var deviceCategorys = database.findAllDeviceCategory()
        if (check.isEmpty()) {
            device_category_name.error = getString(R.string.error_empty_common)
            return false
        } else {
            for (i in 0 until deviceCategorys.size) {
                if (check.equals(deviceCategorys[i].dcategoryName, true)) {
                    device_category_name.error = getString(R.string.error_device_exist)
                    return false
                }
            }
        }
        return true
    }

    /**
     * This method is to batch name
     */
    private fun checkDeviceCategoryImg(check: ByteArray): Boolean {
        if (check.isEmpty()) {
            Toast.makeText(this, R.string.image_no_select_vi, Toast.LENGTH_LONG).show()
            return false
        }
        Log.d("CAT", check.size.toString())
        return true
    }

    private fun addDeviceCategory() {
        database = Database(activity)
        var device_category_name = device_category_name.text.toString().trim()

        var checkVegName = checkDeviceCategoryName(device_category_name)
        /*format date*/
        val current = Calendar.getInstance().time
        val formatter: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
        val formatted: String = formatter.format(current)

//        var bitmapDrawable: BitmapDrawable = selected_device_category_image.drawable as BitmapDrawable
//        var bitmap: Bitmap = bitmapDrawable.bitmap
//        var byteArray: ByteArrayOutputStream = ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.PNG,0,byteArray)

        var bitmap: Bitmap = (selected_device_category_image.drawable as BitmapDrawable).bitmap

        var uri = saveImageSDcard(bitmap)

//        var image: ByteArray = byteArray.toByteArray()
//        var checkDeviceCategoryImg = checkDeviceCategoryImg(image)

        if (checkVegName) {
            var dcategory = DeviceCategory(null, device_category_name, uri.toString(), "admin", formatted)
            var id = database.addDeviceCategoryImageDefault(dcategory)
            if (id != null) {
                Toast.makeText(this, getString(R.string.insert_data_success_vi), Toast.LENGTH_LONG)
                    .show()
                activity.finish()
            }
        } else {
            Toast.makeText(this, getString(R.string.insert_data_fail_vi), Toast.LENGTH_LONG).show()
        }
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
