package com.appveg.farmfamily.ui.vegetable

import android.Manifest.permission.CAMERA
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.*
import androidx.core.app.ActivityCompat
import com.appveg.farmfamily.R
import com.appveg.farmfamily.ui.database.Database
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_add_device.*
import kotlinx.android.synthetic.main.activity_add_vegetable.*
import java.io.*
import java.text.SimpleDateFormat
import java.util.*


class AddVegetableActivity : AppCompatActivity() {
    private val activity = this@AddVegetableActivity

    private var REQUEST_CODE_CAMERA: Int = 123
    private var REQUEST_CODE_FOLDER: Int = 124

    private lateinit var database: Database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_vegetable)


        actionButton()
        actionButtonForImageView()


    }

    /**
     * This method to select image default
     */
    private fun actionButtonForImageView() {
        add_image1.setOnClickListener {
            var veg_img = R.drawable.xalach
            var veg_name_1 = "Xà Lách"
            this.selected_image.setImageResource(veg_img)
            this.veg_name.setText(veg_name_1)
            this.veg_name.setSelection(veg_name.text.length)
        }
        add_image2.setOnClickListener {
            var veg_img = R.drawable.raucai
            var veg_name_2 = "Cải Thìa"
            this.selected_image.setImageResource(veg_img)
            this.veg_name.setText(veg_name_2)
            this.veg_name.setSelection(veg_name.text.length)
        }
        add_image3.setOnClickListener {
            var veg_name_3 = "Xúp Lơ"
            var veg_img = R.drawable.xuplo
            this.selected_image.setImageResource(veg_img)
            this.veg_name.setText(veg_name_3)
            this.veg_name.setSelection(veg_name.text.length)
        }
        add_image4.setOnClickListener {
            var veg_name_4 = "Rau muống"
            var veg_img = R.drawable.raumuong
            this.selected_image.setImageResource(veg_img)
            this.veg_name.setText(veg_name_4)
            this.veg_name.setSelection(veg_name.text.length)
        }
        add_image5.setOnClickListener {
            var veg_name_5 = "Ớt đỏ"
            var veg_img = R.drawable.ot
            this.selected_image.setImageResource(veg_img)
            this.veg_name.setText(veg_name_5)
            this.veg_name.setSelection(veg_name.text.length)
        }
        add_image6.setOnClickListener {
            var add_image6 = "Dưa leo"
            var veg_img = R.drawable.dualeo
            this.selected_image.setImageResource(veg_img)
            this.veg_name.setText(add_image6)
            this.veg_name.setSelection(veg_name.text.length)
        }
        add_image7.setOnClickListener {
            var add_image7 = "Bí đỏ"
            var veg_img = R.drawable.bido
            this.selected_image.setImageResource(veg_img)
            this.veg_name.setText(add_image7)
            this.veg_name.setSelection(veg_name.text.length)
        }

        add_image8.setOnClickListener {
            var add_image8 = "Cà chua"
            var veg_img = R.drawable.cachua
            this.selected_image.setImageResource(veg_img)
            this.veg_name.setText(add_image8)
            this.veg_name.setSelection(veg_name.text.length)
        }
    }

    private fun actionButton() {
        /*event add veg*/
        add_veg.setOnClickListener {
            addVegetable()
        }

        /*event call camera*/
        add_camera.setOnClickListener {
            getImageFromCamera()
        }
        /*event call image*/
        add_image.setOnClickListener {
            getImageFromGallery()
        }

        /*event cancel*/
        cancel_action_veg.setOnClickListener {
            activity.finish()
        }

    }

    private fun getImageFromCamera() {
        ActivityCompat.requestPermissions(activity, arrayOf(CAMERA), REQUEST_CODE_CAMERA)
    }

    private fun getImageFromGallery() {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(READ_EXTERNAL_STORAGE),
            REQUEST_CODE_FOLDER
        )
    }

    private fun addVegetable() {
        database = Database(activity)
        var vegName = veg_name.text.toString().trim()

        var checkVegName = checkVegName(vegName)
        /*format date*/
        val current = Calendar.getInstance().time
        val formatter: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
        val formatted: String = formatter.format(current)

//        var bitmapDrawable: BitmapDrawable = selected_image.drawable as BitmapDrawable
//        var bitmap: Bitmap = bitmapDrawable.bitmap
//        var byteArray: ByteArrayOutputStream = ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArray)

        var bitmap: Bitmap = (selected_image.drawable as BitmapDrawable).bitmap

        var uri = saveImageSDcard(bitmap)

        if (checkVegName) {
            var vegetable = Vegetable(null, vegName, uri.toString(), "admin", formatted)
            var id = database.addVegImageDefault(vegetable)
            if (id != null) {
                Toast.makeText(this, getString(R.string.insert_data_success_vi), Toast.LENGTH_LONG)
                    .show()
                activity.finish()
            }

        } else {
            Toast.makeText(this, getString(R.string.insert_data_fail_vi), Toast.LENGTH_LONG).show()
        }

    }

    /**
     * This method is to batch name
     */
    private fun checkVegName(check: String): Boolean {
        database = Database(activity)
        var vegs = database.findAllVegetable()
        if (check.isEmpty()) {
            veg_name.error = getString(R.string.error_empty_common)
            return false
        } else {
            for (i in 0 until vegs.size) {
                if (check.equals(vegs[i].vegName, true)) {
                    veg_name.error = getString(R.string.error_veg_exist)
                    return false
                }
            }
        }
        return true
    }

    /**
     * This method is to batch name
     */
    private fun checkVegImage(check: ByteArray): Boolean {
        if (check.isEmpty()) {
            Toast.makeText(this, R.string.image_no_select_vi, Toast.LENGTH_LONG).show()
            return false
        }
        Log.d("CAT", check.size.toString())
        return true
    }

    /**
     * This method is to get permissions
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE_CAMERA -> {

                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(intent, REQUEST_CODE_CAMERA)
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show()
                }
                return
            }

            else -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.type = "image/*"
                    startActivityForResult(intent, REQUEST_CODE_FOLDER)
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    /**
     * This method is to set data for image view
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_CAMERA && data != null) {
            val bitmap = data.extras!!.get("data") as Bitmap
            selected_image.setImageBitmap(bitmap)
        }
        if (requestCode == REQUEST_CODE_FOLDER && resultCode == Activity.RESULT_OK && data != null) {
            val uri = data.data
            try {
//                val inputStream = contentResolver.openInputStream(uri!!)
//                val bitmap = BitmapFactory.decodeStream(inputStream)
                Glide.with(this)
                    .load(uri)
                    .into(selected_image)

                //selected_image.setImageBitmap(bitmap)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

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

}
