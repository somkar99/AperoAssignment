package com.apero.task.activity.service

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.apero.task.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.json.JSONObject
import java.util.*

class FirebaseService: FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)

        Log.d("Tag",token.toString())

    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        var title = ""
        var image = ""
        var msg = ""

        if (message?.notification != null) {

           message.notification.let {

               title = it!!.title.toString()
               image = it!!.imageUrl.toString()
               msg = it!!.body.toString()

           }

        }
        sendNotificationToAPi26(message, title, msg)
    }

    private fun getPendingIntents(remoteMessage: RemoteMessage): PendingIntent? {

        val intent = Intent(this, Receiver::class.java)

        var title = ""
        var image = ""
        var msg = ""
        var resultIntent: PendingIntent? = null

        // check if message contains a remoteMessage payload
        if (remoteMessage.notification!=null) {

            remoteMessage.notification.let {

                title = it!!.title.toString()
                image = it!!.imageUrl.toString()
                msg = it!!.body.toString()


            }


                    //intent = Intent(this, LeadDetails::class.java)
                    intent.putExtra("title",title)
                    intent.putExtra("imageurl",image)
                    intent.putExtra("message",msg)


                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    resultIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)


        }
        return PendingIntent.getBroadcast(this, UUID.randomUUID().hashCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT)

    }
    // get notification in >=O
    private fun sendNotificationToAPi26(remoteMessage: RemoteMessage?, msgTitle: String, msgSubTitle: String?) {

        val resultIntent = getPendingIntents(remoteMessage!!)

        var msgTitle =msgTitle





        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val data = remoteMessage.data
            val notificationBuilder = NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.bg)
                    .setColor(ContextCompat.getColor(applicationContext, R.color.colorAccent))
                    .setContentTitle(msgTitle)
                    .setContentText(if (msgSubTitle?.length!! > 0) msgSubTitle else "")
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(resultIntent)

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(0, notificationBuilder.build())
        } else {
            val mHelper = NotificationHelper(this)
            val mBuilder: Notification.Builder
            val data = remoteMessage.data
            val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            mBuilder = mHelper.getICSNotification(msgTitle, if (msgSubTitle?.length!! > 0) msgSubTitle else "", defaultSoundUri)
            mBuilder.setContentIntent(resultIntent)
            mBuilder.setSmallIcon(R.drawable.bg)
            mHelper.manager.notify(Random().nextInt(), mBuilder.build())

        }


    }

}
