package com.v4vic.bibleapp.notification

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        val title = remoteMessage.notification?.title ?: "BibleApp"
        val message = remoteMessage.notification?.body ?: "Новое сообщение"

        NotificationHelper.showNotification(
            context = applicationContext,
            title = title,
            message = message
        )
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM", "FCM Token: $token")
        // Тут можно отправить токен на сервер, если надо
    }
}