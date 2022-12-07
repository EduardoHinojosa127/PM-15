package com.miempresa.notificacionesv4

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompatExtras
import androidx.core.app.NotificationManagerCompat
import com.onesignal.OneSignal

class MainActivity : AppCompatActivity() {

    var ID = 1
    val ONESIGNAL_APP_ID = "00338162-61c3-405b-8d9b-3900cad0ee3a"


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONESIGNAL_APP_ID)

    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun crearCanalNotificacion(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            val name: CharSequence = getString(R.string.channel_name)
            val description = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(getString(R.string.channel_id),name, importance)
            channel.description = description
            val notificationManager= getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun notificacionOreo(v: View?){
        crearCanalNotificacion()
        val intent = Intent(this, MainActivity::class.java)
        val imagen = BitmapFactory.decodeResource(getResources(), R.drawable.pajaro)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(this, 0, intent,0)
        val mBuilder =
            NotificationCompat.Builder(this, getString(R.string.channel_id))
                .setSmallIcon(R.drawable.pajaro)
                .setContentTitle("Notificacion Oreo")
                .setLargeIcon(imagen)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setStyle(NotificationCompat.BigTextStyle()
                    .bigText("Hasta Android 7.1 Nougat, el usuario desde los ajustes del sistema sólo podía silenciar todas las notificaciones de una aplicación, no podía discriminar que tipo de notificación recibir, cosa que en Android 8.0 Oreo se soluciona con estos canales de notificación o Para ello lanzó los canales de configuración o categorías de notificación."))


        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.notify(ID++, mBuilder.build())
    }
}