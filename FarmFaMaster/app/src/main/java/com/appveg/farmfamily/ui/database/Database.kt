package com.appveg.farmfamily.ui.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.security.keystore.StrongBoxUnavailableException
import android.util.Log
import com.appveg.farmfamily.ui.device.Device
import com.appveg.farmfamily.ui.device.DeviceDetail
import com.appveg.farmfamily.ui.device_catogory.DeviceCategory
import com.appveg.farmfamily.ui.garden.Garden
import com.appveg.farmfamily.ui.login.User
import com.appveg.farmfamily.ui.mode.Mode
import com.appveg.farmfamily.ui.mode.ModeDevice
import com.appveg.farmfamily.ui.param.Param
import com.appveg.farmfamily.ui.send.Batch
import com.appveg.farmfamily.ui.send.BatchCustom
import com.appveg.farmfamily.ui.send.BatchQtyDetail
import com.appveg.farmfamily.ui.share.Substance
import com.appveg.farmfamily.ui.share.SubstanceDetail
import com.appveg.farmfamily.ui.vegetable.Vegetable

class Database(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "smartfarm"
        /*table*/
        private val TABLE_USERS = "users"
        private val TABLE_ROLES = "roles"
        private val TABLE_BATCH = "batch"
        private val TABLE_BATCH_DETAIL = "bacth_quantity_detail"
        private val TABLE_VEGETABLE = "vegetable"
        private val TABLE_GARDEN = "garden"
        private val TABLE_DEVICE = "device"
        private val TABLE_DEVICE_DETAIL = "device_detail"
        private val TABLE_DEVICE_CATEGORY = "device_category"
        private val TABLE_SUBSTANCE_MASS = "substance_mass"
        private val TABLE_SUBSTANCE_MASS_DETAIL = "substance_mass_detail"
        private val TABLE_PARAM = "param"
        private val TABLE_MODE = "mode"
        private val TABLE_MODE_DEVICE = "mode_device"
        /*users*/
        private val COLUMN_USER_ID = "user_id"
        private val COLUMN_USER_EMAIL = "email"
        private val COLUMN_USER_NAME = "user_name"
        private val COLUMN_USER_FULL_NAME = "full_name"
        private val COLUMN_USER_PASSWORD = "password"
        private val COLUMN_USER_GENDER = "gender"
        private val COLUMN_USER_STATUS = "status"

        /*users*/
        private val COLUMN_ROLE_ID = "role_id"

        /*common*/
        private val COLUMN_CREATEDBY = "created_by"
        private val COLUMN_CREATEDDATE = "created_date"
        private val COLUMN_UPDATED_BY = "updated_by"
        private val COLUMN_UPDATED_DATE = "updated_date"

        /*batch*/
        private val COLUMN_BATCH_ID = "batch_id"
        private val COLUMN_BATCH_IMAGE = "batch_image"
        private val COLUMN_BATCH_NAME = "batch_name"
        private val COLUMN_BATCH_END_DATE = "the_end_date"
        private val COLUMN_BATCH_TOTAL_QTY = "total_quantity"
        private val COLUMN_BATCH_GARDEN_ID = "garden_id"

        /*batch detail*/
        private val COLUMN_BATCH_DETAIL_ID = "qty_detail_id"
        private val COLUMN_BATCH_QTY_ID = "qty_id"
        private val COLUMN_BATCH_VEG_NAME = "vegetable_name"
        private val COLUMN_BATCH_VEG_QTY = "vegetable_quantity"

        /*vegetable table*/
        private val COLUMN_VEG_ID = "veg_id"
        private val COLUMN_VEG_NAME = "veg_name"
        private val COLUMN_VEG_CODE = "veg_code"
        private val COLUMN_VEG_IMG_BLOB = "veg_image_blob"

        /*garden*/
        private val COLUMN_GARDEN_ID = "garden_id"
        private val COLUMN_GARDEN_NAME = "garden_name"
        private val COLUMN_GARDEN_IMAGE = "garden_image"
        private val COLUMN_GARDEN_CODE = "garden_code"

        /*device category*/
        private val COLUMN_DEVICE_CATEGORY_ID = "device_category_id"
        private val COLUMN_DEVICE_CATEGORY_NAME = "device_category_name"
        private val COLUMN_DEVICE_CATEGORY_IMAGE = "device_category_image"
        private val COLUMN_DEVICE_CATEGORY_CODE = "device_category_code"

        /*device*/
        private val COLUMN_DEVICE_ID = "device_id"
        private val COLUMN_DEVICE_NAME = "device_name"
        private val COLUMN_DEVICE_IMAGE = "device_image"
        private val COLUMN_DEVICE_NUM = "device_num"

        /*device detail*/
        private val COLUMN_DEVICE_DETAIL_ID = "device_detail_id"
        private val COLUMN_DEVICE_DETAIL_IMAGE = "device_detail_image"
        private val COLUMN_DEVICE_DETAIL_CODE = "device_detail_code"
        private val COLUMN_DEVICE_DETAIL_CODE_SS = "device_detail_code_ss"
        private val COLUMN_DEVICE_DETAIL_STATUS = "device_detail_status"


        /*substance mass*/
        private val COLUMN_SUBSTANCE_ID = "substance_mass_id"
        private val COLUMN_SUBSTANCE_NAME = "substance_mass_name"
        private val COLUMN_SUBSTANCE_IMAGE = "substance_mass_image"
        private val COLUMN_SUBSTANCE_TOTAL = "total_substance_mass"
        private val COLUMN_SUBSTANCE_CATEGORY = "substance_category"

        /*substance mass detail*/
        private val COLUMN_SUBSTANCE_DETAIL_ID = "substance_mass_detail_id"
        private val COLUMN_SUBSTANCE_DETAIL_NAME = "substance_mass_detail_name"
        private val COLUMN_SUBSTANCE_DETAIL_IMAGE = "substance_mass_detail_image"
        private val COLUMN_SUBSTANCE_DETAIL_NUM = "substance_mass_detail_num"
        private val COLUMN_SUBSTANCE_DETAIL_CATEGORY = "substance_mass_detail_category"

        /*param*/
        private val COLUMN_PARAM_ID = "param_id"
        private val COLUMN_TEMP_TO_NIGHT = "temp_to_night"
        private val COLUMN_TEMP_FROM_NIGHT = "temp_from_night"
        private val COLUMN_TEMP_TO_DAY = "temp_to_day"
        private val COLUMN_TEMP_FROM_DAY = "temp_from_day"
        private val COLUMN_PH_TO = "ph_to"
        private val COLUMN_PH_FROM = "ph_from"
        private val COLUMN_PPM_TO = "ppm_to"
        private val COLUMN_PPM_FROM = "ppm_from"
        private val COLUMN_TDS_LEVEL_TO = "tds_level_to"
        private val COLUMN_TDS_LEVEL_FROM = "tds_level_from"

        /*mode*/
        private val COLUMN_MODE_ID = "mode_id"
        private val COLUMN_MODE_CODE = "code"
        private val COLUMN_MODE_TIME_ON = "time_on"
        private val COLUMN_MODE_TIME_OFF = "time_off"
        private val COLUMN_MODE_ON = "on1"
        private val COLUMN_MODE_OFF = "off"
        private val COLUMN_MODE_TIME_REPEAT = "time_repeat"
        private val COLUMN_MODE_REPEAT = "repeat"
        private val COLUMN_MODE_ACTIVE_FLAG = "active_flag"

        /*mode device*/
        private val COLUMN_MODE_DEVICE_ID = "mode_device_id"

    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS)
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_ROLES)
        onCreate(db)
    }

    override fun onCreate(db: SQLiteDatabase?) {
        /*TABLE PROJECT*/
        val CREATE_USERS_TABLE =
            ("CREATE TABLE " + TABLE_USERS + " (user_id INTEGER PRIMARY KEY AUTOINCREMENT,full_name VARCHAR(50)," +
                    "email VARCHAR(50),password VARCHAR(50),gender VARCHAR(10),status INTEGER,role_id INTEGER,created_by VARCHAR(50)," +
                    "created_date VARCHAR(50),updated_by VARCHAR(50),updated_date VARCHAR(50),deleted_by VARCHAR(50),deleted_date VARCHAR(50),deleted_flag INTEGER)")
        val CREATE_ROLES_TABLE =
            ("CREATE TABLE " + TABLE_ROLES + " (role_id INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR(50),code VARCHAR(50),created_by VARCHAR(50)," +
                    "created_date VARCHAR(50),updated_by VARCHAR(50),updated_date VARCHAR(50),deleted_by VARCHAR(50),deleted_date VARCHAR(50),deleted_flag INTEGER)")
        val CREATE_BATCH_TABLE =
            ("CREATE TABLE " + TABLE_BATCH + " (batch_id INTEGER PRIMARY KEY AUTOINCREMENT,batch_image VARCHAR(100),batch_name VARCHAR(100),the_end_date VARCHAR(15)," +
                    "total_quantity VARCHAR(100),garden_id INTEGER,created_by VARCHAR(50),created_date VARCHAR(50),updated_by VARCHAR(50),updated_date VARCHAR(50),deleted_by VARCHAR(50)," +
                    "deleted_date VARCHAR(50),deleted_flag INTEGER)")
        val CREATE_QUANTITY_DETAIL_TABLE =
            ("CREATE TABLE " + TABLE_BATCH_DETAIL + "(qty_detail_id INTEGER PRIMARY KEY AUTOINCREMENT,vegetable_name VARCHAR(100)," +
                    "vegetable_quantity VARCHAR(100),qty_id INTEGER,created_by VARCHAR(50),created_date VARCHAR(50),updated_by VARCHAR(50),updated_date VARCHAR(50),deleted_by VARCHAR(50)," +
                    "deleted_date VARCHAR(50),deleted_flag INTEGER)")

        val CREATE_VEGETABLE_TABLE =
            ("CREATE TABLE " + TABLE_VEGETABLE + "(veg_id INTEGER PRIMARY KEY AUTOINCREMENT,veg_name VARCHAR(100)," +
                    "veg_image_blob VARCHAR(300),garden_id INTEGER,param_id INTEGER,created_by VARCHAR(50),created_date VARCHAR(50),updated_by VARCHAR(50),updated_date VARCHAR(50),deleted_by VARCHAR(50)," +
                    "deleted_date VARCHAR(50),deleted_flag INTEGER)")

        val CREATE_GARDEN_TABLE =
            ("CREATE TABLE " + TABLE_GARDEN + "(garden_id INTEGER PRIMARY KEY AUTOINCREMENT,garden_code VARCHAR(100),garden_name VARCHAR(100)," +
                    "garden_image BLOB,created_by VARCHAR(50),created_date VARCHAR(50),updated_by VARCHAR(50),updated_date VARCHAR(50),deleted_by VARCHAR(50)," +
                    "deleted_date VARCHAR(50),deleted_flag INTEGER)")

        val CREATE_DEVICE_TABLE =
            ("CREATE TABLE " + TABLE_DEVICE + "(device_id INTEGER PRIMARY KEY AUTOINCREMENT,device_name VARCHAR(100)," +
                    "device_image VARCHAR(300), device_num VARCHAR(100),device_category_id INTEGER)")

        val CREATE_DEVICE_DETAIL_TABLE =
            ("CREATE TABLE " + TABLE_DEVICE_DETAIL + "(device_detail_id INTEGER PRIMARY KEY AUTOINCREMENT,device_detail_code VARCHAR(100)," +
                    "device_detail_code_ss VARCHAR(100), device_detail_image VARCHAR(300),device_detail_status VARCHAR(50),device_id INTEGER,garden_id INTEGER,created_by VARCHAR(50),created_date VARCHAR(50),updated_by VARCHAR(50),updated_date VARCHAR(50),deleted_by VARCHAR(50)," +
                    "deleted_date VARCHAR(50),deleted_flag INTEGER)")

        val CREATE_SUBSTANCE_TABLE =
            ("CREATE TABLE " + TABLE_SUBSTANCE_MASS + "(substance_mass_id INTEGER PRIMARY KEY AUTOINCREMENT,substance_mass_name VARCHAR(100)," +
                    "substance_mass_image VARCHAR(300), total_substance_mass VARCHAR(100),substance_category VARCHAR(100),garden_id INTEGER)")

        val CREATE_SUBSTANCE_DETAIL_TABLE =
            ("CREATE TABLE " + TABLE_SUBSTANCE_MASS_DETAIL + "(substance_mass_detail_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "substance_mass_detail_name VARCHAR(50),substance_mass_detail_image VARCHAR(300),substance_mass_detail_num VARCHAR(50),substance_mass_detail_category VARCHAR(50),substance_mass_id INTEGER,garden_id INTEGER,created_by VARCHAR(50),created_date VARCHAR(50),updated_by VARCHAR(50),updated_date VARCHAR(50),deleted_by VARCHAR(50)," +
                    "deleted_date VARCHAR(50),deleted_flag INTEGER)")

        val CREATE_DEVICE_CATEGORY_TABLE =
            ("CREATE TABLE " + TABLE_DEVICE_CATEGORY + "(device_category_id INTEGER PRIMARY KEY AUTOINCREMENT,device_category_code VARCHAR(100),device_category_name VARCHAR(100)," +
                    "device_category_image VARCHAR(300),created_by VARCHAR(50),created_date VARCHAR(50),updated_by VARCHAR(50),updated_date VARCHAR(50),deleted_by VARCHAR(50)," +
                    "deleted_date VARCHAR(50),deleted_flag INTEGER)")

        val CREATE_PARAM_TABLE =
            ("CREATE TABLE " + TABLE_PARAM + "(param_id INTEGER PRIMARY KEY AUTOINCREMENT,veg_id INTEGER,temp_to_night VARCHAR(100),temp_from_night VARCHAR(100)," +
                    "temp_to_day VARCHAR(100), temp_from_day VARCHAR(100),  ph_to VARCHAR(100), ph_from VARCHAR(100),ppm_to VARCHAR(100),ppm_from VARCHAR(100),tds_level_to VARCHAR(100),tds_level_from VARCHAR(100), created_by VARCHAR(50),created_date VARCHAR(50),updated_by VARCHAR(50),updated_date VARCHAR(50),deleted_by VARCHAR(50)," +
                    "deleted_date VARCHAR(50),deleted_flag INTEGER)")

        val CREATE_MODE_TABLE =
            ("CREATE TABLE " + TABLE_MODE + "(mode_id INTEGER PRIMARY KEY AUTOINCREMENT,code VARCHAR(100),time_on VARCHAR(100)," +
                    "time_off VARCHAR(100),on1 VARCHAR(100),off VARCHAR(100),time_repeat VARCHAR(100),repeat VARCHAR(100),active_flag VARCHAR(100),created_by VARCHAR(50),created_date VARCHAR(50),updated_by VARCHAR(50),updated_date VARCHAR(50),deleted_by VARCHAR(50)," +
                    "deleted_date VARCHAR(50),deleted_flag INTEGER)")
        val CREATE_MODE_DEVICE_TABLE =
            ("CREATE TABLE $TABLE_MODE_DEVICE(mode_device_id INTEGER PRIMARY KEY AUTOINCREMENT,mode_id INTEGER,device_id INTEGER)")


        /*INSERT DATA*/
//        val INSERT_GARDEN_ITEM =
//            ("INSERT INTO garden VALUES(null,'Khu vườn 1','R.drawable.kv2','admin',null,null,null,null,null,1)")
//        val INSERT_GARDEN1_ITEM =
//            ("INSERT INTO garden VALUES(null,'Khu vườn 2','R.drawable.kv2','admin',null,null,null,null,null,1)")
        val INSERT_ROLES_ITEM =
            ("INSERT INTO roles VALUES(null,'admin','admin','vu',null,null,null,null,null,1)")
        val INSERT_ROLES_ITEM_1 =
            ("INSERT INTO roles VALUES(null,'user','user','vu',null,null,null,null,null,1)")
        val INSERT_USERS_ITEM =
            ("INSERT INTO users VALUES(null,'NGUYEN HOANG VU','hvu3011@gmail.com','21232f297a57a5a743894ae4a801fc3','1','active',1,'vu',null,null,null,null,null,1)")
        db?.execSQL(CREATE_USERS_TABLE)
        db?.execSQL(CREATE_ROLES_TABLE)
        db?.execSQL(CREATE_GARDEN_TABLE)
        db?.execSQL(INSERT_ROLES_ITEM)
        db?.execSQL(INSERT_ROLES_ITEM_1)
        db?.execSQL(INSERT_USERS_ITEM)
        db?.execSQL(CREATE_BATCH_TABLE)
        db?.execSQL(CREATE_QUANTITY_DETAIL_TABLE)
        db?.execSQL(CREATE_VEGETABLE_TABLE)
        db?.execSQL(CREATE_DEVICE_TABLE)
        db?.execSQL(CREATE_DEVICE_DETAIL_TABLE)
        db?.execSQL(CREATE_SUBSTANCE_TABLE)
        db?.execSQL(CREATE_SUBSTANCE_DETAIL_TABLE)
        db?.execSQL(CREATE_DEVICE_CATEGORY_TABLE)
        db?.execSQL(CREATE_PARAM_TABLE)
        db?.execSQL(CREATE_MODE_TABLE)
        db?.execSQL(CREATE_MODE_DEVICE_TABLE)


    }

    //exec query
    fun QueryData(sql: String): Unit {
        var database: SQLiteDatabase = writableDatabase
        database.execSQL(sql)
    }

    // get data
    fun getData(sql: String): Cursor {
        var database: SQLiteDatabase = readableDatabase
        return database.rawQuery(sql, null)
    }
    /*----------------------------------------------LOGIN-------------------------------------------------*/
    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */
    fun getUserByEmail(email: String): User {
        val selectQuery = "SELECT * FROM $TABLE_USERS WHERE $COLUMN_USER_EMAIL = $email"
        val db = this.readableDatabase
        var user: User = User()
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            Log.d("AAA", e.message)
        }
        var userId: Int
        var userNameEmail: String
        var userFullName: String
        var password: String
        var status: Int
        var roleId: Int
        if (cursor!!.moveToFirst()) {
            do {
                userId = cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID))
                userNameEmail = cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL))
                userFullName = cursor.getString(cursor.getColumnIndex(COLUMN_USER_FULL_NAME))
                password = cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD))
                status = cursor.getInt(cursor.getColumnIndex(COLUMN_USER_STATUS))
                roleId = cursor.getInt(cursor.getColumnIndex(COLUMN_ROLE_ID))
                user = User(userId, userFullName, userNameEmail, password, status, roleId)
            } while (cursor.moveToNext())
        }
        return user
    }

    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */
    fun getAllUser(): ArrayList<User> {
        var userList: ArrayList<User> = ArrayList()
        val selectQuery = "SELECT * FROM $TABLE_USERS"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var userId: Int
        var userNameEmail: String
        var userFullName: String
        var password: String
        var status: Int
        var roleId: Int
        if (cursor.moveToFirst()) {
            do {
                userId = cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID))
                userNameEmail = cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL))
                userFullName = cursor.getString(cursor.getColumnIndex(COLUMN_USER_FULL_NAME))
                password = cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD))
                status = cursor.getInt(cursor.getColumnIndex(COLUMN_USER_STATUS))
                roleId = cursor.getInt(cursor.getColumnIndex(COLUMN_ROLE_ID))
                val user = User(userId, userFullName, userNameEmail, password, status, roleId)
                userList.add(user)
            } while (cursor.moveToNext())
        }
        return userList
    }

    /**
     * This method is to create user record
     *
     * @param user
     */
    fun addUser(user: User): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_USER_FULL_NAME, user.fullName)
        values.put(COLUMN_USER_EMAIL, user.email)
        values.put(COLUMN_USER_PASSWORD, user.password)
        values.put(COLUMN_USER_GENDER, user.gender)
        values.put(COLUMN_USER_STATUS, user.status)
        values.put(COLUMN_ROLE_ID, user.roleId)
        values.put(COLUMN_CREATEDBY, user.createdBy)
        values.put(COLUMN_CREATEDDATE, user.createdDate)

        // Inserting Row
        val success = db.insert(TABLE_USERS, null, values)
        db.close()
        return success
    }

    /**
     * This method to update user record
     *
     * @param user
     */
    fun updateUser(user: User) {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(COLUMN_USER_PASSWORD, user.password)
        values.put(COLUMN_UPDATED_DATE, user.updatedDate)

        // updating row
        db.update(
            TABLE_USERS, values, "$COLUMN_USER_ID = ?",
            arrayOf(user.id.toString())
        )
        db.close()
    }

    fun updateStatusByUserNameEmail(userNameEmail: String, status: Int): Int {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(COLUMN_USER_EMAIL, userNameEmail)
        contentValues.put(COLUMN_USER_STATUS, status)

        // updating row
        val success = db.update(
            TABLE_USERS, contentValues, "$COLUMN_USER_EMAIL = ?",
            arrayOf(userNameEmail)
        )
        db.close()
        return success
    }

    fun updateStatusById(user: User): Int {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(COLUMN_USER_ID, user.id)
        contentValues.put(COLUMN_USER_STATUS, user.status)

        // updating row
        val success = db.update(
            TABLE_USERS, contentValues, "$COLUMN_USER_ID = ?",
            arrayOf(user.id.toString())
        )
        db.close()
        return success
    }

    /**
     * This method is to delete user record
     *
     * @param user
     */
    fun deleteUser(user: User) {

        val db = this.writableDatabase
        // delete user record by id
        db.delete(
            TABLE_USERS, "$COLUMN_USER_ID = ?",
            arrayOf(user.id.toString())
        )
        db.close()

    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @return true/false
     */
    fun checkUser(email: String): Boolean {

        // array of columns to fetch
        val columns = arrayOf(COLUMN_USER_ID)
        val db = this.readableDatabase

        // selection criteria
        val selection = "$COLUMN_USER_EMAIL = ?"

        // selection argument
        val selectionArgs = arrayOf(email)

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        val cursor = db.query(
            TABLE_USERS, //Table to query
            columns,        //columns to return
            selection,      //columns for the WHERE clause
            selectionArgs,  //The values for the WHERE clause
            null,  //group the rows
            null,   //filter by row groups
            null
        )  //The sort order


        val cursorCount = cursor.count
        cursor.close()
        db.close()

        if (cursorCount > 0) {
            return true
        }

        return false
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @param password
     * @return true/false
     */
    fun checkUser(email: String, password: String): Boolean {

        // array of columns to fetch
        val columns = arrayOf(COLUMN_USER_ID)

        val db = this.readableDatabase

        // selection criteria
        val selection = "$COLUMN_USER_EMAIL = ? AND $COLUMN_USER_PASSWORD = ?"

        // selection arguments
        val selectionArgs = arrayOf(email, password)

        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        val cursor = db.query(
            TABLE_USERS, //Table to query
            columns, //columns to return
            selection, //columns for the WHERE clause
            selectionArgs, //The values for the WHERE clause
            null,  //group the rows
            null, //filter by row groups
            null
        ) //The sort order

        val cursorCount = cursor.count
        cursor.close()
        db.close()

        if (cursorCount > 0) {
            return true
        }
        return false

    }

    /*----------------------------------------------QUANTITY-------------------------------------------------*/
    /**
     * This method to insert data
     *
     * @param batch
     * @return Long
     */
    fun addBatch(batch: Batch): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_BATCH_ID, batch.batchId)
        contentValues.put(COLUMN_BATCH_IMAGE, batch.batchImage)
        contentValues.put(COLUMN_BATCH_NAME, batch.batchName)
        contentValues.put(COLUMN_BATCH_END_DATE, batch.theEndDate)
        contentValues.put(COLUMN_BATCH_TOTAL_QTY, batch.totalQuantity)
        contentValues.put(COLUMN_BATCH_GARDEN_ID, batch.gardenId)
        contentValues.put(COLUMN_CREATEDBY, batch.createdBy)
        contentValues.put(COLUMN_CREATEDDATE, batch.createdDate)

        // Inserting Row
        val success = db.insert(TABLE_BATCH, null, contentValues)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    /**
     * This method to insert data for batch detail
     *
     * @param batchQtyDetail
     * @return Long
     */
    fun addBatchDetail(batchQtyDetail: BatchQtyDetail): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_BATCH_DETAIL_ID, batchQtyDetail.qtyDetailId)
        contentValues.put(COLUMN_BATCH_QTY_ID, batchQtyDetail.qtyId)
        contentValues.put(COLUMN_BATCH_VEG_NAME, batchQtyDetail.vegetableName)
        contentValues.put(COLUMN_BATCH_VEG_QTY, batchQtyDetail.vegetableQuantity)
        contentValues.put(COLUMN_CREATEDBY, batchQtyDetail.createdBy)
        contentValues.put(COLUMN_CREATEDDATE, batchQtyDetail.createdDate)

        // Inserting Row
        val success = db.insert(TABLE_BATCH_DETAIL, null, contentValues)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }


    /**
     * This method to find by id
     *
     * @param batchQtyDetail
     * @return Arraylist
     */
    fun viewBatchByGardenId(garden_id: Int): ArrayList<BatchCustom> {
        val batchList: ArrayList<BatchCustom> = ArrayList()
        val selectQuery = "SELECT  * FROM $TABLE_BATCH WHERE $COLUMN_BATCH_GARDEN_ID=$garden_id"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var batchId: Int
        var batchName: String
        var batchImage: String
        var gardenId: Int
        var totalQty: String
        if (cursor.moveToFirst()) {
            do {
                batchId = cursor.getInt(cursor.getColumnIndex(COLUMN_BATCH_ID))
                batchName = cursor.getString(cursor.getColumnIndex(COLUMN_BATCH_NAME))
                batchImage = cursor.getString(cursor.getColumnIndex(COLUMN_BATCH_IMAGE))
                gardenId = cursor.getInt(cursor.getColumnIndex(COLUMN_BATCH_GARDEN_ID))
                totalQty = cursor.getString(cursor.getColumnIndex(COLUMN_BATCH_TOTAL_QTY))
                val batch = BatchCustom(batchId, batchImage, batchName, totalQty, gardenId)
                batchList.add(batch)
            } while (cursor.moveToNext())
        }
        return batchList
    }

    /**
     * This method to delete data
     *
     * @param batch_id
     * @return Int
     */
    fun deleteBatch(batch_id: Int): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_BATCH_ID, batch_id) // EmpModelClass UserId
        // Deleting Row
        val success = db.delete(TABLE_BATCH, "batch_id=$batch_id", null)
        deleteBatchDetail(batch_id)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    /**
     * This method to delete data
     *
     * @param batch_id
     * @return Int
     */
    fun deleteBatchDetail(batchQtyDetailId: Int): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_BATCH_DETAIL_ID, batchQtyDetailId) // EmpModelClass UserId
        // Deleting Row
        val success =
            db.delete(TABLE_BATCH_DETAIL, "$COLUMN_BATCH_DETAIL_ID=$batchQtyDetailId", null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    /**
     * This method to delete batch by batch detail id
     *
     * @param batch_id
     * @return Int
     */
    fun deleteBatchDetailByBatchDetailId(batchQtyDetailId: Int): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_BATCH_DETAIL_ID, batchQtyDetailId) // EmpModelClass UserId
        // Deleting Row
        val success = db.delete(TABLE_BATCH_DETAIL, "qty_detail_id=$batchQtyDetailId", null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    /**
     * This method to update data
     *
     * @param batchQtyDetail
     * @return true/false
     */
    fun updateBatch(batch: Batch): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_BATCH_ID, batch.batchId)
        contentValues.put(COLUMN_BATCH_NAME, batch.batchName)
        contentValues.put(COLUMN_BATCH_END_DATE, batch.theEndDate)
        contentValues.put(COLUMN_BATCH_TOTAL_QTY, batch.totalQuantity)
        contentValues.put(COLUMN_BATCH_GARDEN_ID, batch.gardenId)
        contentValues.put(COLUMN_CREATEDBY, batch.createdBy)
        contentValues.put(COLUMN_CREATEDDATE, batch.createdDate)
        contentValues.put(COLUMN_UPDATED_BY, batch.updatedBy)
        contentValues.put(COLUMN_UPDATED_DATE, batch.updatedDate)

        // Updating Row
        val success = db.update(TABLE_BATCH, contentValues, "batch_id=" + batch.batchId, null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    /**
     * This method to update data
     *
     * @param batchQtyDetail
     * @return true/false
     */
    fun updateBatchDetail(batchQtyDetail: BatchQtyDetail): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_BATCH_DETAIL_ID, batchQtyDetail.qtyDetailId)
        contentValues.put(COLUMN_BATCH_QTY_ID, batchQtyDetail.qtyId)
        contentValues.put(COLUMN_BATCH_VEG_NAME, batchQtyDetail.vegetableName)
        contentValues.put(COLUMN_BATCH_VEG_QTY, batchQtyDetail.vegetableQuantity)
        contentValues.put(COLUMN_CREATEDBY, batchQtyDetail.createdBy)
        contentValues.put(COLUMN_CREATEDDATE, batchQtyDetail.createdDate)
        contentValues.put(COLUMN_UPDATED_BY, batchQtyDetail.updatedBy)


        // Updating Row
        val success = db.update(
            TABLE_BATCH_DETAIL,
            contentValues,
            "qty_detail_id=" + batchQtyDetail.qtyDetailId,
            null
        )
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    /**
     * This method to find batch by id
     *
     * @param batch_id
     * @return Batch
     */
    fun findBatchById(batch_id: Int): Batch {
        val selectQuery = "SELECT  * FROM $TABLE_BATCH WHERE $COLUMN_BATCH_ID = $batch_id"
        val db = this.readableDatabase
        var batch: Batch = Batch()
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            Log.d("AAA", e.message)
        }
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    var batch_id = cursor.getInt(cursor.getColumnIndex(COLUMN_BATCH_ID))
                    var batch_name = cursor.getString(cursor.getColumnIndex(COLUMN_BATCH_NAME))
                    var created_date = cursor.getString(cursor.getColumnIndex(COLUMN_CREATEDDATE))
                    var the_end_date =
                        cursor.getString(cursor.getColumnIndex(COLUMN_BATCH_END_DATE))
                    var total_quantity =
                        cursor.getString(cursor.getColumnIndex(COLUMN_BATCH_TOTAL_QTY))
                    var updated_date = cursor.getString(cursor.getColumnIndex(COLUMN_UPDATED_DATE))
                    batch = Batch(
                        batch_id,
                        batch_name,
                        the_end_date,
                        total_quantity,
                        created_date,
                        "admin",
                        updated_date
                    )
                } while (cursor.moveToNext())
            }
        }
        return batch
    }

    /**
     * This method to find batch
     *
     * @param batch_id
     * @return Batch
     */
    fun findAllBatch(): ArrayList<Batch> {
        val selectQuery = "SELECT  * FROM $TABLE_BATCH"
        val db = this.readableDatabase
        var batchs: ArrayList<Batch> = ArrayList()
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            Log.d("AAA", e.message)
        }
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    var batchId = cursor.getInt(cursor.getColumnIndex(COLUMN_BATCH_ID))
                    var batchName = cursor.getString(cursor.getColumnIndex(COLUMN_BATCH_NAME))
                    var createdDate = cursor.getString(cursor.getColumnIndex(COLUMN_CREATEDDATE))
                    var theEndDate = cursor.getString(cursor.getColumnIndex(COLUMN_BATCH_END_DATE))
                    var totalQuantity =
                        cursor.getString(cursor.getColumnIndex(COLUMN_BATCH_TOTAL_QTY))
                    var updatedDate = cursor.getString(cursor.getColumnIndex(COLUMN_UPDATED_DATE))
                    var batch = Batch(
                        batchId,
                        batchName,
                        theEndDate,
                        totalQuantity,
                        createdDate,
                        "admin",
                        updatedDate
                    )
                    batchs.add(batch)
                } while (cursor.moveToNext())
            }
        }
        return batchs
    }

    /**
     * This method to find all batch detail by id
     *
     * @param batch_id
     * @return ArrayList
     */
    fun findAllBatchDetailById(batch_id: Int): ArrayList<BatchQtyDetail> {
        var batchDetailList: ArrayList<BatchQtyDetail> = ArrayList()
        val selectQuery =
            "SELECT  * FROM $TABLE_BATCH_DETAIL WHERE  $COLUMN_BATCH_QTY_ID = $batch_id"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var qty_detail_id: Int
        var vegetable_name: String
        var vegetable_quantity: String
        if (cursor.moveToFirst()) {
            do {
                qty_detail_id = cursor.getInt(cursor.getColumnIndex(COLUMN_BATCH_DETAIL_ID))
                vegetable_name = cursor.getString(cursor.getColumnIndex(COLUMN_BATCH_VEG_NAME))
                vegetable_quantity = cursor.getString(cursor.getColumnIndex(COLUMN_BATCH_VEG_QTY))
                val batchdetail =
                    BatchQtyDetail(qty_detail_id, vegetable_name, vegetable_quantity, "admin")
                batchDetailList.add(batchdetail)
            } while (cursor.moveToNext())
        }
        return batchDetailList
    }

    /*----------------------------------------------GARDEN-------------------------------------------------*/
    /**
     * This method to find all garden
     *
     * @param batchQtyDetail
     * @return true/false
     */
    fun findAllGarden(): ArrayList<Garden> {
        val gardenList: ArrayList<Garden> = ArrayList()
        val selectQuery = "SELECT  * FROM $TABLE_GARDEN"
        val db = this.readableDatabase
        var garden: Garden = Garden()
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var garden_id: Int
        var garden_name: String
        var garden_image: String
        var garden_code: String
        if (cursor.moveToFirst()) {
            do {
                garden_id = cursor.getInt(cursor.getColumnIndex(COLUMN_GARDEN_ID))
                garden_name = cursor.getString(cursor.getColumnIndex(COLUMN_GARDEN_NAME))
                garden_image = cursor.getString(cursor.getColumnIndex(COLUMN_GARDEN_IMAGE))
                garden_code = cursor.getString(cursor.getColumnIndex(COLUMN_GARDEN_CODE))

                garden = Garden()
                garden.gardenId = garden_id
                garden.gardenCode = garden_code
                garden.gardenName = garden_name
                garden.gardenImage = garden_image
                gardenList.add(garden)
            } while (cursor.moveToNext())
        }
        return gardenList
    }

    /**
     * This method to insert data vegetable
     *
     * @param batchQtyDetail
     * @return true/false
     */
    fun addGardenImageDefault(garden: Garden): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_GARDEN_NAME, garden.gardenName)
        contentValues.put(COLUMN_GARDEN_IMAGE, garden.gardenImage)
        contentValues.put(COLUMN_CREATEDBY, garden.createdBy)
        contentValues.put(COLUMN_CREATEDDATE, garden.createdDate)
        contentValues.put(COLUMN_GARDEN_CODE, garden.gardenCode)

        // Inserting Row
        val success = db.insert(TABLE_GARDEN, null, contentValues)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    /**
     * This method to update data garden
     *
     * @param garden
     * @return int
     */
    fun updateGardenImageDefault(garden: Garden): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_GARDEN_ID, garden.gardenId)
        contentValues.put(COLUMN_GARDEN_NAME, garden.gardenName)
        contentValues.put(COLUMN_GARDEN_IMAGE, garden.gardenImage)
        contentValues.put(COLUMN_UPDATED_DATE, garden.updatedDate)

        // Inserting Row
        val success = db.update(TABLE_GARDEN, contentValues, "garden_id=" + garden.gardenId, null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    /**
     * This method to delete data
     *
     * @param batch_id
     * @return Int
     */
    fun deleteGarden(garden_id: Int): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_GARDEN_ID, garden_id)
        // Deleting Row
        val success = db.delete(TABLE_GARDEN, "garden_id=" + garden_id, null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    /**
     * This method to find garden by id
     *
     * @param batch_id
     * @return Batch
     */
    fun findGardenById(garden_id: Int): Garden {
        val selectQuery = "SELECT  * FROM $TABLE_GARDEN WHERE $COLUMN_GARDEN_ID = $garden_id"
        val db = this.readableDatabase
        var garden: Garden = Garden()
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            Log.d("AAA", e.message)
        }
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    var gardenId = cursor.getInt(cursor.getColumnIndex(COLUMN_GARDEN_ID))
                    var gardenName = cursor.getString(cursor.getColumnIndex(COLUMN_GARDEN_NAME))
                    var gardenImage = cursor.getString(cursor.getColumnIndex(COLUMN_GARDEN_IMAGE))
                    var gardenCode = cursor.getString(cursor.getColumnIndex(COLUMN_GARDEN_CODE))
                    garden = Garden()
                    garden.gardenId = gardenId
                    garden.gardenCode = gardenCode
                    garden.gardenName = gardenName
                    garden.gardenImage = gardenImage
                } while (cursor.moveToNext())
            }
        }
        cursor?.close()
        return garden
    }


    /*----------------------------------------------VEGETABLE-------------------------------------------------*/
    /**
     * This method to insert data vegetable
     *
     * @param batchQtyDetail
     * @return true/false
     */
    fun addVegImageDefault(veg: Vegetable): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_VEG_NAME, veg.vegName)
        contentValues.put(COLUMN_VEG_IMG_BLOB, veg.vegImgBlob)
        contentValues.put(COLUMN_CREATEDBY, veg.createdBy)
        contentValues.put(COLUMN_CREATEDDATE, veg.createdDate)

        // Inserting Row
        val success = db.insert(TABLE_VEGETABLE, null, contentValues)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    /**
     * This method to update data vegetable
     *
     * @param veg
     * @return true/false
     */
    fun updateVegImageDefault(veg: Vegetable): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_VEG_ID, veg.vegID)
        contentValues.put(COLUMN_VEG_NAME, veg.vegName)
        contentValues.put(COLUMN_VEG_IMG_BLOB, veg.vegImgBlob)
        contentValues.put(COLUMN_UPDATED_DATE, veg.updatedDate)

        // Inserting Row
        val success = db.update(TABLE_VEGETABLE, contentValues, "veg_id=" + veg.vegID, null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    /**
     * This method to insert data vegetable
     *
     * @return ArrayList
     */
    fun findAllVegetable(): ArrayList<Vegetable> {
        val vegList: ArrayList<Vegetable> = ArrayList()
        val selectQuery = "SELECT  * FROM $TABLE_VEGETABLE"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var veg_id: Int
        var veg_name: String
        var veg_image: String
        var garden_id: Int
        if (cursor.moveToFirst()) {
            do {
                veg_id = cursor.getInt(cursor.getColumnIndex(COLUMN_VEG_ID))
                veg_name = cursor.getString(cursor.getColumnIndex(COLUMN_VEG_NAME))
                veg_image = cursor.getString(cursor.getColumnIndex(COLUMN_VEG_IMG_BLOB))
                garden_id = cursor.getInt(cursor.getColumnIndex(COLUMN_GARDEN_ID))

                val vegetable = Vegetable(veg_id, veg_name, veg_image, garden_id)
                vegList.add(vegetable)
            } while (cursor.moveToNext())
        }
        cursor?.close()
        return vegList
    }

    /**
     * This method to delete data
     *
     * @param batch_id
     * @return Int
     */
    fun deleteVeg(veg_id: Int): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_VEG_ID, veg_id) // EmpModelClass UserId
        // Deleting Row
        val success = db.delete(TABLE_VEGETABLE, "veg_id=" + veg_id, null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    /**
     * This method to find vegetable by id
     *
     * @param batch_id
     * @return Batch
     */
    fun findVegetableById(veg_id: Int): Vegetable {
        val selectQuery = "SELECT  * FROM $TABLE_VEGETABLE WHERE $COLUMN_VEG_ID = $veg_id"
        val db = this.readableDatabase
        var vegetable: Vegetable = Vegetable()
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            Log.d("AAA", e.message)
        }
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    var vegId = cursor.getInt(cursor.getColumnIndex(COLUMN_VEG_ID))
                    var vegName = cursor.getString(cursor.getColumnIndex(COLUMN_VEG_NAME))
                    var vegImage = cursor.getString(cursor.getColumnIndex(COLUMN_VEG_IMG_BLOB))
                    var paramId = cursor.getInt(cursor.getColumnIndex(COLUMN_PARAM_ID))
                    vegetable.vegID = vegId
                    vegetable.vegName = vegName
                    vegetable.vegImgBlob = vegImage
                    vegetable.paramId = paramId
                } while (cursor.moveToNext())
            }
        }
        cursor?.close()
        return vegetable
    }

    /**
     * This method to find vegetable by gardenId
     *
     * @param batch_id
     * @return Batch
     */
    fun findVegetableByGardenId(garden_id: Int): ArrayList<Vegetable> {
        val selectQuery = "SELECT  * FROM $TABLE_VEGETABLE WHERE $COLUMN_GARDEN_ID = $garden_id"
        val db = this.readableDatabase
        var vegetables: ArrayList<Vegetable> = ArrayList()
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            Log.d("AAA", e.message)
        }
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    var vegId = cursor.getInt(cursor.getColumnIndex(COLUMN_VEG_ID))
                    var vegName = cursor.getString(cursor.getColumnIndex(COLUMN_VEG_NAME))
                    var vegImage = cursor.getString(cursor.getColumnIndex(COLUMN_VEG_IMG_BLOB))
                    var paramId = cursor.getInt(cursor.getColumnIndex(COLUMN_PARAM_ID))
                    var vegetable = Vegetable()
                    vegetable.vegID = vegId
                    vegetable.vegName = vegName
                    vegetable.vegImgBlob = vegImage
                    vegetable.paramId = paramId
                    vegetables.add(vegetable)
                } while (cursor.moveToNext())
            }
        }
        cursor?.close()
        return vegetables
    }
    /*----------------------------------------------Device-------------------------------------------------*/
    /**
     * This method to insert data device
     *
     * @param device
     * @return Long
     */
    fun addDeviceImageDefault(device: Device): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_DEVICE_NAME, device.deviceName)
        contentValues.put(COLUMN_DEVICE_IMAGE, device.deviceImg)
        contentValues.put(COLUMN_DEVICE_NUM, device.deviceNum)
        contentValues.put(COLUMN_DEVICE_CATEGORY_ID, device.deviceCategoryId)


        // Inserting Row
        val success = db.insert(TABLE_DEVICE, null, contentValues)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    /**
     * This method to update data updateDeviceImageDefault
     *
     * @param veg
     * @return true/false
     */
    fun updateDeviceImageDefault(device: Device): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_DEVICE_ID, device.deviceID)
        contentValues.put(COLUMN_DEVICE_NAME, device.deviceName)
        contentValues.put(COLUMN_DEVICE_IMAGE, device.deviceImg)
        contentValues.put(COLUMN_DEVICE_CATEGORY_ID, device.deviceCategoryId)

        // Inserting Row
        val success = db.update(TABLE_DEVICE, contentValues, "device_id=" + device.deviceID, null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    /**
     * This method to find all data device
     *
     * @return ArrayList
     */
    fun findAllDevice(): ArrayList<Device> {
        val deviceList: ArrayList<Device> = ArrayList()
        val selectQuery = "SELECT  * FROM $TABLE_DEVICE"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var device_id: Int
        var device_name: String
        var device_image: String
        var device_num: String
        if (cursor.moveToFirst()) {
            do {
                device_id = cursor.getInt(cursor.getColumnIndex(COLUMN_DEVICE_ID))
                device_name = cursor.getString(cursor.getColumnIndex(COLUMN_DEVICE_NAME))
                device_image = cursor.getString(cursor.getColumnIndex(COLUMN_DEVICE_IMAGE))
                device_num = cursor.getString(cursor.getColumnIndex(COLUMN_DEVICE_NUM))

                val device = Device(device_id, device_name, device_image, device_num)
                deviceList.add(device)
            } while (cursor.moveToNext())
        }
        cursor?.close()
        return deviceList
    }

    /**
     * This method to find vegetable by id
     *
     * @param batch_id
     * @return Batch
     */
    fun findDeviceById(device_id: Int): Device {
        val selectQuery = "SELECT  * FROM $TABLE_DEVICE WHERE $COLUMN_DEVICE_ID = $device_id"
        val db = this.readableDatabase
        var device: Device = Device()
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            Log.d("AAA", e.message)
        }
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    var device_id = cursor.getInt(cursor.getColumnIndex(COLUMN_DEVICE_ID))
                    var device_name = cursor.getString(cursor.getColumnIndex(COLUMN_DEVICE_NAME))
                    var device_image = cursor.getString(cursor.getColumnIndex(COLUMN_DEVICE_IMAGE))
                    var device_num = cursor.getString(cursor.getColumnIndex(COLUMN_DEVICE_NUM))
                    var device_category_id = cursor.getInt(
                        cursor.getColumnIndex(
                            COLUMN_DEVICE_CATEGORY_ID
                        )
                    )
                    device =
                        Device(device_id, device_name, device_image, device_num, device_category_id)
                } while (cursor.moveToNext())
            }
        }
        cursor?.close()
        return device
    }

    /**
     * This method to delete data
     *
     * @param batch_id
     * @return Int
     */
    fun deleteDevice(device_id: Int): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_DEVICE_ID, device_id) // EmpModelClass UserId
        // Deleting Row
        val success = db.delete(TABLE_DEVICE, "device_id=$device_id", null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    /*----------------------------------------------Device detail-------------------------------------------------*/
    /**
     * This method to insert data device detail
     *
     * @param deviceDetail
     * @return Long
     */
    fun addDeviceDetailImageDefault(deviceDetail: DeviceDetail): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_DEVICE_DETAIL_IMAGE, deviceDetail.deviceDetailImg)
        contentValues.put(COLUMN_DEVICE_DETAIL_CODE, deviceDetail.deviceDetailCode)
        contentValues.put(COLUMN_DEVICE_DETAIL_STATUS, deviceDetail.deviceDetailStatus)
        contentValues.put(COLUMN_DEVICE_ID, deviceDetail.deviceID)
        contentValues.put(COLUMN_CREATEDBY, deviceDetail.createdBy)
        contentValues.put(COLUMN_CREATEDDATE, deviceDetail.createdDate)


        // Inserting Row
        val success = db.insert(TABLE_DEVICE_DETAIL, null, contentValues)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    /**
     * This method to update data devicedetail
     *
     * @param veg
     * @return true/false
     */
    fun updateDeviceDetailImageDefault(deviceDetail: DeviceDetail): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_DEVICE_DETAIL_ID, deviceDetail.deviceDetailID)
        contentValues.put(COLUMN_DEVICE_DETAIL_IMAGE, deviceDetail.deviceDetailImg)
        contentValues.put(COLUMN_UPDATED_DATE, deviceDetail.updatedDate)

        // Inserting Row
        val success = db.update(
            TABLE_DEVICE_DETAIL,
            contentValues,
            "device_detail_id=" + deviceDetail.deviceDetailID,
            null
        )
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    /**
     * This method to update status devicedetail
     *
     * @param veg
     * @return true/false
     */
    fun updateDeviceDetailStatus(deviceDetail: DeviceDetail): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_DEVICE_DETAIL_ID, deviceDetail.deviceDetailID)
        contentValues.put(COLUMN_DEVICE_DETAIL_STATUS, deviceDetail.deviceDetailStatus)
        contentValues.put(COLUMN_GARDEN_ID, deviceDetail.gardenDetailId)
        contentValues.put(COLUMN_DEVICE_DETAIL_CODE_SS, deviceDetail.deviceDetailCodeSS)

        // Inserting Row
        val success = db.update(
            TABLE_DEVICE_DETAIL,
            contentValues,
            "device_detail_id=" + deviceDetail.deviceDetailID,
            null
        )
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    /**
     * This method to find All Device Detail
     *
     * @return ArrayList
     */
    fun findAllDeviceDetail(device_id: Int): ArrayList<DeviceDetail> {
        val deviceDetailList: ArrayList<DeviceDetail> = ArrayList()
        val selectQuery = "SELECT  * FROM $TABLE_DEVICE_DETAIL WHERE $COLUMN_DEVICE_ID = $device_id"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var device_detail_id: Int
        var device_detail_code: String
        var device_detail_image: String
        var device_detail_status: String
        var device_id: Int
        var garden_id: Int
        if (cursor.moveToFirst()) {
            do {
                device_detail_id = cursor.getInt(cursor.getColumnIndex(COLUMN_DEVICE_DETAIL_ID))
                device_detail_code = cursor.getString(
                    cursor.getColumnIndex(
                        COLUMN_DEVICE_DETAIL_CODE
                    )
                )
                device_detail_image =
                    cursor.getString(cursor.getColumnIndex(COLUMN_DEVICE_DETAIL_IMAGE))
                device_detail_status = cursor.getString(
                    cursor.getColumnIndex(
                        COLUMN_DEVICE_DETAIL_STATUS
                    )
                )
                device_id = cursor.getInt(cursor.getColumnIndex(COLUMN_DEVICE_ID))
                garden_id = cursor.getInt(cursor.getColumnIndex(COLUMN_GARDEN_ID))


                val device = DeviceDetail(
                    device_detail_id,
                    device_detail_code,
                    device_detail_image,
                    device_detail_status,
                    device_id,
                    garden_id
                )
                deviceDetailList.add(device)
            } while (cursor.moveToNext())
        }
        cursor?.close()
        return deviceDetailList
    }

    /**
     * This method to delete data
     *
     * @param batch_id
     * @return Int
     */
    fun deleteDetailDevice(device_detail_id: Int): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_DEVICE_DETAIL_ID, device_detail_id) // EmpModelClass UserId
        // Deleting Row
        val success = db.delete(TABLE_DEVICE_DETAIL, "device_detail_id=$device_detail_id", null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    /*----------------------------------------------DEVICE-CATEGORY-------------------------------------------------*/

    /**
     * This method to insert data vegetable
     *
     * @param batchQtyDetail
     * @return true/false
     */
    fun addDeviceCategoryImageDefault(deviceCategory: DeviceCategory): Long {

        val db: SQLiteDatabase = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_DEVICE_CATEGORY_NAME, deviceCategory.dcategoryName)
        contentValues.put(COLUMN_DEVICE_CATEGORY_IMAGE, deviceCategory.dcategoryImg)
        contentValues.put(COLUMN_CREATEDBY, deviceCategory.createdBy)
        contentValues.put(COLUMN_CREATEDDATE, deviceCategory.createdDate)

        //insert row
        var sucess: Long = db.insert(TABLE_DEVICE_CATEGORY, null, contentValues)
        db.close()
        return sucess
    }

    /**
     * This method to insert data vegetable
     *
     * @return ArrayList
     */
    fun findAllDeviceCategory(): ArrayList<DeviceCategory> {
        val deviceCategoryList: ArrayList<DeviceCategory> = ArrayList()
        val selectQuery = "SELECT  * FROM $TABLE_DEVICE_CATEGORY"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var device_category_id: Int
        var device_category_name: String
        var device_category_img: String
        if (cursor.moveToFirst()) {
            do {
                device_category_id = cursor.getInt(cursor.getColumnIndex(COLUMN_DEVICE_CATEGORY_ID))
                device_category_name =
                    cursor.getString(cursor.getColumnIndex(COLUMN_DEVICE_CATEGORY_NAME))
                device_category_img = cursor.getString(
                    cursor.getColumnIndex(
                        COLUMN_DEVICE_CATEGORY_IMAGE
                    )
                )

                val deviceCategory =
                    DeviceCategory(device_category_id, device_category_name, device_category_img)
                deviceCategoryList.add(deviceCategory)
            } while (cursor.moveToNext())
        }
        cursor?.close()
        return deviceCategoryList
    }

    /**
     * This method to update data vegetable
     *
     * @param batchQtyDetail
     * @return true/false
     */
    fun updateDeviceCategoryImageDefault(dcategory: DeviceCategory): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_DEVICE_CATEGORY_ID, dcategory.dcategoryID)
        contentValues.put(COLUMN_DEVICE_CATEGORY_NAME, dcategory.dcategoryName)
        contentValues.put(COLUMN_DEVICE_CATEGORY_IMAGE, dcategory.dcategoryImg)
        contentValues.put(COLUMN_UPDATED_DATE, dcategory.updatedDate)

        // Inserting Row
        val success = db.update(
            TABLE_DEVICE_CATEGORY,
            contentValues,
            "device_category_id=" + dcategory.dcategoryID,
            null
        )
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }


    /**
     * This method to find dcategory by id
     *
     * @param batch_id
     * @return Batch
     */
    fun findDeviceCategoryId(dcategory_id: Int): DeviceCategory {
        val selectQuery =
            "SELECT  * FROM $TABLE_DEVICE_CATEGORY WHERE $COLUMN_DEVICE_CATEGORY_ID = $dcategory_id"
        val db = this.readableDatabase
        var dcategory: DeviceCategory = DeviceCategory()
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            Log.d("AAA", e.message)
        }
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    var dcategoryId =
                        cursor.getInt(cursor.getColumnIndex(COLUMN_DEVICE_CATEGORY_ID))
                    var dcategoryName = cursor.getString(
                        cursor.getColumnIndex(
                            COLUMN_DEVICE_CATEGORY_NAME
                        )
                    )
                    var dcategoryImg = cursor.getString(
                        cursor.getColumnIndex(
                            COLUMN_DEVICE_CATEGORY_IMAGE
                        )
                    )
                    dcategory = DeviceCategory(dcategoryId, dcategoryName, dcategoryImg)
                } while (cursor.moveToNext())
            }
        }
        cursor?.close()
        return dcategory
    }

    /**
     * This method to delete data
     *
     * @param
     * @return Int
     */
    fun deleteDeviceCategory(dcategory_id: Int): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_DEVICE_CATEGORY_ID, dcategory_id) // EmpModelClass UserId
        // Deleting Row
        val success = db.delete(TABLE_DEVICE_CATEGORY, "device_category_id=" + dcategory_id, null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    /*----------------------------------------------SELECT DEVICE FOR GARDEN -------------------------------------------------*/
    /**
     * This method to find all device detail for garden
     *
     * @param no data
     * @return ArrayList
     */
    fun findAllDeviceDetailForGarden(gardenId: Int): ArrayList<DeviceDetail> {
        val deviceDetailList: ArrayList<DeviceDetail> = ArrayList()
        val selectQuery =
            "SELECT  * FROM $TABLE_DEVICE_DETAIL WHERE $COLUMN_DEVICE_DETAIL_STATUS = 'N' OR $COLUMN_GARDEN_ID = $gardenId"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var device_detail_id: Int
        var device_detail_code: String
        var device_detail_image: String
        var device_detail_status: String
        var device_id: Int
        var garden_id: Int
        if (cursor.moveToFirst()) {
            do {
                device_detail_id = cursor.getInt(cursor.getColumnIndex(COLUMN_DEVICE_DETAIL_ID))
                device_detail_code = cursor.getString(
                    cursor.getColumnIndex(
                        COLUMN_DEVICE_DETAIL_CODE
                    )
                )
                device_detail_image =
                    cursor.getString(cursor.getColumnIndex(COLUMN_DEVICE_DETAIL_IMAGE))
                device_detail_status = cursor.getString(
                    cursor.getColumnIndex(
                        COLUMN_DEVICE_DETAIL_STATUS
                    )
                )
                device_id = cursor.getInt(cursor.getColumnIndex(COLUMN_DEVICE_ID))
                garden_id = cursor.getInt(cursor.getColumnIndex(COLUMN_GARDEN_ID))

                val device = DeviceDetail(
                    device_detail_id,
                    device_detail_code,
                    device_detail_image,
                    device_detail_status,
                    device_id,
                    garden_id
                )
                deviceDetailList.add(device)
            } while (cursor.moveToNext())
        }
        cursor?.close()
        return deviceDetailList
    }

    /**
     * This method to find all device detail for garden
     *
     * @param no data
     * @return ArrayList
     */
    fun findAllDeviceByGardenId(gardenId: Int): ArrayList<DeviceDetail> {
        val deviceDetailList: ArrayList<DeviceDetail> = ArrayList()
        val selectQuery = "SELECT  * FROM $TABLE_DEVICE_DETAIL WHERE $COLUMN_GARDEN_ID = $gardenId"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var device_detail_id: Int
        var device_detail_code: String
        var device_detail_image: String
        var device_detail_status: String
        var device_id: Int
        var garden_id: Int
        if (cursor.moveToFirst()) {
            do {
                device_detail_id = cursor.getInt(cursor.getColumnIndex(COLUMN_DEVICE_DETAIL_ID))
                device_detail_code = cursor.getString(
                    cursor.getColumnIndex(
                        COLUMN_DEVICE_DETAIL_CODE
                    )
                )
                device_detail_image =
                    cursor.getString(cursor.getColumnIndex(COLUMN_DEVICE_DETAIL_IMAGE))
                device_detail_status = cursor.getString(
                    cursor.getColumnIndex(
                        COLUMN_DEVICE_DETAIL_STATUS
                    )
                )
                device_id = cursor.getInt(cursor.getColumnIndex(COLUMN_DEVICE_ID))
                garden_id = cursor.getInt(cursor.getColumnIndex(COLUMN_GARDEN_ID))


                val device = DeviceDetail(
                    device_detail_id,
                    device_detail_code,
                    device_detail_image,
                    device_detail_status,
                    device_id,
                    garden_id
                )
                deviceDetailList.add(device)
            } while (cursor.moveToNext())
        }
        cursor?.close()
        return deviceDetailList
    }

    /**
     * This method to find all device detail for garden
     *
     * @param no data
     * @return ArrayList
     */
    fun findAllDeviceByGardenIdForInfo(gardenId: Int): ArrayList<DeviceDetail> {
        val deviceDetailList: ArrayList<DeviceDetail> = ArrayList()
        val selectQuery = "SELECT  * FROM $TABLE_DEVICE_DETAIL WHERE $COLUMN_GARDEN_ID = $gardenId"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var device_detail_id: Int
        var device_detail_code: String
        var device_detail_image: String
        var device_detail_status: String
        var device_id: Int
        var garden_id: Int
        var device_detail_code_ss: String
        if (cursor.moveToFirst()) {
            do {
                device_detail_id = cursor.getInt(cursor.getColumnIndex(COLUMN_DEVICE_DETAIL_ID))
                device_detail_code = cursor.getString(
                    cursor.getColumnIndex(
                        COLUMN_DEVICE_DETAIL_CODE
                    )
                )
                device_detail_image =
                    cursor.getString(cursor.getColumnIndex(COLUMN_DEVICE_DETAIL_IMAGE))
                device_detail_status = cursor.getString(
                    cursor.getColumnIndex(
                        COLUMN_DEVICE_DETAIL_STATUS
                    )
                )
                device_id = cursor.getInt(cursor.getColumnIndex(COLUMN_DEVICE_ID))
                garden_id = cursor.getInt(cursor.getColumnIndex(COLUMN_GARDEN_ID))

                device_detail_code_ss = cursor.getString(
                    cursor.getColumnIndex(
                        COLUMN_DEVICE_DETAIL_CODE_SS
                    )
                )

                val device = DeviceDetail(
                    device_detail_id,
                    device_detail_code,
                    device_detail_code_ss,
                    device_detail_image,
                    device_detail_status,
                    device_id,
                    garden_id
                )
                deviceDetailList.add(device)
            } while (cursor.moveToNext())
        }
        cursor?.close()
        return deviceDetailList
    }

    /**
     * This method to find all device detail for garden by id
     *
     * @param no data
     * @return ArrayList
     */
    fun findAllVegetableForGarden(garden_id: Int): ArrayList<Vegetable> {
        val vegList: ArrayList<Vegetable> = ArrayList()
        val selectQuery =
            "SELECT * FROM $TABLE_VEGETABLE WHERE $COLUMN_GARDEN_ID IS NULL OR $COLUMN_GARDEN_ID = 0 OR $COLUMN_GARDEN_ID = $garden_id"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var vegID: Int
        var vegName: String
        var vegImage: String
        var gardenId: Int
        if (cursor.moveToFirst()) {
            do {
                vegID = cursor.getInt(cursor.getColumnIndex(COLUMN_VEG_ID))
                vegName = cursor.getString(cursor.getColumnIndex(COLUMN_VEG_NAME))
                vegImage = cursor.getString(cursor.getColumnIndex(COLUMN_VEG_IMG_BLOB))
                gardenId = cursor.getInt(cursor.getColumnIndex(COLUMN_GARDEN_ID))

                val vegetable = Vegetable(vegID, vegName, vegImage, gardenId)
                vegList.add(vegetable)
            } while (cursor.moveToNext())
        }
        cursor?.close()
        return vegList
    }

    /**
     * This method to find all vegetable for garden by id
     *
     * @param no data
     * @return ArrayList
     */
    fun findVegetableForGardenByGardenId(garden_id: Int): ArrayList<Vegetable> {
        val vegList: ArrayList<Vegetable> = ArrayList()
        val selectQuery = "SELECT * FROM $TABLE_VEGETABLE WHERE $COLUMN_GARDEN_ID = $garden_id"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var veg_id: Int
        var veg_name: String
        var veg_image: String
        var garden_id: Int
        if (cursor.moveToFirst()) {
            do {
                veg_id = cursor.getInt(cursor.getColumnIndex(COLUMN_VEG_ID))
                veg_name = cursor.getString(cursor.getColumnIndex(COLUMN_VEG_NAME))
                veg_image = cursor.getString(cursor.getColumnIndex(COLUMN_VEG_IMG_BLOB))
                garden_id = cursor.getInt(cursor.getColumnIndex(COLUMN_GARDEN_ID))

                val vegetable = Vegetable(veg_id, veg_name, veg_image, garden_id)
                vegList.add(vegetable)
            } while (cursor.moveToNext())
        }
        cursor?.close()
        return vegList
    }

    /**
     * This method to update device for garden
     *
     * @param veg
     * @return true/false
     */
    fun updateDeviceForGarden(deviceDetail: DeviceDetail): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_DEVICE_DETAIL_ID, deviceDetail.deviceDetailID)
        contentValues.put(COLUMN_GARDEN_ID, deviceDetail.gardenDetailId)
        contentValues.put(COLUMN_DEVICE_DETAIL_STATUS, deviceDetail.deviceDetailStatus)
        contentValues.put(COLUMN_DEVICE_DETAIL_CODE_SS, deviceDetail.deviceDetailCodeSS)

        // Inserting Row
        val success = db.update(
            TABLE_DEVICE_DETAIL,
            contentValues,
            "device_detail_id=" + deviceDetail.deviceDetailID,
            null
        )
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    /**
     * This method to update vegetable for garden
     *
     * @param veg
     * @return true/false
     */
    fun updateVegForGarden(vegetable: Vegetable): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_VEG_ID, vegetable.vegID)
        contentValues.put(COLUMN_GARDEN_ID, vegetable.gardenId)

        // Inserting Row
        val success = db.update(TABLE_VEGETABLE, contentValues, "veg_id=" + vegetable.vegID, null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    /**
     * This method to find All Device Detail
     *
     * @return ArrayList
     */
    fun findAllDeviceDetail(device_id: Int, garden_id: Int): ArrayList<DeviceDetail> {
        val deviceDetailList: ArrayList<DeviceDetail> = ArrayList()
        val selectQuery =
            "SELECT  * FROM $TABLE_DEVICE_DETAIL WHERE $COLUMN_DEVICE_ID = $device_id AND $COLUMN_GARDEN_ID = $garden_id"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var device_detail_id: Int
        var device_detail_code: String
        var device_detail_image: String
        var device_detail_status: String
        var device_id: Int
        var garden_id: Int
        if (cursor.moveToFirst()) {
            do {
                device_detail_id = cursor.getInt(cursor.getColumnIndex(COLUMN_DEVICE_DETAIL_ID))
                device_detail_code = cursor.getString(
                    cursor.getColumnIndex(
                        COLUMN_DEVICE_DETAIL_CODE
                    )
                )
                device_detail_image =
                    cursor.getString(cursor.getColumnIndex(COLUMN_DEVICE_DETAIL_IMAGE))
                device_detail_status = cursor.getString(
                    cursor.getColumnIndex(
                        COLUMN_DEVICE_DETAIL_STATUS
                    )
                )
                device_id = cursor.getInt(cursor.getColumnIndex(COLUMN_DEVICE_ID))
                garden_id = cursor.getInt(cursor.getColumnIndex(COLUMN_GARDEN_ID))

                val device = DeviceDetail(
                    device_detail_id,
                    device_detail_code,
                    device_detail_image,
                    device_detail_status,
                    device_id,
                    garden_id
                )
                deviceDetailList.add(device)
            } while (cursor.moveToNext())
        }
        cursor?.close()
        return deviceDetailList
    }
/*---------------------------------------------- SETTING PARAM FOR VEGETABLE -------------------------------------------------*/
    /**
     * This method to insert data vegetable
     *
     * @return ArrayList
     */
    fun findAllVegetableForParam(): ArrayList<Vegetable> {
        val vegList: ArrayList<Vegetable> = ArrayList()
        val selectQuery = "SELECT  * FROM $TABLE_VEGETABLE"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var vegId: Int
        var vegName: String
        var vegImage: String
        var gardenId: Int
        var paramId: Int
        if (cursor.moveToFirst()) {
            do {
                vegId = cursor.getInt(cursor.getColumnIndex(COLUMN_VEG_ID))
                vegName = cursor.getString(cursor.getColumnIndex(COLUMN_VEG_NAME))
                vegImage = cursor.getString(cursor.getColumnIndex(COLUMN_VEG_IMG_BLOB))
                gardenId = cursor.getInt(cursor.getColumnIndex(COLUMN_GARDEN_ID))
                paramId = cursor.getInt(cursor.getColumnIndex(COLUMN_PARAM_ID))

                val vegetable = Vegetable(vegId, vegName, vegImage, gardenId, paramId)
                vegList.add(vegetable)
            } while (cursor.moveToNext())
        }
        cursor?.close()
        return vegList
    }

    /**
     * This method to insert data param
     *
     * @param param
     * @return true/false
     */
    fun addParam(param: Param): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_TEMP_FROM_DAY, param.tempFromDay)
        contentValues.put(COLUMN_TEMP_TO_DAY, param.tempToDay)
        contentValues.put(COLUMN_PH_FROM, param.phFrom)
        contentValues.put(COLUMN_PH_TO, param.phTo)
        contentValues.put(COLUMN_PPM_FROM, param.ppmFrom)
        contentValues.put(COLUMN_PPM_TO, param.ppmTo)
        contentValues.put(COLUMN_TEMP_TO_NIGHT, param.tempToNight)
        contentValues.put(COLUMN_TEMP_FROM_NIGHT, param.tempFromNight)
        contentValues.put(COLUMN_TDS_LEVEL_TO, param.tdsLevelTo)
        contentValues.put(COLUMN_TDS_LEVEL_FROM, param.tdsLevelFrom)

        // Inserting Row
        val success = db.insert(TABLE_PARAM, null, contentValues)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    /**
     * This method to update data param
     *
     * @param param
     * @return true/false
     */
    fun updateParam(param: Param): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_PARAM_ID, param.paramId)
        contentValues.put(COLUMN_TEMP_FROM_DAY, param.tempFromDay)
        contentValues.put(COLUMN_TEMP_TO_DAY, param.tempToDay)
        contentValues.put(COLUMN_PH_FROM, param.phFrom)
        contentValues.put(COLUMN_PH_TO, param.phTo)
        contentValues.put(COLUMN_PPM_FROM, param.ppmFrom)
        contentValues.put(COLUMN_PPM_TO, param.ppmTo)
        contentValues.put(COLUMN_TEMP_TO_NIGHT, param.tempToNight)
        contentValues.put(COLUMN_TEMP_FROM_NIGHT, param.tempFromNight)
        contentValues.put(COLUMN_TDS_LEVEL_TO, param.tdsLevelTo)
        contentValues.put(COLUMN_TDS_LEVEL_FROM, param.tdsLevelFrom)

        // Inserting Row
        val success = db.update(TABLE_PARAM, contentValues, "param_id=" + param.paramId, null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    /**
     * This method to update data param
     *
     * @param param
     * @return true/false
     */
    fun updateParamIdForVeg(paramId: Long, veg_id: Int): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_VEG_ID, veg_id)
        contentValues.put(COLUMN_PARAM_ID, paramId)

        // Inserting Row
        val success = db.update(TABLE_VEGETABLE, contentValues, "veg_id=$veg_id", null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    /**
     * This method to delete pram
     *
     * @param paramId
     * @return Int
     */
    fun deleteParam(paramId: Int): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_PARAM_ID, paramId) // EmpModelClass UserId
        // Deleting Row
        val success = db.delete(TABLE_PARAM, "param_id=$paramId", null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    /**
     * This method to find vegetable by id
     *
     * @param batch_id
     * @return Batch
     */
    fun findParamById(paramId: Int): Param {
        val selectQuery = "SELECT  * FROM $TABLE_PARAM WHERE $COLUMN_PARAM_ID = $paramId"
        val db = this.readableDatabase
        var param: Param = Param()
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            Log.d("AAA", e.message)
        }
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    var param_id = cursor.getInt(cursor.getColumnIndex(COLUMN_PARAM_ID))
                    var tempDayFrom = cursor.getString(cursor.getColumnIndex(COLUMN_TEMP_FROM_DAY))
                    var tempDayTo = cursor.getString(cursor.getColumnIndex(COLUMN_TEMP_TO_DAY))
                    var tempNightFrom = cursor.getString(
                        cursor.getColumnIndex(
                            COLUMN_TEMP_FROM_NIGHT
                        )
                    )
                    var tempNightTo = cursor.getString(cursor.getColumnIndex(COLUMN_TEMP_TO_NIGHT))
                    var ppmFrom = cursor.getString(cursor.getColumnIndex(COLUMN_PPM_FROM))
                    var ppmTo = cursor.getString(cursor.getColumnIndex(COLUMN_PPM_TO))
                    var phFrom = cursor.getString(cursor.getColumnIndex(COLUMN_PH_FROM))
                    var phTo = cursor.getString(cursor.getColumnIndex(COLUMN_PH_TO))
                    var tdsLevelFrom =
                        cursor.getString(cursor.getColumnIndex(COLUMN_TDS_LEVEL_FROM))
                    var tdsLevelTo = cursor.getString(cursor.getColumnIndex(COLUMN_TDS_LEVEL_TO))

                    param = Param(
                        param_id,
                        tempNightTo,
                        tempNightFrom,
                        tempDayTo,
                        tempDayFrom,
                        phTo,
                        phFrom,
                        ppmTo,
                        ppmFrom,
                        tdsLevelTo,
                        tdsLevelFrom
                    )
                } while (cursor.moveToNext())
            }
        }
        cursor?.close()
        return param
    }

    /*---------------------------------------------- SUBSTANCE AND SUBSTANCE DETAIL -------------------------------------------------*/
    /**
     * This method to findAllSubstance
     *
     * @return ArrayList
     */
    fun findAllSubstanceByGardenId(garden_id: Int): ArrayList<Substance> {
        val substanceList: ArrayList<Substance> = ArrayList()
        val selectQuery =
            "SELECT  * FROM $TABLE_SUBSTANCE_MASS WHERE $COLUMN_GARDEN_ID = $garden_id"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var substanceMassId: Int
        var substanceMassName: String
        var substanceMassImage: String
        var totalSubstanceMass: String
        var substanceMassCategory: String
        var gardenId: Int
        if (cursor.moveToFirst()) {
            do {
                substanceMassId = cursor.getInt(cursor.getColumnIndex(COLUMN_SUBSTANCE_ID))
                substanceMassName = cursor.getString(cursor.getColumnIndex(COLUMN_SUBSTANCE_NAME))
                substanceMassImage = cursor.getString(cursor.getColumnIndex(COLUMN_SUBSTANCE_IMAGE))
                totalSubstanceMass = cursor.getString(cursor.getColumnIndex(COLUMN_SUBSTANCE_TOTAL))
                substanceMassCategory = cursor.getString(
                    cursor.getColumnIndex(
                        COLUMN_SUBSTANCE_CATEGORY
                    )
                )
                gardenId = cursor.getInt(cursor.getColumnIndex(COLUMN_GARDEN_ID))

                val substance = Substance(
                    substanceMassId,
                    substanceMassName,
                    substanceMassImage,
                    totalSubstanceMass,
                    substanceMassCategory,
                    gardenId
                )
                substanceList.add(substance)
            } while (cursor.moveToNext())
        }
        cursor?.close()
        return substanceList
    }

    /**
     * This method to findAllSubstance
     *
     * @return ArrayList
     */
    fun findAllSubstanceBySubstanceId(substanceId: Int): Substance {
        var substance = Substance()
        val selectQuery =
            "SELECT  * FROM $TABLE_SUBSTANCE_MASS WHERE $COLUMN_SUBSTANCE_ID = $substanceId"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return substance
        }
        var substanceMassId: Int
        var substanceMassName: String
        var substanceMassImage: String
        var totalSubstanceMass: String
        var substanceMassCategory: String
        var gardenId: Int
        if (cursor.moveToFirst()) {
            do {
                substanceMassId = cursor.getInt(cursor.getColumnIndex(COLUMN_SUBSTANCE_ID))
                substanceMassName = cursor.getString(cursor.getColumnIndex(COLUMN_SUBSTANCE_NAME))
                substanceMassImage = cursor.getString(cursor.getColumnIndex(COLUMN_SUBSTANCE_IMAGE))
                totalSubstanceMass = cursor.getString(cursor.getColumnIndex(COLUMN_SUBSTANCE_TOTAL))
                substanceMassCategory = cursor.getString(
                    cursor.getColumnIndex(
                        COLUMN_SUBSTANCE_CATEGORY
                    )
                )
                gardenId = cursor.getInt(cursor.getColumnIndex(COLUMN_GARDEN_ID))

                substance = Substance(
                    substanceMassId,
                    substanceMassName,
                    substanceMassImage,
                    totalSubstanceMass,
                    substanceMassCategory,
                    gardenId
                )
            } while (cursor.moveToNext())
        }
        cursor?.close()
        return substance
    }


    /**
     * This method to findAllSubstanceDetail
     *
     * @return ArrayList
     */
    fun findAllSubstanceDetailByGardenIdAndSubstance(
        garden_id: Int,
        substanceId: Int
    ): ArrayList<SubstanceDetail> {
        val substanceDetailList: ArrayList<SubstanceDetail> = ArrayList()
        val selectQuery =
            "SELECT  * FROM $TABLE_SUBSTANCE_MASS_DETAIL WHERE $COLUMN_GARDEN_ID = $garden_id AND $COLUMN_SUBSTANCE_ID = $substanceId"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var substanceMassDetailId: Int
        var substanceMassDetailName: String
        var substanceMassDetailImage: String
        var substanceMassDetailNum: String
        var substanceMassDetailCategory: String
        var substanceId: Int
        var gardenId: Int
        var createBy: String
        var createDate: String
        if (cursor.moveToFirst()) {
            do {
                substanceMassDetailId = cursor.getInt(
                    cursor.getColumnIndex(
                        COLUMN_SUBSTANCE_DETAIL_ID
                    )
                )
                substanceMassDetailName =
                    cursor.getString(cursor.getColumnIndex(COLUMN_SUBSTANCE_DETAIL_NAME))
                substanceMassDetailImage =
                    cursor.getString(cursor.getColumnIndex(COLUMN_SUBSTANCE_DETAIL_IMAGE))
                substanceMassDetailNum = cursor.getString(
                    cursor.getColumnIndex(
                        COLUMN_SUBSTANCE_DETAIL_NUM
                    )
                )
                substanceMassDetailCategory = cursor.getString(
                    cursor.getColumnIndex(
                        COLUMN_SUBSTANCE_DETAIL_CATEGORY
                    )
                )
                substanceId = cursor.getInt(
                    cursor.getColumnIndex(
                        COLUMN_SUBSTANCE_ID
                    )
                )
                gardenId = cursor.getInt(cursor.getColumnIndex(COLUMN_GARDEN_ID))
                createBy = cursor.getString(cursor.getColumnIndex(COLUMN_CREATEDBY))
                createDate = cursor.getString(cursor.getColumnIndex(COLUMN_CREATEDDATE))

                val substanceDetail = SubstanceDetail(
                    substanceMassDetailId,
                    substanceMassDetailName,
                    substanceMassDetailImage,
                    substanceMassDetailNum,
                    substanceMassDetailCategory,
                    substanceId,
                    gardenId,
                    createBy,
                    createDate
                )
                substanceDetailList.add(substanceDetail)
            } while (cursor.moveToNext())
        }
        cursor?.close()
        return substanceDetailList
    }

    /**
     * This method to findAllSubstanceDetail
     *
     * @return ArrayList
     */
    fun findAllSubstanceDetailBySubstanceId(substanceDetailId: Int): SubstanceDetail {
        var substanceDetail = SubstanceDetail()
        val selectQuery =
            "SELECT  * FROM $TABLE_SUBSTANCE_MASS_DETAIL WHERE $COLUMN_SUBSTANCE_DETAIL_ID = $substanceDetailId"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return substanceDetail
        }
        var substanceMassDetailId: Int
        var substanceMassDetailName: String
        var substanceMassDetailImage: String
        var substanceMassDetailNum: String
        var substanceMassDetailCategory: String
        var substanceId: Int
        var gardenId: Int
        var createBy: String
        var createDate: String
        if (cursor.moveToFirst()) {
            do {
                substanceMassDetailId = cursor.getInt(
                    cursor.getColumnIndex(
                        COLUMN_SUBSTANCE_DETAIL_ID
                    )
                )
                substanceMassDetailName =
                    cursor.getString(cursor.getColumnIndex(COLUMN_SUBSTANCE_DETAIL_NAME))
                substanceMassDetailImage =
                    cursor.getString(cursor.getColumnIndex(COLUMN_SUBSTANCE_DETAIL_IMAGE))
                substanceMassDetailNum = cursor.getString(
                    cursor.getColumnIndex(
                        COLUMN_SUBSTANCE_DETAIL_NUM
                    )
                )
                substanceMassDetailCategory = cursor.getString(
                    cursor.getColumnIndex(
                        COLUMN_SUBSTANCE_DETAIL_CATEGORY
                    )
                )
                substanceId = cursor.getInt(
                    cursor.getColumnIndex(
                        COLUMN_SUBSTANCE_ID
                    )
                )
                gardenId = cursor.getInt(cursor.getColumnIndex(COLUMN_GARDEN_ID))
                createBy = cursor.getString(cursor.getColumnIndex(COLUMN_CREATEDBY))
                createDate = cursor.getString(cursor.getColumnIndex(COLUMN_CREATEDDATE))

                substanceDetail = SubstanceDetail(
                    substanceMassDetailId,
                    substanceMassDetailName,
                    substanceMassDetailImage,
                    substanceMassDetailNum,
                    substanceMassDetailCategory,
                    substanceId,
                    gardenId,
                    createBy,
                    createDate
                )

            } while (cursor.moveToNext())
        }
        cursor?.close()
        return substanceDetail
    }

    /**
     * This method to addSubstance
     *
     * @param device
     * @return Long
     */
    fun addSubstance(substance: Substance): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_SUBSTANCE_NAME, substance.substanceMassName)
        contentValues.put(COLUMN_SUBSTANCE_IMAGE, substance.substanceMassImage)
        contentValues.put(COLUMN_SUBSTANCE_TOTAL, substance.totalSubstanceMass)
        contentValues.put(COLUMN_SUBSTANCE_CATEGORY, substance.substanceCategory)
        contentValues.put(COLUMN_GARDEN_ID, substance.gardenId)


        // Inserting Row
        val success = db.insert(TABLE_SUBSTANCE_MASS, null, contentValues)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    /**
     * This method to updateSubstance
     *
     * @param veg
     * @return true/false
     */
    fun updateSubstance(substance: Substance): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_SUBSTANCE_ID, substance.substanceMassId)
        contentValues.put(COLUMN_SUBSTANCE_TOTAL, substance.totalSubstanceMass)


        // Inserting Row
        val success = db.update(
            TABLE_SUBSTANCE_MASS,
            contentValues,
            "$COLUMN_SUBSTANCE_ID=" + substance.substanceMassId,
            null
        )
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    /**
     * This method to updateSubstance
     *
     * @param veg
     * @return true/false
     */
    fun updateSubstance1(substance: Substance): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_SUBSTANCE_ID, substance.substanceMassId)
        contentValues.put(COLUMN_SUBSTANCE_TOTAL, substance.totalSubstanceMass)


        // Inserting Row
        val success = db.update(
            TABLE_SUBSTANCE_MASS,
            contentValues,
            "$COLUMN_SUBSTANCE_ID=" + substance.substanceMassId,
            null
        )
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    /**
     * This method to insert data device detail
     *
     * @param deviceDetail
     * @return Long
     */
    fun addSubstanceDetail(substanceDetail: SubstanceDetail): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_SUBSTANCE_DETAIL_NAME, substanceDetail.substanceMassDetailName)
        contentValues.put(COLUMN_SUBSTANCE_DETAIL_IMAGE, substanceDetail.substanceMassDetailImage)
        contentValues.put(COLUMN_SUBSTANCE_DETAIL_NUM, substanceDetail.substanceMassDetailNum)
        contentValues.put(
            COLUMN_SUBSTANCE_DETAIL_CATEGORY,
            substanceDetail.substanceMassDetailCategory
        )
        contentValues.put(COLUMN_SUBSTANCE_ID, substanceDetail.substanceMassId)
        contentValues.put(COLUMN_GARDEN_ID, substanceDetail.gardenId)
        contentValues.put(COLUMN_CREATEDBY, substanceDetail.createdBy)
        contentValues.put(COLUMN_CREATEDDATE, substanceDetail.createdDate)


        // Inserting Row
        val success = db.insert(TABLE_SUBSTANCE_MASS_DETAIL, null, contentValues)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    /**
     * This method to update data devicedetail
     *
     * @param veg
     * @return true/false
     */
    fun updateSubstanceDetail(substanceDetail: SubstanceDetail): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_SUBSTANCE_DETAIL_ID, substanceDetail.substanceMassDetailId)
        contentValues.put(COLUMN_SUBSTANCE_DETAIL_NUM, substanceDetail.substanceMassDetailNum)
        contentValues.put(COLUMN_CREATEDDATE, substanceDetail.createdDate)

        // Inserting Row
        val success = db.update(
            TABLE_SUBSTANCE_MASS_DETAIL,
            contentValues,
            "$COLUMN_SUBSTANCE_DETAIL_ID=" + substanceDetail.substanceMassDetailId,
            null
        )
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    /**
     * This method to delete data
     *
     * @param batch_id
     * @return Int
     */
    fun deleteSubstanceDetail(substanceDetailId: Int): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_SUBSTANCE_DETAIL_ID, substanceDetailId) // EmpModelClass UserId
        // Deleting Row
        val success = db.delete(
            TABLE_SUBSTANCE_MASS_DETAIL,
            "$COLUMN_SUBSTANCE_DETAIL_ID=$substanceDetailId",
            null
        )
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    /**
     * This method to delete data
     *
     * @param batch_id
     * @return Int
     */
    fun deleteSubstance(substanceId: Int): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_SUBSTANCE_ID, substanceId) // EmpModelClass UserId
        // Deleting Row
        val success = db.delete(TABLE_SUBSTANCE_MASS, "$COLUMN_SUBSTANCE_ID=$substanceId", null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    /*---------------------------------------------- MODE -------------------------------------------------*/
    /**
     * This method to find all findAllModeDeviceByDeviceId
     *
     * @param no data
     * @return ArrayList
     */
    fun findAllModeDeviceByDeviceId(deviceId: Int): ArrayList<ModeDevice> {
        val modeDeviceList: ArrayList<ModeDevice> = ArrayList()
        val selectQuery = "SELECT  * FROM $TABLE_MODE_DEVICE WHERE $COLUMN_DEVICE_ID = $deviceId"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var modeDeviceId: Int
        var deviceId: Int
        var modeId: Int
        if (cursor.moveToFirst()) {
            do {
                modeDeviceId = cursor.getInt(cursor.getColumnIndex(COLUMN_MODE_DEVICE_ID))
                deviceId = cursor.getInt(cursor.getColumnIndex(COLUMN_DEVICE_ID))
                modeId = cursor.getInt(cursor.getColumnIndex(COLUMN_MODE_ID))

                val modeDevice = ModeDevice(modeDeviceId, modeId, deviceId)
                modeDeviceList.add(modeDevice)
            } while (cursor.moveToNext())
        }
        cursor?.close()
        return modeDeviceList
    }

    /**
     * This method to find all findAllMode
     *
     * @param no data
     * @return ArrayList
     */
    fun findAllMode(): ArrayList<Mode> {
        val modeList: ArrayList<Mode> = ArrayList()
        val selectQuery = "SELECT  * FROM $TABLE_MODE"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var modeId: Int
        var code: String
        var timeOn: String
        var timeOff: String
        var on: String
        var off: String
        var timeRepeat: String
        var repeat: String
        var activeFlag: String
        if (cursor.moveToFirst()) {
            do {
                modeId = cursor.getInt(cursor.getColumnIndex(COLUMN_MODE_ID))
                code = cursor.getString(cursor.getColumnIndex(COLUMN_MODE_CODE))
                timeOn = cursor.getString(cursor.getColumnIndex(COLUMN_MODE_TIME_ON))
                timeOff = cursor.getString(cursor.getColumnIndex(COLUMN_MODE_TIME_OFF))
                on = cursor.getString(cursor.getColumnIndex(COLUMN_MODE_ON))
                off = cursor.getString(cursor.getColumnIndex(COLUMN_MODE_OFF))
                timeRepeat = cursor.getString(cursor.getColumnIndex(COLUMN_MODE_TIME_REPEAT))
                repeat = cursor.getString(cursor.getColumnIndex(COLUMN_MODE_REPEAT))
                activeFlag = cursor.getString(cursor.getColumnIndex(COLUMN_MODE_ACTIVE_FLAG))

                val mode =
                    Mode(modeId, code, timeOn, timeOff, on, off, timeRepeat, repeat, activeFlag)
                modeList.add(mode)
            } while (cursor.moveToNext())
        }
        cursor?.close()
        return modeList
    }


    /**
     * This method to insert data mode
     *
     * @param param
     * @return true/false
     */
    fun addMode(mode: Mode): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_MODE_CODE, mode.code)
        contentValues.put(COLUMN_MODE_TIME_ON, mode.timeOn)
        contentValues.put(COLUMN_MODE_TIME_OFF, mode.timeOff)
        contentValues.put(COLUMN_MODE_ON, mode.on)
        contentValues.put(COLUMN_MODE_OFF, mode.off)
        contentValues.put(COLUMN_MODE_TIME_REPEAT, mode.timeRepeat)
        contentValues.put(COLUMN_MODE_REPEAT, mode.repeat)
        contentValues.put(COLUMN_MODE_ACTIVE_FLAG, mode.activeFlag)
        contentValues.put(COLUMN_CREATEDDATE, mode.createdDate)

        // Inserting Row
        val success = db.insert(TABLE_MODE, null, contentValues)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    /**
     * This method to update data mode
     *
     * @param param
     * @return true/false
     */
    fun updateMode(mode: Mode): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_MODE_TIME_ON, mode.timeOn)
        contentValues.put(COLUMN_MODE_TIME_OFF, mode.timeOff)
        contentValues.put(COLUMN_MODE_ON, mode.on)
        contentValues.put(COLUMN_MODE_OFF, mode.off)
        contentValues.put(COLUMN_MODE_TIME_REPEAT, mode.timeRepeat)
        contentValues.put(COLUMN_MODE_REPEAT, mode.repeat)
        contentValues.put(COLUMN_UPDATED_DATE, mode.updatedDate)

        // Inserting Row
        val success = db.update(TABLE_MODE, contentValues, "$COLUMN_MODE_ID=" + mode.modeId, null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }


    /**
     * This method to insert data mode device
     *
     * @param param
     * @return true/false
     */
    fun addModeDevice(modeDevice: ModeDevice): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_MODE_ID, modeDevice.modeId)
        contentValues.put(COLUMN_DEVICE_ID, modeDevice.deviceId)

        // Inserting Row
        val success = db.insert(TABLE_MODE_DEVICE, null, contentValues)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    /**
     * This method to delete data
     *
     * @param batch_id
     * @return Int
     */

    fun deleteModeDevice(modeId: Int, deviceId: Int): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_MODE_ID, modeId) // EmpModelClass UserId
        contentValues.put(COLUMN_DEVICE_ID, deviceId)
        // Deleting Row
        val success = db.delete(
            TABLE_MODE_DEVICE,
            "$COLUMN_MODE_ID=$modeId and $COLUMN_DEVICE_ID=$deviceId",
            null
        )
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    /**
     * This method to delete data
     *
     * @param batch_id
     * @return Int
     */

    fun deleteModeDeviceByDeviceId(deviceId: Int): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(COLUMN_DEVICE_ID, deviceId)
        // Deleting Row
        val success = db.delete(TABLE_MODE_DEVICE, "$COLUMN_DEVICE_ID=$deviceId", null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    /**
     * This method to find all findAllMode
     *
     * @param no data
     * @return ArrayList
     */
    fun findModeDeviceByModeIdAndDeviceId(mode_Id: Int, device_Id: Int): ModeDevice {
        var modeDevice = ModeDevice()
        val selectQuery =
            "SELECT  * FROM $TABLE_MODE_DEVICE WHERE $COLUMN_MODE_ID = $mode_Id AND $COLUMN_DEVICE_ID=$device_Id"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return modeDevice
        }
        var modeDeviceId: Int
        var modeId: Int
        var deviceId: Int
        if (cursor.moveToFirst()) {
            do {
                modeDeviceId = cursor.getInt(cursor.getColumnIndex(COLUMN_MODE_DEVICE_ID))
                modeId = cursor.getInt(cursor.getColumnIndex(COLUMN_MODE_ID))
                deviceId = cursor.getInt(cursor.getColumnIndex(COLUMN_DEVICE_ID))


                modeDevice = ModeDevice(modeDeviceId, modeId, deviceId)
            } while (cursor.moveToNext())
        }
        cursor?.close()
        return modeDevice
    }

    /**
     * This method to find All Device Detail
     *
     * @return ArrayList
     */
    fun findAllDeviceDetailForMode(device_id: Int, garden_id: Int): DeviceDetail {
        var deviceDetail: DeviceDetail = DeviceDetail()
        val selectQuery =
            "SELECT  * FROM $TABLE_DEVICE_DETAIL WHERE $COLUMN_DEVICE_ID = $device_id AND $COLUMN_GARDEN_ID = $garden_id"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return deviceDetail
        }
        var device_detail_id: Int
        var device_detail_code: String
        var device_detail_code_ss: String
        var device_detail_image: String
        var device_detail_status: String
        var device_id: Int
        var garden_id: Int
        if (cursor.moveToFirst()) {
            do {
                device_detail_id = cursor.getInt(cursor.getColumnIndex(COLUMN_DEVICE_DETAIL_ID))
                device_detail_code = cursor.getString(
                    cursor.getColumnIndex(
                        COLUMN_DEVICE_DETAIL_CODE
                    )
                )
                device_detail_code_ss = cursor.getString(cursor.getColumnIndex(
                    COLUMN_DEVICE_DETAIL_CODE_SS))
                device_detail_image =
                    cursor.getString(cursor.getColumnIndex(COLUMN_DEVICE_DETAIL_IMAGE))
                device_detail_status = cursor.getString(
                    cursor.getColumnIndex(
                        COLUMN_DEVICE_DETAIL_STATUS
                    )
                )
                device_id = cursor.getInt(cursor.getColumnIndex(COLUMN_DEVICE_ID))
                garden_id = cursor.getInt(cursor.getColumnIndex(COLUMN_GARDEN_ID))

                deviceDetail = DeviceDetail(
                    device_detail_id,
                    device_detail_code,
                    device_detail_code_ss,
                    device_detail_image,
                    device_detail_status,
                    device_id,
                    garden_id
                )
            } while (cursor.moveToNext())
        }
        cursor?.close()
        return deviceDetail
    }

    /**
     * This method to find all findAllMode by mode id
     *
     * @param no data
     * @return ArrayList
     */
    fun findAllModeByModeId(modeId: Int): Mode {
        var mode: Mode = Mode()
        val selectQuery = "SELECT  * FROM $TABLE_MODE WHERE $COLUMN_MODE_ID = $modeId"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return mode
        }
        var modeId: Int
        var code: String
        var timeOn: String
        var timeOff: String
        var on: String
        var off: String
        var timeRepeat: String
        var repeat: String
        var activeFlag: String
        if (cursor.moveToFirst()) {
            do {
                modeId = cursor.getInt(cursor.getColumnIndex(COLUMN_MODE_ID))
                code = cursor.getString(cursor.getColumnIndex(COLUMN_MODE_CODE))
                timeOn = cursor.getString(cursor.getColumnIndex(COLUMN_MODE_TIME_ON))
                timeOff = cursor.getString(cursor.getColumnIndex(COLUMN_MODE_TIME_OFF))
                on = cursor.getString(cursor.getColumnIndex(COLUMN_MODE_ON))
                off = cursor.getString(cursor.getColumnIndex(COLUMN_MODE_OFF))
                timeRepeat = cursor.getString(cursor.getColumnIndex(COLUMN_MODE_TIME_REPEAT))
                repeat = cursor.getString(cursor.getColumnIndex(COLUMN_MODE_REPEAT))
                activeFlag = cursor.getString(cursor.getColumnIndex(COLUMN_MODE_ACTIVE_FLAG))

                mode =
                    Mode(modeId, code, timeOn, timeOff, on, off, timeRepeat, repeat, activeFlag)
            } while (cursor.moveToNext())
        }
        cursor?.close()
        return mode
    }

    /**
     * This method to delete data
     *
     * @param mode_id
     * @return Int
     */
    fun deleteMode(modeId: Int): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_MODE_ID, modeId) // EmpModelClass UserId
        // Deleting Row
        val success = db.delete(TABLE_MODE, "mode_id=$modeId", null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    /**
     * This method to delete data mode_device
     *
     * @param mode_id
     * @return Int
     */

    fun deleteModeDeviceByModeId(modeId: Int): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(COLUMN_MODE_ID,modeId)
        // Deleting Row
        val success = db.delete(TABLE_MODE_DEVICE, "$COLUMN_MODE_ID=$modeId", null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }
}