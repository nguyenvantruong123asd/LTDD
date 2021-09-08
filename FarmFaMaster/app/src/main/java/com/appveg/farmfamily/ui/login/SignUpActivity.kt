package com.appveg.farmfamily.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.appveg.farmfamily.R
import com.appveg.farmfamily.ui.database.Database
import java.security.NoSuchAlgorithmException
import java.text.SimpleDateFormat
import java.util.*

class SignUpActivity : AppCompatActivity() {
    private val activity = this@SignUpActivity

    private lateinit var fullName: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var confirmPassword: EditText

    private lateinit var alreadyUser: TextView

    private lateinit var btSign: Button

    private lateinit var radioGenderGroup: RadioGroup


    private lateinit var database: Database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup)

        //edit text
        fullName = findViewById(R.id.fullName)
        email = findViewById(R.id.userEmailId)
        password = findViewById(R.id.password)
        confirmPassword = findViewById(R.id.confirmPassword)

        //button
        alreadyUser = findViewById(R.id.already_user)
        btSign = findViewById(R.id.signUpBtn)

        //radio button
        radioGenderGroup = findViewById(R.id.radioGenderGroup)


        //buotn listener sig up
        btSign.setOnClickListener {
            verifyFromSQLite()
        }

        //button lister already user
        alreadyUser.setOnClickListener {
            val intent: Intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }

    /**
     * This method is to validate the input text fields and verify login credentials from SQLite
     */
    private fun verifyFromSQLite() {
        database = Database(activity)
        val userFullName = fullName.text.toString().trim()
        val userEmail = email.text.toString().trim()
        val userPassword = password.text.toString().trim()
        val userConfirmPass = confirmPassword.text.toString().trim()

        // radio button gender
        val userSelectedID: Int = radioGenderGroup.checkedRadioButtonId
        val userRadioGenderGroup = findViewById<RadioButton>(userSelectedID)
        val userGenderGroup = userRadioGenderGroup.text.toString().trim()

        // validate
        var checkFullName = checkFullName(userFullName)
        var checkEmail = checkEmail(userEmail)
        var checkPassword = checkPassword(userPassword)
        var checkConfirmPass = checkConfirmPassword(userConfirmPass)

        val current = Calendar.getInstance().time
        val formatter: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
        val formatted: String = formatter.format(current)

        var checkMatchPass: Boolean = false
        if (checkPassword && checkConfirmPass) {
            checkMatchPass = checkMatchPass(userPassword, userConfirmPass)


        }

        if (checkFullName && checkEmail && checkConfirmPass && checkPassword && checkMatchPass) {
            // if checked all then add user
            // new user
            Log.d("RE",md5(userPassword))
            var userPassword1 = md5(userPassword)
            var user: User = User(
                null,
                userFullName,
                userEmail,
                userPassword1,
                userGenderGroup,
                0,
                2,
                "admin",
                formatted
            )
            var id = database!!.addUser(user)
            if (id != null) {
                Toast.makeText(
                    applicationContext, getString(R.string.sign_up_success),
                    Toast.LENGTH_SHORT
                ).show()
            }else{
                Toast.makeText(
                    applicationContext, getString(R.string.sign_up_fail),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    /**
     * This method is to email
     */
    private fun checkEmail(check: String): Boolean {
        var users = database.getAllUser()
        if (check.isEmpty()) {
            email.error = getString(R.string.error_empty_common)
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(check).matches()) {
            email.error = getString(R.string.error_invalid_email)
            return false
        } else {
            if (!users.isNullOrEmpty()) {
                for (item in 0 until users.size) {
                    if (check.equals(users[item].email,true)) {
                        email.error = getString(R.string.error_invalid_email_exits)
                        return false
                    }
                }
            }
        }
        return true
    }

    /**
     * This method is to full name
     */
    private fun checkFullName(check: String): Boolean {
        if (check.isEmpty()) {
            fullName.error = getString(R.string.error_empty_common)
            return false
        }
        return true
    }

    /**
     * This method is to password
     */
    private fun checkPassword(check: String): Boolean {
        if (check.isEmpty()) {
            password.error = getString(R.string.error_empty_common)
            return false
        }
        return true
    }

    /**
     * This method is to confirmPassword
     */
    private fun checkConfirmPassword(check: String): Boolean {
        if (check.isEmpty()) {
            confirmPassword.error = getString(R.string.error_empty_common)
            return false
        }
        return true
    }

    /**
     * This method is to checkMatchPass
     */
    private fun checkMatchPass(check1: String, check2: String): Boolean {
        if (check1 != check2) {
            confirmPassword.error = getString(R.string.error_match_password)
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