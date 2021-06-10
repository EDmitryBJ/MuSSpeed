package com.nibble.musspeed
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import kotlin.random.Random

class StorageManager {
    fun GetRandomTrackURL(storage:FirebaseStorage,speed:Int, musicType:String):String{
        val tasks = storage.reference.child("$musicType/").child("$musicType$speed/").listAll().result
            return tasks?.items!![Random.nextInt(0, tasks.items.count()-1)].toString()
    }
}