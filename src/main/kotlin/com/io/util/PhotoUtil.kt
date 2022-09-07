package com.io.util

import java.io.File
import java.io.InputStream

fun getPhoto(fileName: String): File{
    return File("/${Constants.PHOTO_FILE}/$fileName")
}

fun savePhoto(fileName: String, stream: InputStream): String{
    val imageUrl = "/${Constants.PHOTO_FILE}/$fileName.png"
    val folder = File("/${Constants.PHOTO_FILE}/")
    folder.mkdirs()
    File(imageUrl)
        .outputStream()
        .buffered()
        .use { buffered ->
            stream.use {
                it.copyTo(buffered)
            }
        }

    return imageUrl
}