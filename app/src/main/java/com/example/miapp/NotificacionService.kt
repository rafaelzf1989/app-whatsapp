package com.example.miapp

import android.app.Notification
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NotificacionService : NotificationListenerService() {

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        if (sbn.packageName != "com.whatsapp" && sbn.packageName != "com.whatsapp.w4b") return

        val notif = sbn.notification
        if ((notif.flags and Notification.FLAG_GROUP_SUMMARY) != 0) return

        val extras = notif.extras
        val nombre = extras.getCharSequence(Notification.EXTRA_TITLE)?.toString() ?: return
        val mensaje = extras.getCharSequence(Notification.EXTRA_TEXT)?.toString() ?: return

        val ahora = Date()
        val fecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(ahora)
        val hora = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(ahora)
        val texto = mensaje.replace("\n", " ")

        val linea = "Fecha: $fecha\nHora: $hora\nUsuario: $nombre\nMensaje: $texto\n---\n"
        openFileOutput("registro.txt", MODE_APPEND).use { it.write(linea.toByteArray()) }
    }
}
