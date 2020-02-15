package com.apero.task.activity.extensions

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.os.Binder
import android.os.Environment
import android.provider.MediaStore

import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
import android.util.Log
import android.view.Gravity
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.apero.task.R

import com.google.gson.Gson
import com.squareup.picasso.Picasso

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by niranjanshah on 30/01/18.
 *
 * https://stackoverflow.com/questions/45582732/let-also-apply-takeif-takeunless-in-kotlin
 */

fun ImageView.loadBase64Image(image: String?) {
    if (!(image.isNullOrEmpty())) {
        val base64Image = image!!.split(",")[1];
        val decodedString = Base64.decode(base64Image, Base64.NO_WRAP)
        val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        setImageBitmap(decodedByte)
    }
    //return decodedByte;

}


fun ImageView.LoadImageFromUrl(image: String?, ctx: Context?) {

    if (!(image.isNullOrEmpty())) {
        if (ctx != null) {

            Picasso.with(ctx)
                    .load(image)
                .placeholder(R.drawable.person)
                .into(this)
        }
    }

}


@Throws(IOException::class)
fun Context.getBitmapFromUri(uri: Uri): Bitmap {
    val parcelFileDescriptor = contentResolver.openFileDescriptor(uri, "r")
    val fileDescriptor = parcelFileDescriptor!!.fileDescriptor
    val image = BitmapFactory.decodeFileDescriptor(fileDescriptor)
    parcelFileDescriptor.close()
    return image
}

fun Context.getUriFromBitmap(bitmap: Bitmap): Uri {
    val bytes = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
    val path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Title", null)
    return Uri.parse(path);
}




fun isJSONValid(test: String): Boolean {
    try {
        JSONObject(test)
    } catch (ex: JSONException) {
        // edited, to include @Arthur's comment
        // e.g. in case JSONArray is valid as well...
        try {
            JSONArray(test)
        } catch (ex1: JSONException) {
            return false
        }

    }

    return true
}

class ObjectWrapperForBinder(val data: Any) : Binder()

fun EditText.onChange(cb: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            cb(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}


fun Context.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).setGravity(Gravity.CENTER, 0, 0);
}


fun Context.toastLong(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_LONG).setGravity(Gravity.CENTER, 0, 0);
}

// convert GSON object to JSON string
fun Context.convertToJson(obj: Any): com.google.gson.JsonObject = Gson().toJsonTree(obj).getAsJsonObject()

// convert to GSON Array to JSON string
fun Context.convertToJsonArray(obj: List<Any>): com.google.gson.JsonArray? = Gson().toJsonTree(obj).asJsonArray

fun String.isNumber(): Boolean = this.matches("[0-9]+".toRegex())


@SuppressLint("MissingPermission")
fun isConnectionAvailable(context: Context): Boolean {
    val connectivity = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (connectivity != null) {
        val info = connectivity.allNetworkInfo
        if (info != null)
            for (i in info.indices)
                if (info[i].state == NetworkInfo.State.CONNECTED) {
                    return true
                }


    }
    return false
}

fun getTStoDate(time: Long): Date {
    var time = time
    val cal = Calendar.getInstance()
    val tz = cal.timeZone//get your local time zone.
    val sdf = SimpleDateFormat("dd/MM")
    sdf.timeZone = tz//set time zone.
    time = time * 1000
    val localTime = sdf.format(Date(time))
    var date = Date()
    try {
        date = sdf.parse(localTime)//get local date

    } catch (e: ParseException) {
        e.printStackTrace()
    }

    return date
}

fun parseDateTime(dateString: String, originalFormat: String, outputFromat: String): String {

    val formatter = SimpleDateFormat(originalFormat, Locale.US)
    var date: Date? = null
    try {
        date = formatter.parse(dateString)

        val dateFormat = SimpleDateFormat(outputFromat, Locale("US"))

        return dateFormat.format(date)

    } catch (e: ParseException) {
        e.printStackTrace()
        return ""
    }

}


