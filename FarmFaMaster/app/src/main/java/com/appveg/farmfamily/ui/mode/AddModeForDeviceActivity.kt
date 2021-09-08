package com.appveg.farmfamily.ui.mode

import android.app.TimePickerDialog
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.appveg.farmfamily.R
import com.appveg.farmfamily.ui.database.Database
import com.appveg.farmfamily.ui.vegetable.Vegetable
import kotlinx.android.synthetic.main.activity_add_mode_for_device.*
import kotlinx.android.synthetic.main.activity_add_vegetable.*
import kotlinx.android.synthetic.main.activity_detail_garden.*
import kotlinx.android.synthetic.main.activity_mode_detail.*
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddModeForDeviceActivity : AppCompatActivity() {

    private val activity = this@AddModeForDeviceActivity
    private var listDayRepeat = ArrayList<String>()
    var result = ""
    private lateinit var database: Database
    var check1: Int = 1
    var check2: Int = 1
    var check3: Int = 1
    var check4: Int = 1
    var check5: Int = 1
    var check6: Int = 1
    var check7: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_mode_for_device)

        //time_picker_24.setIs24HourView(true)
        time_picker_24.isEnabled = false

        time_mode_select_start.setOnClickListener {
            var calendar = Calendar.getInstance()
            var hour = calendar.get(Calendar.HOUR_OF_DAY)
            var minute = calendar.get(Calendar.MINUTE)
            var timePickerDialog = TimePickerDialog(
                this@AddModeForDeviceActivity,
                TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                    if (minute < 10) {
                        var minute = "0$minute"
                        time_mode_start.setText("$hourOfDay:$minute")
                        time_mode_start.setSelection(time_mode_start.length())
                    } else {
                        time_mode_start.setText("$hourOfDay:$minute")
                        time_mode_start.setSelection(time_mode_start.length())
                    }

                }, hour, minute, true
            )
            timePickerDialog.show()

        }
        time_mode_select_end.setOnClickListener {
            var calendar = Calendar.getInstance()
            var hour = calendar.get(Calendar.HOUR_OF_DAY)
            var minute = calendar.get(Calendar.MINUTE)
            var timePickerDialog = TimePickerDialog(
                this@AddModeForDeviceActivity,
                TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                    if (minute < 10) {
                        var minute = "0$minute"
                        time_mode_end.setText("$hourOfDay:$minute")
                        time_mode_end.setSelection(time_mode_end.length())
                    } else {
                        time_mode_end.setText("$hourOfDay:$minute")
                        time_mode_end.setSelection(time_mode_end.length())
                    }
                }, hour, minute, true
            )

            timePickerDialog.show()

        }

        actionButton()
    }

    private fun actionButton() {
        //cancel
        cancel_action_device.setOnClickListener {
            activity.finish()
        }
        btn_device_add.setOnClickListener {
            addMode()
        }



        t2.setOnClickListener {
            if (check1 == 1) {
                t2.background = getDrawable(R.drawable.back_on)
                listDayRepeat.add("T2")
                check1 = 0
            } else {
                t2.background = getDrawable(R.drawable.dayofweek)
                listDayRepeat.remove("T2")
                check1 = 1
            }
            setDayRepeat()
        }

        t3.setOnClickListener {
            if (check2 == 1) {
                t3.background = getDrawable(R.drawable.back_on)
                listDayRepeat.add("T3")
                check2 = 0
            } else {
                t3.background = getDrawable(R.drawable.dayofweek)
                listDayRepeat.remove("T3")
                check2 = 1
            }
            setDayRepeat()
        }

        t4.setOnClickListener {
            if (check3 == 1) {
                t4.background = getDrawable(R.drawable.back_on)
                listDayRepeat.add("T4")
                check3 = 0
            } else {
                t4.background = getDrawable(R.drawable.dayofweek)
                listDayRepeat.remove("T4")
                check3 = 1
            }
            setDayRepeat()
        }


        t5.setOnClickListener {
            if (check4 == 1) {
                t5.background = getDrawable(R.drawable.back_on)
                listDayRepeat.add("T5")
                check4 = 0
            } else {
                t5.background = getDrawable(R.drawable.dayofweek)
                listDayRepeat.remove("T5")
                check4 = 1
            }
            setDayRepeat()
        }

        t6.setOnClickListener {
            if (check5 == 1) {
                t6.background = getDrawable(R.drawable.back_on)
                listDayRepeat.add("T6")
                check5 = 0
            } else {
                t6.background = getDrawable(R.drawable.dayofweek)
                listDayRepeat.remove("T6")
                check5 = 1
            }
            setDayRepeat()
        }

        t7.setOnClickListener {
            if (check6 == 1) {
                t7.background = getDrawable(R.drawable.back_on)
                listDayRepeat.add("T7")
                check6 = 0
            } else {
                t7.background = getDrawable(R.drawable.dayofweek)
                listDayRepeat.remove("T7")
                check6 = 1
            }
            setDayRepeat()
        }

        cn.setOnClickListener {
            if (check7 == 1) {
                cn.background = getDrawable(R.drawable.back_on)
                listDayRepeat.add("CN")
                check7 = 0
            } else {
                cn.background = getDrawable(R.drawable.dayofweek)
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

        date_repeat_display.text = result
    }

    private fun addMode() {
        database = Database(activity)
        var code = getRandomCodeDetail()
        var timeStart = time_mode_start.text.toString().trim()
        var timeEnd = time_mode_end.text.toString().trim()
        var timeOn = time_mode_on.text.toString().trim()
        var timeOff = time_mode_off.text.toString().trim()
        var timeRepeat = date_repeat_display.text.toString().trim()
        var repeat = getRepeat(checkbox_mode.isChecked)

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
            var mode = Mode(null, code, timeStart, timeEnd,timeOn,timeOff,timeRepeat,repeat,"1",formatted)
            var id = database.addMode(mode)
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
            time_mode_start.error = getString(R.string.error_empty_common)
            return false
        }
        return true
    }
    /**
     * This method is to batch name
     */
    private fun checkTimeEnd(check: String): Boolean {
        if (check.isEmpty()) {
            time_mode_end.error = getString(R.string.error_empty_common)
            return false
        }
        return true
    }
    /**
     * This method is to batch name
     */
    private fun checkTimeOn(check: String): Boolean {
        if (check.isEmpty()) {
            time_mode_on.error = getString(R.string.error_empty_common)
            return false
        }
        return true
    }
    /**
     * This method is to batch name
     */
    private fun checkTimeOff(check: String): Boolean {
        if (check.isEmpty()) {
            time_mode_off.error = getString(R.string.error_empty_common)
            return false
        }
        return true
    }
    /**
     * This method is to batch name
     */
    private fun checkTimeRepeat(check: String): Boolean {
        if (check.isEmpty()) {
            date_repeat_display.error = getString(R.string.error_empty_common)
            return false
        }
        return true
    }

    /**
     * This method is to get random code detail
     */
    private fun getRandomCodeDetail(): String {
        var builder: StringBuilder = StringBuilder()
        builder.append("MODE")
        val random = Random()
        var ran: Int = random.nextInt(9999 - 1000) + 1000
        builder.append(ran.toString())
        return builder.toString()
    }
}
