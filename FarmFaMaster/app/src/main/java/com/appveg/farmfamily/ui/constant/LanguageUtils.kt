@file:Suppress("DEPRECATION")

package com.appveg.farmfamily.ui.constant
import java.util.Locale

import android.app.Activity

class LanguageUtils {
    /* ------------------------------------- */
    private var myLocale: Locale? = null
    // Lưu ngôn ngữ đã cài đặt
    private fun saveLocale(lang: String, activity: Activity) {
        val langPref = "Language"
        val prefs = activity.getSharedPreferences(
            "CommonPrefs",
            Activity.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString(langPref, lang)
        editor.apply()
    }

    // Load lại ngôn ngữ đã lưu và thay đổi chúng
    fun loadLocale(activity: Activity): String? {
        val langPref = "Language"
        val prefs = activity.getSharedPreferences(
            "CommonPrefs",
            Activity.MODE_PRIVATE
        )
        val language = prefs.getString(langPref, "")
        changeLang(language!!, activity)
        return language
    }

    // method phục vụ cho việc thay đổi ngôn ngữ.
    fun changeLang(lang: String, activity: Activity) {
        if (lang.equals("", ignoreCase = true))
            return
        myLocale = Locale(lang)
        saveLocale(lang, activity)
        Locale.setDefault(myLocale)
        val config = android.content.res.Configuration()
        config.locale = myLocale
        activity.baseContext.resources.updateConfiguration(
            config,
            activity.baseContext.resources.displayMetrics
        )
    }
}