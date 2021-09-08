package com.appveg.farmfamily.ui.param

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.appveg.farmfamily.R
import com.appveg.farmfamily.ui.database.Database
import com.appveg.farmfamily.ui.vegetable.Vegetable
import kotlinx.android.synthetic.main.activity_add_param.*
import kotlinx.android.synthetic.main.activity_add_param.cancel_action_param
import kotlinx.android.synthetic.main.activity_edit_param.*
import kotlinx.android.synthetic.main.activity_edit_vegetable.*
import java.text.SimpleDateFormat
import java.util.*

class EditParamActivity : AppCompatActivity() {
    var activity = this@EditParamActivity

    private lateinit var database: Database
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_param)

        // function init data for edit
        initDataEdit()
        // action for button and image
        actionButton()
    }

    private fun actionButton() {
        /*event add veg*/
        btn_edit_param.setOnClickListener {
            editParam()
        }

        /*event cancel*/
        cancel_action_param.setOnClickListener {
            activity.finish()
        }

    }

    /**
     * the method to get data from intent
     */
    private fun getDataFromItent(): Int {
        val bundle: Bundle = intent.extras
        return bundle.get("param_id") as Int
    }

    /**
     * This method is to remove data
     */
    private fun initDataEdit() {
        val paramId: Int = getDataFromItent()

        // gán lại id để tý update data
        var param: Param = getParamById(paramId)

        this.temp_day_from_edit.setText(param.tempFromDay)
        this.temp_day_from_edit.setSelection(temp_day_from_edit.text.length)

        this.temp_day_to_edit.setText(param.tempToDay)
        this.temp_day_to_edit.setSelection(temp_day_to_edit.text.length)

        this.temp_night_from_edit.setText(param.tempFromNight)
        this.temp_night_from_edit.setSelection(temp_night_from_edit.text.length)

        this.temp_night_to_edit.setText(param.tempToNight)
        this.temp_night_to_edit.setSelection(temp_night_to_edit.text.length)

        this.ph_from_edit.setText(param.phFrom)
        this.ph_from_edit.setSelection(ph_from_edit.text.length)

        this.ph_to_edit.setText(param.phTo)
        this.ph_to_edit.setSelection(ph_to_edit.text.length)

        this.ppm_from_edit.setText(param.ppmFrom)
        this.ppm_from_edit.setSelection(ppm_from_edit.text.length)

        this.ppm_to_edit.setText(param.ppmTo)
        this.ppm_to_edit.setSelection(ppm_to_edit.text.length)

        this.tds_level_to_edit.setText(param.tdsLevelTo)
        this.tds_level_to_edit.setSelection(tds_level_to_edit.text.length)

        this.tds_level_from_edit.setText(param.tdsLevelFrom)
        this.tds_level_from_edit.setSelection(tds_level_from_edit.text.length)

    }

    /**
     * the method to get batch by id
     */
    private fun getParamById(param_id: Int) : Param {
        database = Database(activity)
        var param : Param = Param()
        if(param_id != null){
            param = database.findParamById(param_id)
        }
        return param
    }

    private fun editParam() {
        database = Database(activity)
        var tempDayFrom = temp_day_from_edit.text.toString().trim()
        var tempDayTo = temp_day_to_edit.text.toString().trim()
        var tempNightFrom = temp_night_from_edit.text.toString().trim()
        var tempNightTo = temp_night_to_edit.text.toString().trim()
        var phFrom = ph_from_edit.text.toString().trim()
        var phTo = ph_to_edit.text.toString().trim()
        var ppmFrom = ppm_from_edit.text.toString().trim()
        var ppmTo = ppm_to_edit.text.toString().trim()
        var tdsLevelFrom = tds_level_from_edit.text.toString().trim()
        var tdsLevelTo = tds_level_to_edit.text.toString().trim()

        val paramId = getDataFromItent()

        var checkParamName = checkParamName(tempDayFrom)
        var checkParamName1 = checkParamName1(tempDayTo)
        var checkParamName2 = checkParamName2(tempNightFrom)
        var checkParamName3 = checkParamName3(tempNightTo)
        var checkParamName4 = checkParamName4(phFrom)
        var checkParamName5 = checkParamName5(phTo)
        var checkParamName6 = checkParamName6(ppmFrom)
        var checkParamName7 = checkParamName7(ppmTo)
        var checkParamName8 = checkParamName8(tdsLevelFrom)
        var checkParamName9 = checkParamName9(tdsLevelTo)

        var checkParam = checkFromSmallerTo(tempDayFrom,tempDayTo)
        var checkParam1 = checkFromSmallerTo1(tempNightFrom,tempNightTo)
        var checkParam2 = checkFromSmallerTo2(phFrom,phTo)
        var checkParam3 = checkFromSmallerTo3(ppmFrom,ppmTo)
        var checkParam4 = checkFromSmallerTo4(tdsLevelFrom,tdsLevelTo)

        /*format date*/
        val current = Calendar.getInstance().time
        val formatter: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
        val formatted: String = formatter.format(current)

        if (checkParamName && checkParamName1 && checkParamName2 && checkParamName3 && checkParamName4 && checkParamName5 && checkParamName6 && checkParamName7 && checkParamName8 && checkParamName9) {
            var param = Param(paramId,tempNightTo,tempNightFrom,tempDayTo,tempDayFrom,phTo,phFrom,ppmTo,ppmFrom,tdsLevelTo,tdsLevelFrom,formatted)
            if(checkParam && checkParam1 && checkParam2 && checkParam3 && checkParam4) {
                var id = database.updateParam(param)
                if (id != null) {
                    Toast.makeText(
                        this,
                        getString(R.string.update_data_success_vi),
                        Toast.LENGTH_LONG
                    )
                        .show()
                    activity.finish()
                }
            }else {
                Toast.makeText(this, getString(R.string.update_data_fail_vi), Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, getString(R.string.update_data_fail_vi), Toast.LENGTH_LONG).show()
        }

    }

    /**
     * This method is to batch name
     */
    private fun checkParamName(value: String): Boolean {
        if (value.isEmpty()) {
            temp_day_from_edit.error = getString(R.string.error_empty_common)
            return false
        }
        return true
    }
    private fun checkParamName1(value: String): Boolean {
        if (value.isEmpty()) {
            temp_day_to_edit.error = getString(R.string.error_empty_common)
            return false
        }
        return true
    }
    private fun checkParamName2(value: String): Boolean {
        if (value.isEmpty()) {
            temp_night_from_edit.error = getString(R.string.error_empty_common)
            return false
        }
        return true
    }
    private fun checkParamName3(value: String): Boolean {
        if (value.isEmpty()) {
            temp_night_to_edit.error = getString(R.string.error_empty_common)
            return false
        }
        return true
    }
    private fun checkParamName4(value: String): Boolean {
        if (value.isEmpty()) {
            ph_from_edit.error = getString(R.string.error_empty_common)
            return false
        }
        return true
    }
    private fun checkParamName5(value: String): Boolean {
        if (value.isEmpty()) {
            ph_to_edit.error = getString(R.string.error_empty_common)
            return false
        }
        return true
    }
    private fun checkParamName6(value: String): Boolean {
        if (value.isEmpty()) {
            ppm_from_edit.error = getString(R.string.error_empty_common)
            return false
        }
        return true
    }
    private fun checkParamName7(value: String): Boolean {
        if (value.isEmpty()) {
            ppm_to_edit.error = getString(R.string.error_empty_common)
            return false
        }
        return true
    }

    private fun checkParamName8(value: String): Boolean {
        if (value.isEmpty()) {
            tds_level_from_edit.error = getString(R.string.error_empty_common)
            return false
        }
        return true
    }
    private fun checkParamName9(value: String): Boolean {
        if (value.isEmpty()) {
            tds_level_to_edit.error = getString(R.string.error_empty_common)
            return false
        }
        return true
    }

    private fun checkFromSmallerTo(value1: String,value2: String): Boolean {
        if (value1.isNotBlank() && value2.isNotBlank()) {
            if(value2.toDouble() < value1.toDouble()){
                temp_day_from_edit.error = getString(R.string.error_to_smaller_from)
                temp_day_to_edit.error = getString(R.string.error_to_smaller_from)
                return false
            }
        }
        return true
    }
    private fun checkFromSmallerTo1(value1: String,value2: String): Boolean {
        if (value1.isNotBlank() && value2.isNotBlank()) {
            if(value2.toDouble() < value1.toDouble()){
                temp_night_from_edit.error = getString(R.string.error_to_smaller_from)
                temp_night_to_edit.error = getString(R.string.error_to_smaller_from)
                return false
            }
        }
        return true
    }
    private fun checkFromSmallerTo2(value1: String,value2: String): Boolean {
        if (value1.isNotBlank() && value2.isNotBlank()) {
            if(value2.toDouble() < value1.toDouble()){
                ph_from_edit.error = getString(R.string.error_to_smaller_from)
                ph_to_edit.error = getString(R.string.error_to_smaller_from)
                return false
            }
        }
        return true
    }
    private fun checkFromSmallerTo3(value1: String,value2: String): Boolean {
        if (value1.isNotBlank() && value2.isNotBlank()) {
            if(value2.toDouble() < value1.toDouble()){
                ppm_from_edit.error = getString(R.string.error_to_smaller_from)
                ppm_to_edit.error = getString(R.string.error_to_smaller_from)
                return false
            }
        }
        return true
    }

    private fun checkFromSmallerTo4(value1: String,value2: String): Boolean {
        if (value1.isNotBlank() && value2.isNotBlank()) {
            if(value2.toDouble() < value1.toDouble()){
                tds_level_from_edit.error = getString(R.string.error_to_smaller_from)
                tds_level_to_edit.error = getString(R.string.error_to_smaller_from)
                return false
            }
        }
        return true
    }
}
