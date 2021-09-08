package com.appveg.farmfamily.ui.garden

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.appveg.farmfamily.R
import com.appveg.farmfamily.ui.database.Database
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_sua_khu_vuon.*
import kotlinx.android.synthetic.main.activity_them_khu_vuon.add_image_garden_1
import kotlinx.android.synthetic.main.activity_them_khu_vuon.add_image_garden_2
import kotlinx.android.synthetic.main.activity_them_khu_vuon.add_image_garden_3
import kotlinx.android.synthetic.main.activity_them_khu_vuon.add_image_garden_4
import kotlinx.android.synthetic.main.activity_them_khu_vuon.add_image_garden_5
import kotlinx.android.synthetic.main.activity_them_khu_vuon.add_image_garden_6
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

class SuaKhuVuonActivity : AppCompatActivity() {

    private val activity = this@SuaKhuVuonActivity

    private var REQUEST_CODE_CAMERA: Int = 123
    private var REQUEST_CODE_FOLDER: Int = 124

    private lateinit var database: Database
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sua_khu_vuon)
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
        add_image_garden_1.setOnClickListener {
            var garden_img= R.drawable.kv2
            var garden_name_1 = "Khu vườn 1"
            this.selected_garden_image_edit.setImageResource(garden_img)
            this.garden_name_edit.setText(garden_name_1)
            this.garden_name_edit.setSelection(garden_name_edit.text.length)
        }
        add_image_garden_2.setOnClickListener {
            var garden_img= R.drawable.kv3
            var garden_name_2 = "Khu vườn 2"
            this.selected_garden_image_edit.setImageResource(garden_img)
            this.garden_name_edit.setText(garden_name_2)
            this.garden_name_edit.setSelection(garden_name_edit.text.length)
        }
        add_image_garden_3.setOnClickListener {
            var garden_name_3 = "Khu vườn 3"
            var garden_img= R.drawable.kv4
            this.selected_garden_image_edit.setImageResource(garden_img)
            this.garden_name_edit.setText(garden_name_3)
            this.garden_name_edit.setSelection(garden_name_edit.text.length)
        }

        add_image_garden_4.setOnClickListener {
            var garden_img= R.drawable.kv5
            var garden_name_4 = "Khu vườn 1"
            this.selected_garden_image_edit.setImageResource(garden_img)
            this.garden_name_edit.setText(garden_name_4)
            this.garden_name_edit.setSelection(garden_name_edit.text.length)
        }
        add_image_garden_5.setOnClickListener {
            var garden_img= R.drawable.kv6
            var garden_name_5 = "Khu vườn 2"
            this.selected_garden_image_edit.setImageResource(garden_img)
            this.garden_name_edit.setText(garden_name_5)
            this.garden_name_edit.setSelection(garden_name_edit.text.length)
        }
        add_image_garden_6.setOnClickListener {
            var garden_name_6 = "Khu vườn 3"
            var garden_img= R.drawable.kv7
            this.selected_garden_image_edit.setImageResource(garden_img)
            this.garden_name_edit.setText(garden_name_6)
            this.garden_name_edit.setSelection(garden_name_edit.text.length)
        }
    }

    private fun actionButton() {
        /*event add garden*/
        btn_edit_garden.setOnClickListener{
            updateGarden()
        }

        /*event call camera*/
        edit_garden_camera.setOnClickListener {
            getImageFromCamera()
        }
        /*event call image*/
        edit_garden_image.setOnClickListener {
            getImageFromGallery()
        }
        /*event cancel*/
        cancel_action_garden_edit.setOnClickListener {
            activity.finish()
        }
    }

    private fun getImageFromCamera() {
        ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.CAMERA),REQUEST_CODE_CAMERA)
    }

    private fun getImageFromGallery() {
        ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),REQUEST_CODE_FOLDER)
    }
    private fun updateGarden() {
        database = Database(activity)
        var garden_name = garden_name_edit.text.toString().trim()

        var checkGardenName = checkGardenName(garden_name)
        /*format date*/
        val current = Calendar.getInstance().time
        val formatter: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
        val formatted: String = formatter.format(current)

        val garden_id = getDataFromItent()

//        var bitmapDrawable: BitmapDrawable = selected_garden_image_edit.drawable as BitmapDrawable
//        var bitmap: Bitmap = bitmapDrawable.bitmap
//        var byteArray: ByteArrayOutputStream = ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.PNG,0,byteArray)
        var bitmap: Bitmap = (selected_garden_image_edit.drawable as BitmapDrawable).bitmap

        var uri = saveImageSDcard(bitmap)
//        var image: ByteArray = byteArray.toByteArray()
//        var checkGardenImage = checkGardenImage(image)

        if(checkGardenName){
            var garden = Garden(garden_id, garden_name,uri.toString(),formatted)
            database.updateGardenImageDefault(garden)

            Toast.makeText(this,getString(R.string.update_data_success_vi), Toast.LENGTH_LONG).show()
//            var fragmentAdapter : GalleryFragment = GalleryFragment()
//            // hide activity
//            EditGarden_Function.visibility = View.GONE
//            //action bar
//            activity.title = "Quản lý khu vườn"
//            supportFragmentManager.beginTransaction().replace(R.id.edit_fragmentContainer_garden, fragmentAdapter).commit()
            activity.finish()

        }else{
            Toast.makeText(this,getString(R.string.update_data_fail_vi), Toast.LENGTH_LONG).show()
        }

    }
    /**
     * This method is to batch name
     */
    private fun checkGardenName(check: String): Boolean {
        database = Database(activity)
        if (check.isEmpty()) {
            garden_name_edit.error = getString(R.string.error_empty_common)
            return false
        }
        return true
    }
    /**
     * This method is to batch name
     */
    private fun checkGardenImage(check: ByteArray): Boolean {
        if (check.isEmpty()) {
            Toast.makeText(this,R.string.image_no_select_vi, Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }
    /**
     * This method is to get permissions
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE_CAMERA -> {

                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(intent, REQUEST_CODE_CAMERA)
                } else {
                    Toast.makeText(this,"Permission Denied", Toast.LENGTH_LONG).show()
                }
                return
            }

            else -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.type = "image/*"
                    startActivityForResult(intent, REQUEST_CODE_FOLDER)
                } else {
                    Toast.makeText(this,"Permission Denied", Toast.LENGTH_LONG).show()
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
            selected_garden_image_edit.setImageBitmap(bitmap)
        }
        if (requestCode == REQUEST_CODE_FOLDER && resultCode == Activity.RESULT_OK && data != null) {
            val uri = data.data
            try {
//                val inputStream = contentResolver.openInputStream(uri!!)
//                val bitmap = BitmapFactory.decodeStream(inputStream)
                Glide.with(this)
                    .load(uri)
                    .into(selected_garden_image_edit)

                //selected_image.setImageBitmap(bitmap)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
    /**
     * the method to get data from intent
     */
    private fun getDataFromItent(): Int {
        val bundle: Bundle = intent.extras
        return bundle.get("garden_id") as Int
    }

    /**
     * This method is to remove data
     */
    private fun initDataEdit() {
        val gardenId: Int = getDataFromItent()

        // gán lại id để tý update data
        var garden: Garden = getGardenById(gardenId)

        // load photo
        Glide.with(activity)
            .load(Uri.fromFile(File(garden.gardenImage)))
            .into(selected_garden_image_edit)

        this.garden_name_edit.setText(garden.gardenName)
        this.garden_name_edit.setSelection(garden_name_edit.text.length)
    }

    /**
     * the method to get batch by id
     */
    fun getGardenById(garden_id: Int) : Garden {
        database = Database(activity)
        var garden : Garden = Garden()
        if(garden_id != null){
            garden = database.findGardenById(garden_id)
        }
        return garden
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
