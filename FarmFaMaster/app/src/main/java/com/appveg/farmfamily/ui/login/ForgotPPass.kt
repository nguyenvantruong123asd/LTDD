package com.appveg.farmfamily.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.appveg.farmfamily.R
import com.appveg.farmfamily.ui.constant.SendMail
import com.appveg.farmfamily.ui.database.Database
import com.creativityapps.gmailbackgroundlibrary.BackgroundMail
import kotlinx.android.synthetic.main.activity_change_password.*
import kotlinx.android.synthetic.main.forgotpass.*
import java.lang.StringBuilder
import java.security.NoSuchAlgorithmException
import java.text.SimpleDateFormat
import java.util.*

class ForgotPPass : AppCompatActivity() {

    private val activity = this@ForgotPPass

    private lateinit var database: Database
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.forgotpass)

        send_mail_sign_up.setOnClickListener {
            resetPass()
        }
        return_login.setOnClickListener {
            val intent: Intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }


    }

    private fun resetPass() {
        var email = email_sign_up.text.toString().trim()

        var result = checkEmail(email)

        var user = getUser(email)
        var checkEmail = checkEmailEmpty(email)

        var passReset = getRandomPassWord()
        // handling send mail
        var mailTo = email
        var subject = getString(R.string.subject_reset_password)
        var body = getString(R.string.subject_reset_password) + ": " + passReset

        /*format date*/
        val current = Calendar.getInstance().time
        val formatter: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
        val formatted: String = formatter.format(current)

        if (checkEmail) {
            if (result) {
                var user1 = User(user!!.id, md5(passReset), formatted)
                sendTestEmail(mailTo, subject, body)
                database.updateUser(user1)
            } else {
                email_sign_up.error = getString(R.string.error_email)
            }
        }
    }

    //send email
    private fun sendTestEmail(mailTo: String, subject: String, body: String) {
        var user = SendMail()
        BackgroundMail.newBuilder(activity)
            .withUsername(user.username.toString())
            .withPassword(user.password.toString())
            .withMailto(mailTo)
            .withType(BackgroundMail.TYPE_PLAIN)
            .withSubject(subject)
            .withBody(body)
            .withOnSuccessCallback {
                Log.d("SS", "Success")
                Toast.makeText(
                    activity,
                    getString(R.string.send_password_to_email),
                    Toast.LENGTH_LONG
                ).show()
            }
            .withOnFailCallback {
                Log.d("ER", "ERRRRR")
            }
            .send()
    }

    private fun getRandomPassWord(): String {
        var builder: StringBuilder = StringBuilder()
        builder.append("RS")
        val random = Random()
        var ran: Int = random.nextInt(99999 - 10000) + 10000
        builder.append(ran.toString())
        return builder.toString()
    }

    // check email exits
    private fun checkEmail(email: String): Boolean {
        database = Database(activity)
        var result: Boolean = false
        var user = getUser(email)
        if (user!!.email == email) {
            result = true
        }
        return result
    }

    /**
     * This method is to full name
     */
    private fun checkEmailEmpty(check: String): Boolean {
        if (check.isEmpty()) {
            email_sign_up.error = getString(R.string.error_empty_common)
            return false
        }
        return true
    }

    private fun getUser(email : String) : User? {
        database = Database(activity)
        var users = database.getAllUser()
        var user = User()
        if(!users.isNullOrEmpty()){
            for (item in 0 until users.size){
                if(users[item].email == email){
                    user.email = users[item].email
                    user.id = users[item].id
                }
            }
        }
        return user
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