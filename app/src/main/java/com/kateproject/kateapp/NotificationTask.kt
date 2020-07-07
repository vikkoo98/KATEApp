package com.kateproject.kateapp

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build


/*-------------------------------------------------------
---------------------------------------------------------
Ez a class intézi az értesítéseket
---------------------------------------------------------
---------------------------------------------------------*/
class NotificationTask(private val channelID: String, private val description: String, private val context: Context, private val notificationManager: NotificationManager) {
    companion object {
        var ID = 0
    }
    private lateinit var notificationChannel: NotificationChannel
    private lateinit var builder: Notification.Builder
    private var thisID=0

    init {
        ID++
        thisID=ID
    }

    fun sendNotification(title: String, content: String)
    {
        val intent = Intent(context, LauncherActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context,0,intent, PendingIntent.FLAG_UPDATE_CURRENT)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(channelID,description, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor= Color.GREEN
            notificationManager.createNotificationChannel(notificationChannel)

            builder = Notification.Builder(context,channelID)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(R.drawable.kate)
                .setContentIntent(pendingIntent)
        }
        else
        {
            builder = Notification.Builder(context)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setLargeIcon(BitmapFactory.decodeResource(context.resources,R.mipmap.ic_launcher))
                .setContentIntent(pendingIntent)
        }
        notificationManager.notify(thisID,builder.build())
    }

}