package com.tillertest.service

import android.content.Context
import android.util.Log
import com.tiller.test.R
import com.tillertest.BaseApp
import com.tillertest.exception.APIException
import com.tillertest.utils.AppPreferenceManager
import com.tillertest.utils.isOnline
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class ConnectivityInterceptor(private val baseApp: BaseApp) : Interceptor {

    var context: Context? = null

    init {
        baseApp.appComponent.inject(this)
        context = baseApp.applicationContext
    }

    @Inject
    lateinit var appPreferenceManager: AppPreferenceManager

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!baseApp.applicationContext.isOnline()) {
            throw APIException(context?.getString(R.string.network_error))
        }

        val originalRequest = chain.request().newBuilder()
        if (::appPreferenceManager.isInitialized) {
            appPreferenceManager.accessToken?.let {
                Log.e("Token", it)
                originalRequest.addHeader("Authorization", it)
            }
        }

        val response = chain.proceed(originalRequest.build())

        Log.d("Response", response.toString())

        when (response.code) {
            200 -> return response

            401 -> tokenExpiredException()

            else -> throw APIException(
                context?.getString(R.string.error_server)
                    ?: ""
            )
        }

        return response
    }

    private fun tokenExpiredException() {
        throw APIException("Unauthorized")
    }
}
