package com.example.organizd.db.remote

import com.example.organizd.db.entities.Song
import com.example.organizd.other.Constants.SONG_COLLECTION
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await


class MusicDatabase {

    private val firestore = FirebaseFirestore.getInstance()
    private val songCollection  = firestore.collection(SONG_COLLECTION)

    suspend fun getAllSongs(): List<Song> {
        return try{
            songCollection.get().await().toObjects(Song::class.java)
        }catch (e: Exception){
            emptyList()
        }
    }
}