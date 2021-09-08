package com.appveg.farmfamily.ui.login

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.appveg.farmfamily.R
import com.appveg.farmfamily.ui.database.Database
import kotlinx.android.synthetic.main.activity_change_password.*
import java.security.NoSuchAlgorithmException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var database: Database

    private val activity = this@ChangePasswordActivity
    var users: ArrayList<User> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        //buotn listener sig up
        changePassBtn.setOnClickListener {
            updateUser()
        }
        backHome.setOnClickListener {
            val accountsIntent =
                Intent(activity, com.appveg.farmfamily.MainActivity::class.java)
            startActivity(accountsIntent)
        }
    }


    /**
     * the method to display batch
     */
    private fun getListUser(): ArrayList<User> {
        database = Database(activity)
        users = database.getAllUser()
        var listUser = ArrayList<User>()
        if (users.isNullOrEmpty()) {
            Toast.makeText(activity, getString(R.string.list_user_is_empty), Toast.LENGTH_LONG).show()
        }
        for (item in 0 until users.size){
            if(1 == users[item].status || 2 == users[item].status){
                listUser.add(users[item])
            }
        }
        return listUser
    }

    private fun updateUser() {
        database = Database(activity)
        var oldPass = oldPass.text.toString().trim()
        var newPass = newPass.text.toString().trim()
        var confirmNewPass = confirmNewPass.text.toString().trim()

        var users = getListUser()
        var id = users[0].id

        var listUser = getListUser()
        var checkOldPass = checkOldPass(oldPass,listUser)
        var checkOldPassEmpty = checkOldPass(oldPass)
        var checkNewPassEmpty = checkNewPass(oldPass)
        var checkConfirmNewPassEmpty = checkConfirmNewPass(oldPass)
        var checkPassMatch = checkPassMatch(newPass,confirmNewPass)
        /*format date*/
        val current = Calendar.getInstance().time
        val formatter: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
        val formatted: String = formatter.format(current)

        var newPassMD5 = md5(newPass)
        if(checkOldPassEmpty && checkNewPassEmpty && checkConfirmNewPassEmpty && checkPassMatch){
            if(checkOldPass){
                var user = User(id,newPassMD5,formatted)
                database.updateUser(user)
                Toast.makeText(this,getString(R.string.update_data_success_vi),Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this,getString(R.string.error_old_password), Toast.LENGTH_LONG).show()
            }
        }else{
            Toast.makeText(this,getString(R.string.update_data_fail_vi), Toast.LENGTH_LONG).show()
        }

    }

    private fun checkOldPass(oldPass: String, listUser: ArrayList<User>): Boolean {
        var result : Boolean = false
        if(!listUser.isNullOrEmpty()){
            for (item in 0 until listUser.size){
                if(listUser[item].status == 1 || listUser[item].status == 2){
                    if(md5(oldPass) == listUser[item].password){
                        result = true
                    }
                }
            }
        }
        return result
    }

    /**
     * This method is to full name
     */
    private fun checkOldPass(check: String): Boolean {
        if (check.isEmpty()) {
            oldPass.error = getString(R.string.error_empty_common)
            return false
        }
        return true
    }
    /**
     * This method is to full name
     */
    private fun checkNewPass(check: String): Boolean {
        if (check.isEmpty()) {
            newPass.error = getString(R.string.error_empty_common)
            return false
        }
        return true
    }

    /**
     * This method is to full name
     */
    private fun checkConfirmNewPass(check: String): Boolean {
        if (check.isEmpty()) {
            confirmNewPass.error = getString(R.string.error_empty_common)
            return false
        }
        return true
    }
    /**
     * This method is to full name
     */
    private fun checkPassMatch(check: String,check1: String): Boolean {
        if (check != check1) {
            newPass.error = getString(R.string.error_match_password_1)
            confirmNewPass.error = getString(R.string.error_match_password_1)
            return false
        }
        return true
    }

    private fun md5(s: String): String {
        try {
            // Create MD5 Hash
            val digest = java.security.MessageDigest.getInstance("MD5")
            digest.update(s.toByteArray())
            val messageDigest = digest.digest()

            // Create Hex String
            val hexString = StringBuffer()
            for (i in messageDigest.indices)
                hexString.append(Integer.toHexString(0xFF and messageDigest[i].toInt()))

            return hexString.toString()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        return ""
    }
}
