package com.tillertest.repository

import androidx.lifecycle.LiveData
import com.tillertest.response.AppResult

interface AppAuthRepository {

    fun getAccessToken(params: HashMap<String, String>): LiveData<AppResult<Any>>
}
