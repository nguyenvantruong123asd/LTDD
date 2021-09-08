package com.appveg.farmfamily.ui.send


import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.appveg.farmfamily.R
import com.appveg.farmfamily.ui.database.Database
import com.appveg.farmfamily.ui.vegetable.Vegetable
import com.appveg.farmfamily.ui.vegetable.VegetableTemp
import kotlinx.android.synthetic.main.activity_sua_dot_san_luong.*
import kotlinx.android.synthetic.main.activity_them_dot_san_luong.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.ceil

class SuaDotSanLuongActivity : AppCompatActivity() {
    private val activity = this@SuaDotSanLuongActivity

    private lateinit var database: Database

    private var veg_id: Long = -1
    private var selected: String? = ""
    private var listVeg: ArrayList<VegetableTemp> = ArrayList()
    private var sumQty : Double = 0.0

    private var listVegetable: ArrayList<Vegetable> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sua_dot_san_luong)
        /*init data edit*/
        initBatchEdit()
        sumQuantity()

        /*event add data to lisview*/
        addSoSL_edit.setOnClickListener {
            // add item
            addVegTemp()
            // recal total quantity
            sumQuantity()
        }

        /*event insert data to database and validate*/
        addQty_edit.setOnClickListener {
            verifyFromSQLite()
        }

        /*event remove data for temp listview*/
        lv_themSL_edit.setOnItemClickListener { adapterView, view, i, l ->
            // build alert dialog
            val dialogBuilder = AlertDialog.Builder(activity)

            // set message of alert dialog
            dialogBuilder.setMessage(getString(R.string.delete_title_all_vi))
                // if the dialog is cancelable
                .setCancelable(false)
                // positive button text and action
                .setPositiveButton(R.string.yes_vi, DialogInterface.OnClickListener {
                        dialog, id -> removeDataListVeg(i)
                })
                // negative button text and action
                .setNegativeButton(R.string.quit_vi, DialogInterface.OnClickListener {
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
        cancel_action_edit.setOnClickListener {
//            var intent: Intent = Intent(activity, ChiTietDotSanLuongActivity::class.java)
//            intent.putExtra("garden_id", getDataFromItent())
            activity.finish()
           // startActivity(intent)
        }

        /*event back*/
//        backQty_edit.setOnClickListener {
//            var intent: Intent = Intent(activity, ChiTietDotSanLuongActivity::class.java)
//            intent.putExtra("garden_id", getDataFromItent())
//            activity.finish()
//            startActivity(intent)
//        }

        // get create date
        pickDateBD_edit.setOnClickListener {
            val now = Calendar.getInstance()
            val datePicker = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    textViewPickStart_edit.error = null
                    textViewPickStart_edit.clearFocus()
                    textViewPickStart_edit.setText("" + year + "-" + (month + 1) + "-" + dayOfMonth)
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
            )

            datePicker.show()
        }
        // get the end date
        pickDateKT_edit.setOnClickListener {
            val now = Calendar.getInstance()
            val datePicker = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    textViewPickKT_edit.error = null
                    textViewPickKT_edit.clearFocus()
                    textViewPickKT_edit.setText("" + year + "-" + (month + 1) + "-" + dayOfMonth)
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
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        positionSpinner_edit.adapter = adapter

        positionSpinner_edit.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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
    /**
     * the method to insert data batch detail
     */
    private fun addVegTemp() {
        val quantityVegetable = txt_qtyVeg_edit.text.toString()
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
                            break
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
                            break
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
        lv_themSL_edit.adapter = ThemAdapter(activity, listVeg)

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
        totalQty_edit.text = sumQty.toString().trim() + "/kg"
    }
    /**
     * This method is to validate the input text fields and verify add batch credentials from SQLite
     */
    private fun verifyFromSQLite() {
        database = Database(activity)
        var selectedStartDate = textViewPickStart_edit.text.toString().trim()
        var selectedEndDate = textViewPickKT_edit.text.toString().trim()
        var selectedBatchName = batchName_edit.text.toString()
        var totalQty = (ceil(sumQty *10) /10).toString().trim()

        /*id intent from batch*/
        var gardenId = getDataFromItent()
        var batchId = getDataFromItentBatchId()

        var checkBatchName = checkBatchName(selectedBatchName)
        var checkDate = checkDate(selectedStartDate,selectedEndDate)

        /*format date*/
        val current = Calendar.getInstance().time
        val formatter: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
        val formatted: String = formatter.format(current)

        var batch: Batch = Batch(batchId,selectedBatchName,selectedEndDate,totalQty,gardenId,selectedStartDate,"admin",formatted)
        if (checkBatchName && checkDate) {
            /*end format date*/
            try {
                var id: Int = database!!.updateBatch(batch)
                removeDataListVegDb(batchId)
                if (!listVeg.isNullOrEmpty()) {
                    for (item in listVeg) {
                            var batchQtyDetail: BatchQtyDetail = BatchQtyDetail(
                                null,
                                batchId,
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
                        applicationContext, getString(R.string.update_data_success_vi),
                        Toast.LENGTH_LONG
                    ).show()
                }
                // reset list data for batch
//                var  intent: Intent  = Intent(activity, ChiTietDotSanLuongActivity::class.java)
//                intent.putExtra("garden_id",garden_id)
//                startActivity(intent)
                activity.finish()
            } catch (e: Exception) {
                Log.d("AAA", e.message)
                Toast.makeText(
                    applicationContext, getString(R.string.update_data_fail_vi),
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    }

    /**
     * the method to get data from intent
     */
    private fun getDataFromItent(): Int {
        val bundle: Bundle = intent.extras
        val id: Int =
            bundle.get("garden_id") as Int
        return id
    }
    /**
     * the method to get data from intent batch id
     */
    private fun getDataFromItentBatchId(): Int {
        val bundle: Bundle = intent.extras
        val id: Int =
            bundle.get("batch_id") as Int
        return id
    }

    /**
     * This method is to batch name
     */
    private fun checkBatchName(check: String): Boolean {
        if (check.isEmpty()) {
            batchName_edit.error = getString(R.string.error_empty_common)
            return false
        }
        return true
    }

    /**
     * This method is to remove data
     */
    private fun removeDataListVeg(position: Int) {
        var adapter: ThemAdapter = ThemAdapter(this, listVeg)
        listVeg = adapter.removeItemPosition(position)
        //hien thi list view
        lv_themSL_edit.adapter = ThemAdapter(activity, listVeg)
        // remove item of list then must be update list
        sumQuantity()
    }
    /**
     * This method is to remove data
     */
    private fun removeDataListVegDb(batchId: Int) {
        database = Database(activity)
        var listBatchDetail = database.findAllBatchDetailById(batchId)
        if(!listBatchDetail.isNullOrEmpty()){
            for (item in 0 until listBatchDetail.size){
                //trường hợp đã lưu vào db
                database.deleteBatchDetail(listBatchDetail[item].qtyDetailId!!)
            }
        }
        lv_themSL_edit.adapter = ThemAdapter(activity, listVeg)
        // note trong vegetabletemp thì vegid chính là bactDetailId
    }

    /**
     * the method to get batch by id
     */
    fun getBatchById(batch_id: Int) : Batch{
        database = Database(activity)
        var batch : Batch = Batch()
        if(batch_id != null){
            batch = database.findBatchById(batch_id)
        }
        return batch
    }
    /**
     * the method to get batch detail by id
     */
    fun getListBatchDetailById(batch_id: Int) : ArrayList<BatchQtyDetail>{
        var listBatchDetail: ArrayList<BatchQtyDetail> = ArrayList()
        database = Database(activity)
        if(batch_id != null){
            listBatchDetail = database.findAllBatchDetailById(batch_id)
        }
        return listBatchDetail
    }

    /**
     * This method is to remove data
     */
    private fun initBatchEdit() {
        val batch_id: Int = getDataFromItentBatchId()
        // gán lại id để tý update data
        var listBatchDetail: ArrayList<BatchQtyDetail> = getListBatchDetailById(batch_id)
        var batch: Batch = getBatchById(batch_id)

        textViewPickStart_edit.setText(batch.createdDate)
        textViewPickKT_edit.setText(batch.theEndDate)
        batchName_edit.setText(batch.batchName)
        totalQty_edit.text = batch.totalQuantity + "/kg"

        if(!listBatchDetail.isNullOrEmpty()){
            for (item in listBatchDetail) {
                var vegetable = VegetableTemp(item.qtyDetailId, item.vegetableName,item.vegetableQuantity?.toDouble())
                listVeg.add(vegetable)
            }
            if(listVeg.isNullOrEmpty()){
                Toast.makeText(activity,getString(R.string.Veg_Empty_Message_vi),Toast.LENGTH_LONG).show()
            }else{
                lv_themSL_edit.adapter = ThemAdapter(activity, listVeg)
            }

        }
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

    private fun checkDate(selectedStartDate: String, selectedEndDate: String): Boolean {
        if(selectedStartDate.isBlank() && selectedEndDate.isBlank()) {
            textViewPickStart_edit.error = getString(R.string.error_empty_common)
            textViewPickKT_edit.error = getString(R.string.error_empty_common)
            return false
        }else if (selectedStartDate.isBlank()) {
            textViewPickStart_edit.error = getString(R.string.error_empty_common)
            return false
        }else if(selectedEndDate.isBlank()) {
            textViewPickKT_edit.error = getString(R.string.error_empty_common)
            return false
        }else{
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