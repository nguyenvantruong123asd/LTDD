package com.appveg.farmfamily.ui.param

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.appveg.farmfamily.R
import com.appveg.farmfamily.ui.database.Database
import kotlinx.android.synthetic.main.activity_edit_param.*
import kotlinx.android.synthetic.main.activity_param_detail.*

class ParamDetailActivity : AppCompatActivity() {

    var activity = this@ParamDetailActivity

    private lateinit var database: Database
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_param_detail)

        initDataEdit()
    }


    /**
     * This method is to remove data
     */
    private fun initDataEdit() {
        val paramId: Int = getDataFromItent()

        // gán lại id để tý update data
        var param: Param = getParamById(paramId)

        this.temp_day_from_detail.setText(param.tempFromDay)
        this.temp_day_from_detail.isEnabled = false

        this.temp_day_to_detail.setText(param.tempToDay)
        this.temp_day_to_detail.isEnabled = false

        this.temp_night_from_detail.setText(param.tempFromNight)
        this.temp_night_from_detail.isEnabled = false

        this.temp_night_to_detail.setText(param.tempToNight)
        this.temp_night_to_detail.isEnabled = false

        this.ph_from_detail.setText(param.phFrom)
        this.ph_from_detail.isEnabled = false

        this.ph_to_detail.setText(param.phTo)
        this.ph_to_detail.isEnabled = false

        this.ppm_from_detail.setText(param.ppmFrom)
        this.ppm_from_detail.isEnabled = false

        this.ppm_to_detail.setText(param.ppmTo)
        this.ppm_to_detail.isEnabled = false

        this.tds_level_from_detail.setText(param.tdsLevelFrom)
        this.tds_level_from_detail.isEnabled = false

        this.tds_level_to_detail.setText(param.tdsLevelTo)
        this.tds_level_to_detail.isEnabled = false

    }

    /**
     * the method to get data from intent
     */
    private fun getDataFromItent(): Int {
        val bundle: Bundle = intent.extras
        return bundle.get("param_id") as Int
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
}
