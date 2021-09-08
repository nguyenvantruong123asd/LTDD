package com.appveg.farmfamily.ui.param

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.appveg.farmfamily.R
import com.appveg.farmfamily.ui.database.Database
import kotlinx.android.synthetic.main.activity_add_param.*
import java.text.SimpleDateFormat
import java.util.*

class AddParamActivity : AppCompatActivity() {
    var activity = this@AddParamActivity

    private lateinit var database: Database
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_param)

        // event action button
        actionButton()
    }


    private fun actionButton() {
        /*event add veg*/
        btn_add_param.setOnClickListener {
            addParam()
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
        return bundle.get("veg_id") as Int
    }

    private fun addParam() {
        database = Database(activity)
        var tempDayFrom = temp_day_from.text.toString().trim()
        var tempDayTo = temp_day_to.text.toString().trim()
        var tempNightFrom = temp_night_from.text.toString().trim()
        var tempNightTo = temp_night_to.text.toString().trim()
        var phFrom = ph_from.text.toString().trim()
        var phTo = ph_to.text.toString().trim()
        var ppmFrom = ppm_from.text.toString().trim()
        var ppmTo = ppm_to.text.toString().trim()
        var tdsLevelFrom = tds_level_from.text.toString().trim()
        var tdsLevelTo = tds_level_to.text.toString().trim()


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

        var vegId = getDataFromItent()
        if (checkParamName && checkParamName1 && checkParamName2 && checkParamName3 && checkParamName4 && checkParamName5 && checkParamName6 && checkParamName7 && checkParamName8 && checkParamName9) {
            var param = Param(null,tempNightTo,tempNightFrom,tempDayTo,tempDayFrom,phTo,phFrom,ppmTo,ppmFrom,tdsLevelTo,tdsLevelFrom,"admin",formatted)
            if(checkParam && checkParam1 && checkParam2 && checkParam3 && checkParam4) {
                var id = database.addParam(param)
                if (id != null) {
                    database.updateParamIdForVeg(id, vegId)
                    Toast.makeText(
                        this,
                        getString(R.string.insert_data_success_vi),
                        Toast.LENGTH_LONG
                    )
                        .show()
                    activity.finish()
                }
            }else {
                Toast.makeText(this, getString(R.string.insert_data_fail_vi), Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, getString(R.string.insert_data_fail_vi), Toast.LENGTH_LONG).show()
        }

    }

    /**
     * This method is to batch name
     */
    private fun checkParamName(value: String): Boolean {
        if (value.isEmpty()) {
            temp_day_from.error = getString(R.string.error_empty_common)
            return false
        }
        return true
    }
    private fun checkParamName1(value: String): Boolean {
        if (value.isEmpty()) {
            temp_day_to.error = getString(R.string.error_empty_common)
            return false
        }
        return true
    }
    private fun checkParamName2(value: String): Boolean {
        if (value.isEmpty()) {
            temp_night_from.error = getString(R.string.error_empty_common)
            return false
        }
        return true
    }
    private fun checkParamName3(value: String): Boolean {
        if (value.isEmpty()) {
            temp_night_to.error = getString(R.string.error_empty_common)
            return false
        }
        return true
    }
    private fun checkParamName4(value: String): Boolean {
        if (value.isEmpty()) {
            ph_from.error = getString(R.string.error_empty_common)
            return false
        }
        return true
    }
    private fun checkParamName5(value: String): Boolean {
        if (value.isEmpty()) {
            ph_to.error = getString(R.string.error_empty_common)
            return false
        }
        return true
    }
    private fun checkParamName6(value: String): Boolean {
        if (value.isEmpty()) {
            ppm_from.error = getString(R.string.error_empty_common)
            return false
        }
        return true
    }
    private fun checkParamName7(value: String): Boolean {
        if (value.isEmpty()) {
            ppm_to.error = getString(R.string.error_empty_common)
            return false
        }
        return true
    }
    private fun checkParamName8(value: String): Boolean {
        if (value.isEmpty()) {
            tds_level_from.error = getString(R.string.error_empty_common)
            return false
        }
        return true
    }
    private fun checkParamName9(value: String): Boolean {
        if (value.isEmpty()) {
            tds_level_to.error = getString(R.string.error_empty_common)
            return false
        }
        return true
    }

    private fun checkFromSmallerTo(value1: String,value2: String): Boolean {
        if (value1.isNotBlank() && value2.isNotBlank()) {
            if(value2.toDouble() < value1.toDouble()){
                temp_day_from.error = getString(R.string.error_to_smaller_from)
                temp_day_to.error = getString(R.string.error_to_smaller_from)
                return false
            }
        }
        return true
    }
    private fun checkFromSmallerTo1(value1: String,value2: String): Boolean {
        if (value1.isNotBlank() && value2.isNotBlank()) {
            if(value2.toDouble() < value1.toDouble()){
                temp_night_from.error = getString(R.string.error_to_smaller_from)
                temp_night_to.error = getString(R.string.error_to_smaller_from)
                return false
            }
        }
        return true
    }
    private fun checkFromSmallerTo2(value1: String,value2: String): Boolean {
        if (value1.isNotBlank() && value2.isNotBlank()) {
            if(value2.toDouble() < value1.toDouble()){
                ph_from.error = getString(R.string.error_to_smaller_from)
                ph_to.error = getString(R.string.error_to_smaller_from)
                return false
            }
        }
        return true
    }
    private fun checkFromSmallerTo3(value1: String,value2: String): Boolean {
        if (value1.isNotBlank() && value2.isNotBlank()) {
            if(value2.toDouble() < value1.toDouble()){
                ppm_from.error = getString(R.string.error_to_smaller_from)
                ppm_to.error = getString(R.string.error_to_smaller_from)
                return false
            }
        }
        return true
    }
    private fun checkFromSmallerTo4(value1: String,value2: String): Boolean {
        if (value1.isNotBlank() && value2.isNotBlank()) {
            if(value2.toDouble() < value1.toDouble()){
                tds_level_from.error = getString(R.string.error_to_smaller_from)
                tds_level_to.error = getString(R.string.error_to_smaller_from)
                return false
            }
        }
        return true
    }

}
