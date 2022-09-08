package com.io.util

import io.ktor.http.content.*
import java.io.File
import java.io.InputStream
import java.util.*

fun PartData.FileItem.save(path: String, name: String = UUID.randomUUID().toString()): String {
    val fileBytes = streamProvider().readBytes()
    val fileExtension = originalFileName?.takeLastWhile { it != '.' }
    val folderPath = "./src/main/resources/$path"
    val fileName = "${name}.$fileExtension"
    val folder = File(folderPath)
    folder.mkdirs()
    File("$folderPath/$fileName").writeBytes(fileBytes)
    return "$path/$fileName"
}

fun getPhoto(path: String, fileName: String): File{
    return File("./src/main/resources/$path/$fileName")
}