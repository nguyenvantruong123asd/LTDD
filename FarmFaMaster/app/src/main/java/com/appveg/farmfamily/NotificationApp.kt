package com.appveg.farmfamily

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

class NotificationApp : Application() {

    companion object {
        const val CHANNEL_ID: String = "channel1"
    }
    override fun onCreate() {
        super.onCreate()

        this.createNotificationChannels()
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel1 = NotificationChannel(
                CHANNEL_ID,
                "Channel 1",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel1.description = "This is channel 1"

            val manager = this.getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel1)

        }
    }
}