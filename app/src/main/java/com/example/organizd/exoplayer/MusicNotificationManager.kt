package com.example.organizd.exoplayer

import android.content.Context
import android.support.v4.media.session.MediaSessionCompat
import com.google.android.exoplayer2.ui.PlayerNotificationManager

class MusicNotificationManager (
    private val context: Context,
    sessionToken: MediaSessionCompat.Token,
    notificationListener: PlayerNotificationManager.NotificationListener,
    private val newSongCallback : () -> Unit
        ) {


}