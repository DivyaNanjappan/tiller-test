package com.tillertest.repository

import android.R.attr.password
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tillertest.response.AppResult
import com.tillertest.service.APIAuthService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AppAuthRepositoryImpl(private var dataSource: APIAuthService) : AppAuthRepository {

    override fun getAccessToken(params: HashMap<String, String>): LiveData<AppResult<Any>> {
        val liveData = MutableLiveData<AppResult<Any>>()

        dataSource.fetchAccessToken(params).enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        liveData.value = AppResult.Success(it)
                    } ?: run {
                        liveData.value = AppResult.Error(null, ERROR_BODY)
                    }
                } else {
                    liveData.value =
                        AppResult.Error(null, "$ERROR ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                liveData.value = AppResult.Error(t, "$ERROR_NETWORK ${t.localizedMessage}")
            }
        })

        return liveData
    }
    companion object {
        const val ERROR = "Error:"
        const val ERROR_BODY = "Response body is null"
        const val ERROR_NETWORK = "Network request failed:"
    }
}
