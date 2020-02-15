package com.apero.task.activity.utils

import android.content.Context
import android.util.Log
import android.widget.TextView
import com.google.firebase.iid.FirebaseInstanceId
import org.json.JSONObject

class Common {


    companion object{

        fun getToken(): String {
            var token: String = ""
            FirebaseInstanceId.getInstance().instanceId
                    .addOnSuccessListener {
                        if (it.token.isNullOrBlank().not()) {


                            var token1 = it.token




                            Log.d("FCM_TOKEN", it.token.toString())


                        } else {
                            return@addOnSuccessListener
                        }

                    }.addOnFailureListener {
                        // Log.d("FAILED_TOKEN", it.message.toString())
                    }
            return token
        }
    }
    val file_name = "interview.json"
    var jObje: JSONObject? = null

    fun getAsset(context: Context): JSONObject? {
        var jsonString = context.assets.open(file_name).bufferedReader().use {
            it.readText()
        }
        jObje = JSONObject(jsonString)
        return jObje
    }

    fun getByKey(keyName: String): Any? {
        if (jObje != null) {
            return jObje!!.get(keyName)
        }
        return null
    }
    private fun distance(
        lat1: Double,
        lon1: Double,
        lat2: Double,
        lon2: Double
    ): Double {
        val theta = lon1 - lon2
        var dist = (Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + (Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta))))
        dist = Math.acos(dist)
        dist = rad2deg(dist)
        dist = dist * 60 * 1.1515
        return dist
    }

    private fun deg2rad(deg: Double): Double {
        return deg * Math.PI / 180.0
    }

    private fun rad2deg(rad: Double): Double {
        return rad * 180.0 / Math.PI
    }

    fun getToken(): String {
        var token: String = ""
        FirebaseInstanceId.getInstance().instanceId
                .addOnSuccessListener {
                    if (it.token.isNullOrBlank().not()) {


                        var token1 = it.token




                         Log.d("FCM_TOKEN", it.token.toString())


                    } else {
                        return@addOnSuccessListener
                    }

                }.addOnFailureListener {
                    // Log.d("FAILED_TOKEN", it.message.toString())
                }
        return token
    }

}