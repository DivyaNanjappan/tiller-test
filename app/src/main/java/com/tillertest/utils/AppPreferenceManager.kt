package com.tillertest.utils

import android.content.Context
import com.securepreferences.SecurePreferences
import com.tiller.test.R
import com.tillertest.BaseApp

class AppPreferenceManager : PreferenceCore {
    private var context: Context? = null

    constructor(baseApp: BaseApp) {
        baseApp.appComponent.inject(this)
        context = baseApp.applicationContext
        initPreference()
    }

    constructor(context: Context) {
        this.context = context
        initPreference()
    }

    override fun initPreference() {
        prefs = SecurePreferences(
            context,
            context?.getString(R.string.preference_password),
            context?.getString(R.string.preference_name)
        )
    }

    var accessToken: String?
        get() = "Bearer ${getString(KEY_USER_TOKEN)}"
        set(accessToken) = write(KEY_USER_TOKEN, accessToken)


    companion object {
        const val KEY_USER_TOKEN = "user.accessToken"
    }
}
