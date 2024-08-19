package com.tillertest.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tillertest.response.AppResult
import com.tillertest.response.CountryItem
import com.tillertest.service.APIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AppRepositoryImpl(private var dataSource: APIService) : AppRepository {

    override fun getCountries(): LiveData<AppResult<List<CountryItem>>> {
        val liveData = MutableLiveData<AppResult<List<CountryItem>>>()

        dataSource.fetchCountries().enqueue(object : Callback<List<CountryItem>> {
            override fun onResponse(
                call: Call<List<CountryItem>>,
                response: Response<List<CountryItem>>
            ) {
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

            override fun onFailure(call: Call<List<CountryItem>>, t: Throwable) {
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
