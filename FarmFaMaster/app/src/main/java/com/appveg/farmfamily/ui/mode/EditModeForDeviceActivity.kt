package com.appveg.farmfamily.ui.mode

import android.app.TimePickerDialog
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.appveg.farmfamily.R
import com.appveg.farmfamily.ui.database.Database
import com.appveg.farmfamily.ui.device.Device
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_add_mode_for_device.*
import kotlinx.android.synthetic.main.activity_add_mode_for_device.time_picker_24
import kotlinx.android.synthetic.main.activity_edit_device.*
import kotlinx.android.synthetic.main.activity_edit_mode_for_device.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class EditModeForDeviceActivity : AppCompatActivity() {
    private lateinit var database: Database
    var activity = this@EditModeForDeviceActivity
    private lateinit var listDayRepeatInit: List<String>
    private var listDayRepeat = ArrayList<String>()
    var result = ""
    var check1: Int = 1
    var check2: Int = 1
    var check3: Int = 1
    var check4: Int = 1
    var check5: Int = 1
    var check6: Int = 1
    var check7: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_mode_for_device)

        //time_picker_24.setIs24HourView(true)
        time_picker_24_edit.isEnabled = false

        time_mode_select_start_edit.setOnClickListener {
            var calendar = Calendar.getInstance()
            var hour = calendar.get(Calendar.HOUR_OF_DAY)
            var minute = calendar.get(Calendar.MINUTE)
            var timePickerDialog = TimePickerDialog(this@EditModeForDeviceActivity,
                TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                    if(minute < 10){
                        var minute = "0$minute"
                        time_mode_start_edit.setText("$hour:$minute")
                    }else{
                        time_mode_start_edit.setText("$hour:$minute")
                    }

                }, hour, minute, true
            )
            timePickerDialog.show()

        }
        time_mode_select_end_edit.setOnClickListener {
            var calendar = Calendar.getInstance()
            var hour = calendar.get(Calendar.HOUR_OF_DAY)
            var minute = calendar.get(Calendar.MINUTE)
            var timePickerDialog = TimePickerDialog(this@EditModeForDeviceActivity,
                TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                    if(minute < 10){
                        var minute = "0$minute"
                        time_mode_end_edit.setText("$hour:$minute")
                    }else{
                        time_mode_end_edit.setText("$hour:$minute")
                    }
                }, hour, minute, true
            )

            timePickerDialog.show()

        }
        initDataEdit()
        actionButton()
    }

    private fun actionButton() {
        //cancel
        cancel_action_device_mode_edit.setOnClickListener {
            activity.finish()
        }
        btn_device_mode_edit.setOnClickListener {
            editMode()
        }

        t2_edit.setOnClickListener {
            if (check1 == 1) {
                t2_edit.background = getDrawable(R.drawable.back_on)
                listDayRepeat.add("T2")
                check1 = 0
            } else {
                t2_edit.background = getDrawable(R.drawable.dayofweek)
                listDayRepeat.remove("T2")
                check1 = 1
            }
            setDayRepeat()
        }
        t3_edit.setOnClickListener {
            if (check2 == 1) {
                t3_edit.background = getDrawable(R.drawable.back_on)
                listDayRepeat.add("T3")
                check2 = 0
            } else {
                t3_edit.background = getDrawable(R.drawable.dayofweek)
                listDayRepeat.remove("T3")
                check2 = 1
            }
            setDayRepeat()
        }
        t4_edit.setOnClickListener {
            if (check3 == 1) {
                t4_edit.background = getDrawable(R.drawable.back_on)
                listDayRepeat.add("T4")
                check3 = 0
            } else {
                t4_edit.background = getDrawable(R.drawable.dayofweek)
                listDayRepeat.remove("T4")
                check3 = 1
            }
            setDayRepeat()
        }

        t5_edit.setOnClickListener {
            if (check4 == 1) {
                t5_edit.background = getDrawable(R.drawable.back_on)
                listDayRepeat.add("T5")
                check4 = 0
            } else {
                t5_edit.background = getDrawable(R.drawable.dayofweek)
                listDayRepeat.remove("T5")
                check4 = 1
            }
            setDayRepeat()
        }

        t6_edit.setOnClickListener {
            if (check5 == 1) {
                t6_edit.background = getDrawable(R.drawable.back_on)
                listDayRepeat.add("T6")
                check5 = 0
            } else {
                t6_edit.background = getDrawable(R.drawable.dayofweek)
                listDayRepeat.remove("T6")
                check5 = 1
            }
            setDayRepeat()
        }

        t7_edit.setOnClickListener {
            if (check6 == 1) {
                t7_edit.background = getDrawable(R.drawable.back_on)
                listDayRepeat.add("T7")
                check6 = 0
            } else {
                t7_edit.background = getDrawable(R.drawable.dayofweek)
                listDayRepeat.remove("T7")
                check6 = 1
            }
            setDayRepeat()
        }

        cn_edit.setOnClickListener {
            if (check7 == 1) {
                cn_edit.background = getDrawable(R.drawable.back_on)
                listDayRepeat.add("CN")
                check7 = 0
            } else {
                cn_edit.background = getDrawable(R.drawable.dayofweek)
                listDayRepeat.remove("CN")
                check7 = 1
            }
            setDayRepeat()
        }


    }

    // set day repeat
    private fun setDayRepeat() {
        result = ""
        listDayRepeat.sort()
        if (listDayRepeat.size != 7) {
            if (!listDayRepeat.isNullOrEmpty()) {
                for (item in 0 until listDayRepeat.size) {
                    result += if (item == 0) {
                        listDayRepeat[item]
                    } else {
                        "," + listDayRepeat[item]
                    }
                }
            } else {
                result = getString(R.string.everyday_vi)
            }
        } else {
            result = getString(R.string.everyday_vi)
        }

        date_repeat_display_edit.text = result
    }

    // sava data
    private fun editMode() {
        database = Database(activity)

        val modeId: Int = getDataFromItent()

        var timeStart = time_mode_start_edit.text.toString().trim()
        var timeEnd = time_mode_end_edit.text.toString().trim()
        var timeOn = time_mode_on_edit.text.toString().trim()
        var timeOff = time_mode_off_edit.text.toString().trim()
        var timeRepeat = date_repeat_display_edit.text.toString().trim()
        var repeat = getRepeat(checkbox_mode_edit.isChecked)

        var checkTimeStart = checkTimeStart(timeStart)
        var checkTimeEnd = checkTimeEnd(timeEnd)
        var checkTimeOn = checkTimeOn(timeOn)
        var checkTimeOff = checkTimeOff(timeOff)
        var checkTimeRepeat = checkTimeRepeat(timeRepeat)

        /*format date*/
        val current = Calendar.getInstance().time
        val formatter: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
        val formatted: String = formatter.format(current)


        if (checkTimeStart && checkTimeEnd && checkTimeOn && checkTimeOff && checkTimeRepeat ) {
            var mode = Mode(modeId, timeStart, timeEnd,timeOn,timeOff,timeRepeat,repeat,formatted)
            var id = database.updateMode(mode)
            if (id != null) {
                Toast.makeText(this, getString(R.string.update_data_success_vi), Toast.LENGTH_LONG)
                    .show()
                activity.finish()
            }
        } else {
            Toast.makeText(this, getString(R.string.update_data_fail_vi), Toast.LENGTH_LONG).show()
        }

    }


    private fun initDataEdit() {
        database = Database(activity)
        val modeId: Int = getDataFromItent()

        // gán lại id để tý update data
        var mode: Mode = database.findAllModeByModeId(modeId)

        this.checkbox_mode_edit.isChecked = getRepeat(mode.repeat!!)
        this.date_repeat_display_edit.text = mode.timeRepeat
        this.time_mode_start_edit.setText(mode.timeOn)
        this.time_mode_start_edit.setSelection(time_mode_start_edit.text.length)
        this.time_mode_end_edit.setText(mode.timeOff)
        this.time_mode_end_edit.setSelection(time_mode_end_edit.text.length)
        this.time_mode_on_edit.setText(mode.on)
        this.time_mode_on_edit.setSelection(time_mode_on_edit.text.length)
        this.time_mode_off_edit.setText(mode.off)
        this.time_mode_off_edit.setSelection(time_mode_off_edit.text.length)

        selectedButton(mode.timeRepeat.toString())


    }


    /**
     * the method to get data from intent
     */
    private fun getDataFromItent(): Int {
        val bundle: Bundle = intent.extras
        return bundle.get("mode_id") as Int
    }

    /**
     * This method is check repeat
     */
    private fun getRepeat(checked: String): Boolean {
        if (checked == "1") {
            return true
        }
        return false
    }


    /**
     * This method is check repeat
     */
    private fun selectedButton(timeRepeat: String ){
        listDayRepeatInit = timeRepeat.split(",")
        for (item in  listDayRepeatInit){
            if("T2" == item){
                t2_edit.background = getDrawable(R.drawable.back_on)
                listDayRepeat.add("T2")
                check1 = 0
            }
            if ("T3" == item){
                t3_edit.background = getDrawable(R.drawable.back_on)
                listDayRepeat.add("T3")
                check2 = 0
            }
            if ("T4" == item){
                t4_edit.background = getDrawable(R.drawable.back_on)
                listDayRepeat.add("T4")
                check3 = 0
            }
            if ("T5" == item){
                t5_edit.background = getDrawable(R.drawable.back_on)
                listDayRepeat.add("T5")
                check4 = 0
            }
            if ("T6" == item){
                t6_edit.background = getDrawable(R.drawable.back_on)
                listDayRepeat.add("T6")
                check5 = 0
            }
            if ("T7" == item){
                t7_edit.background = getDrawable(R.drawable.back_on)
                listDayRepeat.add("T7")
                check6 = 0
            }
            if ("CN" == item){
                cn_edit.background = getDrawable(R.drawable.back_on)
                listDayRepeat.add("CN")
                check7 = 0
            }
            if (getString(R.string.everyday_vi) == item){
                t2_edit.background = getDrawable(R.drawable.back_on)
                listDayRepeat.add("T2")
                check1 = 0
                t3_edit.background = getDrawable(R.drawable.back_on)
                listDayRepeat.add("T3")
                check2 = 0
                t4_edit.background = getDrawable(R.drawable.back_on)
                listDayRepeat.add("T4")
                check3 = 0
                t5_edit.background = getDrawable(R.drawable.back_on)
                listDayRepeat.add("T5")
                check4 = 0
                t6_edit.background = getDrawable(R.drawable.back_on)
                listDayRepeat.add("T6")
                check5 = 0
                t7_edit.background = getDrawable(R.drawable.back_on)
                listDayRepeat.add("T7")
                check6 = 0
                cn_edit.background = getDrawable(R.drawable.back_on)
                listDayRepeat.add("CN")
                check7 = 0
            }

        }
    }

    /**
     * This method is check repeat
     */
    private fun getRepeat(checked: Boolean): String {
        if (checked) {
            return "1"
        }
        return "0"
    }

    /**
     * This method is to batch name
     */
    private fun checkTimeStart(check: String): Boolean {
        if (check.isEmpty()) {
            time_mode_start_edit.error = getString(R.string.error_empty_common)
            return false
        }
        return true
    }
    /**
     * This method is to batch name
     */
    private fun checkTimeEnd(check: String): Boolean {
        if (check.isEmpty()) {
            time_mode_end_edit.error = getString(R.string.error_empty_common)
            return false
        }
        return true
    }
    /**
     * This method is to batch name
     */
    private fun checkTimeOn(check: String): Boolean {
        if (check.isEmpty()) {
            time_mode_on_edit.error = getString(R.string.error_empty_common)
            return false
        }
        return true
    }
    /**
     * This method is to batch name
     */
    private fun checkTimeOff(check: String): Boolean {
        if (check.isEmpty()) {
            time_mode_off_edit.error = getString(R.string.error_empty_common)
            return false
        }
        return true
    }
    /**
     * This method is to batch name
     */
    private fun checkTimeRepeat(check: String): Boolean {
        if (check.isEmpty()) {
            date_repeat_display_edit.error = getString(R.string.error_empty_common)
            return false
        }
        return true
    }
}
