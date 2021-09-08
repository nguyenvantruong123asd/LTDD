package com.appveg.farmfamily.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.appveg.farmfamily.NotificationApp
import com.appveg.farmfamily.R
import com.appveg.farmfamily.ui.chart.ChartActivity
import com.appveg.farmfamily.ui.database.Database
import com.appveg.farmfamily.ui.device.DeviceDetail
import com.appveg.farmfamily.ui.param.Param
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_detail_garden.*
import kotlin.collections.ArrayList


class DetailGardenActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var database1: Database
    private var notificationManagerCompat: NotificationManagerCompat? = null

    var devices: ArrayList<DeviceDetail> = ArrayList()
    private var child: String = ""
    private var child1: String = ""
    private var child2: String = ""
    private var child3: String = ""


    private val activity = this@DetailGardenActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_garden)
        rainAlert()
        actionButton()

        handlingProcess()
        this.notificationManagerCompat = NotificationManagerCompat.from(activity)
    }

    // action
    fun actionButton() {
        thongkekhuvuon.setOnClickListener {
            var intent: Intent = Intent(this, ChartActivity::class.java)
            var id: String? = getDataFromItent()
            intent.putExtra("garden_code", id)
            startActivity(intent)
        }
        var gardenCode = getDataFromItent()
        var database = FirebaseDatabase.getInstance().reference

        // button tarpaulin next
        button_tarpaulin_ahead.setOnClickListener {
            button_tarpaulin_ahead.background = getDrawable(R.drawable.back_on)
            button_tarpaulin_back.background = getDrawable(R.drawable.back)
            button_bat_che_mua_stop.background = getDrawable(R.drawable.back)
            button_tarpaulin_ahead.setCompoundDrawablesRelativeWithIntrinsicBounds(
                0,
                R.drawable.ic_ahead_on,
                0,
                0
            )
            button_tarpaulin_back.setCompoundDrawablesRelativeWithIntrinsicBounds(
                0,
                R.drawable.ic_back,
                0,
                0
            )
            button_bat_che_mua_stop.setCompoundDrawablesRelativeWithIntrinsicBounds(
                0,
                R.drawable.ic_stop,
                0,
                0
            )
            if (!child.isNullOrEmpty()) {
                database.child(gardenCode).child(child).child("value").setValue("L")
            }
        }
        // button tarpaulin back
        button_tarpaulin_back.setOnClickListener {
            button_tarpaulin_ahead.background = getDrawable(R.drawable.back)
            button_tarpaulin_back.background = getDrawable(R.drawable.back_on)
            button_bat_che_mua_stop.background = getDrawable(R.drawable.back)
            button_tarpaulin_ahead.setCompoundDrawablesRelativeWithIntrinsicBounds(
                0,
                R.drawable.ic_ahead,
                0,
                0
            )
            button_tarpaulin_back.setCompoundDrawablesRelativeWithIntrinsicBounds(
                0,
                R.drawable.ic_back_on,
                0,
                0
            )
            button_bat_che_mua_stop.setCompoundDrawablesRelativeWithIntrinsicBounds(
                0,
                R.drawable.ic_stop,
                0,
                0
            )
            if (!child.isNullOrEmpty()) {
                database.child(gardenCode).child(child).child("value").setValue("R")
            }
        }
        // button tarpaulin stop
        button_bat_che_mua_stop.setOnClickListener {
            button_tarpaulin_ahead.background = getDrawable(R.drawable.back)
            button_tarpaulin_back.background = getDrawable(R.drawable.back)
            button_bat_che_mua_stop.background = getDrawable(R.drawable.back_on)
            button_tarpaulin_ahead.setCompoundDrawablesRelativeWithIntrinsicBounds(
                0,
                R.drawable.ic_ahead,
                0,
                0
            )
            button_tarpaulin_back.setCompoundDrawablesRelativeWithIntrinsicBounds(
                0,
                R.drawable.ic_back,
                0,
                0
            )
            button_bat_che_mua_stop.setCompoundDrawablesRelativeWithIntrinsicBounds(
                0,
                R.drawable.ic_not_stop,
                0,
                0
            )
            if (!child.isNullOrEmpty()) {
                database.child(gardenCode).child(child).child("value").setValue("S")
            }
        }

        // button bom dung dich
        var check1: Int = 1
        button_bom_dung_dich.setOnClickListener {
            if (check1 == 1) {
                button_bom_dung_dich.background = getDrawable(R.drawable.back_on)
                button_bom_dung_dich.text = getString(R.string.ON_vi)
                button_bom_dung_dich.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    0,
                    R.drawable.ic_may_bom,
                    0,
                    0
                )
                if (!child1.isNullOrEmpty()) {
                    database.child(gardenCode).child(child1).child("value").setValue("ON")
                }
                check1 = 0
            } else {
                button_bom_dung_dich.background = getDrawable(R.drawable.back)
                button_bom_dung_dich.text = getString(R.string.OFF_vi)
                button_bom_dung_dich.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    0,
                    R.drawable.ic_may_bom_0,
                    0,
                    0
                )
                if (!child1.isNullOrEmpty()) {
                    database.child(gardenCode).child(child1).child("value").setValue("OFF")
                }
                check1 = 1
            }
        }
        // button misting pumps
        var check2: Int = 1
        button_bom_phun_suong.setOnClickListener {
            if (check2 == 1) {
                button_bom_phun_suong.background = getDrawable(R.drawable.back_on)
                button_bom_phun_suong.text = getString(R.string.ON_vi)
                button_bom_phun_suong.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    0,
                    R.drawable.ic_phun_suong,
                    0,
                    0
                )
                if (!child2.isNullOrEmpty()) {
                    database.child(gardenCode).child(child2).child("value").setValue("ON")
                }
                check2 = 0
            } else {
                button_bom_phun_suong.background = getDrawable(R.drawable.back)
                button_bom_phun_suong.text = getString(R.string.OFF_vi)
                button_bom_phun_suong.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    0,
                    R.drawable.ic_phun_suong_off,
                    0,
                    0
                )
                if (!child2.isNullOrEmpty()) {
                    database.child(gardenCode).child(child2).child("value").setValue("OFF")
                }
                check2 = 1
            }
        }
        // button lamp
        var check3: Int = 1
        button_bong_den.setOnClickListener {
            if (check3 == 1) {
                button_bong_den.background = getDrawable(R.drawable.back_on)
                button_bong_den.text = getString(R.string.ON_vi)
                button_bong_den.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    0,
                    R.drawable.ic_bong_den_sang,
                    0,
                    0
                )
                if (!child3.isNullOrEmpty()) {
                    database.child(gardenCode).child(child3).child("value").setValue("ON")
                }
                check3 = 0
            } else {
                button_bong_den.background = getDrawable(R.drawable.back)
                button_bong_den.text = getString(R.string.OFF_vi)
                button_bong_den.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    0,
                    R.drawable.ic_bong_den,
                    0,
                    0
                )
                if (!child3.isNullOrEmpty()) {
                    database.child(gardenCode).child(child3).child("value").setValue("OFF")
                }
                check3 = 1
            }
        }
    }

    /**
     * the method to get list device info
     */
    private fun getListDeviceInfo(): ArrayList<DeviceDetail> {
        var gardenId = getDataFromItentGardenId()
        database1 = Database(activity)
        var temps = database1.findAllDeviceByGardenIdForInfo(gardenId)
        if (temps.isNullOrEmpty()) {
            Toast.makeText(
                activity,
                getString(R.string.error_empty_device_home_info),
                Toast.LENGTH_SHORT
            )
        }
        return temps
    }


    private fun handlingProcess() {
        devices = getListDeviceInfo()
        if (!devices.isNullOrEmpty()) {
            for (item in 0 until devices.size) {
                when {
                    devices[item].deviceDetailCodeSS!!.contains("CAMBIENNHIETDO", false) -> {
                        tempSenSor(devices[item].deviceDetailCodeSS!!)
                        setting_temp.background = getDrawable(R.color.background_home)
                    }
                    devices[item].deviceDetailCodeSS!!.contains("CAMBIENDOAM", false) -> {
                        humSenSor(devices[item].deviceDetailCodeSS!!)
                        setting_hum.background = getDrawable(R.color.background_home)
                    }
                    devices[item].deviceDetailCodeSS!!.contains("CAMBIENANHSANG", false) -> {
                        theLightSensor(devices[item].deviceDetailCodeSS!!)
                    }
                    devices[item].deviceDetailCodeSS!!.contains("CAMBIENMUA", false) -> {
                        gardenInfoRain(devices[item].deviceDetailCodeSS!!)
                        setting_rain.background = getDrawable(R.color.background_home)
                    }
                    devices[item].deviceDetailCodeSS!!.contains("CAMBIENPH", false) -> {
                        pHSenSor(devices[item].deviceDetailCodeSS!!)
                        setting_ph.background = getDrawable(R.color.background_home)
                    }
                    devices[item].deviceDetailCodeSS!!.contains("CAMBIENTDS", false) -> {
                        tDSSensor(devices[item].deviceDetailCodeSS!!)
                        setting_tds.background = getDrawable(R.color.background_home)
                    }
                    devices[item].deviceDetailCodeSS!!.contains("CAMBIENSIEUAM", false) -> {
                        //gardenInfoRain(devices[item].deviceDetailCodeSS!!)
                        setting_level_tds.background = getDrawable(R.color.background_home)
                    }
                    devices[item].deviceDetailCodeSS!!.contains("BATCHEMUA", false) -> {
                        tarpaulinONOFFControl(devices[item].deviceDetailCodeSS!!)
                        setting_button_bat.text = getString(R.string.da_cai_dat_vi)
                        child = devices[item].deviceDetailCodeSS!!
                    }
                    devices[item].deviceDetailCodeSS!!.contains("MAYBOMDUNGDICH", false) -> {
                        pumpONOFFControl(devices[item].deviceDetailCodeSS!!)
                        setting_button_dd.text = getString(R.string.da_cai_dat_vi)
                        child1 = devices[item].deviceDetailCodeSS!!
                    }
                    devices[item].deviceDetailCodeSS!!.contains("BONGDEN", false) -> {
                        lampONOFFControl(devices[item].deviceDetailCodeSS!!)
                        setting_button_lamp.text = getString(R.string.da_cai_dat_vi)
                        child3 = devices[item].deviceDetailCodeSS!!
                    }
                    devices[item].deviceDetailCodeSS!!.contains("MAYBOMPHUNSUONG", false) -> {
                        mistingPumpsONOFFControl(devices[item].deviceDetailCodeSS!!)
                        setting_button_phun_suong.text = getString(R.string.da_cai_dat_vi)
                        child2 = devices[item].deviceDetailCodeSS!!
                    }
                }

            }
        }

    }


    /**
     * the method to gardenInfo
     */
    private fun tempSenSor(deviceCode: String) {
        database = FirebaseDatabase.getInstance().reference
        // My top posts by number of stars
        var garden = getDataFromItent()

        database.child(garden).child(deviceCode).addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                var temp: DetailGardenFirebase? =
                    dataSnapshot.getValue(DetailGardenFirebase::class.java)
                temperature.text = temp?.value
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {

            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }

    /**
     * the method to gardenInfo
     */
    private fun humSenSor(deviceCode: String) {
        database = FirebaseDatabase.getInstance().reference
        // My top posts by number of stars
        var garden = getDataFromItent()

        database.child(garden).child(deviceCode).addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                var temp: DetailGardenFirebase? =
                    dataSnapshot.getValue(DetailGardenFirebase::class.java)
                humidity.text = temp?.value
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {

            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }

    /**
     * the method to gardenInfoRain
     */
    private fun gardenInfoRain(deviceCode: String) {
        database = FirebaseDatabase.getInstance().reference
        // My top posts by number of stars
        var garden = getDataFromItent()

        database.child(garden).child(deviceCode).addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                var rain: DetailGardenFirebase? =
                    dataSnapshot.getValue(DetailGardenFirebase::class.java)
                if (rain?.value!!.trim() == "1") {
                    rain_status.setImageResource(R.drawable.mua)
                    rain_text.text = getString(R.string.Rained)
                } else {
                    rain_status.setImageResource(R.drawable.khongmua)
                    rain_text.text = getString(R.string.no_rain)
                }
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {

            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }

    /**
     * the method to gardenInfo
     */
    private fun pHSenSor(deviceCode: String) {
        database = FirebaseDatabase.getInstance().reference
        // My top posts by number of stars
        var garden = getDataFromItent()

        database.child(garden).child(deviceCode).addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                var temp: DetailGardenFirebase? =
                    dataSnapshot.getValue(DetailGardenFirebase::class.java)
                ph_sensor.text = temp?.value
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {

            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }

    /**
     * the method to gardenInfo
     */
    private fun tDSSensor(deviceCode: String) {
        database = FirebaseDatabase.getInstance().reference
        // My top posts by number of stars
        var garden = getDataFromItent()

        database.child(garden).child(deviceCode).addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                var temp: DetailGardenFirebase? =
                    dataSnapshot.getValue(DetailGardenFirebase::class.java)
                ppm.text = temp?.value
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {

            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }

    /**
     * the method to gardenInfoRain
     * cảm biến nhiệt độ sẽ gọi hàm này để check trời sáng tối
     */
    private fun theLightSensor(deviceCode: String) {
        database = FirebaseDatabase.getInstance().reference

        var tempCurrent = temperature.text.toString()
        // My top posts by number of stars
        var garden = getDataFromItent()

        var gardenId = getDataFromItentGardenId()
        // get beg by garden
        var vegetable = database1.findVegetableByGardenId(gardenId)
        // getParamById
        var param: Param? = null
        if (vegetable.isNotEmpty()) {
            var paramId = vegetable[0].paramId
            if (paramId != 0) {
                param = database1.findParamById(paramId)
            }
        }
        database.child(garden).child(deviceCode).addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                var temp: DetailGardenFirebase? =
                    dataSnapshot.getValue(DetailGardenFirebase::class.java)
                if (param != null) {
                    // 0 is light
                    if (temp!!.value?.trim() == "0") {
                        if (tempCurrent.toInt() >= param.tempFromDay!!.toInt() && tempCurrent.toInt() <= param.tempToDay!!.toInt()) {
                            temperatureStatus.text = getString(R.string.home_status_safe)
                        } else {
                            temperatureStatus.text = getString(R.string.home_status_no_safe)
                            sendOnNotificationMode(
                                getString(R.string.title_mode),
                                getString(R.string.message_mode_temp)
                            )
                            if (tempCurrent.toInt() > param.tempToDay!!.toInt()) {
                                // kích hoạt máy bơm full sương

                                // kéo lưới cắt nắng

                            } else if (tempCurrent.toInt() < param.tempFromDay!!.toInt()) {
                                //bật đèn
                            }
                        }
                    } else {
                        if (tempCurrent.toInt() >= param.tempFromNight!!.toInt() && tempCurrent.toInt() <= param.tempToNight!!.toInt()) {
                            temperatureStatus.text = getString(R.string.home_status_safe)
                        } else {
                            temperatureStatus.text = getString(R.string.home_status_no_safe)
                            if (tempCurrent.toInt() > param.tempToNight!!.toInt()) {
                                // kích hoạt máy bơm full sương
                            }
                        }
                        // bật đèn
                    }
                } else {
                    if (temp!!.value?.trim() == "0") {
                        if (tempCurrent.toInt() in 25..29) {
                            temperatureStatus.text = getString(R.string.home_status_safe)
                        } else {
                            temperatureStatus.text = getString(R.string.home_status_no_safe)
                            sendOnNotificationMode(
                                getString(R.string.title_mode),
                                getString(R.string.message_mode_temp)
                            )
                        }
                    } else {
                        if (tempCurrent.toInt() in 20..25) {
                            temperatureStatus.text = getString(R.string.home_status_safe)
                        } else {
                            temperatureStatus.text = getString(R.string.home_status_no_safe)
                            sendOnNotificationMode(
                                getString(R.string.title_mode),
                                getString(R.string.message_mode_temp)
                            )
                        }
                    }
                }
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {

            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }

    /**
     * the method to pumpONOFFControl
     */
    private fun pumpONOFFControl(deviceCode: String) {
        database = FirebaseDatabase.getInstance().reference
        var gardenCode = getDataFromItent()
        var childValue = "value"
        database.child(gardenCode).child(deviceCode).child(childValue)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val value = dataSnapshot.value.toString()
                    if (value.trim() == "ON") {
                        button_bom_dung_dich.background = getDrawable(R.drawable.back_on)
                        button_bom_dung_dich.text = getString(R.string.ON_vi)
                        button_bom_dung_dich.setCompoundDrawablesRelativeWithIntrinsicBounds(
                            0,
                            R.drawable.ic_may_bom,
                            0,
                            0
                        )
                    } else {
                        button_bom_dung_dich.background = getDrawable(R.drawable.back)
                        button_bom_dung_dich.text = getString(R.string.OFF_vi)
                        button_bom_dung_dich.setCompoundDrawablesRelativeWithIntrinsicBounds(
                            0,
                            R.drawable.ic_may_bom_0,
                            0,
                            0
                        )
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Getting Post failed, log a message
                    Log.w("AAA", "loadPost:onCancelled", databaseError.toException())
                    // ...
                }
            })

    }


    /**
     * the method to tarpaulinONOFFControl
     */
    private fun tarpaulinONOFFControl(deviceCode: String) {
        database = FirebaseDatabase.getInstance().reference
        var gardenCode = getDataFromItent()
        var childValue = "value"
        database.child(gardenCode).child(deviceCode).child(childValue)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val value = dataSnapshot.value.toString()
                    when {
                        value.trim() == "L" -> {
                            button_tarpaulin_ahead.background = getDrawable(R.drawable.back_on)
                            button_tarpaulin_back.background = getDrawable(R.drawable.back)
                            button_bat_che_mua_stop.background = getDrawable(R.drawable.back)

                            button_tarpaulin_ahead.setCompoundDrawablesRelativeWithIntrinsicBounds(
                                0,
                                R.drawable.ic_ahead_on,
                                0,
                                0
                            )
                            button_tarpaulin_back.setCompoundDrawablesRelativeWithIntrinsicBounds(
                                0,
                                R.drawable.ic_back,
                                0,
                                0
                            )
                            button_bat_che_mua_stop.setCompoundDrawablesRelativeWithIntrinsicBounds(
                                0,
                                R.drawable.ic_stop,
                                0,
                                0
                            )
                        }
                        value.trim() == "R" -> {
                            button_tarpaulin_ahead.background = getDrawable(R.drawable.back)
                            button_tarpaulin_back.background = getDrawable(R.drawable.back_on)
                            button_bat_che_mua_stop.background = getDrawable(R.drawable.back)
                            button_tarpaulin_ahead.setCompoundDrawablesRelativeWithIntrinsicBounds(
                                0,
                                R.drawable.ic_ahead,
                                0,
                                0
                            )
                            button_tarpaulin_back.setCompoundDrawablesRelativeWithIntrinsicBounds(
                                0,
                                R.drawable.ic_back_on,
                                0,
                                0
                            )
                            button_bat_che_mua_stop.setCompoundDrawablesRelativeWithIntrinsicBounds(
                                0,
                                R.drawable.ic_stop,
                                0,
                                0
                            )
                        }
                        else -> {
                            button_tarpaulin_ahead.background = getDrawable(R.drawable.back)
                            button_tarpaulin_back.background = getDrawable(R.drawable.back)
                            button_bat_che_mua_stop.background = getDrawable(R.drawable.back_on)
                            button_tarpaulin_ahead.setCompoundDrawablesRelativeWithIntrinsicBounds(
                                0,
                                R.drawable.ic_ahead,
                                0,
                                0
                            )
                            button_tarpaulin_back.setCompoundDrawablesRelativeWithIntrinsicBounds(
                                0,
                                R.drawable.ic_back,
                                0,
                                0
                            )
                            button_bat_che_mua_stop.setCompoundDrawablesRelativeWithIntrinsicBounds(
                                0,
                                R.drawable.ic_not_stop,
                                0,
                                0
                            )
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Getting Post failed, log a message
                    Log.w("AAA", "loadPost:onCancelled", databaseError.toException())
                    // ...
                }
            })

    }

    /**
     * the method to lampONOFFControl
     */
    private fun lampONOFFControl(deviceCode: String) {
        database = FirebaseDatabase.getInstance().reference
        var gardenCode = getDataFromItent()
        var childValue = "value"
        database.child(gardenCode).child(deviceCode).child(childValue)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val value = dataSnapshot.value.toString()
                    if (value.trim() == "ON") {
                        button_bong_den.background = getDrawable(R.drawable.back_on)
                        button_bong_den.text = getString(R.string.ON_vi)
                        button_bong_den.setCompoundDrawablesRelativeWithIntrinsicBounds(
                            0,
                            R.drawable.ic_bong_den_sang,
                            0,
                            0
                        )
                    } else {
                        button_bong_den.background = getDrawable(R.drawable.back)
                        button_bong_den.text = getString(R.string.OFF_vi)
                        button_bong_den.setCompoundDrawablesRelativeWithIntrinsicBounds(
                            0,
                            R.drawable.ic_bong_den,
                            0,
                            0
                        )
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Getting Post failed, log a message
                    Log.w("AAA", "loadPost:onCancelled", databaseError.toException())
                    // ...
                }
            })

    }

    /**
     * the method to mistingPumpsONOFFControl
     */
    private fun mistingPumpsONOFFControl(deviceCode: String) {
        database = FirebaseDatabase.getInstance().reference
        var gardenCode = getDataFromItent()
        var childValue = "value"
        database.child(gardenCode).child(deviceCode).child(childValue)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val value = dataSnapshot.value.toString()
                    if (value.trim() == "ON") {
                        button_bom_phun_suong.background = getDrawable(R.drawable.back_on)
                        button_bom_phun_suong.text = getString(R.string.ON_vi)
                        button_bom_phun_suong.setCompoundDrawablesRelativeWithIntrinsicBounds(
                            0,
                            R.drawable.ic_phun_suong,
                            0,
                            0
                        )
                    } else {
                        button_bom_phun_suong.background = getDrawable(R.drawable.back)
                        button_bom_phun_suong.text = getString(R.string.OFF_vi)
                        button_bom_phun_suong.setCompoundDrawablesRelativeWithIntrinsicBounds(
                            0,
                            R.drawable.ic_phun_suong_off,
                            0,
                            0
                        )
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Getting Post failed, log a message
                    Log.w("AAA", "loadPost:onCancelled", databaseError.toException())
                    // ...
                }
            })

    }

    /**
     * the method to get data from intent
     */
    private fun getDataFromItent(): String {
        val bundle: Bundle = intent.extras
        return bundle.get("garden_code") as String

    }


    /**
     * the method to get data from intent
     */
    private fun getDataFromItentGardenId(): Int {
        val bundle: Bundle = intent.extras
        return bundle.get("garden_id") as Int

    }


    private fun rainAlert() {
        this.rain_status.setImageResource(R.drawable.khongmua)
        this.rain_text.text = getString(R.string.no_rain)
    }


    private fun sendOnNotificationMode(title: String, messageInput: String) {
        val notification = NotificationCompat.Builder(this, NotificationApp.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification_cc)
            .setContentTitle(title)
            .setContentText(messageInput)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .build()

        val notificationId = 1
        this.notificationManagerCompat!!.notify(notificationId, notification)
    }


    private fun getStatusDevice(name: String): String {
        devices = getListDeviceInfo()
        if (!devices.isNullOrEmpty()) {
            for (item in 0 until devices.size) {
                var status = devices[item].deviceDetailCodeSS
                if (status!!.contains(name, false)) {
                    return status
                }
            }
        }
        return ""
    }


}
