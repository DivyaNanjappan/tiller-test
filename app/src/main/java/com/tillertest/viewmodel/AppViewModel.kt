package com.tillertest.viewmodel

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tillertest.repository.AppAuthRepository
import com.tillertest.repository.AppRepository
import com.tillertest.response.AppResult
import com.tillertest.response.CountryItem
import com.tillertest.response.CountryResponse
import com.tillertest.utils.AppPreferenceManager
import javax.inject.Inject

class AppViewModel : ViewModel(), LifecycleObserver {

    var accessTokenLiveData: LiveData<AppResult<Any>>? = null

    @Inject
    lateinit var appPreferenceManager: AppPreferenceManager

    @Inject
    lateinit var repository: AppRepository

    @Inject
    lateinit var authRepository: AppAuthRepository

    fun fetchAccessToken() {
        val params = HashMap<String, String>()
        params["grant_type"] = "password"
        params["username"] = "interview01"
        params["password"] = "Password1!"
        params["client_id"] = "wxlapp-mobile"
        params["client_secret"] = "secret"
        accessTokenLiveData = authRepository.getAccessToken(params)
    }

    fun getCountries(): LiveData<AppResult<List<CountryItem>>> {
        return repository.getCountries()
    }
}