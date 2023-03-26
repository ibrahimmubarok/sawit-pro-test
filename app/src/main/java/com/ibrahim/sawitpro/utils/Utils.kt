package com.ibrahim.sawitpro.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object Utils {

    fun compressedImage(
        context: Context,
        file: File,
        uri: Uri,
        format: Bitmap.CompressFormat,
        quality: Int
    ) {
        val imageStream: InputStream? = context.contentResolver.openInputStream(uri)
        val byteArrayOutputStream = ByteArrayOutputStream()
        val selectedImageBitMap = BitmapFactory.decodeStream(imageStream)
        selectedImageBitMap.compress(format, quality, byteArrayOutputStream)

        val fileOutputStream = FileOutputStream(file)
        fileOutputStream.write(byteArrayOutputStream.toByteArray())
        fileOutputStream.flush()
        fileOutputStream.close()
    }

    fun getCurrentDate(): String {
        val time = Calendar.getInstance().time
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        return formatter.format(time)
    }
}