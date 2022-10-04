package com.example.organizd.exoplayer

import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.MediaMetadataCompat.*
import com.example.organizd.db.remote.MusicDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FirebaseMusicSource  @Inject  constructor(
    private val musicDatabase: MusicDatabase
){

    private var songs = emptyList<MediaMetadataCompat>()

    suspend fun fetchMediaData() = withContext(Dispatchers.IO){

        state = State.STATE_INITIALIZING
        val allSongs = musicDatabase.getAllSongs()
        songs = allSongs.map { song ->
            MediaMetadataCompat.Builder()
                .putString(METADATA_KEY_MEDIA_ID, song.mediaId)
                .putString(METADATA_KEY_TITLE, song.title)
                .putString(METADATA_KEY_DISPLAY_TITLE, song.title)
                .putString(METADATA_KEY_DISPLAY_ICON_URI, song.imageUrl)
                .putString(METADATA_KEY_MEDIA_URI, song.songUrl)
                .putString(METADATA_KEY_ARTIST, song.subtitle)
                .putString(METADATA_KEY_ALBUM_ART_URI, song.imageUrl)
                .putString(METADATA_KEY_DISPLAY_SUBTITLE, song.subtitle)
                .putString(METADATA_KEY_DISPLAY_DESCRIPTION, song.subtitle)
                .build()
        }

        state = State.STATE_INITIALIZED
    }

    //lista di lambda functions che prendono boolean
    private val onReadyListeners = mutableListOf<(Boolean) -> Unit>()



    private var state: State = State.STATE_CREATED
        set(value){
            if(value == State.STATE_INITIALIZED || value == State.STATE_ERROR){
              synchronized(onReadyListeners){
                  field = value
                  onReadyListeners.forEach{ listener ->
                      listener(state == State.STATE_INITIALIZED)
                  }
              }
            }else{
                field = value
            }
        }

    fun whenReady(action: (Boolean) -> Unit) : Boolean{
        if(state == State.STATE_CREATED  || state == State.STATE_INITIALIZING){
            onReadyListeners += action
            return false
        } else {
            action(state == State.STATE_INITIALIZED)
            return true
        }
    }

}

enum class  State{
    STATE_CREATED,
    STATE_INITIALIZING,
    STATE_INITIALIZED,
    STATE_ERROR
}