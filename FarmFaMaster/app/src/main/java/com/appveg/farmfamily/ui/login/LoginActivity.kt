package com.appveg.farmfamily.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.appveg.farmfamily.R
import com.appveg.farmfamily.ui.database.Database
import com.appveg.farmfamily.ui.garden.Garden
import kotlinx.android.synthetic.main.login.*
import java.security.NoSuchAlgorithmException

class LoginActivity  : AppCompatActivity() {
    private val activity = this@LoginActivity

    private lateinit var edtUserNameEmail: EditText
    private lateinit var editPass: EditText

    private lateinit var txtforgot: TextView

    private lateinit var btLogin: Button
    private lateinit var btSignup: Button

    private lateinit var database: Database

    var users: ArrayList<User> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        initLogin()
        //edit text
        edtUserNameEmail = findViewById(R.id.editUserName)
        editPass = findViewById(R.id.editPass)

        //button
        txtforgot = findViewById(R.id.txtforgot)
        btLogin = findViewById(R.id.btLogin)
        btSignup = findViewById(R.id.btSignup)



        btLogin.setOnClickListener{
            verifyFromSQLite()
//            val intent: Intent = Intent(this, com.appveg.farmfamily.MainActivity::class.java)
//            startActivity(intent)
        }

        btSignup.setOnClickListener{
            val intent: Intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        txtforgot.setOnClickListener{
            val intent: Intent = Intent(this, ForgotPPass::class.java)
            startActivity(intent)
        }




    }
    /**
     * This method is to validate the input text fields and verify login credentials from SQLite
     */
    private fun verifyFromSQLite(){
        database = Database(activity)
        val userNameEmail = edtUserNameEmail.text.toString().trim()
        val userNamePass = editPass.text.toString().trim()

        checkEmail(userNameEmail)
        checkPassword(userNamePass)

        var result = false
        var users = database.getAllUser()
        for (item in 0 until users.size){
            if(userNameEmail == users[item].email){
                if(3 == users[item].status){
                    result = true
                }
            }
        }
        // nếu mà nó bằng 3 thì nó đã bị khóa
        if(!result){
            if (database!!.checkUser(
                    edtUserNameEmail!!.text.toString().trim { it <= ' ' },
                    md5(editPass!!.text.toString().trim { it <= ' ' }))
            ) {
                val accountsIntent =
                    Intent(activity, com.appveg.farmfamily.MainActivity::class.java)
                // accountsIntent.putExtra("EMAIL", textInputEditTextEmail!!.text.toString().trim { it <= ' ' })
                var checked = remember.isChecked
                if (checked) {
                    updateStatusBeforeLogin()
                    database.updateStatusByUserNameEmail(userNameEmail,2)
                }else{
                    updateStatusBeforeLogin()
                    database.updateStatusByUserNameEmail(userNameEmail,1)
                }
                emptyInputEditText()
                startActivity(accountsIntent)
            } else {
                Toast.makeText(
                    applicationContext, getString(R.string.error_username_pass_invalid),
                    Toast.LENGTH_LONG
                ).show()
            }
        }else{
            Toast.makeText(
                applicationContext, getString(R.string.sign_blocked),
                Toast.LENGTH_LONG
            ).show()
        }


    }
    /**
     * This method is to check email
     */
    private fun checkEmail(check: String) : Boolean {
        if (check.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(check).matches()) {
            edtUserNameEmail.error = getString(R.string.error_empty_common)
            return false
        }
        return true
    }
    /**
     * This method is to password
     */
    private fun updateStatusBeforeLogin(){
        database = Database(activity)
        users = database.getAllUser()
        var user = User()
        for (item in 0 until users.size){
            if(users[item].status == 1){
                user.id = users[item].id
                user.status = 0
                database.updateStatusById(user)
            }
        }
    }
    /**
     * This method is to password
     */
    private fun checkPassword(check: String) : Boolean {
        if (check.isEmpty()) {
            editPass.error = getString(R.string.error_empty_common)
            return false
        }
        return true
    }

    /**
     * This method is to empty all input edit text
     */
    private fun emptyInputEditText() {
        edtUserNameEmail!!.text = null
        editPass!!.text = null
    }

    private fun initLogin(){
        database = Database(activity)
        users = database.getAllUser()
        for (item in 0 until users.size){
            if(users[item].status != 0 && users[item].status != 1 && users[item].status != 3){
                val accountsIntent = Intent(activity, com.appveg.farmfamily.MainActivity::class.java)
                startActivity(accountsIntent)
            }
        }
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