package com.kateproject.kateapp

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.IBinder



class NotificationTask(private val context: Context, private val notificationManager: NotificationManager) {
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channelID = "com.kateproject.kateapp"
    private val description = "Általános értesítések"

    fun sendNotification(title: String, content: String)
    {
        val intent = Intent(context, LauncherActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context,0,intent, PendingIntent.FLAG_UPDATE_CURRENT)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(channelID,description,NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor= Color.GREEN
            notificationManager.createNotificationChannel(notificationChannel)

            builder = Notification.Builder(context,channelID)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setLargeIcon(BitmapFactory.decodeResource(context.resources,R.mipmap.ic_launcher))
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
        notificationManager.notify(1,builder.build())
    }

}
/*
class BackgroundService: Service()
{
    private lateinit var comm:Communicator
    private var id: Int = 0

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationTask = NotificationTask(this,notificationManager)
        id = intent.getIntExtra("ID",0)
        comm = Communicator()
        val articles = comm.LoadArticles(1,forceLoad = true)

        if (id == articles[0].id)
        {
            id=articles[0].id
        }
        notificationTask.sendNotification(id.toString(), articles[0].title.rendered)
        println("im done here")
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {

        return null
    }
}
*/