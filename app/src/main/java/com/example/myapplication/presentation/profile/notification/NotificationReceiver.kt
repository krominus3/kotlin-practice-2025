package com.example.myapplication.presentation.profile.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.myapplication.MainActivity
import com.example.myapplication.R

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("NotificationReceiver", "onReceive called!")

        try {
            val className = intent.getStringExtra(EXTRA_CLASS_NAME) ?: "любимой пары"
            val studentName = intent.getStringExtra(EXTRA_STUDENT_NAME) ?: "Студент"

            Log.d("NotificationReceiver", "Showing notification for: $studentName - $className")
            showNotification(context, className, studentName)
        } catch (e: Exception) {
            Log.e("NotificationReceiver", "Error in onReceive: ${e.message}")
            e.printStackTrace()
        }
    }

    private fun showNotification(context: Context, className: String, studentName: String) {
        try {
            createNotificationChannel(context)

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val appIntent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }

            val pendingIntent = PendingIntent.getActivity(
                context,
                0,
                appIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            val notification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("⏰ Напоминание о паре")
                .setContentText("$studentName, скоро начнется $className!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()

            notificationManager.notify(NOTIFICATION_ID, notification)
            Log.d("NotificationReceiver", "Notification shown successfully!")

        } catch (e: Exception) {
            Log.e("NotificationReceiver", "Error showing notification: ${e.message}")
            e.printStackTrace()
        }
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try {
                val channel = NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    description = CHANNEL_DESCRIPTION
                    enableVibration(true)
                }

                val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
                Log.d("NotificationReceiver", "Notification channel created")
            } catch (e: Exception) {
                Log.e("NotificationReceiver", "Error creating channel: ${e.message}")
                e.printStackTrace()
            }
        }
    }

    companion object {
        const val EXTRA_CLASS_NAME = "class_name"
        const val EXTRA_STUDENT_NAME = "student_name"
        const val CHANNEL_ID = "class_reminder_channel"
        const val CHANNEL_NAME = "Напоминания о парах"
        const val CHANNEL_DESCRIPTION = "Уведомления о начале учебных пар"
        const val NOTIFICATION_ID = 1
    }
}