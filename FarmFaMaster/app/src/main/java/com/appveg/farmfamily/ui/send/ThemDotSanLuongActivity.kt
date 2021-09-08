package com.appveg.farmfamily.ui.send


import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.appveg.farmfamily.R
import com.appveg.farmfamily.ui.database.Database
import com.appveg.farmfamily.ui.vegetable.Vegetable
import com.appveg.farmfamily.ui.vegetable.VegetableTemp
import kotlinx.android.synthetic.main.activity_them_dot_san_luong.*
import kotlinx.android.synthetic.main.activity_them_dot_san_luong.pickDateBD
import kotlinx.android.synthetic.main.activity_them_dot_san_luong.pickDateKT
import kotlinx.android.synthetic.main.activity_them_dot_san_luong.positionSpinner
import kotlinx.android.synthetic.main.activity_them_dot_san_luong.textViewPickKT
import kotlinx.android.synthetic.main.activity_them_dot_san_luong.textViewPickStart
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.ceil

class ThemDotSanLuongActivity : AppCompatActivity() {
    private val activity = this@ThemDotSanLuongActivity

    private lateinit var database: Database
    private lateinit var sendFragment: SendFragment

    private var veg_id: Long = -1
    private var selected: String? = ""

    private var listVeg: ArrayList<VegetableTemp> = ArrayList()

    private var sumQty: Double = 0.0

    private var listVegetable: ArrayList<Vegetable> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_them_dot_san_luong)

        /*event add data to lisview*/
        addSoSL.setOnClickListener {
            // add item
            addVegTemp()
            // recal total quantity
            sumQuantity()
        }
        /*event insert data to database and validate*/
        addQty.setOnClickListener {
            verifyFromSQLite()
        }
        /*event remove data for temp listview*/
        lv_themSL.setOnItemClickListener { adapterView, view, i, l ->
            // build alert dialog
            val dialogBuilder = AlertDialog.Builder(activity)

            // set message of alert dialog
            dialogBuilder.setMessage(getString(R.string.delete_title_all_vi))
                // if the dialog is cancelable
                .setCancelable(false)
                // positive button text and action
                .setPositiveButton(getString(R.string.yes_vi), DialogInterface.OnClickListener {
                        dialog, id -> removeDataListVeg(i)
                })
                // negative button text and action
                .setNegativeButton(getString(R.string.quit_vi), DialogInterface.OnClickListener {
                        dialog, id -> dialog.cancel()
                })

            // create dialog box
            val alert = dialogBuilder.create()
            // set title for alert dialog box
            alert.setTitle(getString(R.string.delete_batch_detail))
            // show alert dialog
            alert.show()
        }

        /*event cancel*/
        cancel_action.setOnClickListener {
//            var  intent: Intent  = Intent(activity, ChiTietDotSanLuongActivity::class.java)
//            intent.putExtra("garden_id",getDataFromItent())
            activity.finish()
//            startActivity(intent)
        }

//        /*event back*/
//        backQty.setOnClickListener {
//            var  intent: Intent  = Intent(activity, ChiTietDotSanLuongActivity::class.java)
//            intent.putExtra("garden_id",getDataFromItent())
//            activity.finish()
//            startActivity(intent)
//        }

        // get create date
        pickDateBD.setOnClickListener {
            val now = Calendar.getInstance()
            val datePicker = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    textViewPickStart.error = null
                    textViewPickStart.clearFocus()
                    textViewPickStart.setText("" + year + "-" + (month + 1) + "-" + dayOfMonth)
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
            )

            datePicker.show()
        }
        // get the end date
        pickDateKT.setOnClickListener {
            val now = Calendar.getInstance()
            val datePicker = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    textViewPickKT.error = null
                    textViewPickKT.clearFocus()
                    textViewPickKT.setText("" + year + "-" + (month + 1) + "-" + dayOfMonth)
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
            )

            datePicker.show()
        }

        //spinner hien thi danh sach rau
        val listRau = getListVegetable()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listRau)
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
                veg_id = id
            }
        }

    }
    //ket thuc fun onCreate

    /**
     * the method to insert data batch detail
     */
    private fun addVegTemp() {
        val quantityVegetable = txt_qtyVeg.text.toString()
        var vegetableTemp: VegetableTemp = VegetableTemp()
        var vegName = selected.toString()
        var checkSelectVeg = checkSelectVeg(veg_id.toInt())
        if(checkSelectVeg) {
            if (quantityVegetable.isNotBlank()) {
                if (listVeg.isNullOrEmpty()) {
                    vegetableTemp.vegName = selected.toString()
                    vegetableTemp.vegQty = (ceil(quantityVegetable.toDouble()*10) /10)
                    listVeg.add(vegetableTemp)
                } else {
                    for (i in 0 until listVeg.size) {
                        if (vegName == listVeg[i].vegName) {
                            var x: Double = listVeg[i].vegQty!!.toDouble()
                            x += quantityVegetable.toDouble()
                            vegetableTemp.vegName = listVeg[i].vegName
                            vegetableTemp.vegQty = (ceil(x*10) /10)
                            listVeg[i] = vegetableTemp
                        } else if (i == listVeg.size - 1) {
                            vegetableTemp.vegName = selected.toString()
                            vegetableTemp.vegQty = (ceil(quantityVegetable.toDouble()*10) /10)
                            listVeg.add(vegetableTemp)
                        }
                    }
                }
            } else {
                var vegNumber = 0.0
                if (listVeg.isNullOrEmpty()) {
                    vegetableTemp.vegName = selected.toString()
                    vegetableTemp.vegQty = (ceil(vegNumber*10) /10)
                    listVeg.add(vegetableTemp)
                } else {
                    for (i in 0 until listVeg.size) {
                        if (vegName == listVeg[i].vegName) {
                            var x: Double = listVeg[i].vegQty!!.toDouble()
                            x += vegNumber
                            vegetableTemp.vegName = listVeg[i].vegName
                            vegetableTemp.vegQty = (ceil(x*10) /10)
                            listVeg[i] = vegetableTemp
                        } else if (i == listVeg.size - 1) {
                            vegetableTemp.vegName = selected.toString()
                            vegetableTemp.vegQty = (ceil(vegNumber*10) /10)
                            listVeg.add(vegetableTemp)
                        }
                    }
                }
            }
        }
        //display list view
        lv_themSL.adapter = ThemAdapter(activity, listVeg)
    }

    /**
     * the method to sum total quantity
     */
    private fun sumQuantity() {
        sumQty = 0.0
        for (item in listVeg) {
            var x: Double = item.vegQty!!.toDouble()
            sumQty += x
        }
        totalQty.text = sumQty.toString().trim() + "/kg"
    }

    /**
     * This method is to validate the input text fields and verify add batch credentials from SQLite
     */
    private fun verifyFromSQLite() {
        database = Database(activity)
        var selectedStartDate = textViewPickStart.text.toString().trim()
        var selectedEndDate = textViewPickKT.text.toString().trim()
        var selectedBatchName = batchName.text.toString()
        var totalQty =(ceil(sumQty*10) /10).toString().trim()

        var gardenId = getDataFromItent()

        var garden = database.findGardenById(gardenId)

        var checkBatchName = checkBatchName(selectedBatchName)
        var checkDate = checkDate(selectedStartDate,selectedEndDate)


        var batch: Batch = Batch(
            null,
            garden.gardenImage,
            selectedBatchName,
            selectedEndDate,
            totalQty,
            gardenId,
            "admin",
            selectedStartDate
        )

        if (checkBatchName && checkDate) {
            /*format date*/
            val current = Calendar.getInstance().time
            val formatter: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
            val formatted: String = formatter.format(current)

            /*end format date*/
            try {
                var id: Long = database!!.addBatch(batch)
                if (!listVeg.isNullOrEmpty()) {
                    for (item in listVeg) {
                        var batchQtyDetail: BatchQtyDetail = BatchQtyDetail(
                            null,
                            id.toInt(),
                            item.vegName,
                            (ceil(item.vegQty!! *10) /10).toString(),
                            "admin",
                            formatted
                        )
                        database!!.addBatchDetail(batchQtyDetail)
                    }
                }
                if(id != null){
                    Toast.makeText(
                        applicationContext, getString(R.string.insert_data_success_vi),
                        Toast.LENGTH_LONG
                    ).show()
                }
                activity.finish()
            } catch (e: Exception) {
                Log.d("AAA", e.message)
                Toast.makeText(
                    applicationContext, getString(R.string.insert_data_fail_vi),
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    }



    /**
     * the method to get data from intent
     */
    private fun getDataFromItent() : Int {
        val bundle:Bundle = intent.extras
        return bundle.get("garden_id") as Int
    }

    /**
     * This method is to batch name
     */
    private fun checkBatchName(check: String): Boolean {
        database = Database(activity)
        var gardenId = getDataFromItent()
        var batchs = database.viewBatchByGardenId(gardenId)
        if (check.isEmpty()) {
            batchName.error = getString(R.string.error_empty_common)
            return false
        }else{
            for (i in 0 until batchs.size) {
                if (check.equals(batchs[i].batchName, true)) {
                    batchName.error = getString(R.string.error_batch_exist)
                    return false
                }
            }
        }
        return true
    }

    /**
     * This method is to batch name
     */
    private fun checkSelectVeg(check: Int): Boolean {
        database = Database(activity)
        if (-1 == check) {
            Toast.makeText(this, R.string.error_empty_veg_category_common, Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    /**
     * This method is to remove data
     */
    private fun removeDataListVeg(id: Int) {
        var adapter: ThemAdapter = ThemAdapter(this, listVeg)
        listVeg = adapter.removeItemPosition(id)
        //hien thi list view
        lv_themSL.adapter = ThemAdapter(activity, listVeg)
        // remove item of list then must be update list
        sumQuantity()
    }

    /**
     * This method is to get list category
     */
    private fun getListVegetable(): ArrayList<String> {
        database = Database(activity)
        listVegetable = database.findAllVegetable()
        var categories: ArrayList<String> = ArrayList()
        if (!listVegetable.isNullOrEmpty()) {
            for (i in 0 until listVegetable.size) {
                categories.add(listVegetable[i].vegName!!)
            }
        }
        return categories
    }

    private fun checkDate(selectedStartDate: String, selectedEndDate: String): Boolean {
        if(selectedStartDate.isBlank() && selectedEndDate.isBlank()) {
            textViewPickStart.error = getString(R.string.error_empty_common)
            textViewPickKT.error = getString(R.string.error_empty_common)
            return false
        }else if (selectedStartDate.isBlank()) {
            textViewPickStart.error = getString(R.string.error_empty_common)
            return false
        }else if(selectedEndDate.isBlank()) {
            textViewPickKT.error = getString(R.string.error_empty_common)
            return false
        }else {
            val formatter: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
            var date1 = formatter.parse(selectedStartDate)
            var date2 = formatter.parse(selectedEndDate)
            var compare = date1.compareTo(date2)
            if(compare > 0){
                textViewPickStart.error = getString(R.string.error_to_smaller_from)
                textViewPickKT.error = getString(R.string.error_to_smaller_from)
                return false
            }
        }
        return true
    }

}
