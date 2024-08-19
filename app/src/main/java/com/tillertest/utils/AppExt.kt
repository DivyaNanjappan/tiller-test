package com.tillertest.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

fun Activity.toast(message: CharSequence?, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, message, duration).show()

fun Activity.closeActivity() {
    finish()
}

fun Activity.startAppActivity(c: Class<*>) {
    val intent = Intent(this, c)
    startActivity(intent)
}

fun SwipeRefreshLayout.stopRefreshing() {
    this.isRefreshing = false
}

fun Context.isOnline(): Boolean {
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val netInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
    return netInfo != null && netInfo.isConnected
}

fun String.extractAccessToken(): String {
    val cleanResponseString = this.trim().removePrefix("{").removeSuffix("}")
    val keyValuePairs = cleanResponseString.split(",").map { it.trim() }

    val responseMap = keyValuePairs.associate {
        val (key, value) = it.split("=")
        key to value
    }
    val token = responseMap["access_token"].toString()
    return token
}

fun View.hide() {
    visibility = GONE
}

fun View.show() {
    visibility = VISIBLE
}
