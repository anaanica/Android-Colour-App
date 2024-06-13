package hr.algebra.colourapp.handler

import android.content.Context
import android.util.Log
import hr.algebra.colourapp.factory.createGetHttpUrlConnection
import java.io.File
import java.net.HttpURLConnection
import java.nio.file.Files
import java.nio.file.Paths

fun downloadImageAndStore(context: Context, imageUrl: String): String? {

    // http://www.colourlovers.com/img/226C91/100/100/sky.png
    val filename = imageUrl.substring(imageUrl.lastIndexOf(File.separatorChar) + 1)
    val file: File = createFileonz(context, filename)

    try {
        val con: HttpURLConnection = createGetHttpUrlConnection(imageUrl)

        Files.copy(con.inputStream, Paths.get(file.toURI()))

        return file.absolutePath

    } catch (e: Exception) {
        Log.e("IMAGES_HANDLER", e.toString(), e)
    }

    return null
}

fun createFileonz(context: Context, filename: String): File {
    val dir = context.applicationContext.getExternalFilesDir(null)
    val file = File(dir, filename)
    if(file.exists()) file.delete()
    return file
}
