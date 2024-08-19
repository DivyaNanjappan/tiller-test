package com.tillertest.repository

import androidx.lifecycle.LiveData
import com.tillertest.response.AppResult
import com.tillertest.response.CountryItem

interface AppRepository {

    fun getCountries(): LiveData<AppResult<List<CountryItem>>>
}
