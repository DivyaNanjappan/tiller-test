package com.tillertest.utils

import android.content.SharedPreferences

abstract class PreferenceCore {

    var prefs: SharedPreferences? = null

    protected abstract fun initPreference()

    fun write(key: String, value: String?) {
        val editor = prefs?.edit()
        editor?.putString(key, value)?.apply()
    }

    fun getString(key: String): String? {
        return if (prefs?.contains(key)!!) {
            prefs?.getString(key, null)
        } else ""
    }
}